package ui;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.global;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OnPauseMenuState extends BasicGameState {

    private int stateID;
    private final int distance = 100;
    private final int start_Position = 280;

    OnPauseMenuState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (!BrokenFriendships.debug) {
            // set background image
            Entity background = new Entity("background");
            background.setPosition(new Vector2f(global.X_DIMENSIONS / 2, global.Y_DIMENSIONS / 2));    // Startposition des Hintergrunds
            background.setScale(global.BACKGROUND_SIZE / 2 + 0.1f);
            background.addComponent(new ImageRenderComponent(new Image("/assets/blurred_background.png"))); // Bildkomponente

            // add background entity to the game
            global.entityManager.addEntity(global.PAUSE_STATE, background);

            /* Neues Spiel starten-Entitaet */
            String new_Game = "Neues Spiel starten";
            Entity new_Game_Entity = new Entity(new_Game);

            // Setze Position und Bildkomponente
            new_Game_Entity.setPosition(new Vector2f(global.X_DIMENSIONS / 2, 290));
            new_Game_Entity.setScale(0.28f);
            new_Game_Entity.addComponent(new ImageRenderComponent(new Image("/assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action new_Game_Action = new ChangeStateInitAction(global.GAMEPLAY_STATE);
            mainEvents.addAction(new_Game_Action);
            new_Game_Entity.addComponent(mainEvents);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.PAUSE_STATE, new_Game_Entity);

            /* Beenden-Entitaet */
            Entity quit_Entity = new Entity("HauptMenue");

            // Setze Position und Bildkomponente
            quit_Entity.setPosition(new Vector2f(global.X_DIMENSIONS / 2, 390));
            quit_Entity.setScale(0.28f);
            quit_Entity.addComponent(new ImageRenderComponent(new Image("/assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action quit_Action = new ChangeStateAction(global.MAINMENU_STATE);
            mainEvents_q.addAction(quit_Action);
            quit_Entity.addComponent(mainEvents_q);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.PAUSE_STATE, quit_Entity);

            /* Beenden-Entitaet */
            Entity continue_Entity = new Entity("Weiter spielen");

            // Setze Position und Bildkomponente
            continue_Entity.setPosition(new Vector2f(global.X_DIMENSIONS / 2, 490));
            continue_Entity.setScale(0.28f);
            continue_Entity.addComponent(new ImageRenderComponent(new Image("/assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents_c = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action continue_Action = new ChangeStateAction(global.GAMEPLAY_STATE);
            mainEvents_c.addAction(continue_Action);
            continue_Entity.addComponent(mainEvents_c);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.PAUSE_STATE, continue_Entity);
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        // StatedBasedEntityManager soll alle Entities rendern
        global.entityManager.renderEntities(gameContainer, stateBasedGame, graphics);

        int counter = 0;

        graphics.drawString("  Neues Spiel  ", global.X_DIMENSIONS / 2 - 72, start_Position + counter * distance);
        counter++;
        graphics.drawString(" Zum Hauptmenü ", global.X_DIMENSIONS / 2 - 72, start_Position + counter * distance);
        counter++;
        graphics.drawString("Weiter spielen ", global.X_DIMENSIONS / 2 - 72, start_Position + counter * distance);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        global.entityManager.updateEntities(gameContainer, stateBasedGame, i);
    }

    @Override
    public int getID() {
        return stateID;
    }
}
