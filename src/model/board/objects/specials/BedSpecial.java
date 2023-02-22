package model.board.objects.specials;

import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;

public class BedSpecial implements IGameObject {

    @Override
    public boolean moveTo(IField targetField) {
        return false;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        System.out.println("Activate Bed");
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean requiresFieldInteraction() {
        return true;
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
