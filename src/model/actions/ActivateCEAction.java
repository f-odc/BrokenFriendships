package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ActivateCEAction implements Action {
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        global.activeCE = !global.activeCE;
        try {
            if (!global.activeCE)
                global.entityManager.getEntity(global.MAINMENU_STATE, "CE-activate").addComponent(new ImageRenderComponent(new Image("assets/checkmark_empty.png")));
            else
                global.entityManager.getEntity(global.MAINMENU_STATE, "CE-activate").addComponent(new ImageRenderComponent(new Image("assets/checkmark_full.png")));
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }

    }
}
