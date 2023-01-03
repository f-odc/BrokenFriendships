package model.game;

import model.boardLogic.fields.BoardField;
import model.boardLogic.objects.Figure;
import model.enums.Field;
import model.global;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

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

    public static void nextTurn() {
        global.turn = (global.turn + 1) % 4;
        global.BOARD.getDice().setPosition(global.turn);
        global.phase = Turn.DICE_PHASE;
    }

    public static void enterFigurePhase() {
        global.phase = Turn.SELECT_FIGURE_PHASE;
    }

    public static boolean enterMovementPhase() {
        boolean isMovementPossible = showPossibleMovements();
        if (isMovementPossible)
            global.phase = Turn.SELECT_MOVEMENT_PHASE;
        return isMovementPossible;
    }

    private static boolean showPossibleMovements() {
        int dice = global.BOARD.getDice().getValue();
        Figure current = selectedField.getCurrentFigure();

        //TODO if in home, check if own figure in start field
        //  TODO if no, move
        //TODO if near base, check if move into base possible
        //  TODO if yes, move into base
        //TODO else
        //  TODO check if own figure on destiny
        //      TODO if no, move
        //  TODO reset to selection phase

        //case: home field
        if (selectedField.equals(current.getHomeField())) {
            //case: start field is occupied by own figure
            if (current.getStartField().isOccupied() &&
                    current.getStartField().getCurrentObject().getOwnerID() == selectedField.getCurrentObject().getOwnerID())
                return false;

            movableField = current.getStartField();
            movableField.getBaseEntity().setScale(0.5f);
        }
        //case: normal move
        else {
            BoardField to = global.BOARD.getPlayField(global.BOARD.getGameFieldsIndex(selectedField.getPosition()) + dice);
            //case: target field is occupied but not from own figure
            if ((to.isOccupied() && to.getCurrentObject().getOwnerID() != global.turn) ||
                    !to.isOccupied()) {
                movableField = to;
                movableField.getBaseEntity().setScale(0.5f);
            }
        }
        return movableField != null;
    }

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

    private static void emptyMovableFields() {
        movableField.getBaseEntity().setScale(movableField.getType() == Field.HOME || movableField.getType() == Field.START ?
                global.HOME_AND_START_FIELD_SIZE : global.STANDARD_AND_BASE_FIELD_SIZE);
        movableField = null;
    }

    private static boolean move() {
        return selectedField.getCurrentObject().activate();
    }


    public static void selectField(BoardField field) {
        selectedField = field;
        field.highlight();
    }

    public static void deselectField() {
        selectedField.deHighlight();
        selectedField = null;
    }
}
