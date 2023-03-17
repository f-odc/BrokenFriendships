package tests.adapter;

import java.util.ArrayList;
import java.util.List;

public class BFTestAdapterExtended1 extends BFTestAdapterMinimal {

    /* ***************************************************
     * ****** Anzeigen der Bewegungsmöglichkeiten ********
     * *************************************************** */

    /**
     * Gebe eine Liste an Indizes zurück. Diese sind von den Felder, welche sich eine Figur mit gegebenen Würfelwurf bewegen könnte.
     * @param playerID ID des spielers
     * @param figureID ID der Figur
     * @param diceThrow Würfelwurf
     * @return List an Indizes
     */
    public List<Integer> displayField(int playerID, int figureID, int diceThrow) {
        //TODO
        List<Integer> returnList = new ArrayList<>();
        returnList.add(1);
        return returnList;
    }
}
