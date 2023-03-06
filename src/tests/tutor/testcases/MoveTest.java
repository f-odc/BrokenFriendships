package tests.tutor.testcases;

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

    //Alle Figuren sind im Haus, 1 * keine 6 gewürfelt-
    //Alle Figuren sind im Haus, 2 * keine 6 gewürfelt-
    //Alle Figuren sind im haus, 3 * keine 6 gewürfelt-
    //Alle Figuren sind im Haus, 1 * keine 6 gewürfelt, 1* 6 gewürfelt-
    //Alle Figuren sind im Haus, 2 * keine 6 gewürfelt, 1* 6 gewürfelt-
    //Alle Figuren sind im Haus, 3 * keine 6 gewürfelt, 1* 6 gewürfelt-

    //1 Figur in Home, 3 in Base und können sich nicht bewegen, darf Spieler 3 mal würfeln-
    //3 Figur in Home, 1 in Base und könnte sich um 1 bewegen, darf Spieler trotzdem 3 mal würfeln-

    //Figur schlagen klappt-

    //Figur kann in die base rein, geht sie rein?-
    //Figur kann nicht in die base rein, geht sie vorbei?-

    @Test
    public void testOutOfHome() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.resetTurn();
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(3, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 1);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(2, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 2);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(1, adapter.getDiceThrowAttempts());
                adapter.move(i, j, 3);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                adapter.resetFigures(i);

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 3);
                adapter.move(i, j, 4);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 2);
                adapter.move(i, j, 5);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 1);
                adapter.move(i, j, 6);
                assertFalse(adapter.allHomeFieldsOccupied(i));
                adapter.resetFigures(i);

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 3);
                adapter.move(i, j, 2);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 2);
                adapter.move(i, j, 6);
                assertFalse(adapter.allHomeFieldsOccupied(i));
                adapter.resetFigures(i);

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                assertTrue(adapter.allHomeFieldsOccupied(i));
                assertEquals(adapter.getDiceThrowAttempts(), 3);
                adapter.move(i, j, 6);
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
                assertTrue(adapter.occupiesHomeField(i, (j + 1) % 4));
                adapter.resetFigures(i);
            }
        }
    }

    @Test
    public void testOutOfBaseCatchFigure() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 30);
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                assertTrue(adapter.allHomeFieldsOccupied((i + 1) % 4));
                adapter.resetFigures(i);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
            }
        }
    }

    @Test
    public void testEnterBase() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 2);
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
                adapter.move(i, j, 5);
                assertFalse(adapter.occupiesBaseField(i, 0));
                adapter.resetFigures(i);
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
            assertEquals(3, adapter.getDiceThrowAttempts());
            adapter.move(i, 3, 1);
            assertEquals(2, adapter.getDiceThrowAttempts());
            adapter.move(i, 3, 2);
            assertEquals(1, adapter.getDiceThrowAttempts());
            adapter.resetFigures(i);

            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            for (int j = 0; j < 3; j++) {
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.move(i, j, j);
            }
            assertEquals(3, adapter.getDiceThrowAttempts());
            adapter.move(i, 3, 1);
            assertEquals(3, adapter.getDiceThrowAttempts());
            adapter.resetFigures(i);
        }
    }
}
