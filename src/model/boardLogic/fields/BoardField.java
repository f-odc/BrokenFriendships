package model.boardLogic.fields;

import eea.engine.entity.Entity;
import model.boardLogic.objects.IGameObject;
import org.newdawn.slick.geom.Vector2f;

public class BoardField {

    private Entity baseEntity;

    private Vector2f position;

    private IGameObject displayedObject;

    public BoardField(Entity baseEntity) {
        this.baseEntity = baseEntity;
        this.position = baseEntity.getPosition();
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Entity getBaseEntity() {
        return baseEntity;
    }

    public boolean isOccupied() {
        //TODO
        return displayedObject != null;
    }
}
