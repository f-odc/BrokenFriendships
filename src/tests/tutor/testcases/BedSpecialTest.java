package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;

public class BedSpecialTest {
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
    public void testBedActivation() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bed", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                adapter.move(i, j, 1);
                //bed gets activated, when figure moves on its field
                assertTrue(adapter.isBedActive(i));
            }
        }
    }

    @Test
    public void testBedFunctionality() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bed", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                adapter.move(i, j, 1);
                //next player after bed activation
                assertNotEquals(i, adapter.getActivePlayer());
                adapter.skipTurn();
                adapter.skipTurn();
                adapter.skipTurn();
                //player gets skipped, if he is asleep
                assertNotEquals(i, adapter.getActivePlayer());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bed", (i + 1) % 4, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                adapter.move(i, j, 1);
                adapter.skipTurn();
                adapter.skipTurn();
                adapter.skipTurn();
                //bed placed by player 2 affects player 1
                assertNotEquals(i, adapter.getActivePlayer());
            }
        }
    }

    @Test
    public void testMovementAfter6() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bed", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 6);
                adapter.move(i, j, 6);
                //next player after bed activation with a 6
                assertNotEquals(i, adapter.getActivePlayer());
                adapter.skipTurn();
                adapter.skipTurn();
                adapter.skipTurn();
                //player does not skip turn
                assertEquals(i, adapter.getActivePlayer());
            }
        }

    }

    @Test
    public void testFigureBeaten() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bed", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                adapter.move(i, j, 1);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 31);
                //figure is beaten
                assertTrue(adapter.occupiesHomeField(i, j));
                adapter.skipTurn();
                adapter.skipTurn();
                //skipped turn, even if figure beaten
                assertEquals((i + 1) % 4, adapter.getActivePlayer());
            }
        }
    }
}
