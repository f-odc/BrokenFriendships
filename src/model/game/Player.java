package model.game;

import model.boardLogic.fields.BoardField;
import model.boardLogic.fields.IField;
import model.boardLogic.objects.Figure;
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

    public Player(int id) {
        this.id = id;
        setColor();

        this.startPoint = id * 10;
        // attention if startpoint = 0
        // 40 = length of all board fields
        // prevent negative endpoint
        this.endPoint = (this.startPoint - 1 + 40) % 40;


        getFields();

        setStartField();

        createFigures();

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
    private void getFields() {
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

    public int getEndPoint() {
        return endPoint;
    }

    public BoardField getBaseField(int i) {
        return baseFields.get(i);
    }

    public BoardField getHomeField(int i) {
        return homeFields.get(i);
    }
}
