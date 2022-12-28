package model.boardLogic;

import model.enums.Color;
import model.fields.ColoredField;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieldCluster {
    private List<ColoredField> red = new ArrayList<>();
    private List<ColoredField> blue = new ArrayList<>();
    private List<ColoredField> green = new ArrayList<>();
    private List<ColoredField> yellow = new ArrayList<>();

    /**
     * Funktion um ein ColoredField zu einer der Listen hinzuzufügen.
     * @param color Welcher Liste hinzugefügt wird.
     * @param field Was hinzugefügt wird.
     */
    public void add(Color color, ColoredField field) {
        if (color == Color.BLUE) blue.add(field);
        else if (color == Color.GREEN) green.add(field);
        else if (color == Color.RED) red.add(field);
        else if (color == Color.YELLOW) yellow.add(field);
    }

    /**
     * Funktion um ein gewisses Feld zu erlangen (wird nicht aus der Liste gelöscht).
     * @param color Welche Liste abgefragt werden soll.
     * @param boardPosition Mit welcher Position verlgichen werden soll.
     * @return Optional<ColoredField> das gewünschte Feld
     */
    public Optional<ColoredField> get(Color color, Point boardPosition) {
        return color == Color.BLUE ? blue.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                color == Color.GREEN ? green.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                        color == Color.RED ? red.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                                yellow.stream().filter(ele -> ele.equals(boardPosition)).findAny();
    }

    /**
     * Funktion um ein Element aus einer Liste zu entfernen.
     * @param color Welche Liste gewählt werden soll.
     * @return Das entfernte Element.
     */
    public ColoredField removeAny(Color color) {
        //TODO implement house version of this code
        return color == Color.BLUE ? blue.remove(blue.size() - 1) :
                color == Color.GREEN ? green.remove(green.size() - 1) :
                        color == Color.RED ? red.remove(red.size() - 1) :
                                yellow.remove(yellow.size() - 1);
    }

}
