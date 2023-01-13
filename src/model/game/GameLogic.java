package model.game;

import model.boardLogic.fields.BoardField;
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

    private static BoardField movableField;

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
        global.turn = 0;
        global.phase = Phase.DICE_PHASE;
        global.BOARD.getDice().setPosition(global.turn);
    }

    /**
     * function controlling the logic of going into the next turn and choosing the next player
     */
    public static void nextTurn() {
        //win condition
        /*for (int i = 0; i < 4; i++) {
            BoardField figureField = global.players[global.turn].getFigure(i).getCurrentField();
            if (!global.BOARD.getBaseFields(global.turn).contains(figureField)) {
                global.phase = Phase.END_OF_GAME;
                break;
            }
        }*/
        getOutOfBaseTries = 0;
        global.turn = (global.turn + 1) % 4;
        global.BOARD.getDice().setPosition(global.turn);
        global.phase = Phase.DICE_PHASE;
    }

    /**
     * function controlling the logic of the movement phase
     *
     * @return true if the movement was executed successfully
     */
    public static boolean enterMovementPhase() {
        movableField = MoveLogic.getMovableField();
        boolean isMovementPossible = movableField != null;
        if (isMovementPossible){
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
                Player currentPlayer = global.players[global.turn];
                //check if all figures are in base or home
                for (int i = 0; i < 4; i++) {
                    Figure figure = currentPlayer.getFigure(i);
                    if (!figure.isOnHomeField() && !figure.isOnBaseField()) {
                        allInHomeOrBase = false;
                    }
                }
                //case: all figures in home/base, no 6 and still has tries to get out
                if (allInHomeOrBase && global.BOARD.getDice().getValue() != 6 && getOutOfBaseTries < 2) {
                    getOutOfBaseTries++;
                    return;
                }
                //case: out of tries to get a figure out of the home and no 6
                if (getOutOfBaseTries == 2 && global.BOARD.getDice().getValue() != 6) {
                    nextTurn();
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
    public static void executePhase(BoardField field) {
        //selection phase
        if (global.phase == Phase.SELECT_FIGURE_PHASE && field.getCurrentObject() != null) {
            if (global.turn == field.getCurrentObject().getOwnerID()) {
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
                    nextTurn();
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
