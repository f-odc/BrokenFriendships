package tests.students.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterMinimal;

import static org.junit.Assert.*;

public class MoveTest {
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
    public void testOutOfHome() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                //no figures moved
                assertTrue(adapter.allHomeFieldsOccupied(i));
                //no dice thrown
                assertEquals(3, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 1);
                //no 6 thrown
                assertTrue(adapter.allHomeFieldsOccupied(i));
                //dice thrown once
                assertEquals(2, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 2);
                //no 6 thrown
                assertTrue(adapter.allHomeFieldsOccupied(i));
                //dice thrown twice
                assertEquals(1, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 3);
                //no 6 thrown
                assertTrue(adapter.allHomeFieldsOccupied(i));
                //dice thrown three times
                assertTrue(adapter.allHomeFieldsOccupied(i));
                adapter.resetFigures(i);

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                //no figures moved
                assertTrue(adapter.allHomeFieldsOccupied(i));
                //no dice thrown
                assertEquals(adapter.getDiceThrowAttempts(), 3);
                adapter.move(i, j, 6);
                //6 thrown
                assertFalse(adapter.allHomeFieldsOccupied(i));
                adapter.resetFigures(i);
            }
        }
    }

    @Test
    public void testOutOfHomeBlocked() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, (j + 1) % 4, 6);
                //startfield blocked
                assertTrue(adapter.occupiesHomeField(i, (j + 1) % 4));
                adapter.resetFigures(i);
            }
        }
    }

    @Test
    public void testBeatFigure() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 4);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 34);
                //figure from i is beaten
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertFalse(adapter.occupiesHomeField((i + 1) % 4, j));
            }
        }
    }

    @Test
    public void testEnterBase() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 2);
                //enter base
                assertTrue(adapter.occupiesBaseField(i, j));
                adapter.resetFigures(i);
            }
        }
    }

    @Test
    public void testCannotEnterBase() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 5);
                //moves past base
                assertFalse(adapter.occupiesBaseField(i, j));
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 3);
                //moves into base
                assertTrue(adapter.occupiesBaseField(i, j));
            }
        }
    }

    @Test
    public void testCannotMove() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            for (int j = 0; j < 3; j++) {
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 4 - j);
            }
            //no dice thrown
            assertEquals(3, adapter.getDiceThrowAttempts());
            adapter.move(i, 3, 1);
            //dice thrown once
            assertEquals(2, adapter.getDiceThrowAttempts());
            adapter.move(i, 3, 2);
            //dice thrown twice
            assertEquals(1, adapter.getDiceThrowAttempts());

            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i,0,6);
            adapter.move(i,0,39);
            adapter.setActivePlayer(i);
            adapter.move(i,0,3);
            adapter.setActivePlayer(i);
            adapter.move(i,1,4);
            //no 3 tries, because move of 1 possible
            assertEquals((i + 1) % 4, adapter.getActivePlayer());
        }
    }
}
