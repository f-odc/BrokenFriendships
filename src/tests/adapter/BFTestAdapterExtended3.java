package tests.adapter;

import model.board.objects.specials.BedSpecial;
import model.board.objects.specials.BombSpecial;
import model.board.objects.specials.MoveFourSpecial;
import model.board.objects.specials.SwitchSpecial;
import model.game.logic.GameLogic;
import model.global;

public class BFTestAdapterExtended3 extends BFTestAdapterExtended2 {

    /* ***************************************************
     * ******** Test der komplizierten Specials **********
     * *************************************************** */

    /**
     * Erzwinge die Acivierung eines komplizierten Special Objektes.
     *
     * @param type     Typ des Specials
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     */
    public void forceComplicatedSpecialSpawn(String type, int playerID, int figureID) {
        switch (type) {
            case "bed" : {
                //TODO
                GameLogic.setDebugDiceThrow(6);
                GameLogic.executeInitSpecialsPhase(0, global.players[playerID].figures.get(figureID));
                break;
            }
            case "bomb" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(1, global.players[playerID].figures.get(figureID));
                break;
            }
            case "PlusToThree" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(6, global.players[playerID].figures.get(figureID));
                break;
            }
            case "MoveFour" : {
                //TODO
                GameLogic.executeInitSpecialsPhase(4, global.players[playerID].figures.get(figureID));
                break;
            }
            case "Switch" : {
                //TODO
                GameLogic.setDebugDiceThrow(3);
                GameLogic.executeInitSpecialsPhase(7, global.players[playerID].figures.get(figureID));
                break;
            }
        }
    }

    /**
     * Gebe zurück, ob bei dem Spieler ein Bett aktiv ist, er also schläft.
     *
     * @param playerID ID des Spielers
     * @return True, wenn Bett bei dem Spieler aktiv ist. False, wenn nicht.
     */
    public boolean isBedActive(int playerID) {
        return global.players[playerID].getActiveBed() != null;
    }

    /**
     * Gebe zurück, ob auf diesem Feld ein Bett platziert ist.
     * @param index des Feldes
     * @return True, wenn auf dem Feld ein Bett ist. False, wenn nicht.
     */
    public boolean isOccupiedByBed(int index) {
        return global.BOARD.getGameField(index).getCurrentObject() instanceof BedSpecial;
    }

    /**
     * Gebe zurück, ob auf diesem Feld eine Bombe platziert ist.
     * @param index des Feldes
     * @return True, wenn auf dem Feld eine Bombe ist. False, wenn nicht.
     */
    public boolean isOccupiedByBomb(int index) {
        return global.BOARD.getGameField(index).getCurrentObject() instanceof BombSpecial;
    }

    /**
     * Wähle ein Feld aus. Simuliert das auswählen eines Feldes durch den Spieler.
     *
     * @param fieldID ID des Feldes
     */
    public void selectField(int fieldID) {
        GameLogic.executeSelectMovementPhase(global.BOARD.getGameField(fieldID), 6);
    }

    /**
     * Wähle zwei Felder aus. Simuliert das auswählen der Felder durch den Spieler.
     *
     * @param fieldID1 ID des ersten Feldes.
     * @param fieldID2 ID des zweiten Feldes.
     */
    public void selectTwoFields(int fieldID1, int fieldID2) {
        GameLogic.executeMysterySelectionPhase(global.BOARD.getGameField(fieldID1));
        GameLogic.executeSelectMovementPhase(global.BOARD.getGameField(fieldID2), 6);
    }

    /**
     * Wähle ein Zielfeld aus. Simuliert das auswählen eines Feldes durch den Spieler.
     *
     * @param playerID ID des Spielers
     * @param fieldID  ID des Feldes
     */
    public void selectBaseField(int playerID, int figureID, int fieldID) {
        if (GameLogic.activeSpecial instanceof MoveFourSpecial || GameLogic.activeSpecial instanceof SwitchSpecial)
            GameLogic.executeMysterySelectionPhase(global.players[playerID].figures.get(figureID).getCurrentField());
        GameLogic.executeSelectMovementPhase(global.BOARD.getBase(playerID).get(fieldID), 6);
    }
}
