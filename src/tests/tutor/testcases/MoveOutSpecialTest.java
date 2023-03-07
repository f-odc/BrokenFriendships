package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.*;

public class MoveOutSpecialTest {
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
    public void testMoveOutFunctionality() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.forceSimpleSpecialActivation("moveOut", i, j);
                //moves one figure out of base
                assertFalse(adapter.allHomeFieldsOccupied(i));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceSimpleSpecialActivation("moveOut", i, (j + 1) % 4);
                //moves one figure out of base
                assertFalse(adapter.occupiesHomeField(i, j));
                for (int z = 0; z < 3; z++) {
                    if (z == j) continue;
                    //all other figures are still on a home field
                    assertTrue(adapter.occupiesHomeField(i, z));
                }
            }
        }
    }
}
