package tests.adapter;

import eea.engine.entity.StateBasedEntityManager;
import eea.engine.test.TestAppGameContainer;
import model.board.fields.IField;
import model.enums.FieldType;
import model.enums.Phase;
import model.game.GameManager;
import model.game.logic.GameLogic;
import model.game.logic.MoveLogic;
import model.global;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import ui.BrokenFriendships;

import java.util.List;

/**
 * This is the test adapter for the minimal stage of completion. You must implement the method stubs and match
 * them to your concrete implementation. Please read all the Javadoc of a method before implementing it
 * Important: this class should not contain any real game logic, you should rather only match the
 * method stubs to your game.
 * Example: in {@Link #getColorOfStartField(int playerID)} you may return the value Game.player[playerID].startField.color,
 * if you have a global gamestate called Game, with a player array, access to the starfield with a parameter of color.
 * What you mustn't do is to implement the actual logic of the method in this class.
 * <p>
 * If you have implemented the minimal stage of completion, you should be able to implement all method stubs. The public
 * and private JUnit tests for the minimal stage of completion will be run on this test adapter. The other test adapters
 * will inherit from this class, because they need the basic methods (like loading a map), too.
 * <p>
 * The methods of all test adapters need to function without any kind of user interaction.
 * <p>
 * Note: All other test adapters will inherit from this class.
 *
 * @see BFTestAdapterExtended1
 * @see BFTestAdapterExtended2
 * @see BFTestAdapterExtended3
 */
public class BFTestAdapterMinimal {
    BrokenFriendships brokenFriendships;
    TestAppGameContainer app;

    public BFTestAdapterMinimal() {
        brokenFriendships = null;
        initializeGame();
    }

    /* ***************************************************
     * *** initialisiere, starte, und stoppe das Spiel ***
     * *************************************************** */

