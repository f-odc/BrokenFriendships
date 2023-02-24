package model.board.objects.specials;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BombSpecial implements IGameObject {

    private Entity entity;
    private IField currentField;

    /**
     * create bomb entity and add to entity list
     */
    public void initEntity() {
        Entity bedEntity = new Entity("bomb special-hashcode:" + this.hashCode());
        try {
            bedEntity.addComponent(new ImageRenderComponent(new Image("assets/objects/bomb.png")));
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        bedEntity.setScale(global.OBJECT_SIZE);
        // add to list
        entity = bedEntity;
        global.entityManager.addEntity(global.GAMEPLAY_STATE, entity);
    }

    @Override
    public boolean moveTo(IField targetField, boolean switchFlag) {
        // display entity
        initEntity();

        // check if target field is occupied
        if (targetField.isOccupied()){
            return false;
        }
        // set figure to target field
        targetField.setGameObject(this);
        System.out.println("Bomb Field: " + targetField.getCurrentObject());
        // set current field
        setCurrentField(targetField);
        return true;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        // reset figure
        sourceGameObject.reset();
        // TODO: test removement
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, entity);
        reset();
    }

    @Override
    public void reset() {
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, entity);
        this.currentField.resetCurrentObject();
        this.currentField = null;
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
        this.currentField = field;
    }

    @Override
    public IField getCurrentField() {
        return currentField;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
