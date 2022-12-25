package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DiceAction implements Action {
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        try {
            global.BOARD.dice.throwDice();
            System.out.println("has thrown: " + global.BOARD.dice.getValue());
        } catch (SlickException e) {
            System.out.println("throw has failed");
            throw new RuntimeException(e);
        }
    }
}
