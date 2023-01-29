package model.game;

import model.boardLogic.fields.IField;
import model.boardLogic.objects.Figure;
import model.enums.Field;
import model.enums.Phase;
import model.global;
import org.newdawn.slick.SlickException;

public class GameLogic {
    /*
      - Color ist dran
      - Würfel wird bewegt
      - nichts anKlickbar außer Würfel
      - wird gewürfelt
      - Figur klicken
      - Anzeigen welche Bewegungen möglich sind
      - Feld Klicken um dahin zu bewegen
      - (Glücksrad)
      */

    private static IField movableField;

    private static int getOutOfBaseTries = 0;


    /**
     * Function to set up the logic of the Board and the players
     */
    public static void setup() {
        // Create Player
        for (int i = 0; i < 4; i++) {
            global.players[i] = new Player(i);
        }

        //set first players turn
        global.activePlayer = 0;
        global.turn = 0;
        global.phase = Phase.DICE_PHASE;
        global.BOARD.getDice().setPosition(global.activePlayer);
    }

    /**
     * function controlling the logic of going into the next turn and choosing the next player
     */
    public static void nextPlayer() {
        //win condition
        boolean win = true;
        for (int i = 0; i < 4; i++) {
            IField figureField = global.players[global.activePlayer].getFigure(i).getCurrentField();
            if (!global.BOARD.getBaseFields(global.activePlayer).contains(figureField)) {
                win = false;
                break;
            }
        }
        if (win) {
            global.phase = Phase.END_OF_GAME;
            return;
        }

        getOutOfBaseTries = 0;
        global.activePlayer = (global.activePlayer + 1) % 4;
        global.BOARD.getDice().setPosition(global.activePlayer);
        global.phase = Phase.DICE_PHASE;
        if (global.activePlayer == 0)
            global.turn++;
        //qFields are spawned in once every 2 turns starting on turn 1
        if ((global.turn == 1 || global.turn % 2 == 0) && global.turn != 0 && global.activePlayer == 0)
            BoardLogic.placeQuestionmarkField();
    }

    /**
     * function controlling the logic of the movement phase
     *
     * @return true if the movement was executed successfully
     */
    public static boolean enterMovementPhase() {
        movableField = MoveLogic.getMovableField(MoveLogic.getSelectedField());
        boolean isMovementPossible = movableField != null;
        if (isMovementPossible) {
            //show field to which movement is possible
            //TODO change way of highlighting
            movableField.getBaseEntity().setScale(0.5f);
            //change to next phase
            global.phase = Phase.SELECT_MOVEMENT_PHASE;
        }
        return isMovementPossible;
    }

    /**
     * function controlling the logic of the dice phase
     */
    public static void executeDicePhase() {
        if (global.phase == Phase.DICE_PHASE) {
            try {
                global.BOARD.getDice().throwDice();

                boolean allInHomeOrBase = true;
                Player currentPlayer = global.players[global.activePlayer];
                //check if all figures are in base or home
                for (int i = 0; i < 4; i++) {
                    Figure figure = currentPlayer.getFigure(i);
                    if (MoveLogic.getMovableField(figure.getCurrentField()) != null) {
                        allInHomeOrBase = false;
                        break;
                    }
                }
                //case: all figures in home/base, no 6 and still has tries to get out
                if (allInHomeOrBase && global.BOARD.getDice().getValue() != 6 && getOutOfBaseTries < 2) {
                    getOutOfBaseTries++;
                    return;
                }
                //case: out of tries to get a figure out of the home and no 6
                if (allInHomeOrBase && global.BOARD.getDice().getValue() != 6 && getOutOfBaseTries == 2) {
                    nextPlayer();
                    return;
                }
                //case normal move or all figures in base/home and a 6
                getOutOfBaseTries = 0;
                global.phase = Phase.SELECT_FIGURE_PHASE;
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * function deciding which phase to enter when a field is selected
     *
     * @param field the selected field
     */
    public static void executePhase(IField field) {
        //selection phase
        if (global.phase == Phase.SELECT_FIGURE_PHASE && field.getCurrentObject() != null) {
            if (global.activePlayer == field.getCurrentObject().getOwnerID()) {
                MoveLogic.selectField(field);
                if (!enterMovementPhase())
                    MoveLogic.deselectField();
            }
        }
        //movement phase
        else if (global.phase == Phase.SELECT_MOVEMENT_PHASE) {
            //TODO check if movable
            //  TODO move and change phase if it is
            //TODO if not
            //  deselect and change back to selection phase
            if (movableField.equals(field)) {
                if (MoveLogic.move()) {
                    //reset values if move is possible
                    emptyMovableFields();
                    MoveLogic.deselectField();
                    BoardLogic.removeQuestionmarkField(field.getPosition());
                    if (global.BOARD.getDice().getValue() == 6) {
                        global.phase = Phase.DICE_PHASE;
                        return;
                    }
                    nextPlayer();
                    return;
                }
            }
            //return to selection phase and reset values if move is not possible
            global.phase = Phase.SELECT_FIGURE_PHASE;
            emptyMovableFields();
            MoveLogic.deselectField();
        }
    }

    /**
     * function to reset the selection of movable fields and set the attribute to null
     */
    private static void emptyMovableFields() {
        movableField.getBaseEntity().setScale(movableField.getType() == Field.HOME || movableField.getType() == Field.START ?
                global.HOME_AND_START_FIELD_SIZE : global.STANDARD_AND_BASE_FIELD_SIZE);
        movableField = null;
    }

}
