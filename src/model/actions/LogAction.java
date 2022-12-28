package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.enums.Color;
import model.boardLogic.Point;
import model.global;
import model.interfaces.IGameField;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class LogAction implements Action {
    private Point position;

    private Color color;

    public LogAction(Point position, Color color) {
        //System.out.println("LogAction position: " + position.getX() + "|" + position.getY());
        this.position = position;
        this.color = color;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        System.out.println("Color: " + this.color + " |X: " + position.getX() + " |Y: " + position.getY());
        IGameField field = global.BOARD.getField(position);
        System.out.println("Field from log x:" + field.getBoardPosition().getX() + " Y: " + field.getBoardPosition().getY());
        System.out.println("Field from log type: " + field.getColor());
    }
}
