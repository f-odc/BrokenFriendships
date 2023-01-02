package model.game;

import model.boardLogic.objects.Figure;
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

    /**
     * Start the Game
     */
    public static void start() {

        // Create Player
        Player one = new Player(0);
        Player two = new Player(1);
        Player three = new Player(2);
        Player four = new Player(3);

        //one.getFigure(0).moveToStart(one.getStartField());

    }
}
