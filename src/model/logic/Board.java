package model.logic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
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

public class Board {

    //0 == kein Feld, 1 == standard-Feld
    //2 == Haus-Feld, 3 == Start-Feld
    static public int[][] boardTemplate = {
            {2, 2, 0, 0, 1, 1, 3, 0, 0, 2, 2},
            {2, 2, 0, 0, 1, 4, 1, 0, 0, 2, 2},
            {0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0},
            {3, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1},
            {1, 4, 4, 4, 4, 0, 4, 4, 4, 4, 1},
            {1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 3},
            {0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 4, 1, 0, 0, 0, 0},
            {2, 2, 0, 0, 1, 4, 1, 0, 0, 2, 2},
            {2, 2, 0, 0, 3, 1, 1, 0, 0, 2, 2},
    };

    private IGameField[][] BOARD;

    public Dice dice;

    private Point dicePosition;

    public List<ColoredField> redHome = new ArrayList<>();

    public List<ColoredField> blueHome = new ArrayList<>();

    public List<ColoredField> greenHome = new ArrayList<>();

    public List<ColoredField> yellowHome = new ArrayList<>();

    public List<IGameField> gameFields = new ArrayList<>();

    public List<ColoredField> redBase = new ArrayList<>();

    public List<ColoredField> blueBase = new ArrayList<>();

    public List<ColoredField> greenBase = new ArrayList<>();

    public List<ColoredField> yellowBase = new ArrayList<>();

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
                Point start = new Point(i * xSteps + offsetX, j * ySteps);
                Point end = new Point((i + 1) * xSteps + offsetX, (j + 1) * ySteps);

                int type = boardTemplate[i][j];
                Color color = type != 1 ? getFieldColor(new Point(i, j)) : Color.NONE;
                IGameField tmpField = null;

                switch (type) {
                    case 1 -> {
                        tmpField = new StandardField(start, end);
                        gameFields.add(tmpField);
                    }
                    case 2 -> {
                        tmpField = new ColoredField(color, start, end, Field.HOME);
                        switch (color) {
                            case RED -> redHome.add((ColoredField) tmpField);
                            case BLUE -> blueHome.add((ColoredField) tmpField);
                            case YELLOW -> yellowHome.add((ColoredField) tmpField);
                            case GREEN -> greenHome.add((ColoredField) tmpField);
                        }
                    }
                    case 3 -> {
                        tmpField = new ColoredField(color, start, end, Field.START);
                        gameFields.add(tmpField);
                    }
                    case 4 -> {
                        tmpField = new ColoredField(color, start, end, Field.BASE);
                        switch (color) {
                            case RED -> redBase.add((ColoredField) tmpField);
                            case BLUE -> blueBase.add((ColoredField) tmpField);
                            case YELLOW -> yellowBase.add((ColoredField) tmpField);
                            case GREEN -> greenBase.add((ColoredField) tmpField);
                        }
                    }
                }

                //Würfel Position
                if (i == 10 && j == 2) {
                    tmpField = new StandardField(start, end);
                    this.dicePosition = tmpField.getPosition();
                }

                //zuweisen des richtigen Bildes
                if (tmpField != null) {
                    String img = getImg(color);

                    //erstellen einer neuen Entity
                    Entity point = new Entity("grid point" + tmpField.getPosition().getX());
                    point.setPosition(new Vector2f(tmpField.getPosition().getX(), tmpField.getPosition().getY()));
                    if (i != 10 || j != 2 /*Würfel Position*/)
                        point.addComponent(new ImageRenderComponent(new Image(img)));
                    point.setScale(tmpField.getSize());
                    //TODO delete
                    if (tmpField.getPosition().getX() == 10 && tmpField.getPosition().getY() == 2) {
                        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                        clickEvent.addAction(new LogAction(tmpField.getPosition(), tmpField.getColor()));
                        point.addComponent(clickEvent);
                    }
                    entityManager.addEntity(stateID, point);
                }
            }
        }
    }

    public void initDice(int stateID, StateBasedEntityManager entityManager) throws SlickException {
        //init dice
        Dice tmpDice = new Dice(1,this.dicePosition, stateID, entityManager);
        this.dice = tmpDice;
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
                                color == Color.GREEN ? "assets/greenField.png" :
                                        "assets/standardField.png";

    }

    public IGameField getField(Point point) {
        return BOARD[point.getX()][point.getY()];
    }
}
