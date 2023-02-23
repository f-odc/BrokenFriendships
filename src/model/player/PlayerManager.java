package model.player;

import model.global;

/**
 * Manage all player properties
 */
public class PlayerManager {

    /**
     * Erstellt alle Spieler
     */
    public static void setup() {
        // Create Player
        for (int i = 0; i < 4; i++) {
            global.players[i] = new Player(i);
        }
    }
}
