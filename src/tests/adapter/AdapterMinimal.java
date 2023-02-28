package tests.adapter;

import model.board.Board;
import model.board.objects.Dice;
import model.global;

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

    public Dice getDice() {
        return global.BOARD.getDice();
    }

    public String getDiceEntityID() {
        return "Dice";
    }
}
