package model.actions;

import eea.engine.component.Component;
import model.boardLogic.fields.BoardField;
import model.boardLogic.fields.IField;
import model.enums.Phase;
import model.game.BoardLogic;
import model.global;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class QuestionmarkAction extends FieldSelectedAction {
    public QuestionmarkAction(IField field) {
        super(field);
    }

}
