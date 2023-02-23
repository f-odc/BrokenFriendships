package model.player;

import model.board.fields.BoardField;
import model.board.fields.IField;
import model.board.objects.Figure;
import model.game.logic.MoveLogic;
import model.global;
import java.util.ArrayList;
import java.util.List;


public class Player {

    private int id;
    private List<Figure> figures = new ArrayList<>();
    private List<BoardField> homeFields;
    private List<BoardField> baseFields;
    private IField startField;
    private int startPoint;
    private int endPoint;
    private String color;

    /**
     * Erstellt einen Spieler und seine Spielfiguren
     * @param id player id
     */
    public Player(int id) {
        this.id = id;
        setColor();

        this.startPoint = id * 10;
        // 40 = length of all board fields
        // prevent negative endpoint
        this.endPoint = (this.startPoint - 1 + 40) % 40;

        // get home and base fields
        getOwnFields();
        // set startfield
        setStartField();
        // create 4 figures
        createFigures();
        // set figures onto home fields
        setFigures();
    }

    /**
     * Set color of player depending on the id
     */
    private void setColor() {
        switch (this.id) {
            case 0:
                color = "red";
                return;
            case 1:
                color = "yellow";
                return;
            case 2:
                color = "blue";
                return;
            case 3:
                color = "green";
                return;
            default:
                throw new RuntimeException("Wrong ID in Player Creation!");
        }
    }

    /**
     * Get Home and Base Fields of the Board
     */
    private void getOwnFields() {
        this.homeFields = global.BOARD.getHomeFields(id);
        this.baseFields = global.BOARD.getBaseFields(id);
    }

    /**
     * Create 4 player figures
     */
    private void createFigures() {
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
     * Get a specific player figure
     *
     * @param i figure ID
     * @return Figure with the specific ID
     */
    public Figure getFigure(int i) {
        return figures.get(i);
    }

    /**
     * Set the start field
     *
     * @return BoardField used as start field for the player
     */
    public IField setStartField() {
        startField = global.BOARD.getPlayField(startPoint);
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
     * Get one of the base fields
     * @param i index of the base field
     * @return base field on index i
     */
    public BoardField getBaseField(int i) {
        return baseFields.get(i);
    }

    /**
     * Get one of the home fields
     * @param i index of the home field
     * @return home field on index i
     */
    public BoardField getHomeField(int i) {
        return homeFields.get(i);
    }

    /**
     * Checks if player has won the game
     * @return true if player has won, else false
     */
    public boolean hasWon(){
        boolean hasWon = true;
        for (BoardField field : baseFields){
            hasWon = hasWon & field.isOccupied();
        }
        return hasWon;
    }

    /**
     * Check if the player can move theoretically
     * @return true if a movable field exists, else false
     */
    public boolean couldMove(int step){
        for (Figure fig : figures){
            // move one step to check if movement is possible
            if (!MoveLogic.getMovableField(fig.getCurrentField(),step).isEmpty()){
                return true;
            }
        }
        return false;
    }

}
