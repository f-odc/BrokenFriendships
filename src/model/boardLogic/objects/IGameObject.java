package model.boardLogic.objects;

import eea.engine.entity.Entity;

public interface IGameObject {

    Entity getEntity();

    boolean activate();

    int getOwnerID();

    void reset();


}
