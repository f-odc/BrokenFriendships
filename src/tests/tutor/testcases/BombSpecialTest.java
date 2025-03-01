package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;

public class BombSpecialTest {
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
    public void testBombSpawning() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bomb", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                //Bombe wurde auf dem richtigen Feld gespawned
                assertTrue(adapter.isOccupiedByBomb(adapter.getFigureIndex(i,j) + 1));
            }
        }
    }

    @Test
    public void testBombActivation() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bomb", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                adapter.move(i, j, 1);
                //bomb gets activated, when figure moves on its field
                assertTrue(adapter.occupiesHomeField(i, j));
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 1);
                //bomb gets removed and does not work twice or more times
                assertFalse(adapter.occupiesHomeField(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bomb", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 11);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 1);
                //bomb from player1 gets activated from any other player
                assertTrue(adapter.occupiesHomeField((i + 1) % 4, j));
            }
        }
    }

    @Test
    public void test6IntoBomb() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("bomb", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 6);
                adapter.move(i, j, 6);
                //player gets to go again
                assertEquals(i, adapter.getActivePlayer());
                adapter.move(i, j, 6);
                //player can move out figure
                assertFalse(adapter.occupiesHomeField(i, j));
            }
        }
    }
}
