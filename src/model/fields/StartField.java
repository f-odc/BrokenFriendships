package model.fields;

import model.interfaces.IGameField;
import model.logic.Color;
import model.logic.Point;

public class StartField implements IGameField {

    private Color color;

    private model.logic.Point position;

    private float size = 0.15f;
    public StartField(Color color, Point start, Point end){
        this.color = color;
        this.position = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    public float getSize(){return this.size;}

    public Color getColor(){
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
