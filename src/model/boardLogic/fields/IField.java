package model.boardLogic.fields;

import eea.engine.entity.Entity;
import model.boardLogic.objects.Figure;
import model.boardLogic.objects.IGameObject;
import model.enums.Field;
import org.newdawn.slick.geom.Vector2f;

public interface IField {

    Vector2f getPosition();
    Entity getBaseEntity();
    Field getType();
    boolean isOccupied();
    IGameObject getCurrentObject();
    void setGameObject(IGameObject gameObject);
    IGameObject resetCurrentObject();
    void highlight();
    void deHighlight();
    boolean equals(BoardField other);
    Figure getCurrentFigure();
}
