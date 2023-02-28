package tests.adapter;

import eea.engine.entity.StateBasedEntityManager;
import eea.engine.test.TestAppGameContainer;
import model.board.Board;
import model.board.fields.BoardField;
import model.board.fields.IField;
import model.board.objects.Figure;
import model.game.GameManager;
import model.global;
import model.player.Player;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import ui.Launch;

import java.util.List;

public class TestAdapterMinimal {
    Launch brokenFriendships;
    TestAppGameContainer app;

    public TestAdapterMinimal() {
        brokenFriendships = null;
    }

    public void initializeGame() {
        // Setze den library Pfad abhaengig vom Betriebssystem
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/windows");
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx");
        } else {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/" + System.getProperty("os.name").toLowerCase());
        }

        global.entityManager = StateBasedEntityManager.getInstance();

        // Setze dieses StateBasedGame in einen App Container (oder Fenster)
        try {
            app = new TestAppGameContainer(new Launch(true));

            // Lege die Einstellungen des Fensters fest und starte das Fenster
            app.setDisplayMode(global.X_DIMENSIONS, global.Y_DIMENSIONS, false);
            app.start(0);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }

        // initialize all game objects
        GameManager.setup();
        // start the game
        GameManager.start();
    }

    public void stopGame() {
        if (app != null) {
            app.exit();
            app.destroy();
        }
        StateBasedEntityManager.getInstance().clearAllStates();
        brokenFriendships = null;
    }

    public Board initializeBoard() {
        return new Board();
    }

    public List<BoardField> getHome(int i) {
        return global.BOARD.getHome(i);
    }

    public List<BoardField> getBase(int i) {
        return global.BOARD.getBase(i);
    }

    public IField[] getGameFields() {
        IField[] fields = new IField[40];
        for (int i = 0; i < 40; i++) {
            fields[i] = global.BOARD.getGameField(i);
        }
        return fields;
    }

    public Player[] getPlayer() {
        return global.players;
    }

    public List<Figure> getPlayerFigures(int i) {
        return global.players[i].figures;
    }

    public int getPlayerID(int i){
        return global.players[i].id;
    }
}
