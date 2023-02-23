package model.board.fields;

import model.enums.Color;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlayerColorFields {
    public List<BoardField> red = new ArrayList<>();
    public List<BoardField> blue = new ArrayList<>();
    public List<BoardField> green = new ArrayList<>();
    public List<BoardField> yellow = new ArrayList<>();

    /**
     * Funktion um ein ColoredField zu einer der Listen hinzuzufügen.
     *
     * @param field Was hinzugefügt wird.
     */
    public void add(BoardField field, Color color) {
        if (color == Color.BLUE && blue.size() <= 4) blue.add(field);
        else if (color == Color.GREEN && green.size() <= 4) green.add(field);
        else if (color == Color.RED && red.size() <= 4) red.add(field);
        else if (color == Color.YELLOW && yellow.size() <= 4) yellow.add(field);
    }

    /**
     * reverses the order of the blue and green fields
     */
    public void initCorrectOrder(){
        Collections.reverse(blue);
        Collections.reverse(green);
    }

    /**
     * Funktion um ein gewisses Feld zu erlangen (wird nicht aus der Liste gelöscht).
     *
     * @param color         Welche Liste abgefragt werden soll.
     * @param boardPosition Mit welcher Position verlgichen werden soll.
     * @return Optional<ColoredField> das gewünschte Feld
     */
    public Optional<BoardField> get(Color color, Vector2f boardPosition) {
        float x = boardPosition.getX();
        float y = boardPosition.getY();
        System.out.println("get color: " + color + " position: " + x + "," + y);
        return color == Color.BLUE ? blue.stream().filter(ele -> ele.getPosition().getX() == x && ele.getPosition().getY() == y).findAny() :
                color == Color.GREEN ? green.stream().filter(ele -> ele.getPosition().getX() == x && ele.getPosition().getY() == y).findAny() :
                        color == Color.RED ? red.stream().filter(ele -> ele.getPosition().getX() == x && ele.getPosition().getY() == y).findAny() :
                                yellow.stream().filter(ele -> ele.getPosition().getX() == x && ele.getPosition().getY() == y).findAny();
    }

    /**
     * Get player fields
     *
     * @param id player id
     * @return List of BoardFields
     */
    public List<BoardField> getFieldsFromId(int id) {
        switch (id) {
            case 0:
                return red;
            case 1:
                return yellow;
            case 2:
                return blue;
            case 3:
                return green;
            default:
                throw new RuntimeException("Invalid ID");
        }
    }
}
