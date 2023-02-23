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
        try {
            // initialisieren des Spielbrettes
            global.BOARD = new Board();
        }
        catch (Exception e){
            // TODO: end game with failure message
            System.out.println(e.getMessage());
        }
    }
}
