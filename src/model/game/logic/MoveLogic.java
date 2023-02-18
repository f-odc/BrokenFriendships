package model.game.logic;

import model.board.fields.BoardField;
import model.board.fields.IField;
import model.board.objects.Figure;
import model.enums.Field;
import model.global;
import java.util.List;

public class MoveLogic {

    /**
     * Checks to which field the figure can move
     * @param field the field on which the current figure is located
     * @param step optional parameter, indicate the number of steps the figure can move, default/not specified = dice value
     * @return Boardfield if there is one field to move to, else null
     */
    public static IField getMovableField(IField field, int ... step) {
        // get dice throw value
        int stepValue = global.BOARD.getDice().getValue();
        // check if optional parameter is used
        if (step.length > 0){
            stepValue = step[0];
        }
        // get current figure
        Figure currentFigure = field.getCurrentFigure();

        // case: home field
        if (field.getType() == Field.HOME) {
            // case: check if no 6 is thrown and start field is occupied by own figure
            IField startField = currentFigure.getStartField();
            if (stepValue != 6 || (startField.getCurrentFigure() != null && startField.getCurrentFigure().getOwnerID() == currentFigure.getOwnerID()))
                return null;
            return startField;
        }
        // case: figure in base
        else if (field.getType() == Field.BASE) {
            return moveInBase(field, stepValue);
        }
        // default case: normal field
        else {
            // check if the base can be entered
            IField movableField = canEnterBase(field, stepValue);
            //case: cannot enter base
            if (movableField == null) {
                // calculate reachable field of game fields
                IField boardField = global.BOARD.getPlayField(global.BOARD.getGameFieldsIndex(field.getPosition()) + stepValue);
                // check if target field is not occupied with own figure
                if (boardField.getCurrentFigure() == null || (boardField.getCurrentFigure().getOwnerID() != currentFigure.getOwnerID())) {
                    movableField = boardField;
                }
            }
            return movableField;
        }
    }

    /**
     * Checks if fields can be reached inside the base
     * @param field current field
     * @param stepValue number of steps figure has to go
     * @return BoardField which can be reached inside the base
     */
    static IField moveInBase(IField field, int stepValue) {
        List<BoardField> playerBaseFields = global.BOARD.getBaseFields(global.activePlayer);
        // get index of current base field
        int indexOfBaseField = playerBaseFields.indexOf(field);
        int newIndex = indexOfBaseField + stepValue;
        // enough space in base?
        if (newIndex < 4) {
            // return board field if not occupied
            BoardField currentBaseField = playerBaseFields.get(newIndex);
            if (!currentBaseField.isOccupied()) {
                return currentBaseField;
            }
        }
        return null;
    }

    /**
     * Checks if figure can enter base
     * @param field current field
     * @param stepValue number of steps the figure perform
     * @return empty board field if movement is possible, else null
     */
    static IField canEnterBase(IField field, int stepValue) {
        // calculate the new index of the reachable field
        int selectedIndex = global.BOARD.getGameFieldsIndex(field.getPosition());
        int moveIndex = selectedIndex + stepValue;
        // get endpoint of player
        int endIndex = global.players[global.activePlayer].getEndPoint();

        // check if figure is for the end point, new move index is between endIndex + 1 and endIndex + 4
        if (selectedIndex <= endIndex && moveIndex > endIndex && moveIndex <= endIndex + 4) {
            // calculate the base index
            int index = moveIndex - (endIndex + 1);
            BoardField currentBaseField = global.BOARD.getBaseFields(global.activePlayer).get(index);
            // if not occupied -> field can be reached
            if (!currentBaseField.isOccupied()) {
                return currentBaseField;
            }
        }
        return null;
    }

}
