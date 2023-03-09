package tests.adapter;

import model.board.fields.IField;
import model.game.logic.MoveLogic;
import model.global;

import java.util.List;
import java.util.stream.Collectors;

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
        return MoveLogic.getMovableField(global.players[playerID].figures.get(figureID).getCurrentField(), diceThrow).stream().map(IField::getFieldIndex).collect(Collectors.toList());
    }

    /**
     * Gebe den Index einer Figur zurück.
     *
     * @param playerID ID des Spielers
     * @param figureID ID der Figur
     * @return Index der Figur
     */
    public int getFigureIndex(int playerID, int figureID) {
        return global.players[playerID].figures.get(figureID).getCurrentField().getFieldIndex();
    }
}
