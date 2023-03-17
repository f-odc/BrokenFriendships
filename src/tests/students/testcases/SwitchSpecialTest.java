package tests.students.testcases;

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
                assertNotEquals(figureIndexFirst, figureIndexSecond);
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
                assertNotEquals(figurePlayer2, figurePlayer3);
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
