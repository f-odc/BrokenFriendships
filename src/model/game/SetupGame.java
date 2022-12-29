package model.game;

import model.boardLogic.Board;
import model.global;
import org.newdawn.slick.SlickException;

public class SetupGame {

    static public void setup() throws SlickException {
        //initialisieren des Spielbrettes
        global.BOARD = new Board();

        //initialisieren von Spielern
        //4 player init
    }
}
