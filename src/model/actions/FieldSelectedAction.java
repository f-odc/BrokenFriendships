package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.boardLogic.fields.BoardField;
import model.boardLogic.objects.Figure;
import model.game.GameLogic;
import model.game.Turn;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class FieldSelectedAction implements Action {
    private BoardField field;


    public FieldSelectedAction(BoardField field) {
        //System.out.println("LogAction position: " + position.getX() + "|" + position.getY());
        this.field = field;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        System.out.println("Color: "  + " |X: " + field.getPosition().getX() + " |Y: " + field.getPosition().getY());
        if(field.isOccupied())System.out.println( field.getCurrentObject().getOwnerID());
        GameLogic.executePhase(field);
    }
}
