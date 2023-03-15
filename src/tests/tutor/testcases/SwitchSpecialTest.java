package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;


public class SwitchSpecialTest {
    BFTestAdapterExtended3 adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterExtended3();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testNormalSwitch() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 3);
                int figureIndexFirst = adapter.getFigureIndex(i, j);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 3);
                int figureIndexSecond = adapter.getFigureIndex((i + 1) % 4, j);
                adapter.forceComplicatedSpecialSpawn("Switch", i, j);
                adapter.selectTwoFields(figureIndexFirst, figureIndexSecond);
                //first figure has the index of the second
                assertEquals(figureIndexSecond, adapter.getFigureIndex(i, j));
                //second figure has the index of the first
                assertEquals(figureIndexFirst, adapter.getFigureIndex((i + 1) % 4, j));

                adapter.resetTurn();
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 3);
                adapter.setActivePlayer((i + 2) % 4);
                adapter.resetFigures((i + 2) % 4);
                adapter.move((i + 2) % 4, j, 6);
                adapter.move((i + 2) % 4, j, 3);
                int figurePlayer2 = adapter.getFigureIndex((i + 1) % 4, j);
                int figurePlayer3 = adapter.getFigureIndex((i + 2) % 4, j);
                adapter.setActivePlayer(i);
                //player i can change figures from (i + 1) % 4 with figures from (i + 2) % 4
                adapter.forceComplicatedSpecialSpawn("Switch", i, j);
                adapter.selectTwoFields(figurePlayer2, figurePlayer3);
                //second players figure has index of third
                assertEquals(figurePlayer3, adapter.getFigureIndex((i + 1) % 4, j));
                //third players figure has index of second
                assertEquals(figurePlayer2, adapter.getFigureIndex((i + 2) % 4, j));
            }
        }
    }

    @Test
    public void testBaseSwitch() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 39);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 3);
                //figure in base
                int figurePlayerTwo1 = adapter.getFigureIndex((i + 1) % 4, j);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, (j + 1) % 4, 6);
                //figure on startfield
                int figurePlayerTwo2 = adapter.getFigureIndex((i + 1) % 4, (j + 1) % 4);

                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                //figure on startfield
                int figurePlayerOne = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("Switch", i, j);
                adapter.selectTwoFields(figurePlayerOne, figurePlayerTwo1);
                //no switch with figure in base
                assertEquals(figurePlayerOne, adapter.getFigureIndex(i, j));
                assertEquals(figurePlayerTwo1, adapter.getFigureIndex((i + 1) % 4, j));
                adapter.selectTwoFields(figurePlayerOne, figurePlayerTwo2);
                //figure of player 1 now has the index of the second figure of player 2
                assertEquals(figurePlayerTwo2, adapter.getFigureIndex(i, j));
                //Second figure of player 2 now has index of figure of player 1
                assertEquals(figurePlayerOne, adapter.getFigureIndex((i + 1) % 4, (j + 1) % 4));
            }
        }
    }

    @Test
    public void testHomeSwitch() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 3);
                int fieldIndex1 = adapter.getFigureIndex(i, j);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                int fieldIndex2 = adapter.getFigureIndex((i + 1) % 4, j);
                adapter.forceComplicatedSpecialSpawn("Switch", i, j);
                adapter.selectTwoFields(fieldIndex1, adapter.getFigureIndex((i + 1) % 4, (j + 1) % 4));
                //no change with figure in home
                assertEquals(fieldIndex1, adapter.getFigureIndex(i, j));
                //select new and valid pair and change
                adapter.selectTwoFields(fieldIndex1, fieldIndex2);
                //first players figure has index of the second
                assertEquals(fieldIndex2, adapter.getFigureIndex(i, j));
                //second players index has index of the first
                assertEquals(fieldIndex1, adapter.getFigureIndex((i + 1) % 4, j));
            }
        }
    }

    @Test
    public void testNoSwitch() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.forceComplicatedSpecialSpawn("Switch", i, 0);
            assertEquals((i + 1) % 4, adapter.getActivePlayer());

            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 3);
            adapter.setActivePlayer(i);
            adapter.forceComplicatedSpecialSpawn("Switch", i, 0);
            assertEquals((i + 1) % 4, adapter.getActivePlayer());
        }
    }
}
