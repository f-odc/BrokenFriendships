package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.board.fields.IField;
import model.enums.Phase;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class TestAction implements Action {

    public TestAction() {
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        // selection phase
        //System.out.println("clicked");
        //System.out.println(gameContainer.getInput().getMouseX());
        //System.out.println(gameContainer.getInput().getMouseY());
    }
}
