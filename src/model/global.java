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
    //number of cells in a row of the board
    static public int NUM_OF_FIELDS = 11;

    //board
    static public Board BOARD;

    //add and remove entities with this manager
    static public StateBasedEntityManager entityManager;

    // state ID
    public static final int MAINMENU_STATE = 0;
    public static final int GAMEPLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;

    //size of fields in pixel
    public static final int X_DIMENSIONS = 1920;
    public static final int Y_DIMENSIONS = 1080;
    public static final float HOME_AND_START_FIELD_SIZE = 0.16f;
    public static final float STANDARD_AND_BASE_FIELD_SIZE = 0.14f;
    public static final float FIGURE_SIZE = 0.1f;
    public static final float OBJECT_SIZE = 0.08f;
    public static final float BACKGROUND_SIZE = 1.8f;
    public static final float ANIMATION_SIZE = 100;

    //currently active player: 0 == red, 1 == yellow, 2 == blue, 3 == green
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

    // CE tasks flag
    public static boolean activeCE = true;
}
