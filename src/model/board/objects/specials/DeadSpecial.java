package model.board.objects.specials;

import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;
import model.game.logic.GameLogic;
import model.global;
import ui.BrokenFriendships;

public class DeadSpecial implements IGameObject {


    @Override
    public boolean moveTo(IField targetField, boolean switchFlag) {
        return false;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        sourceGameObject.reset();
        // only next player if
        int diceThrow = BrokenFriendships.debug ? GameLogic.getDebugDiceThrow() : global.BOARD.getDice().getValue();
        if (diceThrow != 6) {
            GameLogic.nextPlayer();
        }
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
        return null;
    }

    @Override
    public Entity getEntity() {
        return null;
    }
}
