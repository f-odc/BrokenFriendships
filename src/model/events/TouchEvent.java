package model.events;

import eea.engine.event.Event;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Date;

public class TouchEvent extends Event {

    long startEventTime = -1;
    Vector2f previousMouseCoords;

    public TouchEvent() {
        super("MouseEnteredEvent");
    }

    protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
        Shape shape = this.getOwnerEntity().getShape();
        Vector2f mousePosition = new Vector2f((float) gc.getInput().getMouseX(), (float) gc.getInput().getMouseY());

        // Mouse hovered above shape
        if (shape.contains(mousePosition.x, mousePosition.y)) {

            // check if previous coords exist
            if (previousMouseCoords == null) {
                previousMouseCoords = mousePosition;
            }
            // check if startTime set
            if (startEventTime == -1) {
                // store time
                startEventTime = new Date().getTime();
                // first event trigger -> return true
                return true;
            }
            // startTime is set
            else{
                // time diff in milliseconds
                long timeDif = (new Date().getTime()) - startEventTime;
                // check if 0.8 seconds are passed
                if (timeDif >= 800){
                    // check coords equality
                    if (previousMouseCoords.getX() == mousePosition.getX() && previousMouseCoords.getY() == mousePosition.getY()){
                        return false;
                    }
                    // coords are changing -> new event
                    else {
                        // reset values+
                        startEventTime = -1;
                    }
                }
            }
            previousMouseCoords = mousePosition;
        }
        // default -> return false
        return false;
    }
}
