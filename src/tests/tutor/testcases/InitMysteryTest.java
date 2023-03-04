package tests.tutor.testcases;

import model.global;
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

    //mystery objekt spawned
    //mystery objekt geht weg wenn figur drauf geht
    //korrekte Anzahl an Mystery objekten

    @Test
    public void testMysterySpawn() {
        adapter.resetTurn();
        for (int i = 0; i < 4; i++) {
            adapter.skipTurn(i);
        }
        assertEquals(1, adapter.getNumberOfMysteryObjects());

        adapter.resetTurn();
        for (int t = 0; t < 2; t++) {
            for (int i = 0; i < 4; i++) {
                adapter.skipTurn(i);
            }
        }
        assertEquals(2, adapter.getNumberOfMysteryObjects());

        adapter.resetTurn();
        for (int t = 0; t < 35; t++) {
            for (int i = 0; i < 4; i++) {
                adapter.skipTurn(i);
            }
            if (t % 2 == 0) {
                if (t >= 22)
                    assertEquals(12, adapter.getNumberOfMysteryObjects());
                else
                    assertEquals((t / 2) + 1, adapter.getNumberOfMysteryObjects());
            }

        }
    }

    @Test
    public void testMysteryDisappearing() {
        adapter.resetTurn();
        adapter.spawnMystery(10);
        adapter.setActivePlayer(0);
        adapter.resetFigures(0);
        adapter.moveTo(0, 0, 10);
        assertEquals(0, adapter.getNumberOfMysteryObjects());
    }

}
