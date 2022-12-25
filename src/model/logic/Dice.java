package model.logic;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;

public class Dice {

    private String img;

    private int value;

    private int STATEID;

    private Point position;

    private StateBasedEntityManager entityManager;

    private ImageRenderComponent imgComponent;

    Dice(int value, Point position, int state, StateBasedEntityManager entityManager) throws SlickException {
        this.img = getImg(value);
        this.value = value;
        this.STATEID = state;
        this.entityManager = entityManager;
        this.position = position;

        addEntity();
    }

    public String getImg() {
        return this.img;
    }

    public int getValue() {
        return this.value;
    }

    public int throwDice() throws SlickException {
        Random rand = new Random();
        int diceThrow = rand.nextInt(1,7);
        this.value = diceThrow;
        this.img = getImg(diceThrow);

        updateEntity();
        return diceThrow;
    }

    private void addEntity() throws SlickException {
        Entity dice = new Entity("dice");
        dice.setPosition(new Vector2f(position.getX(),position.getY()));
        ImageRenderComponent comp = new ImageRenderComponent(new Image(this.img));
        this.imgComponent = comp;
        dice.addComponent(comp);
        dice.setScale(0.12f);
        ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
        clickEvent.addAction(new DiceAction());
        dice.addComponent(clickEvent);
        entityManager.addEntity(STATEID, dice);
    }

    private void updateEntity() throws SlickException {
        Entity dice = entityManager.getEntity(STATEID, "dice");
        dice.removeComponent(this.imgComponent);
        ImageRenderComponent comp = new ImageRenderComponent(new Image(this.img));
        this.imgComponent = comp;
        dice.addComponent(comp);
    }

    private String getImg(int value) {
        return value == 1 ? "assets/diceOne.png" :
                value == 2 ? "assets/diceTwo.png" :
                        value == 3 ? "assets/diceThree.png" :
                                value == 4 ? "assets/diceFour.png" :
                                        value == 5 ? "assets/diceFive.png" :
                                                "assets/diceSix.png";
    }
}
