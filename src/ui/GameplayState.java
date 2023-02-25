package ui;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.basicevents.*;
import model.enums.Phase;
import model.game.GameManager;
import model.global;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
     * gets called once to initialize the game
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // set the background color
        Color lightGreen = new Color(202, 255, 202);
        container.getGraphics().setBackground(lightGreen);
        // set background image
        Entity background = new Entity("background");
        background.setPosition(new Vector2f(global.X_DIMENSIONS/2, global.Y_DIMENSIONS/2));	// Startposition des Hintergrunds
        background.setScale(global.BACKGROUND_SIZE);
        background.addComponent(new ImageRenderComponent(new Image("/assets/background.png"))); // Bildkomponente

        // add background entity to the game
        global.entityManager.addEntity(global.GAMEPLAY_STATE, background);

        // go back to the main menu on ESC clicked
        Entity esc_Listener = new Entity("ESC_Listener");
        KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
        esc_pressed.addAction(new ChangeStateAction(global.MAINMENU_STATE));
        esc_Listener.addComponent(esc_pressed);
        entityManager.addEntity(global.GAMEPLAY_STATE, esc_Listener);

        // initialize all game objects
        GameManager.setup();
        // start the game
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
        Vector2f mysteryAnimationPos = new Vector2f(global.X_DIMENSIONS/2,global.Y_DIMENSIONS/2);
        global.mysteryAnimation.draw(mysteryAnimationPos.x-global.ANIMATION_SIZE/2-offset, mysteryAnimationPos.y-global.ANIMATION_SIZE/2-offset, global.ANIMATION_SIZE, global.ANIMATION_SIZE);
    }

}
