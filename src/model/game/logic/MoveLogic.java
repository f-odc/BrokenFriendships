package model.game.logic;

import model.board.fields.BoardField;
import model.board.fields.IField;
import model.board.objects.Figure;
import model.enums.Field;
import model.global;

import java.util.ArrayList;
import java.util.List;

public class MoveLogic {

    /**
     * Checks to which field the figure can move
     * @param field the field on which the current figure is located
     * @param step optional parameter, indicate the number of steps the figure can move, default/not specified = dice value
     * @return Boardfield if there is one field to move to, else null
     */
    public static ArrayList<IField> getMovableField(IField field, int ... step) {
        ArrayList<IField> movableFields = new ArrayList<>();
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
            movableFields.add(startField);
            return movableFields;
        }
        // case: figure in base
        else if (field.getType() == Field.BASE) {
            IField possibleBaseMove = moveInsideBase(field, stepValue);
            if (possibleBaseMove != null){
                movableFields.add(possibleBaseMove);
            }
            return movableFields;
        }
        // default case: figure on normal field
        else {
            // check if the base can be entered
            IField movableField = canEnterBase(field, stepValue);
            if (movableField != null){
                movableFields.add(movableField);
            }
            // calculate reachable field of game fields
            IField boardField = global.BOARD.getPlayField(((global.BOARD.getGameFieldsIndex(field.getPosition()) + stepValue) + 40) % 40);
            // check if target field is not occupied with own figure
            if (boardField.getCurrentFigure() == null || (boardField.getCurrentFigure().getOwnerID() != currentFigure.getOwnerID())) {
                movableFields.add(boardField);
            }
            return movableFields;
        }
    }

    /**
     * Checks if fields can be reached inside the base
     * @param field current field
     * @param stepValue number of steps figure has to go
     * @return BoardField which can be reached inside the base
     */
    static IField moveInsideBase(IField field, int stepValue) {
        // inside base no negative movement possible
        if (stepValue <= 0){
            return null;
        }
        // get base fields of owner of figure
        int figureOwnerId = field.getCurrentObject().getOwnerID();
        List<BoardField> playerBaseFields = global.BOARD.getBaseFields(figureOwnerId);
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
        // get endpoint of owner of figure
        int figureOwnerId = field.getCurrentObject().getOwnerID();
        int endIndex = global.players[figureOwnerId].getEndPoint();
        // calculate the new index of the reachable field
        int selectedIndex = global.BOARD.getGameFieldsIndex(field.getPosition());

        // special case: step value is negative
        if (stepValue < 0){
            // convert negative results
            if (selectedIndex + stepValue < 0){
                selectedIndex = selectedIndex + 40;
            }
            int moveIndex = selectedIndex + stepValue;
            // check if figure can move backwards into base
            if (selectedIndex >= endIndex && moveIndex < endIndex && moveIndex >= endIndex - 4){
                int index = endIndex - moveIndex - 1;
                BoardField currentBaseField = global.BOARD.getBaseFields(figureOwnerId).get(index);
                // if not occupied -> field can be reached
                if (!currentBaseField.isOccupied()) {
                    return currentBaseField;
                }
            }
        }else{ // for positive steps
            int moveIndex = selectedIndex + stepValue;
            // check if figure is for the end point, new move index is between endIndex + 1 and endIndex + 4
            if (selectedIndex <= endIndex && moveIndex > endIndex && moveIndex <= endIndex + 4) {
                // calculate the base index
                int index = moveIndex - (endIndex + 1);
                BoardField currentBaseField = global.BOARD.getBaseFields(figureOwnerId).get(index);
                // if not occupied -> field can be reached
                if (!currentBaseField.isOccupied()) {
                    return currentBaseField;
                }
            }
        }
        return null;
    }

}
