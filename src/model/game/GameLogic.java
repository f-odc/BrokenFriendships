package model.game;

import model.boardLogic.Board;
import model.boardLogic.fields.BoardField;
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

    /**
     * Start the Game
     */
    public static void startGame() {

    }

    public static void setup() {
        // Create Player
        for (int i = 0; i < 4; i++) {
            new Player(i);
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

    public static void enterDicePhase() {
        global.phase = Turn.DICE_PHASE;
    }

    public static void enterFigurePhase() {
        global.phase = Turn.SELECT_FIGURE_PHASE;
    }

    public static void enterMovementPhase() {
        global.phase = Turn.SELECT_MOVEMENT_PHASE;
        //TODO show which movements are available (if none are available, phase is not changed)
    }

    private boolean showPossibleMovements(){
        int dice = global.BOARD.getDice().getValue();
        //TODO if in home, check if own figure in start field
        //  TODO if no, move
        //TODO if near base, check if move into base possible
        //  TODO if yes, move into base
        //TODO else
        //  TODO check if own figure on destiny
        //      TODO if no, move
        //  TODO reset to selection phase
        return true;
    }

    public static void executeDicePhase() {
        if (global.phase == Turn.DICE_PHASE) {
            try {
                global.BOARD.getDice().throwDice();
                enterFigurePhase();
                System.out.println("has thrown: " + global.BOARD.getDice().getValue());
            } catch (SlickException e) {
                System.out.println("throw has failed");
                throw new RuntimeException(e);
            }
        }
    }

    public static void executePhase(BoardField field) {
        //selection phase
        if (global.phase == Turn.SELECT_FIGURE_PHASE && field.getCurrentObject() != null) {
            if(global.turn == field.getCurrentObject().getOwnerID()){
                System.out.println("Is this field occupied? " + field.isOccupied());
                selectField(field);
                enterMovementPhase();
            }
        }
        //movement phase
        else if (global.phase == Turn.SELECT_MOVEMENT_PHASE) {
            //TODO check if movable
            //  TODO move and change phase if it is
            //TODO if not
            //  deselect and change back to selection phase
            if (field.getPosition().getX() == selectedField.getPosition().getX() &&
                    field.getPosition().getY() == selectedField.getPosition().getY()) {
                System.out.println("ACTIVATE");
                if (!move()){
                    enterFigurePhase();
                    deselectField();
                    return;
                }
            }
            deselectField();
            //  TODO next turn
            nextTurn();
        }
    }

    private static boolean move(){
        if(selectedField.getType() == Field.HOME){
            return selectedField.getCurrentObject().activate();
        }
        return false;
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
