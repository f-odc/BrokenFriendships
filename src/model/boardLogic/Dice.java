package model.boardLogic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.DiceAction;
import model.global;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

public class Dice {

    private int value;  //Augenzahl des Würfels

    private Point displayPosition;  //Position auf dem Monitor


    private ImageRenderComponent imgComponent; //zwischenspeicher des ImageRenderComponent um dies in updateEntity() zu überschreiben

    /**
     * @param value der initialisierungs Wert des Würfels.
     * @param displayPosition die Koordinaten auf dem Monitor.
     * @throws SlickException falls das zum Würfel gehörige Bild nicht gefunden wurde.
     */
    Dice(int value, Point displayPosition) throws SlickException {
        this.value = value;
        this.displayPosition = displayPosition;

        addEntity();
    }

    /**
     * Funktion die die AUgenzahl des Würfels wiedergibt.
     * @return Augenzahl des Würfels.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Funktion die einen Würfelwurf simuliert.
     * @return Augenzahl des würfels.
     * @throws SlickException falls das zum Würfel gehörige Bild nicht gefunden wurde.
     */
    public int throwDice() throws SlickException {
        Random rand = new Random();
        int diceThrow = rand.nextInt(1,7);
        this.value = diceThrow;

        updateEntity();
        return diceThrow;
    }

    /**
     * Initialisiert die Entity für den Würfel.
     * @throws SlickException falls das zum Würfel gehörige Bild nicht gefunden wurde.
     */
    private void addEntity() throws SlickException {
        Entity dice = new Entity("dice");
        dice.setPosition(new Vector2f(displayPosition.getX(), displayPosition.getY()));
        ImageRenderComponent comp = new ImageRenderComponent(new Image(getImg(this.value)));
        this.imgComponent = comp;
        dice.addComponent(comp);
        dice.setScale(0.12f);
        //Würfel wurf Event
        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        clickEvent.addAction(new DiceAction());
        dice.addComponent(clickEvent);
        global.entityManager.addEntity(global.GAMEPLAY_STATE, dice);
    }

    /**
     * Funktion die nach jedem Würfelwurf die Entity mit dem Bild der neuen Augenzahl aktualisiert.
     * @throws SlickException falls das zum Würfel gehörige Bild nicht gefunden wurde.
     */
    private void updateEntity() throws SlickException {
        Entity dice = global.entityManager.getEntity(global.GAMEPLAY_STATE, "dice");
        dice.removeComponent(this.imgComponent);
        ImageRenderComponent comp = new ImageRenderComponent(new Image(getImg(this.value)));
        this.imgComponent = comp;
        dice.addComponent(comp);
    }

    /**
     * Funktion die das zur Augenzahl gehörige Bild herrausfindet.
     * @param value Augenzahl des Würfels.
     * @return Pfad zum Bild.
     */
    private String getImg(int value) {
        return value == 1 ? "assets/diceOne.png" :
                value == 2 ? "assets/diceTwo.png" :
                        value == 3 ? "assets/diceThree.png" :
                                value == 4 ? "assets/diceFour.png" :
                                        value == 5 ? "assets/diceFive.png" :
                                                "assets/diceSix.png";
    }
}
