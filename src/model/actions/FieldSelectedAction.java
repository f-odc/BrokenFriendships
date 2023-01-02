package model.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;
import model.boardLogic.fields.BoardField;
import model.enums.Color;
import model.game.GameLogic;
import model.game.Turn;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class FieldSelectedAction implements Action {
    private Vector2f position;

    private int turn;

    public FieldSelectedAction(Vector2f position, int turn) {
        //System.out.println("LogAction position: " + position.getX() + "|" + position.getY());
        this.position = position;
        this.turn = turn;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i, Component component) {
        System.out.println("Color: " + turn + " |X: " + position.getX() + " |Y: " + position.getY());
        BoardField field = global.BOARD.getField(position);
        if (global.phase == Turn.SELECT_FIGURE_PHASE && global.turn == turn) {
            System.out.println("Is this field occupied? " + field.isOccupied());
            GameLogic.selectField(field);
            GameLogic.enterMovementPhase();
        } else if (global.phase == Turn.SELECT_MOVEMENT_PHASE && global.turn == turn) {
            GameLogic.deselectField();
            GameLogic.enterDicePhase();
        }
    }
}
