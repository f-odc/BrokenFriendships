package model.interfaces;

import model.boardLogic.Point;
import model.enums.Color;

public interface IGameField {

    boolean isOccupied();
    Point getPosition();
    float getSize();
    Color getColor();
    Point getBoardPosition();

}
