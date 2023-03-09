package tests.adapter;

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
            case "bed" -> {
                //TODO
                GameLogic.setDebugDiceThrow(6);
                GameLogic.executeInitSpecialsPhase(0, global.players[playerID].figures.get(figureID));
            }
            case "bomb" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(1, global.players[playerID].figures.get(figureID));
            }
            case "PlusToThree" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(6, global.players[playerID].figures.get(figureID));
            }
            case "MoveFour" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(4, global.players[playerID].figures.get(figureID));
            }
            case "Switch" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(7, global.players[playerID].figures.get(figureID));
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
