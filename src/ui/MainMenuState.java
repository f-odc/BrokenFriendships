package ui;

import eea.engine.action.basicactions.ChangeStateAction;
import model.actions.ActivateCEAction;
import model.global;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * @author Timo Bähr
 * <p>
 * Diese Klasse repraesentiert das Menuefenster, indem ein neues
 * Spiel gestartet werden kann und das gesamte Spiel beendet
 * werden kann.
 */
public class MainMenuState extends BasicGameState {    // zugehoeriger entityManager

    private final int distance = 100;
    private final int start_Position = 280;


    /**
     * Wird vor dem (erstmaligen) Starten dieses State's ausgefuehrt
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (!BrokenFriendships.debug) {
            // set background image
            Entity background = new Entity("background");
            background.setPosition(new Vector2f(global.X_DIMENSIONS / 2, global.Y_DIMENSIONS / 2));    // Startposition des Hintergrunds
            background.setScale(global.BACKGROUND_SIZE / 2 + 0.1f);
            background.addComponent(new ImageRenderComponent(new Image("/assets/blurred_background.png"))); // Bildkomponente

            // add background entity to the game
            global.entityManager.addEntity(global.MAINMENU_STATE, background);

            /* Neues Spiel starten-Entitaet */
            String new_Game = "Neues Spiel starten";
            Entity new_Game_Entity = new Entity(new_Game);

            // Setze Position und Bildkomponente
            new_Game_Entity.setPosition(new Vector2f(global.X_DIMENSIONS / 2, 290));
            new_Game_Entity.setScale(0.28f);
            new_Game_Entity.addComponent(new ImageRenderComponent(new Image("assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action new_Game_Action = new ChangeStateInitAction(global.GAMEPLAY_STATE);
            mainEvents.addAction(new_Game_Action);
            new_Game_Entity.addComponent(mainEvents);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.MAINMENU_STATE, new_Game_Entity);

            /* Beenden-Entitaet */
            Entity quit_Entity = new Entity("Beenden");

            // Setze Position und Bildkomponente
            quit_Entity.setPosition(new Vector2f(global.X_DIMENSIONS / 2, 390));
            quit_Entity.setScale(0.28f);
            quit_Entity.addComponent(new ImageRenderComponent(new Image("assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action quit_Action = new QuitAction();
            mainEvents_q.addAction(quit_Action);
            quit_Entity.addComponent(mainEvents_q);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.MAINMENU_STATE, quit_Entity);

            //CE-aktivieren textbox Entitaet
            Entity ce_ActivateEntity = new Entity("CE-activate-text");

            // Setze Position und Bildkomponente
            ce_ActivateEntity.setPosition(new Vector2f(global.X_DIMENSIONS - 100, global.Y_DIMENSIONS - 145));
            ce_ActivateEntity.setScale(0.07f);
            ce_ActivateEntity.addComponent(new ImageRenderComponent(new Image("assets/entryButton.png")));

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.MAINMENU_STATE, ce_ActivateEntity);

            //CE-aktivieren checkbox Entitaet
            Entity ce_Entity = new Entity("CE-activate");

            // Setze Position und Bildkomponente
            ce_Entity.setPosition(new Vector2f(global.X_DIMENSIONS - 100, global.Y_DIMENSIONS - 100));
            ce_Entity.setScale(0.1f);
            if(!global.activeCE)
                ce_Entity.addComponent(new ImageRenderComponent(new Image("assets/checkmark_empty.png")));
            else
                ce_Entity.addComponent(new ImageRenderComponent(new Image("assets/checkmark_full.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents_ce = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action ce_action = new ActivateCEAction();
            mainEvents_ce.addAction(ce_action);
            ce_Entity.addComponent(mainEvents_ce);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.MAINMENU_STATE, ce_Entity);
        }
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        global.entityManager.updateEntities(container, game, delta);
    }

    /**
     * Wird mit dem Frame ausgefuehrt
     */
    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) {
        global.entityManager.renderEntities(container, game, g);

        int counter = 0;

        g.drawString("  Neues Spiel  ", global.X_DIMENSIONS / 2 - 72, start_Position + counter * distance);
        counter++;
        g.drawString("    Beenden    ", global.X_DIMENSIONS / 2 - 72, start_Position + counter * distance);
        g.drawString("CE Aufgabe", global.X_DIMENSIONS - 140, global.Y_DIMENSIONS - 155);
    }

    @Override
    public int getID() {
        return global.MAINMENU_STATE;
    }

}
