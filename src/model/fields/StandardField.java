package model.fields;

import model.interfaces.IGameField;
import model.enums.Color;
import model.boardLogic.Point;

public class StandardField implements IGameField {

    private Point position;

    private Point boardPosition;

    private float size = 0.12f;

    public StandardField(Point start, Point end, Point boardPosition){
        this.position = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.boardPosition = boardPosition;
    }

    public float getSize(){return this.size;}

    @Override
    public Color getColor() {
        return Color.NONE;
    }

    @Override
    public Point getBoardPosition() {
        return this.boardPosition;
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
