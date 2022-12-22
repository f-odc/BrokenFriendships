package model.logic;

/**
 * Klasse um einen Koordinatenpunkt zu implementieren
 */
public class Point {

    private int x;

    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
