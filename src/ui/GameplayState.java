package ui;

import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.component.RenderComponent;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.*;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Timo Bähr
 * <p>
 * Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 * erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {

    private int stateID;                            // Identifier dieses BasicGameState
    private StateBasedEntityManager entityManager;    // zugehoeriger entityManager

    private Field[][] Coordinates = new Field[11][11];

    GameplayState(int sid) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();

        int width = Toolkit.getDefaultToolkit().getScreenSize().height;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        int offsetX = Toolkit.getDefaultToolkit().getScreenSize().width/2 - width/2;

        int xSteps = width / 11;
        int ySteps = height / 11;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++){
                Coordinates[i][j] = new Field(new Point(i * xSteps + offsetX,j * ySteps), new Point((i+1) * xSteps + offsetX, (j+ 1) * ySteps));
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

        //field sizes
        float normalSize = 0.18f;
        float homeSize = 0.12f;

        //init
        for (Field[] row: Coordinates) {
            for (Field coordinate: row) {
                Entity point = new Entity("grid point" + coordinate.getMid().getX());
                point.setPosition(new Vector2f(coordinate.getMid().getX(), coordinate.getMid().getY()));
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
