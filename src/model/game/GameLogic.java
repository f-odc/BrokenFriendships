package model.game;

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

    private static boolean isWon;

    /**
     * Start the Game
     */
    public static void start() {
        setup();

        while(!isWon){
            turn();
        }
    }

    private static void setup() {
        // Create Player
        for (int i = 0; i < 4; i++) {
            new Player(i);
        }

        isWon = false;

        //set first players turn
        global.turn = 0;
        global.BOARD.getDice().setPosition(global.turn);
    }

    public static void turn() {

    }

    public static void nextTurn() {
        global.turn = (global.turn + 1) % 4;
    }
}
