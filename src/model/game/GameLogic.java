package model.game;

import eea.engine.event.NOTEvent;
import model.boardLogic.fields.BoardField;
import model.boardLogic.objects.Figure;
import model.enums.Field;
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

    private static BoardField selectedField;

    private static BoardField movableField;


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
        global.phase = Turn.DICE_PHASE;
        global.BOARD.getDice().setPosition(global.turn);
    }

    /**
     * function controlling the logic of going into the next turn and choosing the next player
     */
    public static void nextTurn() {
        //win condition
        /*boolean hasWon = true;
        for (int i = 0; i < 4; i++) {
            BoardField figureField = global.players[global.turn].getFigure(i).getCurrentField();
            if(!global.BOARD.getBaseFields(global.turn).contains(figureField)){
                hasWon = false;
                break;
            }
        }*/

        global.turn = (global.turn + 1) % 4;
        global.BOARD.getDice().setPosition(global.turn);
        global.phase = Turn.DICE_PHASE;
    }

    /**
     * function controlling the logic og the figure selection phase
     */
    public static void enterFigurePhase() {
        global.phase = Turn.SELECT_FIGURE_PHASE;
    }

    /**
     * function controlling the logic of the movement phase
     *
     * @return true if the movement was executed successfully
     */
    public static boolean enterMovementPhase() {
        boolean isMovementPossible = showPossibleMovements();
        if (isMovementPossible)
            global.phase = Turn.SELECT_MOVEMENT_PHASE;
        return isMovementPossible;
    }

    /**
     * function displaying which fields a figure can move, when it is selected
     *
     * @return true if there are fields to be displayed
     */
    private static boolean showPossibleMovements() {
        //TODO if in home, check if own figure in start field
        //  TODO if no, move
        //TODO if near base, check if move into base possible
        //  TODO if yes, move into base
        //TODO else
        //  TODO check if own figure on destiny
        //      TODO if no, move
        //  TODO reset to selection phase
        movableField = getMovableField();
        if (movableField != null)
            movableField.getBaseEntity().setScale(0.5f);
        return movableField != null;
    }

    /**
     * function which checks which field the figure on the selected field can be moved to
     * @return Boardfield if there is one to move to, else null
     */
    public static BoardField getMovableField() {
        int dice = global.BOARD.getDice().getValue();
        Figure currentFigure = selectedField.getCurrentFigure();

        //case: home field
        if (selectedField.equals(currentFigure.getHomeField())) {
            //case: start field is occupied by own figure
            if (currentFigure.getStartField().isOccupied() &&
                    currentFigure.getStartField().getCurrentObject().getOwnerID() == selectedField.getCurrentObject().getOwnerID())
                return null;
            return currentFigure.getStartField();
        }
        //case: normal move
        else {
            //check if the base can be entered
            BoardField tmp = canEnterBase();
            //case: cannot enter base
            if (tmp == null) {
                System.out.println("normal move");
                BoardField to = global.BOARD.getPlayField(global.BOARD.getGameFieldsIndex(selectedField.getPosition()) + dice);
                //case: target field is occupied but not from own figure
                if ((to.isOccupied() && to.getCurrentObject().getOwnerID() != global.turn) ||
                        !to.isOccupied()) {
                    tmp = to;
                }
            }
            return tmp;
        }
    }

    /**
     * function checking if the figure on the selected field can enter the base
     * @return Boardfield from base if it is possible, else null
     */
    private static BoardField canEnterBase() {
        int selectedIndex = global.BOARD.getGameFieldsIndex(selectedField.getPosition());
        BoardField endField = global.BOARD.getPlayField(global.BOARD.getGameFieldsIndex(selectedField.getCurrentFigure().getStartField().getPosition()) - 1);
        int endIndex = global.BOARD.getGameFieldsIndex(endField.getPosition());
        int moveIndex = selectedIndex + global.BOARD.getDice().getValue();

        System.out.println("going to check for: selectedIndex: " + selectedIndex + " endIndex" + endIndex + " moveIndex" + moveIndex);

        //move would go beyond start point
        //TODO change, does not work properly
        if (selectedIndex > 0 && selectedIndex < endIndex && moveIndex > endIndex) {
            BoardField baseField = global.BOARD.getBaseFields(global.turn).get(4 - (global.BOARD.getDice().getValue() - (endIndex - selectedIndex)));
            //the base field is empty
            if (!baseField.isOccupied()) {
                System.out.println("into base move possible");
                return baseField;
            }
            System.out.println("base field is occupied");
        }
        System.out.println("would not go beyond start point, selectedIndex: " + selectedIndex + " endIndex" + endIndex + " moveIndex" + moveIndex);
        return null;
    }

    /**
     * function controlling the logic of the dice phase
     */
    public static void executeDicePhase() {
        if (global.phase == Turn.DICE_PHASE) {
            try {
                global.BOARD.getDice().throwDice();
                enterFigurePhase();
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
        if (global.phase == Turn.SELECT_FIGURE_PHASE && field.getCurrentObject() != null) {
            if (global.turn == field.getCurrentObject().getOwnerID()) {
                selectField(field);
                if (!enterMovementPhase())
                    deselectField();
            }
        }
        //movement phase
        else if (global.phase == Turn.SELECT_MOVEMENT_PHASE) {
            //TODO check if movable
            //  TODO move and change phase if it is
            //TODO if not
            //  deselect and change back to selection phase
            if (movableField.equals(field)) {
                if (move()) {
                    //reset values if move is possible
                    emptyMovableFields();
                    deselectField();
                    nextTurn();
                    return;
                }
            }
            //return to selection phase and reset values if move is not possible
            enterFigurePhase();
            emptyMovableFields();
            deselectField();
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

    /**
     * function to call the Figures own activation fucntion
     *
     * @return true if move was successfully executed
     */
    private static boolean move() {
        return selectedField.getCurrentObject().activate();
    }

    /**
     * function to highlight a selected field
     *
     * @param field the selected field
     */
    public static void selectField(BoardField field) {
        selectedField = field;
        field.highlight();
    }

    /**
     * function to de-highlight a field
     */
    public static void deselectField() {
        selectedField.deHighlight();
        selectedField = null;
    }
}
