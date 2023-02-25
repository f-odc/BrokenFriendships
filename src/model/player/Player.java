package model.player;

import model.board.fields.BoardField;
import model.board.fields.IField;
import model.board.objects.Figure;
import model.board.objects.specials.BedSpecial;
import model.enums.Color;
import model.game.logic.MoveLogic;
import model.global;

import java.util.ArrayList;
import java.util.List;


public class Player {

    private int id;
    private BedSpecial activeBedSpecial;
    private List<Figure> figures = new ArrayList<>();
    private List<BoardField> homeFields;
    private List<BoardField> baseFields;
    private IField startField;
    private int startPoint;
    private int endPoint;

    /**
     * create a player and it's figures
     *
     * @param id player id
     */
    public Player(int id) {
        this.id = id;

        this.startPoint = id * 10;
        // 40 = length of all board fields
        // prevent negative endpoint
        this.endPoint = (this.startPoint - 1 + 40) % 40;

        // get home and base fields
        initOwnFields();
        // set startfield
        setStartField();
        // create 4 figures
        createFigures();
        // set figures onto home fields
        setFigures();
    }

    /**
     * Get Home and Base Fields of the Board
     */
    private void initOwnFields() {
        this.homeFields = global.BOARD.getHome(id);
        this.baseFields = global.BOARD.getBase(id);
    }

    /**
     * Create 4 player figures
     */
    private void createFigures() {
        Color color = this.id == 0 ? Color.RED : this.id == 1 ? Color.YELLOW : this.id == 2 ? Color.BLUE : Color.GREEN;

        for (int i = 0; i < 4; i++) {
            Figure fig = new Figure(id, i, color, startField, homeFields.get(i));
            this.figures.add(fig);
        }
    }

    /**
     * Place player figures on home fields on board
     */
    private void setFigures() {
        for (int i = 0; i < 4; i++) {
            homeFields.get(i).setGameObject(figures.get(i));
        }
    }

    /**
     * Set the start field
     * @return BoardField used as start field for the player
     */
    public IField setStartField() {
        startField = global.BOARD.getGameField(startPoint);
        return startField;
    }

    /**
     * Get the field from which the player can move to its base
     * @return index of the field before the base
     */
    public int getEndPoint() {
        return endPoint;
    }

    /**
     * Checks if player has won the game
     * @return true if player has won, else false
     */
    public boolean hasWon() {
        boolean hasWon = true;
        for (BoardField field : baseFields) {
            hasWon = hasWon & field.isOccupied();
        }
        return hasWon;
    }

    /**
     * Check if the player can move theoretically
     * @return true if a movable field exists, else false
     */
    public boolean couldMove(int step) {
        for (Figure fig : figures) {
            // move one step to check if movement is possible
            if (!MoveLogic.getMovableField(fig.getCurrentField(), step).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move a figure out of start if possible
     */
    public void moveOut() {
        // if start field is occupied from own figure -> cannot move out
        if (startField.getCurrentFigure() != null && startField.getCurrentFigure().getOwnerID() == id) {
            return;
        }
        // else start field is free
        for (Figure fig : figures) {
            if (fig.getCurrentField().isHomeField()) {
                // move to start field
                fig.moveTo(startField, false);
                return;
            }
        }
    }

    /**
     * Get the active bed object
     *
     * @return bed if active, else null
     */
    public BedSpecial getActiveBed() {
        return activeBedSpecial;
    }

    /**
     * Set the active bed object
     */
    public void setBedSpecial(BedSpecial bed) {
        activeBedSpecial = bed;
    }

}
