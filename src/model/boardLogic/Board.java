package model.boardLogic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.LogAction;
import model.enums.Color;
import model.enums.Field;
import model.fields.ColoredField;
import model.fields.StandardField;
import model.global;
import model.interfaces.IGameField;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse welches das Spielbrett und die darin vorhande Logik implementiert.
 */
public class Board {

    //-2 == Haus-Feld, -3 == Ziel-Feld, -1 == kein Feld
    //alles andere sind standardfelder in der richtigen Reihenfolge
    static public int[][] boardTemplate = {
            {-2, -2, -1, -1, 38, 39, 0, -1, -1, -2, -2},
            {-2, -2, -1, -1, 37, -3, 1, -1, -1, -2, -2},
            {-1, -1, -1, -1, 36, -3, 2, -1, -1, -1, -1},
            {-1, -1, -1, -1, 35, -3, 3, -1, -1, -1, -1},
            {30, 31, 32, 33, 34, -3, 4, 5, 6, 7, 8},
            {29, -3, -3, -3, -3, -1, -3, -3, -3, -3, 9},
            {28, 27, 26, 25, 24, -3, 14, 13, 12, 11, 10},
            {-1, -1, -1, -1, 23, -3, 15, -1, -1, -1, -1},
            {-1, -1, -1, -1, 22, -3, 16, -1, -1, -1, -1},
            {-2, -2, -1, -1, 21, -3, 17, -1, -1, -2, -2},
            {-2, -2, -1, -1, 20, 19, 18, -1, -1, -2, -2},
    };

    private Dice dice;

    public FieldCluster bases;  //Zielfelder

    public FieldCluster homes;  //Hausfelder

    public IGameField[] gameFields = new IGameField[40]; //Standard- und Startfelder

    public Board(StateBasedEntityManager entityManager, int stateID) throws SlickException {
        //Initialisieren von den Haus und Ziel Feldern
        this.bases = new FieldCluster();
        this.homes = new FieldCluster();

        //Initialisieren der Spielbrett-Dimensionen
        int dimensions = Toolkit.getDefaultToolkit().getScreenSize().height;

        //offset um das Spielbrett in die Mitte des Fensters zu schieben
        int offsetX = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - dimensions / 2;

        //Schrittweite in die Breite
        int xSteps = dimensions / global.NUM_OF_FIELDS;
        //Schrittweite in die Höhe
        int ySteps = dimensions / global.NUM_OF_FIELDS;

        //initialisieren der Entities für jedes Feld
        //durchläuft das boardTemplate um zu erkennen welche Felder hinzugefügt werden
        for (int j = 0; j < global.NUM_OF_FIELDS; j++) {    //Y-Richtung
            for (int i = 0; i < global.NUM_OF_FIELDS; i++) {    //X-Richtung
                Point displayStartPoint = new Point(i * xSteps + offsetX, j * ySteps);
                Point displayEndPoint = new Point((i + 1) * xSteps + offsetX, (j + 1) * ySteps);

                int type = boardTemplate[i][j];
                Color color = type < 0 ? getFieldColor(new Point(i, j)) :   //Haus- oder Zielfeld
                        (type == 0 || type == 10 || type == 20 || type == 30) ? getFieldColor(new Point(i, j)) :    //Startfelder
                                Color.NONE; //Standardfelder
                IGameField tmpField = null;

                //zuweisung von den verschiedenen Feldern und hinzufügen zu den Felderlisten
                switch (type) {
                    case -3 -> {
                        //Ziel Feld
                        tmpField = new ColoredField(color,
                                displayStartPoint, displayEndPoint,
                                new Point(i, j), Field.BASE);
                        switch (color) {
                            case RED -> bases.red.add((ColoredField) tmpField);
                            case BLUE -> bases.blue.add((ColoredField) tmpField);
                            case YELLOW -> bases.yellow.add((ColoredField) tmpField);
                            case GREEN -> bases.green.add((ColoredField) tmpField);
                        }
                    }
                    case -2 -> {
                        //Home Feld
                        tmpField = new ColoredField(color,
                                displayStartPoint, displayEndPoint,
                                new Point(i, j), Field.HOME);
                        switch (color) {
                            case RED -> homes.red.add((ColoredField) tmpField);
                            case BLUE -> homes.blue.add((ColoredField) tmpField);
                            case YELLOW -> homes.yellow.add((ColoredField) tmpField);
                            case GREEN -> homes.green.add((ColoredField) tmpField);
                        }
                    }
                    case -1 -> {
                        //no Field
                    }
                    default -> {
                        if (type == 0 || type == 10 || type == 20 || type == 30) {
                            //Start Feld
                            tmpField = new ColoredField(color,
                                    displayStartPoint, displayEndPoint,
                                    new Point(i, j), Field.START);
                            gameFields[type] = tmpField;
                        } else {
                            //Standard Feld
                            System.out.println("x: " + i + " y: " + j);
                            tmpField = new StandardField(displayStartPoint, displayEndPoint, new Point(i, j));
                            gameFields[type] = tmpField;
                        }
                    }
                }

                //Würfel Position
                if (i == 10 && j == 2) {
                    tmpField = new StandardField(displayStartPoint, displayEndPoint, new Point(10, 2));
                    this.dice = new Dice(1, tmpField.getDisplayPosition(), stateID, entityManager);
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
                    if (tmpField.getDisplayPosition().getX() == 10 && tmpField.getDisplayPosition().getY() == 2) {
                        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                        clickEvent.addAction(new LogAction(tmpField.getDisplayPosition(), tmpField.getColor()));
                        point.addComponent(clickEvent);
                    }
                    entityManager.addEntity(stateID, point);
                }
            }
        }
        //TODO delete
        for (IGameField gameField : gameFields) {
            System.out.println(gameField.getColor() + "|" + gameField.getBoardPosition().getX() + "," + gameField.getBoardPosition().getY());
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
     * @return der Würfel
     */
    public Dice getDice() {
        return dice;
    }

    //TODO implement and set parameter fields to private
    public IGameField getField(Point point) {
        return null;
    }
}
