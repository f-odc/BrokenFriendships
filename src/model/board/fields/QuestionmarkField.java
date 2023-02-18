package model.board.fields;

import eea.engine.entity.Entity;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.enums.Field;
import model.global;
import org.newdawn.slick.geom.Vector2f;

public class QuestionmarkField implements IField{

    private Entity baseEntity;

    private Vector2f position;

    private float scale;

    private IGameObject displayedObject;

    public QuestionmarkField(Entity baseEntity) {
        this.baseEntity = baseEntity;
        this.scale = baseEntity.getScale();
        this.position = baseEntity.getPosition();
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Entity getBaseEntity() {
        return baseEntity;
    }

    public Field getType() {
        return Field.QUESTIONMARK;
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
     * Set game object on top of board field
     *
     * @param gameObject Figure/Object
     */
    public void setGameObject(IGameObject gameObject) {
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
     *
     * @return the IGameObject being reset
     */
    public IGameObject resetCurrentObject() {
        IGameObject tmp = displayedObject;
        displayedObject = null;
        return tmp;
    }

    public void highlight() {
        //TODO change way of highlighting
        baseEntity.setScale(0.3f);
    }

    public void deHighlight() {
        baseEntity.setScale(scale);
    }

    public boolean equals(BoardField other) {
        return getPosition().getX() == other.getPosition().getX() &&
                getPosition().getY() == other.getPosition().getY();
    }

    public Figure getCurrentFigure(){
        if(displayedObject instanceof Figure){
            return (Figure) displayedObject;
        }
        return null;
    }
}
