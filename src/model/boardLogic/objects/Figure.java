package model.boardLogic.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.List;

public class Figure implements IGameObject {

    private Entity entity;
    private int playerID;
    private String color;
    private int id;

    public Figure(int playerID, int figureID, String color){
        this.id = figureID;
        this.playerID = id;
        this.color = color;
        initEntity();
    }

    /**
     * create the figure entity and add the entity list
     * id, scale, image
     */
    public void initEntity(){

        Entity fieldEntity = new Entity("player:" + this.playerID + "-figure:" + this.id);
        try {
            fieldEntity.addComponent(new ImageRenderComponent(new Image("assets/figures/"+ color +".png")));
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        fieldEntity.setScale(0.1f);
        // add to list
        entity = fieldEntity;
    }

    /**
     * get figure entity
     * @return figure entity
     */
    public Entity getEntity(){
        return entity;
    }

}
