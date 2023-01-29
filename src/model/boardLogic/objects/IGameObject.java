package model.boardLogic.objects;

import eea.engine.entity.Entity;
import model.boardLogic.fields.IField;

public interface IGameObject {

    Entity getEntity();

    boolean activate();

    int getOwnerID();

    void reset();

    void setCurrentField(IField field);


}
