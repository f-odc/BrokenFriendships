package model.game;

import model.boardLogic.Board;
import model.boardLogic.fields.BoardField;
import model.global;

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
    }

    public static void enterDicePhase() {
        global.phase = Turn.DICE_PHASE;
    }

    public static void enterFigurePhase() {
        global.phase = Turn.SELECT_FIGURE_PHASE;
    }

    public static void enterMovementPhase() {
        global.phase = Turn.SELECT_MOVEMENT_PHASE;
    }

    public static void selectField(BoardField field){
        selectedField = field;
        field.getCurrentObject().getEntity().setScale(0.12f);
    }

    public static void deselectField(){
        selectedField.getCurrentObject().getEntity().setScale(0.1f);
        selectedField = null;
    }
}
