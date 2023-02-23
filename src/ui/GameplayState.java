package ui;

import eea.engine.event.basicevents.*;
import model.enums.Phase;
import model.game.GameManager;
import model.global;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.action.basicactions.ChangeStateAction;
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
        // Setzen des Hintergrunds
        container.getGraphics().setBackground(Color.gray);

        // Bei Drücken der ESC-Taste zurueck ins Hauptmenue wechseln
        Entity esc_Listener = new Entity("ESC_Listener");
        KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
        esc_pressed.addAction(new ChangeStateAction(global.MAINMENU_STATE));
        esc_Listener.addComponent(esc_pressed);
        entityManager.addEntity(global.GAMEPLAY_STATE, esc_Listener);

        // Initialisiert alle Spielobjekte
        GameManager.setup();
        // Startet das Spiel
        GameManager.start();

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

        // render dice animation on right player dice field
        Vector2f dicePos = global.BOARD.getDice().getCurrentPosition();
        global.diceAnimation.draw(dicePos.x-48/2, dicePos.y-48/2, 48, 48);
        // render mystery selection
        int offset = 4;
        int sizeAnimation = 100;
        Vector2f mysteryAnimationPos = new Vector2f(Toolkit.getDefaultToolkit().getScreenSize().width/2,Toolkit.getDefaultToolkit().getScreenSize().height/2);
        global.mysteryAnimation.draw(mysteryAnimationPos.x-sizeAnimation/2-offset, mysteryAnimationPos.y-sizeAnimation/2-offset, sizeAnimation, sizeAnimation);
    }

}
