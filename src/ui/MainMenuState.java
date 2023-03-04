package ui;

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
    private final int start_Position = 180;

    private Animation test;

    /**
     * Wird vor dem (erstmaligen) Starten dieses State's ausgefuehrt
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        if (!BrokenFriendships.debug) {
            // set the background
            container.getGraphics().setBackground(Color.white);


            /* Neues Spiel starten-Entitaet */
            String new_Game = "Neues Spiel starten";
            Entity new_Game_Entity = new Entity(new_Game);

            // Setze Position und Bildkomponente
            new_Game_Entity.setPosition(new Vector2f(218, 190));
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
            quit_Entity.setPosition(new Vector2f(218, 290));
            quit_Entity.setScale(0.28f);
            quit_Entity.addComponent(new ImageRenderComponent(new Image("assets/entryButton.png")));

            // Erstelle das Ausloese-Event und die zugehoerige Action
            ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            Action quit_Action = new QuitAction();
            mainEvents_q.addAction(quit_Action);
            quit_Entity.addComponent(mainEvents_q);

            // Fuege die Entity zum StateBasedEntityManager hinzu
            global.entityManager.addEntity(global.MAINMENU_STATE, quit_Entity);
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

        g.drawString("Neues Spiel", 110, start_Position + counter * distance);
        counter++;
        g.drawString("Beenden", 110, start_Position + counter * distance);
    }

    @Override
    public int getID() {
        return global.MAINMENU_STATE;
    }

}
