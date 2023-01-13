package model.game;

import model.boardLogic.fields.BoardField;
import model.boardLogic.objects.Figure;
import model.enums.Field;
import model.global;

public class MoveLogic {

    static BoardField selectedField;

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

    /**
     * function which checks which field the figure on the selected field can be moved to
     *
     * @return Boardfield if there is one to move to, else null
     */
    public static BoardField getMovableField() {
        int dice = global.BOARD.getDice().getValue();
        Figure currentFigure = selectedField.getCurrentFigure();

        //case: home field
        if (selectedField.equals(currentFigure.getHomeField())) {
            //case: start field is occupied by own figure
            if ((currentFigure.getStartField().isOccupied() &&
                    currentFigure.getStartField().getCurrentObject().getOwnerID() == selectedField.getCurrentObject().getOwnerID()) ||
                    global.BOARD.getDice().getValue() != 6)
                return null;
            return currentFigure.getStartField();
        }
        // case: figure in base
        else if (selectedField.getType() == Field.BASE) {
            return moveInBase();
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
     * movement inside the base fields
     *
     * @return BoardField which can be reached inside the base
     */
    static BoardField moveInBase() {
        System.out.println("Start Move in Base");
        int dice = global.BOARD.getDice().getValue();

        // TODO: change -> get current field in base
        int index = 0;
        for (BoardField field : global.BOARD.getBaseFields(global.turn)) {
            if (selectedField.equals(field)) break;
            else index++;
        }
        // TODO: change -> workaround for blue and green
        if (global.turn == 2 || global.turn == 3) {
            index = (3 + 3 * index) % 4;
        }
        int newIndex = dice + index;
        // enough space in base?
        if (newIndex < 4) {
            System.out.println("Enough space");
            // TODO: change -> workaround for blue and green
            if (global.turn == 2 || global.turn == 3) {
                newIndex = (3 + 3 * newIndex) % 4;
            }
            System.out.println("BaseIndex: " + index + " NewIndex: " + newIndex);
            // return board field if not occupied
            BoardField currentBaseField = global.BOARD.getBaseFields(global.turn).get(newIndex);
            if (currentBaseField.isOccupied()) {
                System.out.println("base field is occupied");
            } else {
                System.out.println("into base move possible");
                return currentBaseField;
            }
        }
        return null;
    }

    /**
     * function checking if the figure on the selected field can enter the base
     *
     * @return Boardfield from base if it is possible, else null
     */
    static BoardField canEnterBase() {
        int selectedIndex = global.BOARD.getGameFieldsIndex(selectedField.getPosition());
        // get endpoint of player
        int endIndex = global.players[selectedField.getCurrentFigure().getOwnerID()].getEndPoint();
        int moveIndex = selectedIndex + global.BOARD.getDice().getValue();

        System.out.println("going to check for: selectedIndex: " + selectedIndex + " endIndex" + endIndex + " moveIndex" + moveIndex + "newMoveIndex:" + moveIndex % 40);

        if (selectedIndex <= endIndex && moveIndex > endIndex && moveIndex <= endIndex + 4) {
            // possible
            // check if occupied
            // -1 else no 0 -> need of index not of id
            int index = moveIndex - endIndex - 1;
            // TODO: change to better workaround
            // wrong base field allocation for blue and green
            if (global.turn == 2 || global.turn == 3) {
                index = (3 + 3 * index) % 4;
            }
            BoardField currentBaseField = global.BOARD.getBaseFields(global.turn).get(index);
            if (currentBaseField.isOccupied()) {
                System.out.println("base field is occupied");
            } else {
                System.out.println("into base move possible");
                return currentBaseField;
            }
        }
        return null;
    }


    /**
     * function to call the Figures own activation fucntion
     *
     * @return true if move was successfully executed
     */
    static boolean move() {
        return selectedField.getCurrentObject().activate();
    }

}
