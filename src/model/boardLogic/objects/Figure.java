package model.boardLogic.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.boardLogic.fields.BoardField;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
     * Move figure to start field if start field is empty or occupied with from different player
     *
     * @return true -> if move to start is successful, else false
     */
    public boolean moveToStart() {
        // move
        return moveFromTo(currentField, startField);
    }

    /**
     * Move figure to new field, delete object from old field and add to new one
     *
     * @param from current field
     * @param to   new field
     */
    public boolean moveFromTo(BoardField from, BoardField to) {
        //figure of same player on to field
        if (to.isOccupied() && to.getCurrentObject().getOwnerID() == playerID) {
            System.out.println("same Figure");
            return false;
        }
        //other object on to field
        else if (to.isOccupied()) {
            System.out.println("reset " + to.getCurrentObject().getOwnerID() + "," + playerID);
            IGameObject tmp = to.resetCurrentField();
            tmp.reset();
            from.resetCurrentField();
        }
        //standard move with no hindrances
        else {
            System.out.println("standard move");
            // from delete object
            from.resetCurrentField();
        }
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
        //move from home to start field
        if (currentField.getPosition().getX() == homeField.getPosition().getX() &&
                currentField.getPosition().getY() == homeField.getPosition().getY()) {
            if (!moveToStart()) {
                //return figure to home if it fails
                reset();
                return false;
            }
        }
        //move from game field to another game field
        else {
            int from = global.BOARD.getGameFieldsIndex(currentField.getPosition());
            BoardField to = global.BOARD.getPlayField(from + global.BOARD.getDice().getValue());
            //return false and do nothing if it fails
            return moveFromTo(currentField, to);
        }
        return true;
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

    public BoardField getCurrentField() {
        return currentField;
    }

    public BoardField getHomeField() {
        return homeField;
    }

    public BoardField getStartField() {
        return startField;
    }

}
