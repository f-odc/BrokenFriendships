package ui;

import eea.engine.event.basicevents.*;
import model.game.SetupGame;
import model.global;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Timo Bähr
 * <p>
 * Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 * erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {
    // Identifier dieses BasicGameState
    private final StateBasedEntityManager entityManager;    // zugehoeriger entityManager

    GameplayState() {
        entityManager = StateBasedEntityManager.getInstance();
    }

    @Override
    public int getID() {
        return global.GAMEPLAY_STATE;
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
        esc_pressed.addAction(new ChangeStateAction(global.MAINMENU_STATE));
        esc_Listener.addComponent(esc_pressed);
        entityManager.addEntity(global.GAMEPLAY_STATE, esc_Listener);

        //initialisiert das Spielbrett und die Figuren
        SetupGame.setup();

    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
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

}
