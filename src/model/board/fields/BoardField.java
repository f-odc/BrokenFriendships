package model.board.fields;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.BoardFieldAction;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.enums.Color;
import model.enums.FieldType;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import ui.BrokenFriendships;

public class BoardField implements IField{

    private Entity baseEntity;

    private Vector2f position;

    private FieldType type;

    private int fieldIndex;

    private IGameObject displayedObject;

    private Entity highlightEntity;

    public Color color;

    public BoardField(Vector2f position, int fieldIndex, Color color, FieldType type) {
        try {
            this.baseEntity = createEntity(position, fieldIndex, color, false);
            this.highlightEntity = createEntity(position, fieldIndex, color, true);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        this.position = baseEntity.getPosition();
        this.type = type;
        this.fieldIndex = fieldIndex;
        this.color = color;

        // add board and highlight entity
        global.entityManager.addEntity(global.GAMEPLAY_STATE, this.highlightEntity);
        global.entityManager.addEntity(global.GAMEPLAY_STATE, baseEntity);
    }

    /**
     * creates an entity for the field
     * @param position field coordinates
     * @param type field type
     * @param color field color
     * @param isHighlightField is it the highlighted version or normal
     * @return created entity
     * @throws SlickException if entity object cannot be created
     */
    private Entity createEntity(Vector2f position, int type, Color color, boolean isHighlightField) throws SlickException {
        //create entity
        Entity fieldEntity = new Entity("gameField:" + position.getX() + "," + position.getY() + "," + isHighlightField);
        fieldEntity.setPosition(position);

        //initialize the image of the entity
        String imgPath = isHighlightField ? "assets/field/highlightField.png" :
                color == Color.NONE ? "assets/field/standardField.png" :
                        "assets/field/" + color.toString().toLowerCase() + "Field.png";
        if(!BrokenFriendships.debug) fieldEntity.addComponent(new ImageRenderComponent(new Image(imgPath)));
        //initialize the size of the entity
        fieldEntity.setScale(type == -3 ? global.STANDARD_AND_BASE_FIELD_SIZE :
                type == -2 ? global.HOME_AND_START_FIELD_SIZE :
                        (type == 0 || type == 10 || type == 20 || type == 30) ? global.HOME_AND_START_FIELD_SIZE :
                                global.STANDARD_AND_BASE_FIELD_SIZE);
        //resize highlighted field to be larger
        if (isHighlightField){
            fieldEntity.setScale(fieldEntity.getScale() + 0.02f);
            fieldEntity.setVisible(false);
        }
        else{
            //add action to entity
            ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
            clickEvent.addAction(new BoardFieldAction(this));
            fieldEntity.addComponent(clickEvent);
        }

        return fieldEntity;
    }

    /**
     * get field position
     * @return position of the field
     */
    public Vector2f getPosition() {
        return this.position;
    }

    /**
     * get Entity of the field
     * @return Entity of the field
     */
    public Entity getBaseEntity() {
        return baseEntity;
    }

    /**
     * get the type of the field
     * @return field type
     */
    public FieldType getType() {
        return type;
    }

    /**
     * get the occupation status
     * @return if the field is occupied
     */
    public boolean isOccupied() {
        return displayedObject != null;
    }

    /**
     * Get object currently occupying the field
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
     * @return the IGameObject being reset
     */
    public IGameObject resetCurrentObject() {
        IGameObject tmp = displayedObject;
        displayedObject = null;
        return tmp;
    }

    /**
     * highlight the field
     */
    public void highlight() {
        // make the entity visible
        highlightEntity.setVisible(true);
    }

    /**
     * revert the highlighting of the field
     */
    public void unHighlight() {
        highlightEntity.setVisible(false);
    }

    /**
     * Checks if the object on the field is a figure
     * @return Figure if figure is contained, else null
     */
    public Figure getCurrentFigure() {
        if (displayedObject instanceof Figure) {
            return (Figure) displayedObject;
        }
        return null;
    }

    /**
     * Checks if field is a start field from a player
     * @return true if start field, else false
     */
    public boolean isPlayerStartField(){
        return type == FieldType.START;
    }

    /**
     * Checks if field is a home field from a player
     * @return true if home field, else false
     */
    public boolean isHomeField(){ return type == FieldType.HOME;}

    @Override
    public int getFieldIndex() {
        return fieldIndex;
    }
}
