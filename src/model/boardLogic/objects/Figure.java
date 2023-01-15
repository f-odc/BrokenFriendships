package model.boardLogic.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.boardLogic.fields.BoardField;
import model.game.GameLogic;
import model.game.MoveLogic;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class Figure implements IGameObject {

    private Entity entity;
    private BoardField startField;
    private BoardField homeField;
    private BoardField currentField;
    private int playerID;
    private String color;
    private int id;

    public Figure(int playerID, int figureID, String color, BoardField startField, BoardField homeField) {
        this.id = figureID;
        this.playerID = playerID;
        this.color = color;
        this.startField = startField;
        this.homeField = homeField;
        this.currentField = homeField;
        initEntity();
    }

    /**
     * create the figure entity and add the entity list
     * id, scale, image
     */
    public void initEntity() {

        Entity fieldEntity = new Entity("player:" + this.playerID + "-figure:" + this.id);
        try {
            fieldEntity.addComponent(new ImageRenderComponent(new Image("assets/figures/" + color + ".png")));
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        fieldEntity.setScale(0.1f);
        // add to list
        entity = fieldEntity;
    }

    /**
     * get figure entity
     *
     * @return figure entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Move figure to new field, delete object from old field and add to new one
     *
     * @param from current field
     * @param to   new field
     */
    public boolean moveFromTo(BoardField from, BoardField to) {
        //case: own figure on "to" field
        if (to.isOccupied() && to.getCurrentObject().getOwnerID() == playerID) {
            System.out.println("same Figure");
            return false;
        }
        //case: other figure on "to" field
        if (to.isOccupied()) {
            System.out.println("reset " + to.getCurrentObject().getOwnerID() + "," + playerID);
            IGameObject tmp = to.resetCurrentField();
            tmp.reset();
        }
        // from delete object
        from.resetCurrentField();
        // to add object
        to.setGameObject(this);
        // set currentField
        currentField = to;
        return true;
    }

    public int getFigureID() {
        return id;
    }

    public boolean activate() {
        // TODO: what to do if clicked
        BoardField targetField = MoveLogic.getMovableField();
        if (targetField != null) {
            System.out.println("Figure movable field: " + targetField.getPosition().getX() + "," + targetField.getPosition().getY());
            return moveFromTo(currentField, targetField);
        }
        return false;
    }

    /**
     * Set figure to home field
     */
    public void reset() {
        moveFromTo(currentField, homeField);
    }

    public int getOwnerID() {
        return playerID;
    }

    public BoardField getHomeField() {
        return homeField;
    }

    public BoardField getStartField() {
        return startField;
    }

    public BoardField getCurrentField() {
        return currentField;
    }

    public boolean isOnBaseField() {
        boolean isOnBaseField = false;
        for (int i = 0; i < 4; i++) {
            if (global.players[playerID].getBaseField(i).equals(currentField))
                isOnBaseField = true;
        }
        return isOnBaseField;
    }

    public boolean isOnHomeField() {
        boolean isOnHomeField = false;
        for (int i = 0; i < 4; i++) {
            if (global.players[playerID].getHomeField(i).equals(currentField))
                isOnHomeField = true;
        }
        return isOnHomeField;
    }
}
