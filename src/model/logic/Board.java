package model.logic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.fields.HomeField;
import model.fields.StandardField;
import model.fields.StartField;
import model.global;
import model.interfaces.IGameField;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import java.awt.Toolkit;

public class Board {

    //0 == kein Feld, 1 == standard-Feld
    //2 == Haus-Feld, 3 == Start-Feld
    static public int[][] boardTemplate = {
            {2,2,0,0,1,1,3,0,0,2,2},
            {2,2,0,0,1,2,1,0,0,2,2},
            {0,0,0,0,1,2,1,0,0,0,0},
            {0,0,0,0,1,2,1,0,0,0,0},
            {3,1,1,1,1,2,1,1,1,1,1},
            {1,2,2,2,2,0,2,2,2,2,1},
            {1,1,1,1,1,2,1,1,1,1,3},
            {0,0,0,0,1,2,1,0,0,0,0},
            {0,0,0,0,1,2,1,0,0,0,0},
            {2,2,0,0,1,2,1,0,0,2,2},
            {2,2,0,0,3,1,1,0,0,2,2},
    };

    static public IGameField[][] BOARD = new IGameField[global.NUM_OF_FIELDS][global.NUM_OF_FIELDS];

    public Board(StateBasedEntityManager entityManager, int stateID) throws SlickException {
        //Initialisieren des Spielbrettes
        int dimensions = Toolkit.getDefaultToolkit().getScreenSize().height;

        //offset um das Spielbrett in die Mitte des Fensters zu schieben
        int offsetX = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - dimensions / 2;

        //Initialisieren des fields array, mit den einzelnen Feldern des Spielbrettes
        //Schrittweite in die breite
        int xSteps = dimensions / global.NUM_OF_FIELDS;
        //Schrittweite in die Höhe
        int ySteps = dimensions / global.NUM_OF_FIELDS;


        IGameField[][] tmp = new IGameField[11][11];

        //initialisieren der Entities für jedes Feld
        for (int i = 0; i < global.NUM_OF_FIELDS; i++) {
            for (int j = 0; j < global.NUM_OF_FIELDS; j++) {
                Point x = new Point(i * xSteps + offsetX, j * ySteps);
                Point y = new Point((i + 1) * xSteps + offsetX, (j + 1) * ySteps);

                //initialisieren des richtigen Feldes
                IGameField tmpField = boardTemplate[i][j] == 1 ? new StandardField(x, y) :
                        boardTemplate[i][j] == 2 ? new HomeField(getFieldColor(new Point(i, j)), x, y) :
                                boardTemplate[i][j] == 3 ? new StartField(getFieldColor(new Point(i, j)), x, y) :
                                        null;
                tmp[i][j] = tmpField;

                //zuweisen des richtigen Bildes
                if(tmpField != null) {
                    String img = tmpField instanceof StandardField ? "assets/standardField.png" :
                            getImg(tmpField.getColor());

                    //erstellen einer neuen Entity
                    Entity point = new Entity("grid point" + tmpField.getPosition().getX());
                    point.setPosition(new Vector2f(tmpField.getPosition().getX(), tmpField.getPosition().getY()));
                    point.addComponent(new ImageRenderComponent(new Image(img)));
                    point.setScale(tmpField.getSize());
                    //TODO delete
                    ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                    clickEvent.addAction(new LogAction(tmpField.getPosition(),tmpField.getColor()));
                    point.addComponent(clickEvent);
                    entityManager.addEntity(stateID, point);
                }
            }
        }
        //globales board mit dem temporären initialisieren
        BOARD = tmp;
    }


    private Color getFieldColor(Point point) {
        return point.getX() <= 5 && point.getY() <= 4 ? Color.RED :
                point.getX() >= 5 && point.getY() <= 5 ? Color.YELLOW :
                        point.getX() <= 4 && point.getY() >= 5 ? Color.GREEN :
                                point.getX() >= 5 && point.getY() >= 5 ? Color.BLUE :
                                        Color.NONE;
    }

    private String getImg(Color color) {
        return color == Color.RED ? "assets/redField.png" :
                color == Color.BLUE ? "assets/blueField.png" :
                        color == Color.YELLOW ? "assets/yellowField.png" :
                                "assets/greenField.png";

    }

    public IGameField getField(Point point){
        return BOARD[point.getX()][point.getY()];
    }
}
