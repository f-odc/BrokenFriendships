package model.board;

import model.global;
import org.newdawn.slick.SlickException;

/**
 * Manage all board properties
 */
public class BoardManager {

    /**
     * Das Board mit allen Feldern wird gerendert
     * Zusätzlich wird der Würfel erstellt
     */
    static public void setup(){
            // initialisieren des Spielbrettes
            global.BOARD = new Board();
    }
}
