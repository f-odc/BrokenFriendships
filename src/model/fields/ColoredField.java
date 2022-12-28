package model.fields;

import model.interfaces.IGameField;
import model.enums.Color;
import model.enums.Field;
import model.boardLogic.Point;

public class ColoredField implements IGameField {
    private Color color;

    private Point displayPosition;

    private Point boardPosition;

    private float size;

    private Field type;

    /**
     * @param color Die Farbe zu welcher das Feld gehört.
     * @param start Koordinaten der linken oberen Ecke des Feldes auf dem Monitor.
     * @param end Koordinaten der rechten unteren Ecke des Fedles auf dem Monitor.
     * @param boardPosition Koordinaten des Feldes auf dem Spielbrett.
     * @param type 'Field.HOME' für Hausfeld, 'Field.START' für Startfeld, 'Field.BASE' für Zielfeld.
     */
    public ColoredField(Color color, Point start, Point end, Point boardPosition, Field type) {
        this.color = color;
        this.type = type;
        this.displayPosition = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.boardPosition = boardPosition;
        this.size = type == Field.START ? 0.15f : 0.1f;
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Point getDisplayPosition() {
        return this.displayPosition;
    }

    @Override
    public float getSize() {
        return this.size;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public Point getBoardPosition() {
        return this.boardPosition;
    }
}
