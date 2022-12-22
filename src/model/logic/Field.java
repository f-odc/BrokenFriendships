package model.logic;

public class Field {
    private Point start;

    private Point mid;

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
