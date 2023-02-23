package model;

import eea.engine.entity.StateBasedEntityManager;
import model.board.Board;
import model.player.Player;
import model.enums.Phase;
import org.newdawn.slick.Animation;

import java.util.ArrayList;
import java.util.List;

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

    //Größe der Felder
    public static final float HOME_AND_START_FIELD_SIZE = 0.16f;
    public static final float STANDARD_AND_BASE_FIELD_SIZE = 0.14f;
    public static final float FIGURE_SIZE = 0.1f;
    public static final float OBJECT_SIZE = 0.08f;

    //which player is currently active
    public static int activePlayer;

    // game rounds
    public static int rounds;

    //phase of the current turn
    public static Phase phase;

    //the four playing players
    public static Player[] players = new Player[4];

    // dice animation
    public static Animation diceAnimation = new Animation();
    // mystery animation
    public static Animation mysteryAnimation = new Animation();
    // mystery objects, name and image
    public static ArrayList<List<String>> specialsMap = new ArrayList<List<String>>();
}
