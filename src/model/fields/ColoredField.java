package model.fields;

import model.interfaces.IGameField;
import model.logic.Color;
import model.logic.Field;
import model.logic.Point;

import java.util.Objects;

public class ColoredField implements IGameField {
    private Color color;

    private Point position;

    private float size;

    private Field type;

    public ColoredField(Color color, Point start, Point end, Field type) {
        this.color = color;
        this.type = type;
        this.position = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.size = type == Field.START ? 0.15f : 0.1f;
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public float getSize() {
        return this.size;
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}
