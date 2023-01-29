package model.boardLogic.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.boardLogic.fields.BoardField;
import model.boardLogic.fields.IField;
import model.game.MoveLogic;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Figure implements IGameObject {

    private Entity entity;
    private IField startField;
    private IField homeField;
    private IField currentField;
    private int playerID;
    private String color;
    private int id;

    public Figure(int playerID, int figureID, String color, IField startField, IField homeField) {
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
        fieldEntity.setScale(global.FIGURE_SIZE);
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
    public boolean moveFromTo(IField from, IField to) {
        //case: own figure on "to" field
        if (to.isOccupied() && to.getCurrentObject().getOwnerID() == playerID) {
            return false;
        }
        //case: other figure on "to" field
        if (to.isOccupied()) {
            IGameObject tmp = to.resetCurrentObject();
            tmp.reset();
        }
        System.out.println("Figure: normal move");
        // from delete object
        from.resetCurrentObject();
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
        IField targetField = MoveLogic.getMovableField(MoveLogic.getSelectedField());
        if (targetField != null) {
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

    /**
     * only used to reset currentField in case of change from qField to standardField
     * @param field new current field
     */
    @Override
    public void setCurrentField(IField field) {
        this.currentField = field;
    }

    public int getOwnerID() {
        return playerID;
    }

    public IField getHomeField() {
        return homeField;
    }

    public IField getStartField() {
        return startField;
    }

    public IField getCurrentField() {
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
