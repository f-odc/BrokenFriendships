package model.board.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import model.actions.DiceAction;
import model.global;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

public class Dice {

    private int value;  //Augenzahl des Würfels

    private Vector2f[] displayPosition;  //Position auf dem Monitor

    private Image[] diceImages = new Image[6];

    private ImageRenderComponent imgComponent; //zwischenspeicher des ImageRenderComponent um dies in updateEntity() zu überschreiben

    /**
     * Initalisiert die Würfel Instanz
     * @param displayPosition die Koordinaten auf dem Monitor.
     */
    public Dice(Vector2f[] displayPosition){
        this.value = 1;
        this.displayPosition = displayPosition;
        initDiceImages();
        addDiceEntity();
    }

    /**
     * Funktion die die Augenzahl des Würfels wiedergibt.
     * @return Augenzahl des Würfels.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Funktion die einen Würfelwurf simuliert.
     * @return Augenzahl des würfels.
     */
    public int throwDice(){
        int diceThrow = new Random().nextInt(6) + 1;
        this.value = diceThrow;
        // update dice image
        updateEntity();
        // start animation
        animate();
        return diceThrow;
    }

    /**
     * Create dice animation
     */
    public void animate(){
        // create new animation
        global.diceAnimation = new Animation();
        global.diceAnimation.setLooping(false);
        for (int i=0; i<4; i++){
            int randomPictureIndex = new Random().nextInt(6);
            global.diceAnimation.addFrame(diceImages[randomPictureIndex], 150);
        }
        // set last frame the dice value
        global.diceAnimation.addFrame(diceImages[value-1], 1000);
    }

    /**
     * Append to the dice animation a frame with a next arrow
     */
    public void animateNextArrow(){
        try {
            global.diceAnimation.addFrame(new Image("assets/dice/nextArrow" + global.activePlayer + ".png"), 350);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop and set animation to null
     */
    public void resetAnimation(){
        global.diceAnimation = new Animation();
    }

    /**
     * Stores the dice images from assets in class
     */
    private void initDiceImages(){
        try {
            diceImages[0] = new Image("assets/dice/diceOne.png");
            diceImages[1] = new Image("assets/dice/diceTwo.png");
            diceImages[2] = new Image("assets/dice/diceThree.png");
            diceImages[3] = new Image("assets/dice/diceFour.png");
            diceImages[4] = new Image("assets/dice/diceFive.png");
            diceImages[5] = new Image("assets/dice/diceSix.png");
        } catch (SlickException e) {
            System.out.println("Dice images missing!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialisiert die Entity für den Würfel zusammen mit dem passenden Bild
     */
    private void addDiceEntity(){
        Entity dice = new Entity("dice");
        dice.setPosition(new Vector2f(displayPosition[0].getX(), displayPosition[0].getY()));
        ImageRenderComponent comp = new ImageRenderComponent(diceImages[value-1]);
        this.imgComponent = comp;
        dice.addComponent(comp);
        dice.setScale(0.12f);
        // Würfel wurf Event
        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        clickEvent.addAction(new DiceAction());
        dice.addComponent(clickEvent);
        global.entityManager.addEntity(global.GAMEPLAY_STATE, dice);
    }

    /**
     * Funktion die nach jedem Würfelwurf die Entity mit dem Bild der neuen Augenzahl aktualisiert.
     */
    private void updateEntity(){
        Entity dice = global.entityManager.getEntity(global.GAMEPLAY_STATE, "dice");
        dice.removeComponent(this.imgComponent);
        ImageRenderComponent comp = null;
        // set new image
        comp = new ImageRenderComponent(diceImages[value-1]);
        this.imgComponent = comp;
        dice.addComponent(comp);
    }

    /**
     * Funktion um die Position des Würfels zu tauschen, wenn eine andere Farbe dran ist.
     * @param playerID welche Farbe dran ist
     */
    public void setPosition(int playerID) {
        Vector2f pos = displayPosition[playerID];
        Entity dice = global.entityManager.getEntity(global.GAMEPLAY_STATE, "dice");
        dice.setPosition(pos);
    }

    /**
     * Get current position of the dice depending on current player
     * @return the current position of the dice
     */
    public Vector2f getCurrentPosition(){
        return displayPosition[global.activePlayer];
    }
}
