package model.logic;

import eea.engine.action.Action;
import eea.engine.component.Component;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

public class LogAction implements Action {
    private Point position;

    private Color color;

    public LogAction(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        System.out.println("Color: " + this.color.toString() + " |X: " + position.getX() + " |Y: " + position.getY());
    }
}
