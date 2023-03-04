package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.enums.Phase;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DiceAction implements Action {
    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        // can be only executed if during dice phase
        if (global.phase == Phase.DICE_PHASE) {
            GameLogic.executeDicePhase(-1, -1);
        }
    }

}
