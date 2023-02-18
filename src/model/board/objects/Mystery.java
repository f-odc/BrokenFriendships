package model.board.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mystery implements IGameObject{

    private Entity entity;
    private int id;
    private int ownerID;
    private IField currentField;

    public Mystery(int id, int ownerID, IField field){
        this.id = id;
        this.ownerID = ownerID;
        this.currentField = field;
        initEntity();
    }

    /**
     * Init mystery entity
     */
    public void initEntity() {
        Entity newEntity = new Entity("player:" + this.ownerID + "-mystery:" + this.id);
        try {
            newEntity.addComponent(new ImageRenderComponent(new Image("assets/objects/questionmark.png")));
        } catch (SlickException e) {
            System.out.println("Cannot find: assets/objects/questionmark.png!");
            throw new RuntimeException(e);
        }
        newEntity.setScale(0.15f);
        // add to list
        this.entity = newEntity;
    }
    @Override
    public boolean moveTo(IField targetField) {
        return false;
    }

    @Override
    public void activate() {
        // TODO: start new phase
        reset();
    }

    @Override
    public void reset() {
        // remove entity from entity manager
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, entity);
        // reset the current field
        currentField.resetCurrentObject();
        entity = null;
        currentField = null;
    }

    @Override
    public int getOwnerID() {
        return ownerID;
    }

    @Override
    public void setCurrentField(IField field) {
        this.currentField = field;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
