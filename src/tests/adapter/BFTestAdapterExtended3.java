package tests.adapter;


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
                break;
            }
            case "bomb" : {
                //TODO
                break;
            }
            case "PlusToThree" : {
                //TODO
                break;
            }
            case "MoveFour" : {
                //TODO
                break;
            }
            case "Switch" : {
                //TODO
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
        return false;
    }

    /**
     * Gebe zurück, ob auf diesem Feld ein Bett platziert ist.
     *
     * @param index des Feldes
     * @return True, wenn auf dem Feld ein Bett ist. False, wenn nicht.
     */
    public boolean isOccupiedByBed(int index) {
        return false;
    }

    /**
     * Gebe zurück, ob auf diesem Feld eine Bombe platziert ist.
     *
     * @param index des Feldes
     * @return True, wenn auf dem Feld eine Bombe ist. False, wenn nicht.
     */
    public boolean isOccupiedByBomb(int index) {
        return false;
    }

    /**
     * Wähle ein Feld aus. Simuliert das auswählen eines Feldes durch den Spieler.
     *
     * @param fieldID ID des Feldes
     */
    public void selectField(int fieldID) {
    }

    /**
     * Wähle zwei Felder aus. Simuliert das auswählen der Felder durch den Spieler.
     *
     * @param fieldID1 ID des ersten Feldes.
     * @param fieldID2 ID des zweiten Feldes.
     */
    public void selectTwoFields(int fieldID1, int fieldID2) {
    }

    /**
     * Wähle ein Zielfeld aus. Simuliert das auswählen eines Feldes durch den Spieler.
     *
     * @param playerID ID des Spielers
     * @param fieldID  ID des Feldes
     */
    public void selectBaseField(int playerID, int figureID, int fieldID) {
    }
}
