package model.fields;

import model.interfaces.IGameField;
import model.logic.Point;

import java.awt.*;


public class StandardField implements IGameField {

    private Point position;

    private Color color = Color.gray;

    private float size = 0.12f;

    public StandardField(Point start, Point end){
        this.position = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    public float getSize(){return this.size;}

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean isOccupied() {
        //TODO
        return false;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }
}
