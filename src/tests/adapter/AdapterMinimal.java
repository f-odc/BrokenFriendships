package tests.adapter;

import eea.engine.entity.Entity;
import model.board.Board;
import model.global;
import org.newdawn.slick.geom.Vector2f;

public class AdapterMinimal extends AdapterTemplate {

    /**
     * Initializes game in testing mode
     */
    public AdapterMinimal() {
        super();
    }

    public Board initializeBoard() {
        return new Board();
    }

    public int throwDice() {
        return global.BOARD.getDice().throwDice();
    }

    public int getDiceValue() {
        return global.BOARD.getDice().getValue();
    }

    public Vector2f getDicePosition() {
        return global.BOARD.getDice().getCurrentPosition();
    }

    public void setDicePosition(int i) {
        global.BOARD.getDice().setPosition(i);
    }

    public Vector2f calculateDicePositions(int i, int j){
        return Board.getMidPoint(i , j);
    }

    public Entity getDiceEntity(){
        return global.entityManager.getEntity(global.GAMEPLAY_STATE, "dice");
    }
}
