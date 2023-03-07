package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;
import tests.adapter.BFTestAdapterMinimal;

import static org.junit.Assert.*;

public class RoundTest {
    BFTestAdapterMinimal adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterMinimal();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testNextPlayerAfterNoMovement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 3);
                adapter.move(i, j, 3);
                adapter.move(i, j, 3);
                //player changes
                assertNotEquals(i, adapter.getActivePlayer());
                //next player after 3 get out of base tries
                assertEquals((i + 1) % 4, adapter.getActivePlayer());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 4);
                //player changes
                assertNotEquals(i, adapter.getActivePlayer());
                //next player after standard move
                assertEquals((i + 1) % 4, adapter.getActivePlayer());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 3);
                //player changes
                assertNotEquals(i, adapter.getActivePlayer());
                //next player after
                assertEquals((i + 1) % 4, adapter.getActivePlayer());
            }
        }
    }

    @Test
    public void testWinCondition() {
        for (int i = 0; i < 4; i++) {
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.resetTurn();
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 4 - j);
            }
            //player has all figures in base
            assertTrue(adapter.hasWon(i));
            adapter.resetFigures(i);
        }
    }

    @Test
    public void testSamePlayerAfter6() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            //same player after a 6
            assertEquals(i, adapter.getActivePlayer());
            adapter.move(i, 0, 3);
            //player changes
            assertNotEquals(i, adapter.getActivePlayer());
            //next player after not getting a 6
            assertEquals((i + 1) % 4, adapter.getActivePlayer());
        }
    }

    @Test
    public void testDiceMovement() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            //dice has the correct position
            assertEquals(adapter.getDicePosition(i), adapter.getActiveDicePosition());
            Vector2f before = adapter.getActiveDicePosition();
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 1);
            //dice has moved
            assertNotEquals(before, adapter.getActiveDicePosition());
        }
    }
}
