package model.boardLogic.objects;

import eea.engine.entity.Entity;
import model.boardLogic.fields.BoardField;

public interface IGameObject {

    public Entity getEntity();

    public void activate();

    public int getOwner();

    public void reset();

}
