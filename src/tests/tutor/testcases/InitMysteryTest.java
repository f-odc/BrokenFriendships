package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.*;

public class InitMysteryTest {
    BFTestAdapterExtended2 adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterExtended2();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testMysterySpawn() {
        adapter.resetTurn();
        for (int i = 0; i < 4; i++) {
            adapter.skipTurn();
        }
        //one mystery spawned after the first round
        assertEquals(1, adapter.getNumberOfMysteryObjects());

        adapter.resetTurn();
        for (int t = 0; t < 2; t++) {
            for (int i = 0; i < 4; i++) {
                adapter.skipTurn();
            }
        }
        //two mysteries spawned after 2 rounds
        assertEquals(2, adapter.getNumberOfMysteryObjects());

        adapter.resetTurn();
        for (int t = 0; t < 35; t++) {
            for (int i = 0; i < 4; i++) {
                adapter.skipTurn();
            }
            if (t % 2 == 0) {
                if (t >= 22)
                    //not more than 12 mysteries spawned
                    assertEquals(12, adapter.getNumberOfMysteryObjects());
                else
                    //correct number of mysteries spawned
                    assertEquals((t / 2) + 1, adapter.getNumberOfMysteryObjects());
            }
        }
    }

    @Test
    public void testMysteryDisappearing() {
        adapter.resetTurn();
        adapter.spawnMystery(11);
        assertEquals(1, adapter.getNumberOfMysteryObjects());
        adapter.setActivePlayer(0);
        adapter.resetFigures(0);
        adapter.moveTo(0, 0, 11);
        //mystery gets deleted, when appropriate
        assertEquals(0, adapter.getNumberOfMysteryObjects());
    }

}
