package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.board.fields.IField;
import model.enums.Phase;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class BoardFieldAction implements Action {
    IField field;

    public BoardFieldAction(IField field) {
        this.field = field;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        // selection phase
        if (global.phase == Phase.SELECT_FIGURE_PHASE){
            GameLogic.executeSelectFigurePhase(field);
        }else if (global.phase == Phase.SELECT_MOVEMENT_PHASE){
            GameLogic.executeSelectMovementPhase(field);
        }
    }
}
