package model.interfaces;


import java.awt.*;

public interface IGameField {

    public boolean isOccupied();
    public model.logic.Point getPosition();
    public float getSize();
    public Color getColor();

}
