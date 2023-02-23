package model.board.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.board.fields.IField;
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
     * create the figure entity and add to entity list
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

    @Override
    public boolean moveTo(IField targetField, boolean switchFlag){
        // check if target field is valid
        if (targetField.getCurrentFigure() != null && targetField.getCurrentFigure().getOwnerID() == global.activePlayer){
            return false;
        }
        // store old fields
        Figure targetFig = targetField.getCurrentFigure();
        IField currentStoredField = currentField;

        // check if target field is occupied
        if (targetField.isOccupied()){
            // activate the current object on the field
            targetField.getCurrentObject().activate(currentField.getCurrentObject());
            // remove the occupying object from board
            targetField.resetCurrentObject();
        }
        // set figure to target field
        targetField.setGameObject(this);
        // reset current field
        currentField.resetCurrentObject();
        // set current field
        setCurrentField(targetField);

        // check if switch with target figure possible
        if (targetFig != null && switchFlag){
            System.out.println("Switch");
            targetFig.moveTo(currentStoredField, false);
        }
        return true;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        reset();
    }

    @Override
    public void reset() {
        moveTo(homeField, false);
    }

    @Override
    public boolean requiresFieldInteraction() {
        return true;
    }

    @Override
    public void setCurrentField(IField field) {
        this.currentField = field;    }

    @Override
    public int getOwnerID() {
        return playerID;
    }

    /**
     * Get start field of the figure
     * @return IField on which the figure start the game
     */
    public IField getStartField() {
        return startField;
    }

    /**
     * Get the current fields of the figure
     * @return IField on which the figure is currently standing
     */
    public IField getCurrentField() {
        return currentField;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

}
