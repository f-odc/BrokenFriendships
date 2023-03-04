package model.board.objects;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.enums.Phase;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import ui.BrokenFriendships;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Mystery implements IGameObject {

    private Entity entity;
    private int id;
    private int ownerID;
    private IField currentField;

    public Mystery(int id, int ownerID, IField field) {
        this.id = id;
        this.ownerID = ownerID;
        this.currentField = field;
        initEntity();
    }

    /**
     * Init mystery entity
     */
    public void initEntity() {
        Entity newEntity = new Entity("player:" + this.ownerID + "-mystery:" + this.id + "-hashcode:" + this.hashCode());
        try {
            if (!BrokenFriendships.debug)
                newEntity.addComponent(new ImageRenderComponent(new Image("assets/objects/questionmark.png")));
        } catch (SlickException e) {
            System.out.println("Cannot find: assets/objects/questionmark.png!");
            throw new RuntimeException(e);
        }
        newEntity.setScale(0.15f);
        // add to list
        this.entity = newEntity;
    }

    @Override
    public boolean moveTo(IField targetField, boolean switchFlag) {
        return false;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        System.out.println("Activate Mystery");
        global.phase = Phase.MYSTERY_SELECTION_PHASE;

        // random select mystery
        int mysteryNr = new Random().nextInt(8);

        // animate mystery selection
        animateMystery(mysteryNr);

        // wait till animation is over:
        int animationDuration = 0;
        for (int frameDuration : global.mysteryAnimation.getDurations()) {
            animationDuration += frameDuration;
        }

        // after delay execute new phase
        Timer timer = new Timer();
        TimerTask startNewPhaseAfterAnimation = new TimerTask() {
            @Override
            public void run() {
                // animation is finished
                // execute special execution phase
                GameLogic.executeInitSpecialsPhase(mysteryNr, sourceGameObject);
            }
        };
        timer.schedule(startNewPhaseAfterAnimation, animationDuration);

        // dismiss mystery from field
        reset();
        GameLogic.numOfMysteryFields--;
    }

    /**
     * Animate mystery selection
     *
     * @param specialNr number of selected special
     */
    private void animateMystery(int specialNr) {

        // create new animation
        global.mysteryAnimation = new Animation();
        global.mysteryAnimation.setLooping(false);
        if (!BrokenFriendships.debug) {
            try {
                // mystery selection animation
                for (int i = 1; i < 12; i++) {
                    global.mysteryAnimation.addFrame(new Image(global.specialsMap.get(i % 8).get(1)), 150 * i / 2);
                }
                // set last frame the selected mystery
                global.mysteryAnimation.addFrame(new Image(global.specialsMap.get(specialNr).get(1)), 2000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void reset() {
        // remove entity from entity manager
        global.entityManager.removeEntity(global.GAMEPLAY_STATE, entity);
        // reset the current field
        currentField.resetCurrentObject();
        entity = null;
        currentField = null;
    }

    @Override
    public boolean requiresFieldInteraction() {
        return true;
    }

    @Override
    public int getOwnerID() {
        return ownerID;
    }

    @Override
    public void setCurrentField(IField field) {
        this.currentField = field;
    }

    @Override
    public IField getCurrentField() {
        return currentField;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
