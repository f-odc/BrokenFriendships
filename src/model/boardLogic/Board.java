package model.boardLogic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.FieldSelectedAction;
import model.enums.Color;
import model.boardLogic.fields.BoardField;
import model.enums.Field;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.awt.Toolkit;
import java.util.List;

/**
 * Klasse welches das Spielbrett und die darin vorhande Logik implementiert.
 */
public class Board {

    //-2 == Haus-Feld, -3 == Ziel-Feld, -1 == kein Feld
    //alles andere sind standardfelder in der richtigen Reihenfolge
    //nur mit accessTemplate() Funktion drauf zugreifen
    private final int[][] boardTemplate = {
            {-2, -2, -1, -1, 8, 9, 10, -1, -1, -2, -2},
            {-2, -2, -1, -1, 7, -3, 11, -1, -1, -2, -2},
            {-1, -1, -1, -1, 6, -3, 12, -1, -1, -1, -1},
            {-1, -1, -1, -1, 5, -3, 13, -1, -1, -1, -1},
            {0, 1, 2, 3, 4, -3, 14, 15, 16, 17, 18},
            {39, -3, -3, -3, -3, -1, -3, -3, -3, -3, 19},
            {38, 37, 36, 35, 34, -3, 24, 23, 22, 21, 20},
            {-1, -1, -1, -1, 33, -3, 25, -1, -1, -1, -1},
            {-1, -1, -1, -1, 32, -3, 26, -1, -1, -1, -1},
            {-2, -2, -1, -1, 31, -3, 27, -1, -1, -2, -2},
            {-2, -2, -1, -1, 30, 29, 28, -1, -1, -2, -2},
    };
    private int xOffset;
    private int xStep;
    private int yStep;
    private Vector2f[] dicePositions;
    private Dice dice;
    private FieldCluster bases;  //Zielfelder
    private FieldCluster homes;  //Hausfelder
    private BoardField[] gameFields = new BoardField[40]; //Standard- und Startfelder

    public Board() throws SlickException {
        //initialisiere die Feld größe und Offset des Spielbrettes
        initSizeAndOffset();

        //initialisiere die Felder des Spielbrettes
        initBoardFields();

        //initialisiere den Würfel
        initDice();

    }

    /**
     * Funktion um die Größe der Felder und den offset des Spielbrettes auszurechnen.
     */
    private void initSizeAndOffset() {
        //Initialisieren der Spielbrett-Dimensionen
        int dimensions = Toolkit.getDefaultToolkit().getScreenSize().height;

        //offset um das Spielbrett in die Mitte des Fensters zu schieben
        this.xOffset = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - dimensions / 2;

        //Schrittweite in die Breite
        this.xStep = dimensions / global.NUM_OF_FIELDS;
        //Schrittweite in die Höhe
        this.yStep = dimensions / global.NUM_OF_FIELDS;
    }

    /**
     * Funktion um das Feld mit den einzelnen Feldern zu initialisieren.
     */
    private void initBoardFields() throws SlickException {
        //TODO change startfields
        //Initialisieren von den Haus- und Zielfeldern
        this.bases = new FieldCluster();
        this.homes = new FieldCluster();
        this.dicePositions = new Vector2f[4];


        //initialisieren des spielen buttons

        //initialisieren der Entities für jedes Feld
        //durchläuft das boardTemplate um zu erkennen welche Felder hinzugefügt werden
        for (int j = 0; j < global.NUM_OF_FIELDS; j++) {    //Y-Richtung
            for (int i = 0; i < global.NUM_OF_FIELDS; i++) {    //X-Richtung

                int type = accessGameTemplate(i, j);
                Color color = type < -1 ? getFieldColor(new Vector2f(i, j)) :   //Haus- oder Zielfeld
                        (type == 0 || type == 10 || type == 20 || type == 30) ? getFieldColor(new Vector2f(i, j)) :    //Startfelder
                                Color.NONE; //Standardfelder

                //initialisierung der Würfel felder
                if ((i == 1 && j == 2) || (i == 9 && j == 2) || (i == 9 && j == 8) || (i == 1 && j == 8)) {
                    dicePositions[(i == 1 && j == 2) ? 0 :
                            (i == 9 && j == 2) ? 1 :
                                    (i == 9 && j == 8) ? 2 : 3] = getMidPoint(i, j);
                }

                //continue, wenn kein Feld an diesen Koorinaten existiert
                if (type == -1) continue;

                Entity currentEntity = createEntity(i, j, type, color);
                BoardField boardtmp;
                switch (type) {
                    case -3 -> {
                        //Ziel Feld
                        boardtmp = new BoardField(currentEntity, Field.BASE);
                        bases.add(boardtmp, color);
                    }
                    case -2 -> {
                        //Home Feld
                        boardtmp = new BoardField(currentEntity, Field.HOME);
                        homes.add(boardtmp, color);
                    }
                    default -> {
                        //Startfeld und Standardfeld
                        if (type == 0 || type == 10 || type == 20 || type == 30)
                            boardtmp = new BoardField(currentEntity, Field.START);
                        else boardtmp = new BoardField(currentEntity, Field.STANDARD);
                        gameFields[type] = boardtmp;
                    }
                }

                //Hinzufügen von Action
                //TODO change to actual action
                if (i != 10 || j != 2 /*Würfel Position*/) {
                    ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                    clickEvent.addAction(new FieldSelectedAction(boardtmp));
                    boardtmp.getBaseEntity().addComponent(clickEvent);
                }
            }
        }
        //turn around the order of the green and blue bases, as they are read in an incorrect order
        bases.initCorrectOrder();
    }

