package ui;

import eea.engine.component.RenderComponent;
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
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.event.basicevents.MouseClickedEvent;

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

    private Point[][] Coordinates = new Point[11][11];

    GameplayState(int sid) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        int xSteps = width / 11;
        int ySteps = height / 11;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++){
                Coordinates[i][j] = new Point(i * xSteps,j * ySteps);
            }
        }
    }

    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // setzen des Hintergrunds
        container.getGraphics().setBackground(Color.white);

        // Bei Drücken der ESC-Taste zurueck ins Hauptmenue wechseln
        Entity esc_Listener = new Entity("ESC_Listener");
        KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
        esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
        esc_Listener.addComponent(esc_pressed);
        entityManager.addEntity(stateID, esc_Listener);

        for (Point[] row: Coordinates) {
            for (Point coordinate: row) {
                Entity point = new Entity("grid point" + coordinate.getX());
                point.setPosition(new Vector2f(coordinate.getX(), coordinate.getY()));
                point.addComponent(new ImageRenderComponent(new Image("assets/point.png")));
                point.setScale(0.02f);
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
