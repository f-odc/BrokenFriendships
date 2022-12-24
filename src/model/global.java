package model;

import model.interfaces.IGameField;
import model.logic.Board;

/**
 * Class to store any global variables
 */
public class global {
    static public int NUM_OF_FIELDS = 11;       //Anzahl der Felder

    static public int[][] board = {
            {2,2,0,0,1,1,3,0,0,2,2},
            {2,2,0,0,1,2,1,0,0,2,2},
            {0,0,0,0,1,2,1,0,0,0,0},
            {0,0,0,0,1,2,1,0,0,0,0},
            {3,1,1,1,1,2,1,1,1,1,1},
            {1,2,2,2,2,0,2,2,2,2,1},
            {1,1,1,1,1,2,1,1,1,1,3},
            {0,0,0,0,1,2,1,0,0,0,0},
            {0,0,0,0,1,2,1,0,0,0,0},
            {2,2,0,0,1,2,1,0,0,2,2},
            {2,2,0,0,3,1,1,0,0,2,2},
    };

    static public Board BOARD;
}
