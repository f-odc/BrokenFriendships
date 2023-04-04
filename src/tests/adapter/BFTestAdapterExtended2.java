package tests.adapter;

import model.game.logic.GameLogic;
import model.global;

public class BFTestAdapterExtended2 extends BFTestAdapterExtended1 {

    /* ***************************************************
     * ****** Initialisierung der Mystery Objekte ********
     * *************************************************** */

    /**
     * Gebe die Anzahl an Mystery Objekten zurück, die auf dem Feld sind.
     *
     * @return Anzahl der Mytsery objekten
     */
    public int getNumberOfMysteryObjects() {
        return GameLogic.numOfMysteryFields;
    }

    /**
     * Spinge zum nächsten Spieler.
     */
    public void skipTurn() {
        GameLogic.nextPlayer();
    }

    /**
     * Erzwinge das Erscheinen eines Mystery Objektes.
     *
     * @param index An welchem Feld Index das Objekt erscheinen soll.
     */
    public void spawnMystery(int index) {
        if (!global.BOARD.getGameField(index).isOccupied())
            GameLogic.spawnMystery(index);
    }

    /**
     * Bewege eine Figure zu einer gewissen Position.
     *
     * @param playerID ID des Spielers
     * @param from     ID des Feldes von welchem getauscht wird
     * @param to       ID des Feldes zu welchem getauscht wird
     */
    public void moveTo(int playerID, int from, int to) {
        global.players[playerID].figures.get(from).moveTo(global.BOARD.getGameField(to), false);
    }


    /* ***************************************************
     * *********** Test der simplen Specials *************
     * *************************************************** */

    /**
     * Erzwinge die Acivierung eines simplen Special Objektes.
     *
     * @param type     Typ des Specials
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     */
    public void forceSimpleSpecialActivation(String type, int playerID, int figureID) {
        switch (type) {
            case "dead" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(2, global.players[playerID].figures.get(figureID));
                break;
            }
            case "moveOut" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(5, global.players[playerID].figures.get(figureID));
                break;
            }
            case "growing" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(3, global.players[playerID].figures.get(figureID));
                break;
            }
        }
    }
}
