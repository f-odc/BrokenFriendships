package model.game;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.QuestionmarkAction;
import model.boardLogic.fields.IField;
import model.boardLogic.fields.QuestionmarkField;
import model.enums.Field;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardLogic {

    //list to coordinate all pairs of old and Questionmark fields
    static List<FieldsAndIndex> fields = new ArrayList<>();

    //current number of QuestionmarkFields
    static int numOfQFields = 0;

    //max number of questionmarkFields
    static final int MAX_Q_FIELDS = 12;

    /**
     * places a questionmark on a random field that is not occupies, not a startfield and not already a questionmark field.
     */
    public static void placeQuestionmarkField() {
        Random rand = new Random();

        if (numOfQFields <= MAX_Q_FIELDS) {
            int fieldIndex = rand.nextInt(40);
            IField oldField = global.BOARD.getPlayField(fieldIndex);
            //reroll the random number until all criteria are met
            while (isStartField(oldField) || oldField.getType() == Field.QUESTIONMARK || oldField.isOccupied()) {
                fieldIndex = rand.nextInt(40);
                oldField = global.BOARD.getPlayField(fieldIndex);
            }

            try {
                //create new questionmarkentity
                Entity fieldEntity = createNewEntity(oldField);
                QuestionmarkField qField = new QuestionmarkField(fieldEntity);
                //add action
                ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
                clickEvent.addAction(new QuestionmarkAction(qField));
                qField.getBaseEntity().addComponent(clickEvent);

                //add index, old field and new field to list
                fields.add(new FieldsAndIndex(fieldIndex, oldField, qField));
                global.BOARD.setPlayField(fieldIndex, qField);
                numOfQFields++;
            } catch (SlickException e) {
                Vector2f pos = oldField.getPosition();
                System.out.println("QuestionmarkField was not able to be set at postion: " + pos.getX() + "," + pos.getY());
            }
        }
    }

    /**
     * removes a single questionmarkfield
     * @param pos position of current qField, which is to be deleted
     */
    public static void removeQuestionmarkField(Vector2f pos) {
        for (int i = fields.size() - 1; i >= 0; i--) {
            Vector2f tmpPos = fields.get(i).qField.getPosition();
            if (pos.getX() == tmpPos.getX() && pos.getY() == tmpPos.getY()) {
                FieldsAndIndex field = fields.remove(i);

                //remove questionmarkField and add standardField to entitymanager
                global.entityManager.removeEntity(global.GAMEPLAY_STATE, field.qField.getBaseEntity());
                global.entityManager.addEntity(global.GAMEPLAY_STATE, field.oldField.getBaseEntity());

                //transfer object to standard field
                if (field.qField.getCurrentObject() != null) {
                    field.oldField.setGameObject(field.qField.resetCurrentObject());
                    field.oldField.getCurrentObject().setCurrentField(field.oldField);
                }

                global.BOARD.setPlayField(field.index, field.oldField);
                numOfQFields--;
                return;
            }
        }
    }

    /**
     * test if a field is a startfield for any color
     * @param field field under test
     * @return true if field a startfield for any color
     */
    private static boolean isStartField(IField field) {
        for (Player player : global.players) {
            if (player.setStartField().equals(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates an ENtity for the QuestionmarkField.
     * @param oldField The field which is to be overriden.
     * @return the new entity
     * @throws SlickException if Image cannot be set
     */
    private static Entity createNewEntity(IField oldField) throws SlickException {
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, oldField.getBaseEntity());
        Entity fieldEntity = new Entity("questionmarkField: " + oldField.getPosition().getX() + "," + oldField.getPosition().getY());
        fieldEntity.setPosition(oldField.getPosition());
        //initialisieren das Bild und die Größe der Entity
        fieldEntity.addComponent(new ImageRenderComponent(new Image("assets/field/questionmarkField.png")));
        fieldEntity.setScale(global.STANDARD_AND_BASE_FIELD_SIZE);

        global.entityManager.addEntity(global.GAMEPLAY_STATE, fieldEntity);
        return fieldEntity;
    }
}

/**
 * Class to save entries in a convenient way
 */
class FieldsAndIndex {
    public int index;
    public IField oldField;
    public QuestionmarkField qField;

    FieldsAndIndex(int index, IField oldField, QuestionmarkField qField) {
        this.index = index;
        this.oldField = oldField;
        this.qField = qField;
    }

}
