package tests.students.testcases;

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
    public void testDeadFunctionality() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceSimpleSpecialActivation("dead", i, j);
                //figure moved back into home
                assertTrue(adapter.occupiesHomeField(i, j));
            }
        }
    }
}
