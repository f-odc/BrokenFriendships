package model.board.fields;

import eea.engine.entity.Entity;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.enums.Field;
import model.global;
import org.newdawn.slick.geom.Vector2f;

public class BoardField implements IField{

    private Entity baseEntity;

    private Vector2f position;

    private Field type;

    private float scale;

    private IGameObject displayedObject;

    private Entity highlightEntity;

    public BoardField(Entity baseEntity, Field type, Entity highlightEntity) {
        this.baseEntity = baseEntity;
        this.highlightEntity = highlightEntity;
        this.scale = baseEntity.getScale();
        this.position = baseEntity.getPosition();
        this.type = type;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Entity getBaseEntity() {
        return baseEntity;
    }

    public Field getType() {
        return type;
    }

    public boolean isOccupied() {
        return displayedObject != null;
    }

    /**
     * Get object currently occupying the field
     *
     * @return GameObject
     */
    public IGameObject getCurrentObject() {
        return displayedObject;
    }

    /**
     * Set the game object on top of board field
     * @param gameObject Figure/Object
     */
    public void setGameObject(IGameObject gameObject) {
        Entity entity = gameObject.getEntity();
        // set Position
        entity.setPosition(getPosition());
        // set game object to current displayed object
        this.displayedObject = gameObject;
        // add entity
        global.entityManager.addEntity(global.GAMEPLAY_STATE, entity);
    }

    /**
     * reset the current displayed object
     *
     * @return the IGameObject being reset
     */
    public IGameObject resetCurrentObject() {
        IGameObject tmp = displayedObject;
        displayedObject = null;
        return tmp;
    }

    public void highlight() {
        // adjust highlight entity and make it visible
        highlightEntity.setPosition(baseEntity.getPosition());
        highlightEntity.setScale(baseEntity.getScale() + 0.02f);
        highlightEntity.setVisible(true);
    }

    public void deHighlight() {
        highlightEntity.setVisible(false);
    }

    public boolean equals(BoardField other) {
        return getPosition().getX() == other.getPosition().getX() &&
                getPosition().getY() == other.getPosition().getY();
    }

    /**
     * Checks if the object on the field is a figure
     * @return Figure if figure is contained, else null
     */
    public Figure getCurrentFigure(){
        if(displayedObject instanceof Figure){
            return (Figure) displayedObject;
        }
        return null;
    }

    /**
     * Checks if field is a start field from a player
     * @return true if start field, else false
     */
    public boolean isPlayerStartField(){
        return type == Field.START;
    }
}
