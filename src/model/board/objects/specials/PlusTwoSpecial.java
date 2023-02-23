package model.board.objects.specials;

import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;
import model.game.logic.MoveLogic;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class PlusTwoSpecial implements IGameObject {

    private IField currentField;


    @Override
    public boolean moveTo(IField targetField, boolean switchFlag) {
        return false;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        // TODO: change
        /*IField movableField = MoveLogic.getMovableField(sourceGameObject.getCurrentField(), 2);
        if (movableField != null){
            sourceGameObject.moveTo(movableField);
        }
         */
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean requiresFieldInteraction() {
        return false;
    }

    @Override
    public int getOwnerID() {
        return 0;
    }

    @Override
    public void setCurrentField(IField field) {

    }

    @Override
    public IField getCurrentField() {
        return currentField;
    }

    @Override
    public Entity getEntity() {
        return null;
    }
}
