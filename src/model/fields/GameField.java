package model.fields;

import model.enums.Color;
import model.enums.Field;
import model.boardLogic.Point;

public class GameField {
    private Color color;

    private Point displayPosition;

    private Point boardPosition;

    private float size;

    private Field type;

    /**
     * @param color         Die Farbe zu welcher das Feld gehört.
     * @param start         Koordinaten der linken oberen Ecke des Feldes auf dem Monitor.
     * @param end           Koordinaten der rechten unteren Ecke des Fedles auf dem Monitor.
     * @param boardPosition Koordinaten des Feldes auf dem Spielbrett.
     * @param type          'Field.HOME' für Hausfeld, 'Field.START' für Startfeld, 'Field.BASE' für Zielfeld.
     */
    public GameField(Color color, Point start, Point end, Point boardPosition, Field type) {
        this.color = color;
        this.type = type;
        this.displayPosition = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.boardPosition = boardPosition;
        this.size = type == Field.START ? 0.15f : 0.1f;
    }

    //TODO implement
    public boolean isOccupied() {
        return false;
    }

    public Point getDisplayPosition() {
        return this.displayPosition;
    }

    public float getSize() {
        return this.size;
    }

    public Color getColor() {
        return this.color;
    }

    public Point getBoardPosition() {
        return this.boardPosition;
    }

    public boolean equals(Point other) {
        return this.boardPosition.getX() == other.getX() &&
                this.boardPosition.getY() == other.getY();
    }

}
