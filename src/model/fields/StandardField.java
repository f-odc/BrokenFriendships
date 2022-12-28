package model.fields;

import model.interfaces.IGameField;
import model.enums.Color;
import model.boardLogic.Point;

public class StandardField implements IGameField {

    private Point displayPosition;

    private Point boardPosition;

    private float size = 0.12f;

    /**
     * @param start         Koordinaten der linken oberen Ecke des Feldes auf dem Monitor.
     * @param end           Koordinaten der rechten unteren Ecke des Feldes auf dem Monitor.
     * @param boardPosition Koordinaten des Feldes auf dem Spielbrett.
     */
    public StandardField(Point start, Point end, Point boardPosition) {
        this.displayPosition = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
        this.boardPosition = boardPosition;
    }

    public float getSize() {
        return this.size;
    }

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
        return false;        //TODO
    }

    @Override
    public Point getDisplayPosition() {
        return this.displayPosition;
    }
}
