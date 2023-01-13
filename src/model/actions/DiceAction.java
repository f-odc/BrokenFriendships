package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.game.GameLogic;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class DiceAction implements Action {
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        GameLogic.executeDicePhase();
    }
}
