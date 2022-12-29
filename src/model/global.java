package model;

import eea.engine.entity.StateBasedEntityManager;
import model.boardLogic.Board;

/**
 * Class to store any global variables
 */
public class global {
    //Anzahl der Felder
    static public int NUM_OF_FIELDS = 11;

    //Spielbrett
    static public Board BOARD;

    //zum hinzufügen und löschen von Entities
    static public StateBasedEntityManager entityManager;

    // Jeder State wird durch einen Integer-Wert gekennzeichnet
    public static final int MAINMENU_STATE = 0;
    public static final int GAMEPLAY_STATE = 1;
}
