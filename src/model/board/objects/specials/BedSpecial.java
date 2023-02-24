package model.board.objects.specials;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BedSpecial implements IGameObject {

    private Entity entity;
    private IField currentField;

    public BedSpecial(){
    }

    /**
     * create bed entity and add to entity list
     */
    public void initEntity() {
        Entity bedEntity = new Entity("bed special-hashcode:" + this.hashCode());
        try {
            bedEntity.addComponent(new ImageRenderComponent(new Image("assets/objects/bed.png")));
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        bedEntity.setScale(global.FIGURE_SIZE);
        // add to list
        this.entity = bedEntity;
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
        // set current field
        setCurrentField(targetField);
        return true;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        // skip new turn
        if (global.BOARD.getDice().getValue() == 6){
            global.entityManager.removeEntity(global.GAMEPLAY_STATE, entity);
            reset();
        }else{
            global.players[sourceGameObject.getOwnerID()].setBedSpecial(this);
        }
        GameLogic.nextPlayer();
    }

    @Override
    public void reset() {
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, this.entity);
        currentField = null;
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
        return this.currentField;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }
}
