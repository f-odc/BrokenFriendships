package tests.adapter;

import model.board.fields.BoardField;
import model.board.objects.Figure;
import model.board.objects.Mystery;
import model.board.objects.specials.DeadSpecial;
import model.enums.Color;
import model.enums.FieldType;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.geom.Vector2f;

public class BFTestAdapterExtended2 extends BFTestAdapterExtended1 {

    /* ***************************************************
     * ****** Initialisierung der Mystery Objekte ********
     * *************************************************** */

    public int getNumberOfMysteryObjects() {
        return GameLogic.numOfMysteryFields;
    }

    public void skipTurn(int playerID) {
        GameLogic.nextPlayer();
    }

    public void spawnMystery(int index) {
        GameLogic.spawnMystery(index);
    }

    public void moveTo(int playerID, int FieldID, int index) {
        global.players[playerID].figures.get(FieldID).moveTo(global.BOARD.getGameField(index), false);
    }

    public void forceSimpleMysteryActivate(String type, int playerID, int figureID) {
        switch (type) {
            case "dead" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(2, global.players[playerID].figures.get(figureID));
            }
            case "moveOut" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(5, global.players[playerID].figures.get(figureID));
            }
            case "growing" -> {
                //TODO
                GameLogic.executeInitSpecialsPhase(3, global.players[playerID].figures.get(figureID));
            }
            default -> {
            }
        }
    }
}
