package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.boardLogic.fields.BoardField;
import model.boardLogic.fields.IField;
import model.game.GameLogic;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class FieldSelectedAction implements Action {
    IField field;

    public FieldSelectedAction(IField field) {
        this.field = field;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        GameLogic.executePhase(field);
    }
}
