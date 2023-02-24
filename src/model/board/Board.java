package model.board;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.BoardFieldAction;
import model.board.fields.PlayerColorFields;
import model.board.fields.IField;
import model.board.objects.Dice;
import model.enums.Color;
import model.board.fields.BoardField;
import model.enums.Field;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasse welches das Spielbrett und die darin vorhande Logik implementiert.
 */
public class Board {

    //-2 == house-field, -3 == base-field, -1 == no field, -4 == dice field
    //all positive values are standard fields in the correct order
    private final int[][] boardTemplate = {
            {-2, -2, -1, -1, 8, 9, 10, -1, -1, -2, -2},
            {-2, -2, -1, -1, 7, -3, 11, -1, -1, -2, -2},
            {-1, -4, -1, -1, 6, -3, 12, -1, -1, -4, -1},
            {-1, -1, -1, -1, 5, -3, 13, -1, -1, -1, -1},
            {0, 1, 2, 3, 4, -3, 14, 15, 16, 17, 18},
            {39, -3, -3, -3, -3, -1, -3, -3, -3, -3, 19},
            {38, 37, 36, 35, 34, -3, 24, 23, 22, 21, 20},
            {-1, -1, -1, -1, 33, -3, 25, -1, -1, -1, -1},
            {-1, -4, -1, -1, 32, -3, 26, -1, -1, -4, -1},
            {-2, -2, -1, -1, 31, -3, 27, -1, -1, -2, -2},
            {-2, -2, -1, -1, 30, 29, 28, -1, -1, -2, -2},
    };
    private static int xOffset; //offset to center the board on the screen
    private static int stepSize;  //width and height of a board cell
    private Vector2f[] dicePositions; //positions of dice, relative to screen-size
    private Dice dice;  //the current dice object
    private PlayerColorFields bases;  //base fields
    private PlayerColorFields homes;  //house fields
    private final IField[] gameFields = new IField[40]; //Standard- und Start-fields

    public Board() {
        //initialize size and offset
        initSizeAndOffset();

        //initialize the board fields
        initBoardFields();

        //initialize the dice
        initDice();
    }


    //private methods to build up the board

    /**
     * Funktion um die Größe der Felder und den offset des Spielbrettes auszurechnen.
     */
    private void initSizeAndOffset() {
        //initialize offset
        xOffset = global.X_DIMENSIONS / 2 - global.Y_DIMENSIONS / 2;

        //initialize step size
        stepSize = global.Y_DIMENSIONS / global.NUM_OF_FIELDS;
    }

    /**
     * Funktion um das Feld mit den einzelnen Feldern zu initialisieren.
     */
    private void initBoardFields() {
        //initialize house and base fields
        this.bases = new PlayerColorFields();
        this.homes = new PlayerColorFields();
        this.dicePositions = new Vector2f[4];

        //initialize entities for every field
        //iterates through every field of the template
        for (int j = 0; j < global.NUM_OF_FIELDS; j++) {
            for (int i = 0; i < global.NUM_OF_FIELDS; i++) {
                //get field type from template
                int type = boardTemplate[j][i];
                //get color for the field
                Color color = type < -1 ?
                        getFieldColor(new Vector2f(i, j)) :
                        //house of base field
                        (type == 0 || type == 10 || type == 20 || type == 30) ? getFieldColor(new Vector2f(i, j)) :
                                //standard field
                                Color.NONE;

                BoardField boardtmp;
                switch (type) {
                    case -4 -> {
                        //initialize the positions for the dice
                        dicePositions[(i == 1 && j == 2) ? 0 :
                                (i == 9 && j == 2) ? 1 :
                                        (i == 9 && j == 8) ? 2 : 3] = getMidPoint(i, j);
                        continue;
                    }
                    case -3 -> {
                        //initialize entity for the base fields
                        boardtmp = new BoardField(i, j, type, color, Field.BASE);
                        bases.add(boardtmp, color);
                    }
                    case -2 -> {
                        //initialize entity for the home fields
                        boardtmp = new BoardField(i, j, type, color, Field.HOME);
                        homes.add(boardtmp, color);
                    }
                    case -1 -> {
                        //no field
                        continue;
                    }
                    default -> {
                        //initialize standard and start fields and their entity
                        Field field = (type == 0 || type == 10 || type == 20 || type == 30) ? Field.START : Field.STANDARD;
                        boardtmp = new BoardField(i, j, type, color, field);
                        gameFields[type] = boardtmp;
                    }
                }

                //add action to entity
                ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                clickEvent.addAction(new BoardFieldAction(boardtmp));
                boardtmp.getBaseEntity().addComponent(clickEvent);
            }
        }
        //reverse order of bases, to make later access easier
        bases.initCorrectOrder();
    }

