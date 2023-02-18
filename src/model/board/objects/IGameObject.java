package model.board.objects;

import eea.engine.entity.Entity;
import model.board.fields.IField;

public interface IGameObject {

    /**
     * Move the game object to a desired field, if target field contains game object, activate object
     * @param targetField the field, to which the object has to be moved
     * @return true if movement was possible, else false
     */
    boolean moveTo(IField targetField);

    /**
     * Is performed if other figure moves to the same field as the game object
     * @return
     */
    void activate();

    /**
     * Dismiss game object from play fields
     */
    void reset();

    /**
     * Get the owner of the game object
     * @return id of owner
     */
    int getOwnerID();

    /**
     * Store the current field of the game object
     * @param field current field of game object
     */
    void setCurrentField(IField field);

    /**
     * Get entity of game object
     * @return game object entity
     */
    Entity getEntity();




}