    /**
     * Initialisiere das Spiel im test (mit debug true) mode
     */
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
            app = new TestAppGameContainer(new BrokenFriendships(true));
            app.start(0);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }

        //TODO: initialisiere alle notwendigen Spiel Komponenten

        // initialize all game objects
        GameManager.setup();
        // start the game
        GameManager.start();
    }

    /**
     * Beendet das Spiel.
     */
    public void stopGame() {
        if (app != null) {
            app.exit();
            app.destroy();
        }
        StateBasedEntityManager.getInstance().clearAllStates();
        brokenFriendships = null;
    }

    /* ***************************************************
     * ******************* Felder ************************
     * *************************************************** */

    /**
     * Gebe die Anzahl der Zielfelder des spezifischen Spielers zurück.
     *
     * @param id des Spielers (0 bis 3)
     * @return Anzahl der Zielfelder
     */
    public int getNumberOfBaseFields(int id) {
        //TODO
        return global.BOARD.bases.getBaseSize(id);
    }

    /**
     * Gebe die Anzahl der Hausfelder des spezifischen Spielers zurück.
     *
     * @param id des Spielers  (0 bis 3)
     * @return Anzahl der Hausfelder
     */
    public int getNumberOfHomeFields(int id) {
        //TODO
        return global.BOARD.homes.getBaseSize(id);
    }

    /**
     * Gebe die Anzahl der Startfelder zurück.
     *
     * @return Anzahl der Startfelder
     */
    public int getNumberOfStartFields() {
        //TODO
        int numOfStartfields = 0;
        for (int i = 0; i < 40; i++) {
            if (global.BOARD.getGameField(i).getType() == FieldType.START) numOfStartfields++;
        }
        return numOfStartfields;
    }

    /**
     * Gebe die Anzahl der Spielfelder zurück, auf denen sich alle Figuren bewegen können.
     * Alle Felder ausgenommen der Haus- und Zielfelder.
     *
     * @return Anzahl der Spielfelder
     */
    public int getNumberOfGameFields() {
        //TODO
        return global.BOARD.gameFields.length;
    }

    /**
     * Gebe die Farbe eines Zielfeldes zurück.
     *
     * @param playerID ID des Spielers (0 bis 3)
     * @param fieldID  ID des Zielfeldes (0 bis 3)
     * @return Verwendete Farbe als String
     */
    public String getColorOfBase(int playerID, int fieldID) {
        //TODO
        return global.BOARD.getBase(playerID).get(fieldID).color.toString().toLowerCase();
    }

    /**
     * Gebe die Farbe eines Hausfeldes zurück.
     *
     * @param playerID ID des Spielers (0 bis 3)
     * @param fieldID  ID des Hausfeldes (0 bis 3)
     * @return Verwendete Farbe als String
     */
    public String getColorOfHome(int playerID, int fieldID) {
        //TODO
        return global.BOARD.getHome(playerID).get(fieldID).color.toString().toLowerCase();
    }

    /**
     * Gebe die Farbe eines Hausfeldes zurück.
     *
     * @param playerID ID des Spielers (0 bis 3)
     * @return Verwendete Farbe als String
     */
    public String getColorOfStartField(int playerID) {
        //TODO
        return global.BOARD.getGameField(global.players[playerID].startField.getFieldIndex()).color.toString().toLowerCase();
    }


    /* ***************************************************
     * ******************* Figuren ***********************
     * *************************************************** */

    /**
     * Gebe die Anzahl der Figuren für den spezifischen Spieler zurück.
     *
     * @param id des Spielers
     * @return Anzahl an Figuren
     */
    public int getFigureCount(int id) {
        //TODO
        return global.players[id].figures.size();
    }

    /**
     * Gebe zurück, ob sich die Figur auf einem Hausfeld befindet.
     *
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     * @return True, wenn sich die Figur auf einem Hausfeld befindet. False, wenn sich die Figur auf keinem Hausfeld befindet.
     */
    public boolean occupiesHomeField(int playerID, int figureID) {
        //TODO
        for (int i = 0; i < 4; i++) {
            if (global.players[playerID].figures.get(figureID).getCurrentField().equals(global.players[playerID].homeFields.get(i)))
                return true;
        }
        return false;
    }

    /**
     * Gebe die Farbe der Figur eines spezifischen Spielers zurück.
     *
     * @return Verwendete Farbe als String
     */
    public String getFigureColor(int playerID, int figureID) {
        //TODO
        return global.players[playerID].figures.get(figureID).color.toString().toLowerCase();
    }

    /**
     * Gebe zurück ob alle Hausfelder besetzt sind.
     *
     * @param playerID Id des Spielers
     * @return True, wenn alle Hausfelder besetzt sind. False, wenn nicht alle Hausfelder besetzt sind.
     */
    public boolean allHomeFieldsOccupied(int playerID) {
        //TODO
        for (int i = 0; i < 4; i++) {
            if (!global.players[playerID].homeFields.get(i).isOccupied()) return false;
        }
        return true;
    }

    /* ***************************************************
     * ******************* Würfel ************************
     * *************************************************** */

    /**
     * Führe einen Würfelwurf aus und gebe die gewürfelte Zahl zurück.
     *
     * @return Die gewürfelte Zahl
     */
    public int getDiceThrow() {
        //TODO
        return global.BOARD.getDice().throwDice();
    }

    /**
     * Gebe die Würfelposition, relativ zum Spieler, zurück
     *
     * @param playerID Id des Spielers
     * @return Position des Würfels
     */
    public Vector2f getDicePosition(int playerID) {
        //TODO
        global.BOARD.getDice().setPosition(playerID);
        return global.BOARD.getDice().getCurrentPosition();
    }

    /* ***************************************************
     * ******************* Bewegung **********************
     * *************************************************** */

    /**
     * Setze den Aktiven Spieler.
     *
     * @param id des Spielers
     */
    public void setActivePlayer(int id) {
        //TODO
        global.activePlayer = id;
    }

    /**
     * Setze Alle Figuren des spezifischen Spielers ins Haus.
     *
     * @param playerID Id des Spielers
     */
    public void resetFigures(int playerID) {
        //TODO
        for (int i = 0; i < 4; i++) {
            global.players[playerID].figures.get(i).reset();
        }
    }

    /**
     * Bewege die gegebene Figur des gegebenen Spielers um den gegebenen Würfelwurf.
     * Vorbedingungen, Konsequenzen oder Bewegungsbedingungen hier bitte mit dem richtigen Funktionsaufruf ausführen.
     *
     * @param playerID  ID des Spielers
     * @param figureID  ID der Figur
     * @param diceThrow Der Würfelwurf
     */
    public void move(int playerID, int figureID, int diceThrow) {
        //TODO
        GameLogic.spawnMystery = false;
        List<IField> lst = MoveLogic.getMovableField(global.players[playerID].figures.get(figureID).getCurrentField(), diceThrow);
        GameLogic.executeDicePhase(diceThrow, playerID);
        if (GameLogic.isNextPlayer) GameLogic.executeDicePhase(diceThrow, playerID);
        GameLogic.executeSelectFigurePhase(global.players[playerID].figures.get(figureID).getCurrentField(), diceThrow);
        if (lst.size() > 0) {
            global.players[playerID].figures.get(figureID).moveTo(lst.get(0), false);
            GameLogic.executeSelectMovementPhase(lst.get(0), diceThrow);

        } else if (GameLogic.isNextPlayer) {
            GameLogic.executeDicePhase(diceThrow, playerID);
        }
    }

    /**
     * Gebe die Anzahl an Würfel versuchen zurück.
     *
     * @return Die Anzahl an Würfel versuchen.
     */
    public int getDiceThrowAttempts() {
        //TODO
        return 3 - GameLogic.getOutOfBaseTries;
    }

    /**
     * Verwenden sie diese Funktion um Werte zurückzusetzen, welche am Anfang eines Zuges ein spezifischen Wert haben.
     * Falls anders implementiert, frei lassen.
     */
    public void resetTurn() {
        //TODO
        GameLogic.getOutOfBaseTries = 0;
        global.phase = Phase.DICE_PHASE;
        GameLogic.isNextPlayer = false;
        for (int i = 0; i < 40; i++) {
            if (global.BOARD.getGameField(i).isOccupied()) global.BOARD.getGameField(i).getCurrentObject().reset();
        }
        GameLogic.numOfMysteryFields = 0;
        global.rounds = 0;
        global.activePlayer = 0;
        GameLogic.spawnMystery = true;
    }

    /**
     * Gebe zurück, ob eine Figur auf einem Zielfeld seht.
     *
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     * @return True, wenn die Figur auf einem Zielfeld steht. False, wenn sie es nicht tut.
     */
    public boolean occupiesBaseField(int playerID, int figureID) {
        //TODO
        return global.players[playerID].figures.get(figureID).getCurrentField().getType() == FieldType.BASE;
    }


    /* ***************************************************
     * ******************** Runden ***********************
     * *************************************************** */

    /**
     * Gebe den aktiven Spieler zurück.
     *
     * @return ID des aktiven Spielers
     */
    public int getActivePlayer() {
        return global.activePlayer;
    }

    /**
     * Gebe zurück, ob der SPieler gewonnen hat.
     *
     * @param playerID ID des Spielers
     * @return True, wenn der Spieler gewonnen hat. False, wenn nicht.
     */
    public boolean hasWon(int playerID) {
        return global.players[playerID].hasWon();
    }

    /**
     * Gebe die active Position des Würfels zurück.
     *
     * @return Position des Würfels
     */
    public Vector2f getActiveDicePosition() {
        return global.BOARD.getDice().getCurrentPosition();
    }
}