    /**
     * Initializes the dice
     */
    private void initDice() {
        this.dice = new Dice(this.dicePositions);
    }

    /**
     * calculate ht emid point of a board cell
     *
     * @param i x-index
     * @param j y-index
     * @return Vector2f with x and y coordinates of the mid-point
     */
    public static Vector2f getMidPoint(int i, int j) {
        //bottom-left point of the cell
        Vector2f start = new Vector2f(i * stepSize + xOffset, j * stepSize);
        //top-right point of the cell
        Vector2f end = new Vector2f((i + 1) * stepSize + xOffset, (j + 1) * stepSize);
        return new Vector2f((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Get the color for a field, according to the fields position
     *
     * @param point coordinates of the field
     * @return color of the field
     */
    private Color getFieldColor(Vector2f point) {
        return point.getX() <= 4 && point.getY() <= 5 ? Color.RED :
                point.getX() >= 5 && point.getY() <= 4 ? Color.YELLOW :
                        point.getX() <= 5 && point.getY() >= 4 ? Color.GREEN :
                                point.getX() >= 5 && point.getY() >= 5 ? Color.BLUE :
                                        Color.NONE;
    }

    // public methods to interact with the board

    /**
     * Get the dice
     *
     * @return the dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Get the home fields from the board for a specific player
     *
     * @param id player id
     * @return List of all home fields
     */
    public List<BoardField> getHome(int id) {
        return homes.getFieldsFromId(id);
    }

    /**
     * Get all base fields from a player
     *
     * @param id player id
     * @return List of the base fields
     */
    public List<BoardField> getBase(int id) {
        return switch (id) {
            case 0 -> bases.red;
            case 1 -> bases.yellow;
            case 2 -> bases.blue;
            case 3 -> bases.green;
            default -> throw new RuntimeException("Wrong ID");
        };
    }

    /**
     * Get the index of a gamefield in gamefields, according to postition
     *
     * @param pos position of field
     * @return index belonging to the field, -1 if there is no field at the position
     */
    public int getIndexFromPosition(Vector2f pos) {
        for (int i = 0; i < gameFields.length; i++) {
            //find field with correct position
            if (gameFields[i].getPosition().getX() == pos.getX() &&
                    gameFields[i].getPosition().getY() == pos.getY()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get the current board field on specific index
     *
     * @param index board field number
     * @return BoardField of the specific index
     */
    public IField getGameField(int index) {
        int tmpIndex = index < 0 ? -1 * index : index;
        if (tmpIndex >= gameFields.length) return gameFields[tmpIndex % gameFields.length];
        else return gameFields[tmpIndex];
    }


    /**
     * Get all play fields, which are not occupied by a game object and are no start fields
     *
     * @return ArrayList<IField> with empty game fields
     */
    public ArrayList<IField> getEmptyGameFields() {
        ArrayList<IField> emptyFields = new ArrayList<>();
        for (IField field : gameFields) {
            if (!field.isOccupied() && !field.isPlayerStartField()) {
                emptyFields.add(field);
            }
        }
        return emptyFields;
    }

    /**
     * Get all play fields which are occupied from figures
     *
     * @param exceptions array of fields which should be excluded from the list, can be empty
     * @return ArrayList<IField> with from figure occupied game fields
     */
    public ArrayList<IField> getOccupiedGameFields(IField... exceptions) {
        ArrayList<IField> exceptionFields = new ArrayList<>(Arrays.asList(exceptions));

        ArrayList<IField> occupiedFields = new ArrayList<>();
        for (IField field : gameFields) {
            if (field.getCurrentFigure() != null && !exceptionFields.contains(field)) {
                occupiedFields.add(field);
            }
        }
        return occupiedFields;
    }

}
