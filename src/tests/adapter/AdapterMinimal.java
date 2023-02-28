package tests.adapter;

import model.board.Board;

public class AdapterMinimal extends AdapterTemplate{

    /**
     * Initializes game in testing mode
     */
    public AdapterMinimal() {
        super();
    }

    public Board initializeBoard() {
        return new Board();
    }
}
