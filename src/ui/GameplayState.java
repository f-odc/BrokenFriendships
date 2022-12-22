package ui;

import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.*;
import model.global;
import model.logic.Field;
import model.logic.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import java.awt.*;

/**
 * @author Timo Bähr
 * <p>
 * Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 * erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {

    private int NUM_OF_FIELDS = global.NUM_OF_FIELDS;
    private int stateID;                            // Identifier dieses BasicGameState
    private StateBasedEntityManager entityManager;    // zugehoeriger entityManager

    private Field[][] fields = new Field[NUM_OF_FIELDS][NUM_OF_FIELDS];     //Felder auf dem Spielbrett

    GameplayState(int sid) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();

        //Initialisieren des Spielbrettes
        int dimensions = Toolkit.getDefaultToolkit().getScreenSize().height;

        //offset um das Spielbrett in die Mitte des Fensters zu schieben
        int offsetX = Toolkit.getDefaultToolkit().getScreenSize().width/2 - dimensions/2;

        //Initialisieren des fields array, mit den einzelnen Feldern des Spielbrettes
        //Schrittweite in die breite
        int xSteps = dimensions / NUM_OF_FIELDS;
        //Schrittweite in die Höhe
        int ySteps = dimensions / NUM_OF_FIELDS;
        for (int i = 0; i < NUM_OF_FIELDS; i++) {
            for (int j = 0; j < NUM_OF_FIELDS; j++){
                fields[i][j] = new Field(new Point(i * xSteps + offsetX,j * ySteps), new Point((i+1) * xSteps + offsetX, (j+ 1) * ySteps));
            }
        }
    }

    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // setzen des Hintergrunds
        container.getGraphics().setBackground(Color.black);

        // Bei Drücken der ESC-Taste zurueck ins Hauptmenue wechseln
        Entity esc_Listener = new Entity("ESC_Listener");
        KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
        esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
        esc_Listener.addComponent(esc_pressed);
        entityManager.addEntity(stateID, esc_Listener);

        //größen der Felder
        float normalSize = 0.18f;
        float homeSize = 0.12f;

        //initialisieren der Entities für jedes Feld
        for (Field[] row: fields) {
            for (Field field: row) {
                //erstellen einer neuen Entity
                Entity point = new Entity("grid point" + field.getMid().getX());
                point.setPosition(new Vector2f(field.getMid().getX(), field.getMid().getY()));
                point.addComponent(new ImageRenderComponent(new Image("assets/point.png")));
                point.setScale(normalSize);
                //TODO delete
                ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                clickEvent.addAction(new MoveUpAction(0.5f));
                point.addComponent(clickEvent);
                entityManager.addEntity(stateID, point);
            }
        }
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // StatedBasedEntityManager soll alle Entities aktualisieren
        entityManager.updateEntities(container, game, delta);
    }

    /**
     * Wird mit dem Frame ausgefuehrt
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // StatedBasedEntityManager soll alle Entities rendern
        entityManager.renderEntities(container, game, g);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
