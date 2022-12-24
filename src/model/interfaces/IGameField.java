package model.interfaces;

import model.logic.Color;

public interface IGameField {

    boolean isOccupied();
    model.logic.Point getPosition();
    float getSize();
    Color getColor();

}