    private void initDice() throws SlickException {
        this.dice = new Dice(1, this.dicePositions);
    }

    /**
     * Funktion um eine Entity für ein Feld zu erstellen.
     *
     * @param i     x-Index
     * @param j     y-Index
     * @param type  Art des Feldes aus dem Template.
     * @param color Farbe des Feldes.
     * @return Entity für das Feld
     * @throws SlickException wenn Das Bild des Fledes nicht vorhanden ist
     */
    private Entity createEntity(int i, int j, int type, Color color) throws SlickException {
        Entity fieldEntity = new Entity("gameField:" + i + "," + j);
        Vector2f position = getMidPoint(i, j);
        fieldEntity.setPosition(position);

        if (i != 10 || j != 2 /*Würfel Position*/) {
            //initialisieren das Bild und die Größe der Entity
            fieldEntity.addComponent(new ImageRenderComponent(new Image(getImg(color))));
            fieldEntity.setScale(type == -3 ? global.STANDARD_AND_BASE_FIELD_SIZE :
                    type == -2 ? global.HOME_AND_START_FIELD_SIZE :
                            (type == 0 || type == 10 || type == 20 || type == 30) ? global.HOME_AND_START_FIELD_SIZE :
                                    global.STANDARD_AND_BASE_FIELD_SIZE);
        }

        global.entityManager.addEntity(global.GAMEPLAY_STATE, fieldEntity);
        return fieldEntity;
    }

    /**
     * Funktion um dem Mittleren Punkt eines Feldes auszurechnen.
     *
     * @param i x-Index
     * @param j y-Index
     * @return Vector2f mit den x und y Koorindaten des mittleren Punktes.
     */
    private Vector2f getMidPoint(int i, int j) {
        Vector2f start = new Vector2f(i * xStep + xOffset, j * yStep);
        Vector2f end = new Vector2f((i + 1) * xStep + xOffset, (j + 1) * yStep);
        return new Vector2f((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Funktion die einem Feld eine Farbe zuweist.
     *
     * @param point Koordinaten des Feldes
     * @return die Farbe des feldes
     */
    private Color getFieldColor(Vector2f point) {
        return point.getX() <= 4 && point.getY() <= 5 ? Color.RED :
                point.getX() >= 5 && point.getY() <= 4 ? Color.YELLOW :
                        point.getX() <= 5 && point.getY() >= 4 ? Color.GREEN :
                                point.getX() >= 5 && point.getY() >= 5 ? Color.BLUE :
                                        Color.NONE;
    }

    /**
     * Funktion die einer Farbe ein Bild zuweist.
     *
     * @param color Farbe zurEntscheidung welches Bild verwendet wird.
     * @return der Pfad zum Bild
     */
    private String getImg(Color color) {
        return color == Color.RED ? "assets/field/redField.png" :
                color == Color.BLUE ? "assets/field/blueField.png" :
                        color == Color.YELLOW ? "assets/field/yellowField.png" :
                                color == Color.GREEN ? "assets/field/greenField.png" :
                                        "assets/field/standardField.png";

    }

    /**
     * Funktion um Zugriff auf den Würfel zu bekommen.
     *
     * @return der Würfel
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Funktion um auf die einzelnen Felder des Brettes zuzugreifen.
     *
     * @param point Brettkoordinaten des Feldes.
     * @return Das gewünschte Feld oder null wenn dort kein Feld ist.
     */
    public BoardField getField(Vector2f point) {
        int fieldType = accessGameTemplate(point.getX(), point.getY());
        Color color = getFieldColor(point);
        switch (fieldType) {
            case -3 -> {
                System.out.println("Zielfeld");
                return bases.get(color, getMidPoint((int) point.getX(), (int) point.getY())).get();
            }
            case -2 -> {
                System.out.println("Hausfeld " + point.getX() + "," + point.getY());
                return homes.get(color, getMidPoint((int) point.getX(), (int) point.getY())).get();
            }
            case -1 -> {
                System.out.println("kein Feld");
            }
            default -> {
                System.out.println("Standardfeld mit nummer: " + fieldType);
                return gameFields[fieldType];
            }
        }
        return null;
    }

    /**
     * Funktion um den Zugriff auf Brettelemente einheitlich zu halten.
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return Typ des Feldes.
     */
    private int accessGameTemplate(float x, float y) {
        return boardTemplate[(int) y][(int) x];
    }

    /**
     * Get the home fields from the board for a specific player
     *
     * @param id player id
     * @return List of all home fields
     */
    public List<BoardField> getHomeFields(int id) {
        return homes.getFieldsFromId(id);
    }

    public int getGameFieldsIndex(Vector2f pos) {
        for (int i = 0; i < gameFields.length; i++) {
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
    public BoardField getPlayField(int index) {
        int tmpIndex = index < 0 ? -1 * index : index;
        if (tmpIndex >= gameFields.length) return gameFields[tmpIndex % gameFields.length];
        else return gameFields[tmpIndex];
        //throw new RuntimeException("Index out of Bounds - getPlayField");
    }

    /**
     * Get all base fields from a player
     *
     * @param id player id
     * @return List of the base fields
     */
    public List<BoardField> getBaseFields(int id) {
        switch (id) {
            case 0:
                return bases.red;
            case 1:
                return bases.yellow;
            case 2:
                return bases.blue;
            case 3:
                return bases.green;
            default:
                throw new RuntimeException("Wrong ID");
        }
    }


}
