package model.board;

import model.board.fields.PlayerColorFields;
import model.board.fields.IField;
import model.board.objects.Dice;
import model.enums.Color;
import model.board.fields.BoardField;
import model.enums.FieldType;
import model.global;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class implementing the play board and its logic
 */
public class Board {

    private int[][] boardTemplate;
    private static int xOffset;
    private static int stepSize;
    private Vector2f[] dicePositions;
    private Dice dice;
    public PlayerColorFields bases;
    public PlayerColorFields homes;
    public BoardField[] gameFields;
    private int numPlayers;
    private int boardSize;

    public Board(int numPlayers) {
        this.numPlayers = Math.max(2, Math.min(8, numPlayers)); // Ensure between 2-8 players
        this.boardSize = calculateBoardSize(this.numPlayers);
        this.boardTemplate = generateBoardTemplate(this.numPlayers, this.boardSize);
        this.gameFields = new BoardField[boardSize * 4];
        initSizeAndOffset();
        initBoardFields();
        initDice();
    }

    private int calculateBoardSize(int numPlayers) {
        if (numPlayers <= 4) return 11;
        if (numPlayers <= 6) return 15;
        return 19;
    }

    private int[][] generateBoardTemplate(int numPlayers, int size) {
        int[][] template = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                template[i][j] = -1; // Default to no field
            }
        }
        
        int pathSize = size - 2;
        int numFields = pathSize * 4;
        
        for (int i = 0; i < numFields; i++) {
            int x = (i < pathSize) ? i + 1 : (i < 2 * pathSize) ? pathSize : (i < 3 * pathSize) ? (2 * pathSize - i) : 1;
            int y = (i < pathSize) ? 1 : (i < 2 * pathSize) ? (i - pathSize + 1) : (i < 3 * pathSize) ? pathSize : (4 * pathSize - i);
            template[y][x] = i;
        }
        
        return template;
    }

    //private methods to build up the board

    /**
     * Funktion um die Größe der Felder und den offset des Spielbrettes auszurechnen.
     */
    private void initSizeAndOffset() {
        xOffset = global.X_DIMENSIONS / 2 - global.Y_DIMENSIONS / 2;
        stepSize = global.Y_DIMENSIONS / boardSize;
    }

    /**
     * Funktion um das Feld mit den einzelnen Feldern zu initialisieren.
     */
    private void initBoardFields() {
        this.bases = new PlayerColorFields();
        this.homes = new PlayerColorFields();
        this.dicePositions = new Vector2f[numPlayers];

        for (int j = 0; j < boardSize; j++) {
            for (int i = 0; i < boardSize; i++) {
                int type = boardTemplate[j][i];
                Color color = type >= 0 ? getFieldColor(new Vector2f(i, j)) : Color.NONE;

                BoardField boardtmp;
                Vector2f pos = getMidPoint(i, j);
                switch (type) {
                    case -4 -> dicePositions[diceIndex(i, j)] = pos;
                    case -3 -> {
                        boardtmp = new BoardField(pos, type, color, FieldType.BASE);
                        bases.add(boardtmp, color);
                    }
                    case -2 -> {
                        boardtmp = new BoardField(pos, type, color, FieldType.HOME);
                        homes.add(boardtmp, color);
                    }
                    case -1 -> {}
                    default -> {
                        FieldType field = (type % 10 == 0) ? FieldType.START : FieldType.STANDARD;
                        boardtmp = new BoardField(pos, type, color, field);
                        gameFields[type] = boardtmp;
                    }
                }
            }
        }
        bases.initCorrectOrder();
    }

    /**
     * Initializes the dice
     */
    private void initDice() {
        this.dice = new Dice(this.dicePositions);
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

    /**
     * calculate the mid-point of a board cell
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
        return bases.getFieldsFromId(id);
    }

    /**
     * Get the current board field on specific index
     *
     * @param index board field number
     * @return BoardField of the specific index
     */
    public BoardField getGameField(int index) {
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

    /**
     * Get +1 / -1 neighbors from the given field
     *
     * @param field current field, on the basis of which the neighbors are calculated
     * @return ArrayList with fields next to the field
     */
    public ArrayList<IField> getNeighbors(IField field) {
        int fieldIndex = field.getFieldIndex();
        ArrayList<IField> neighbors = new ArrayList<>();
        if (fieldIndex < 0) return neighbors;
        neighbors.add(gameFields[(fieldIndex + 1) % 40]);
        neighbors.add(fieldIndex == 0 ? gameFields[39] : gameFields[(fieldIndex - 1) % 40]);
        return neighbors;
    }

}
