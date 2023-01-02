package model.boardLogic.fields;

import eea.engine.entity.Entity;
import model.boardLogic.objects.IGameObject;
import model.global;
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

    /**
     * Get object currently occupying the field
     * @return GameObject
     */
    public IGameObject getCurrentObject(){
        return displayedObject;
    }

    /**
     * Set game object on top of board field
     * @param gameObject Figure/Object
     */
    public void setGameObject(IGameObject gameObject){
        // TODO: Change? -> delete from previous, set to current, add board field on figure
        Entity figure = gameObject.getEntity();
        // set Position
        figure.setPosition(getPosition());
        // set game object to current displayed object
        this.displayedObject = gameObject;
        // add entity
        global.entityManager.addEntity(global.GAMEPLAY_STATE, figure);
    }

    /**
     * reset the current displayed object
     */
    public void resetCurrentField(){
        displayedObject = null;
    }
}
