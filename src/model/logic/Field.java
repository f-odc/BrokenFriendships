package model.logic;

/**
 * Klasse um die einzelnen Felder des Spielbrettes zu implementieren
 */
public class Field {
    //linke obere Ecke
    private Point start;

    //mitte von x und y Werten der Kanten
    private Point mid;

    //rechte untere Ecke
    private Point end;

    public Field(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.mid = new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    public Point getStart() {
        return start;
    }

    public Point getMid() {
        return mid;
    }

    public Point getEnd() {
        return end;
    }
}
