package model.boardLogic.objects;

import eea.engine.entity.Entity;

public class Figure implements IGameObject {

    private Entity entity;

    Figure(Entity entity) {
        this.entity = entity;
    }
}
