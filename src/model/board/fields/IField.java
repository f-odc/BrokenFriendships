package model.board.fields;

import eea.engine.entity.Entity;
import model.board.objects.Figure;
import model.board.objects.IGameObject;
import model.enums.FieldType;
import org.newdawn.slick.geom.Vector2f;

public interface IField {

    Vector2f getPosition();
    Entity getBaseEntity();
    FieldType getType();
    boolean isOccupied();
    IGameObject getCurrentObject();
    void setGameObject(IGameObject gameObject);
    IGameObject resetCurrentObject();
    void highlight();
    void unHighlight();
    Figure getCurrentFigure();
    boolean isPlayerStartField();
    boolean isHomeField();
    int getFieldIndex();
}
