package model.board.objects.specials;

import eea.engine.entity.Entity;
import model.board.fields.IField;
import model.board.objects.IGameObject;
import model.game.logic.GameLogic;
import model.global;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import ui.GameplayState;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GrowingSpecial implements IGameObject {


    @Override
    public boolean moveTo(IField targetField, boolean switchFlag) {
        return false;
    }

    @Override
    public void activate(IGameObject sourceGameObject) {
        // get neighbors fields
        ArrayList<IField> neighbors = global.BOARD.getNeighbors(sourceGameObject.getCurrentField());
        animate(sourceGameObject);
        for (IField field : neighbors){
            // reset all neighboring figures
            if (field.getCurrentFigure() != null){
                field.getCurrentFigure().activate(sourceGameObject);
            }
        }
    }

    public void animate(IGameObject fig){
        // scale source field to cover neighbors
        Entity figEntity = fig.getEntity();
        Float scale = figEntity.getScale();
        figEntity.setScale(scale + 0.225f);

        // wait a delay before resetting and starting new player round
        Timer timer = new Timer();
        TimerTask startNewPhaseAfterAnimation = new TimerTask() {
            @Override
            public void run() {
                figEntity.setScale(scale);
                GameLogic.nextPlayer();
            }
        };
        timer.schedule(startNewPhaseAfterAnimation, 2000);
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean requiresFieldInteraction() {
        return false;
    }

    @Override
    public int getOwnerID() {
        return 0;
    }

    @Override
    public void setCurrentField(IField field) {

    }

    @Override
    public IField getCurrentField() {
        return null;
    }

    @Override
    public Entity getEntity() {
        return null;
    }
}
