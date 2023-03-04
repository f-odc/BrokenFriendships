package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.*;

public class DeadSpecialTest {


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
    public void testDeadMysterySpawn() {
        //TODO
    }

    @Test
    public void testDeadMysteryFunctionality() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.forceSimpleMysteryActivate("dead", i, j);
                int currentPlayer = adapter.getActivePlayer();
                assertTrue(adapter.occupiesHomeField(i, j));
                assertEquals(currentPlayer, adapter.getActivePlayer());
            }
        }
    }
}
