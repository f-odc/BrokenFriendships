package ui;

import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.*;
import model.fields.HomeField;
import model.fields.StandardField;
import model.fields.StartField;
import model.global;
import model.interfaces.IGameField;
import model.logic.Board;
import model.logic.LogAction;
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

    private final int[][] BOARD = global.board;

    private final int stateID;
    // Identifier dieses BasicGameState
    private final StateBasedEntityManager entityManager;    // zugehoeriger entityManager

    GameplayState(int sid) {
        stateID = sid;
        entityManager = StateBasedEntityManager.getInstance();
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

        global.BOARD = new Board(entityManager,stateID);

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
