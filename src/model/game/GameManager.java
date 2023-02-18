package model.game;

import model.board.BoardManager;
import model.enums.Phase;
import model.global;
import model.player.Player;
import model.player.PlayerManager;

/**
 * Manage all game related features
 */
public class GameManager {

    /**
     * Initialisiert das Spielbrett und die Spielfiguren
     */
    public static void setup() {

        // setup board
        BoardManager.setup();
        // setup player
        PlayerManager.setup();
    }

    /**
     * Startet das Spiel
     */
    public static void start() {
        //set first players turn
        global.activePlayer = 0;
        global.turn = 0;
        global.phase = Phase.DICE_PHASE;
        global.BOARD.getDice().setPosition(global.activePlayer);
    }
}
