package model.boardLogic;

import model.enums.Color;
import model.fields.GameField;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieldCluster {
    protected List<GameField> red = new ArrayList<>();
    protected List<GameField> blue = new ArrayList<>();
    protected List<GameField> green = new ArrayList<>();
    protected List<GameField> yellow = new ArrayList<>();

    /**
     * Funktion um ein ColoredField zu einer der Listen hinzuzufügen.
     *
     * @param field Was hinzugefügt wird.
     */
    public void add(GameField field) {
        Color color = field.getColor();
        if (color == Color.BLUE && blue.size() <= 4) blue.add(field);
        else if (color == Color.GREEN && green.size() <= 4) green.add(field);
        else if (color == Color.RED && red.size() <= 4) red.add(field);
        else if (color == Color.YELLOW && yellow.size() <= 4) yellow.add(field);
    }

    /**
     * Funktion um ein gewisses Feld zu erlangen (wird nicht aus der Liste gelöscht).
     *
     * @param color         Welche Liste abgefragt werden soll.
     * @param boardPosition Mit welcher Position verlgichen werden soll.
     * @return Optional<ColoredField> das gewünschte Feld
     */
    public Optional<GameField> get(Color color, Point boardPosition) {
        return color == Color.BLUE ? blue.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                color == Color.GREEN ? green.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                        color == Color.RED ? red.stream().filter(ele -> ele.equals(boardPosition)).findAny() :
                                yellow.stream().filter(ele -> ele.equals(boardPosition)).findAny();
    }
}
