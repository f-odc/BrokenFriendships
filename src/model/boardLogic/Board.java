package model.boardLogic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.LogAction;
import model.enums.Color;
import model.enums.Field;
import model.fields.GameField;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.awt.Toolkit;

/**
 * Klasse welches das Spielbrett und die darin vorhande Logik implementiert.
 */
public class Board {

    //-2 == Haus-Feld, -3 == Ziel-Feld, -1 == kein Feld
    //alles andere sind standardfelder in der richtigen Reihenfolge
    //nur mit accessTemplate() Funktion drauf zugreifen
    private final int[][] boardTemplate = {
            {-2, -2, -1, -1, 10, 11, 12, -1, -1, -2, -2},
            {-2, -2, -1, -1, 9, -3, 13, -1, -1, -2, -2},
            {-1, -1, -1, -1, 8, -3, 14, -1, -1, -1, -1},
            {-1, -1, -1, -1, 7, -3, 15, -1, -1, -1, -1},
            {2, 3, 4, 5, 6, -3, 16, 17, 18, 19, 20},
            {1, -3, -3, -3, -3, -1, -3, -3, -3, -3, 21},
            {0, 39, 38, 37, 36, -3, 26, 25, 24, 23, 22},
            {-1, -1, -1, -1, 35, -3, 27, -1, -1, -1, -1},
            {-1, -1, -1, -1, 34, -3, 28, -1, -1, -1, -1},
            {-2, -2, -1, -1, 33, -3, 29, -1, -1, -2, -2},
            {-2, -2, -1, -1, 32, 31, 30, -1, -1, -2, -2},
    };
    private int xOffset;
    private int xStep;
    private int yStep;
    private Dice dice;
    private FieldCluster bases;  //Zielfelder
    private FieldCluster homes;  //Hausfelder
    private GameField[] gameFields = new GameField[40]; //Standard- und Startfelder

    public Board() throws SlickException {
        //initialisiere die Feld größe und Offset des Spielbrettes
        initSizeAndOffset();

        //initialisiere die Felder des Spielbrettes
        initBoardFields();

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
        //Initialisieren von den Haus- und Zielfeldern
        this.bases = new FieldCluster();
        this.homes = new FieldCluster();

        //initialisieren der Entities für jedes Feld
        //durchläuft das boardTemplate um zu erkennen welche Felder hinzugefügt werden
        for (int j = 0; j < global.NUM_OF_FIELDS; j++) {    //Y-Richtung
            for (int i = 0; i < global.NUM_OF_FIELDS; i++) {    //X-Richtung
                Point displayStartPoint = new Point(i * xStep + xOffset, j * yStep);
                Point displayEndPoint = new Point((i + 1) * xStep + xOffset, (j + 1) * yStep);

                int type = accessGameTemplate(i, j);
                Color color = type < -1 ? getFieldColor(new Point(i, j)) :   //Haus- oder Zielfeld
                        (type == 0 || type == 10 || type == 20 || type == 30) ? getFieldColor(new Point(i, j)) :    //Startfelder
                                Color.NONE; //Standardfelder
                GameField tmpField = null;

                //zuweisung von den verschiedenen Feldern und hinzufügen zu den Felderlisten
                switch (type) {
                    case -3 -> {
                        //Ziel Feld
                        tmpField = new GameField(color,
                                displayStartPoint, displayEndPoint,
                                new Point(i, j), Field.BASE);
                        bases.add(tmpField);
                    }
                    case -2 -> {
                        //Home Feld
                        tmpField = new GameField(color,
                                displayStartPoint, displayEndPoint,
                                new Point(i, j), Field.HOME);
                        homes.add(tmpField);
                    }
                    case -1 -> {
                        //no Field
                    }
                    default -> {
                        if (type == 0 || type == 10 || type == 20 || type == 30) {
                            //Start Feld
                            tmpField = new GameField(color,
                                    displayStartPoint, displayEndPoint,
                                    new Point(i, j), Field.START);
                            gameFields[type] = tmpField;
                        } else {
                            //Standard Feld
                            tmpField = new GameField(Color.NONE,
                                    displayStartPoint, displayEndPoint,
                                    new Point(i, j), Field.STANDARD);
                            gameFields[type] = tmpField;
                        }
                    }
                }

                //Würfel initialisieren
                if (i == 10 && j == 2) {
                    tmpField = new GameField(Color.NONE, displayStartPoint, displayEndPoint, new Point(10, 2), Field.STANDARD);
                    this.dice = new Dice(1, tmpField.getDisplayPosition());
                }

                //Zuweisen des richtigen Bildes
                if (tmpField != null) {
                    Point displayPosition = tmpField.getDisplayPosition();
                    //Erstellen einer neuen Entity
                    Entity point = new Entity("grid point" + tmpField.getDisplayPosition().getX());
                    point.setPosition(new Vector2f(displayPosition.getX(), displayPosition.getY()));
                    if (i != 10 || j != 2 /*Würfel Position*/)
                        point.addComponent(new ImageRenderComponent(new Image(getImg(color))));
                    point.setScale(tmpField.getSize());
                    //Hinzufügen von Action zur Entity
                    //TODO change to receive click action
                    if (tmpField.getDisplayPosition().getX() != 10 || tmpField.getDisplayPosition().getY() != 2) {
                        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                        clickEvent.addAction(new LogAction(new Point(i, j), tmpField.getColor()));
                        point.addComponent(clickEvent);
                    }
                    global.entityManager.addEntity(global.GAMEPLAY_STATE, point);
                }
            }
        }
    }

    /**
     * Funktion die einem Feld eine Farbe zuweist.
     *
     * @param point Koordinaten des Feldes
     * @return die Farbe des feldes
     */
    private Color getFieldColor(Point point) {
        return point.getX() <= 5 && point.getY() <= 4 ? Color.RED :
                point.getX() >= 5 && point.getY() <= 5 ? Color.YELLOW :
                        point.getX() <= 4 && point.getY() >= 5 ? Color.GREEN :
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
        return color == Color.RED ? "assets/redField.png" :
                color == Color.BLUE ? "assets/blueField.png" :
                        color == Color.YELLOW ? "assets/yellowField.png" :
                                color == Color.GREEN ? "assets/greenField.png" :
                                        "assets/standardField.png";

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
    public GameField getField(Point point) {
        int fieldType = accessGameTemplate(point.getX(), point.getY());
        Color color = getFieldColor(point);
        switch (fieldType) {
            case -3 -> {
                System.out.println("Zielfeld");
                return bases.get(color, point).isPresent() ? bases.get(color, point).get() : null;
            }
            case -2 -> {
                System.out.println("Hausfeld");
                return homes.get(color, point).isPresent() ? homes.get(color, point).get() : null;
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
    private int accessGameTemplate(int x, int y) {
        return boardTemplate[y][x];
    }
}
