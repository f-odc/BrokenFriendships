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

    //keine möglichkeit sich zu bewegen -> nächster Spieler
    //6 gewürfelt, nochmal gleicher spieler
    //has won
    //nächster Spieler dran nach Zug & pos des Würfels

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
                assertNotEquals(i, adapter.getActivePlayer());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 4);
                assertNotEquals(i, adapter.getActivePlayer());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 3);
                assertNotEquals(i, adapter.getActivePlayer());
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
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.move(i, j, 4 - j);
            }
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
            adapter.move(i, 0, 3);
            assertNotEquals(i, adapter.getActivePlayer());
        }
    }

    @Test
    public void testDiceMovement() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            Vector2f before = adapter.getActiveDicePosition();
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 1);
            assertNotEquals(before, adapter.getActiveDicePosition());
        }
    }
}
