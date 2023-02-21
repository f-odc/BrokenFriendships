package model.game.logic;

import model.board.fields.IField;
import model.board.objects.Dice;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.board.objects.Mystery;
import model.enums.Phase;
import model.global;
import model.player.Player;
import org.newdawn.slick.Animation;
import java.util.Random;

public class GameLogic {

    private static IField movableField;

    private static IGameObject selectedGameObject;

    private static int getOutOfBaseTries = 0;

    private static boolean isNextPlayer = false;

    private static Dice dice = global.BOARD.getDice();

    static int numOfMysteryFields = 0; // current number of mystery fields

    static final int MAX_MYSTERY_FIELDS = 12; // max number of mystery fields


    /**
     * function controlling the logic of going into the next turn and choosing the next player
     */
    public static void nextPlayer() {
        // check if player can throw again

        // check if player has won
        if (global.players[global.activePlayer].hasWon()) {
            global.phase = Phase.END_OF_GAME;
            // TODO: end game
            return;
        }
        // reset isNextPlayer flag
        isNextPlayer = false;
        // reset dice animation
        dice.resetAnimation();
        // reset tries
        getOutOfBaseTries = 0;
        // increase active player num
        global.activePlayer = (global.activePlayer + 1) % 4;
        // set position of dice to new player
        dice.setPosition(global.activePlayer);
        // set phase to dice
        global.phase = Phase.DICE_PHASE;

        // increase turn
        if (global.activePlayer == 0)
            global.turn++;
        // spawn new mystery item in once every 2 turns starting on turn 1
        if ((global.turn == 1 || global.turn % 2 == 0) && global.turn != 0 && global.activePlayer == 0) {
            spawnMystery();
        }

        // reset/hide animation
        global.mysteryAnimation = new Animation();
    }

    /**
     * Spawn new mystery item on valid random field
     */
    private static void spawnMystery(){
        // spawn if not max number is reached
        if (numOfMysteryFields <= MAX_MYSTERY_FIELDS){
            int numOfPlayfields = (global.NUM_OF_FIELDS -1)*4;
            IField randomField;
            do{
                // get a random play field
                int randomFieldIndex = new Random().nextInt(numOfPlayfields);
                randomField = global.BOARD.getPlayField(randomFieldIndex);
            }while(randomField.isOccupied() || randomField.isPlayerStartField()); // if random field occupied or start field, redo:

            // init mystery item
            IGameObject object = new Mystery(numOfMysteryFields, global.activePlayer, randomField);
            // display mystery on selected field
            randomField.setGameObject(object);
        }
    }


    /**
     * Dice is clicked during DICE_PHASE
     * Manage player action depending on thrown dice value
     */
    public static void executeDicePhase(){
        // check if next player -> switch position (for animation reason)
        if(isNextPlayer){
            nextPlayer();
            return;
        }
        // normal behavior: dice position is not switching
        // throw dice
        dice.throwDice();

        // get dice value
        int diceValue = dice.getValue();
        // get current player
        Player currentPlayer = global.players[global.activePlayer];
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
        else if (!currentPlayer.couldMove(diceValue)){
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
     * Board fields is clicked during SELECT_FIGURE_PHASE
     * Check if figure on the field and calculate all movable fields
     * @param field
     */
    public static void executeSelectFigurePhase(IField field){
        // check figure is contained on field on the current player is owner
        Figure figure = field.getCurrentFigure();
        if (figure != null && figure.getOwnerID() == global.activePlayer){
            // store selected figure
            selectedGameObject = figure;
            // check which fields can be reached
            movableField = MoveLogic.getMovableField(field);
            if (movableField != null){
                // highlight field
                movableField.highlight();
                // change to next phase
                global.phase = Phase.SELECT_MOVEMENT_PHASE;
            } // else, do nothing
        }
    }

    /**
     * Board field is clicked during SELECT_MOVEMENT_PHASE
     * Move figure to selected field if possible
     * @param field clicked field
     */
    public static void executeSelectMovementPhase(IField field){
        // check if clicked field is reachable
        if (movableField.equals(field)) {
            // move figure to field
            selectedGameObject.moveTo(field);
            movableField.deHighlight();

            // check if dice throw = 6 -> throw one more
            if (dice.getValue() == 6) {
                global.phase = Phase.DICE_PHASE;
                return;
            }
            // TODO: change
            // check if no other phase is currently running
            if (global.phase == Phase.SELECT_MOVEMENT_PHASE) {
                nextPlayer();
            }
            return;
        }
        // if clicked another field
        // return to selection phase and reset values if move is not possible
        movableField.deHighlight();
        global.phase = Phase.SELECT_FIGURE_PHASE;
    }

    public static void executeInitSpecialsPhase(int mysteryNr, IGameObject sourceGameObject){
        System.out.println("Execute Init Specials Phase");

        // create new game object depending on mysteryNr
        String className = global.specialsMap.get(mysteryNr).get(0);
        IGameObject specialsObject;
        try {
            specialsObject = (IGameObject) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(specialsObject);



        // if no field interaction is needed -> perform action and go to next player
        if (!specialsObject.requiresFieldInteraction()) {
            // perform special action
            specialsObject.activate(sourceGameObject);
            // next player
            nextPlayer();
        }else {
            // highlight and store fields depending on special
        }

    }

    public static void executeSpecialsPlacement(IField targetField){
        // check if clicked field can be a special field

        // check if field is game field and not occupied

        System.out.println("Place Specials Phase");

        // move gameobject to targetfield

        // nextPlayer
        nextPlayer();
    }

}
