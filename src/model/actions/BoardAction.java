package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.boardLogic.fields.BoardField;
import model.enums.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BoardAction implements Action {

    private BoardField boardField;

    public BoardAction(BoardField boardField) {
        this.boardField = boardField;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        System.out.println(boardField.getCurrentObject());
        if (boardField.isOccupied()){
            System.out.println("Do Action!");
            boardField.getCurrentObject().activate();
        }
    }
}
