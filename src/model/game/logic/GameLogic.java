package model.game.logic;

import eea.engine.action.basicactions.ChangeStateAction;
import model.board.fields.IField;
import model.board.objects.Dice;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.board.objects.Mystery;
import model.board.objects.specials.*;
import model.enums.FieldType;
import model.enums.Phase;
import model.global;
import model.player.Player;
import org.newdawn.slick.Animation;
import ui.BrokenFriendships;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private static ArrayList<IField> movableFields = new ArrayList<>();

    private static IGameObject selectedGameObject;

    public static IGameObject activeSpecial = null;

    public static int getOutOfBaseTries = 0;

    public static boolean isNextPlayer = false;

    public static Dice dice = global.BOARD.getDice();

    public static int numOfMysteryFields = 0; // current number of mystery fields

    static final int MAX_MYSTERY_FIELDS = 12; // max number of mystery fields

    public static boolean spawnMystery = true;

    public static int debugDiceThrow;


    /**
     * controls the logic of going into the next turn and choosing the next player
     */
    public static void nextPlayer() {
        nextTurn();

        // check if player has won
        if (global.players[global.activePlayer].hasWon()) {
            global.phase = Phase.END_OF_GAME;
            // TODO: end game
            return;
        }
        // reset isNextPlayer flag
        isNextPlayer = false;
        // reset tries
        getOutOfBaseTries = 0;
        // increase active player num
        global.activePlayer = (global.activePlayer + 1) % 4;
        // set position of dice to new player
        dice.setPosition(global.activePlayer);

        // increase round
        if (global.activePlayer == 0)
            global.rounds++;
        // spawn new mystery item in once every 2 turns starting on turn 1
        if ((global.rounds == 1 || global.rounds % 2 == 0) && global.rounds != 0 && global.activePlayer == 0 && spawnMystery) {
            spawnMystery(-1);
        }

        // check if player is currently sleeping
        BedSpecial usedBed = global.players[global.activePlayer].getActiveBed();
        if (usedBed != null) {
            global.players[global.activePlayer].setBedSpecial(null);
            global.entityManager.removeEntity(global.GAMEPLAY_STATE, usedBed.getEntity());
            usedBed.reset();
            nextPlayer();
        }
    }

    /**
     * Resets values to allow a new dice throw to begin
     */
    private static void nextTurn() {

        // reset/hide animation if animation is over
        if (global.phase == Phase.SELECT_MOVEMENT_PHASE) {
            global.mysteryAnimation = new Animation();
        }
        // reset dice animation
        dice.resetAnimation();
        // set phase to dice
        global.phase = Phase.DICE_PHASE;
        // reset active special
        activeSpecial = null;
    }

    /**
     * Spawn new mystery item on valid random field
     *
     * @param index -1 if no predetermined index should be used
     */
    public static void spawnMystery(int index) {
        // spawn if not max number is reached
        if (numOfMysteryFields < MAX_MYSTERY_FIELDS) {
            GameLogic.numOfMysteryFields++;
            ArrayList<IField> emptyGameFields = global.BOARD.getEmptyGameFields();
            // get a random play field
            int randomFieldIndex = new Random().nextInt(emptyGameFields.size());
            IField randomField = index == -1 ? emptyGameFields.get(randomFieldIndex) : global.BOARD.getGameField(index);

            // init and display mystery item
            IGameObject object = new Mystery(numOfMysteryFields, global.activePlayer, randomField);
            // set mystery on selected field
            randomField.setGameObject(object);
        }
    }


    /**
     * Dice is clicked during DICE_PHASE
     * Manage player action depending on thrown dice value
     *
     * @param diceThrow -1 if diceThrow should not be predetermined (for tests)
     */
    public static void executeDicePhase(int diceThrow, int activePlayer) {
        // check if next player -> switch position (for animation reason)
        if (isNextPlayer) {
            getOutOfBaseTries = 0;
            nextPlayer();
            return;
        }
        // normal behavior: dice position is not switching
        // throw dice
        dice.throwDice();

        // get dice value
        int diceValue = diceThrow != -1 ? diceThrow : dice.getValue();
        // get current player
        Player currentPlayer = activePlayer != -1 ? global.players[activePlayer] : global.players[global.activePlayer];
        // check if player is allowed to throw 3 times on the first throw, implies only 6 is needed
        boolean inHomeAndAllowedThreeTimes = !currentPlayer.couldMove(1);

        // case: player can throw 3 times, no 6 and still has tries to get out
        if (inHomeAndAllowedThreeTimes && diceValue != 6 && getOutOfBaseTries < 2) {
            getOutOfBaseTries++;
        }
        // case: out of tries to get a figure out of the home and no 6
        else if (inHomeAndAllowedThreeTimes && diceValue != 6 && getOutOfBaseTries == 2) {
            // set next arrow frame -> dice throw is the last
            dice.animateNextArrow();
            isNextPlayer = true;
        }
        // case: player is not allowed to throw 3 times but cannot move with thrown value
        else if (!currentPlayer.couldMove(diceValue)) {
            // set next arrow frame -> player can not move
            dice.animateNextArrow();
            isNextPlayer = true;
        }
        // case: player can move, change phase
        else {
            getOutOfBaseTries = 0;
            global.phase = Phase.SELECT_FIGURE_PHASE;
        }
    }

    /**
     * Board field is clicked during SELECT_FIGURE_PHASE
     * Check if figure on the field and calculate all movable fields
     *
     * @param diceThrow -1 if diceThrow should not be predetermined (for tests)
     * @param field     clicked board field
     */
    public static void executeSelectFigurePhase(IField field, int diceThrow) {
        // empty movable fields
        movableFields = new ArrayList<IField>();
        // check figure is contained on field on the current player is owner
        Figure figure = field.getCurrentFigure();
        // use only own figure
        if (figure != null && figure.getOwnerID() == global.activePlayer) {
            // store selected figure
            selectedGameObject = figure;
            // check which fields can be reached
            movableFields = diceThrow == -1 ? MoveLogic.getMovableField(field) : MoveLogic.getMovableField(field, diceThrow);
            if (!movableFields.isEmpty()) {
                // highlight fields
                highlightMovableFields();
                // change to next phase
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
            }
        }
    }

    /**
     * Board field is clicked during MYSTERY_SELECTION_PHASE
     * Calculate possible fields to place active special
     *
     * @param field clicked board field
     */
    public static void executeMysterySelectionPhase(IField field) {
        // empty movable fields
        movableFields = new ArrayList<IField>();
        // check if clicked field is suited
        Figure figure = field.getCurrentFigure();
        if (figure == null || field.getType() == FieldType.BASE || field.getType() == FieldType.HOME) {
            return;
        }
        // check if special active
        if (activeSpecial != null) {
            // MoveFourSpecial
            if (activeSpecial instanceof MoveFourSpecial) {
                selectedGameObject = figure;
                // movement fields +/- 4
                movableFields.addAll(MoveLogic.getMovableField(field, -4));
                movableFields.addAll(MoveLogic.getMovableField(field, 4));
                highlightMovableFields();
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
            }
            // SwitchSpecial
            if (activeSpecial instanceof SwitchSpecial) {
                selectedGameObject = figure;
                // movable fields all figures
                movableFields.addAll(global.BOARD.getOccupiedGameFields(field));
                highlightMovableFields();
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
            }
        }
    }

    /**
     * Board field is clicked during SELECT_MOVEMENT_PHASE
     * Move figure to selected field if possible
     *
     * @param diceThrow -1 if diceThrow should not be predetermined (for tests)
     * @param field     clicked field
     */
    public static void executeSelectMovementPhase(IField field, int diceThrow) {
        // check if clicked field is reachable
        if (movableFields.contains(field)) {
            // move figure to field
            selectedGameObject.moveTo(field, activeSpecial instanceof SwitchSpecial);
            unHighlightMovableFields();

            // check if dice throw = 6 -> throw one more
            int diceValue = diceThrow == -1 ? dice.getValue() : diceThrow;
            if (diceValue == 6) {
                nextTurn();
                return;
            }
            // check if no other phase is currently running
            if (global.phase == Phase.SELECT_MOVEMENT_PHASE) {
                global.mysteryAnimation = new Animation();
                nextPlayer();
            }

            return;
        }
        // if clicked another field

        // bed and bomb special do not allow other behavior
        if (activeSpecial instanceof BedSpecial || activeSpecial instanceof BombSpecial || activeSpecial instanceof PlusToThreeSpecial) {
            return;
        }
        // return to selection phase and reset values if move is not possible
        unHighlightMovableFields();
        // check which phase is next
        if (activeSpecial != null) {
            global.phase = Phase.MYSTERY_SELECTION_PHASE;
        } else {
            // for move four
            global.phase = Phase.SELECT_FIGURE_PHASE;
        }
    }

    /**
     * Performed after the mystery animation is finished during the MYSTERY_SELECTION_PHASE
     * Init the selected special
     *
     * @param specialNr        indicate the number of the selected special
     * @param sourceGameObject figure which activate the mystery selection
     */
    public static void executeInitSpecialsPhase(int specialNr, IGameObject sourceGameObject) {
        // create new game object depending on mysteryNr
        String className = global.specialsMap.get(specialNr).get(0);
        IGameObject specialsObject;
        try {
            specialsObject = (IGameObject) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // if no field interaction is needed -> perform action and go to next player
        if (!specialsObject.requiresFieldInteraction()) {
            // perform special action
            specialsObject.activate(sourceGameObject);
            // hide mystery selection
            global.mysteryAnimation = new Animation();
        } else {
            // save active special object
            activeSpecial = specialsObject;
            // highlight and store fields depending on special
            if (specialsObject instanceof BedSpecial || specialsObject instanceof BombSpecial) {
                // save currentObject
                selectedGameObject = specialsObject;
                // set phase
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
                // get movable fields
                movableFields = global.BOARD.getEmptyGameFields();
                highlightMovableFields();
                return;
            }
            if (specialsObject instanceof MoveFourSpecial) {
                global.phase = Phase.MYSTERY_SELECTION_PHASE;
            }
            if (specialsObject instanceof SwitchSpecial) {
                List<IField> occupied = global.BOARD.getOccupiedGameFields();
                int diceValue = BrokenFriendships.debug ? debugDiceThrow : global.BOARD.getDice().getValue();
                //no figure to switch and no 6 thrown
                if (occupied.size() < 2 && diceValue != 6)
                    nextPlayer();
                    //no figure to switch and  6 thrown
                else if (occupied.size() < 2)
                    nextTurn();
                else global.phase = Phase.MYSTERY_SELECTION_PHASE;
            }
            if (specialsObject instanceof PlusToThreeSpecial) {
                // empty movable fields
                movableFields = new ArrayList<>();
                // get movable fields +1 / +2 / +3
                movableFields.addAll(MoveLogic.getMovableField(sourceGameObject.getCurrentField(), 1));
                movableFields.addAll(MoveLogic.getMovableField(sourceGameObject.getCurrentField(), 2));
                movableFields.addAll(MoveLogic.getMovableField(sourceGameObject.getCurrentField(), 3));

                int diceValue = BrokenFriendships.debug ? debugDiceThrow : global.BOARD.getDice().getValue();
                // if no figure can be moved
                if(movableFields.isEmpty()){
                    if(diceValue != 6){
                        nextTurn();
                        return;
                    }
                    nextPlayer();
                    return;
                }
                // highlight fields
                highlightMovableFields();
                // set phase
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
            }
        }
    }

    /**
     * Highlight all movable fields
     */
    private static void highlightMovableFields() {
        for (IField field : movableFields) {
            if (field != null) {
                field.highlight();
            }
        }
    }

    /**
     * Unhighlight all movable fields
     */
    private static void unHighlightMovableFields() {
        for (IField field : movableFields) {
            if (field != null) {
                field.unHighlight();
            }
        }
    }

    public static int getDebugDiceThrow() {
        return debugDiceThrow;
    }

    public static void setDebugDiceThrow(int diceThrow) {
        debugDiceThrow = diceThrow;
    }

}
