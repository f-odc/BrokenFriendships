package tests.students.testcases;

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
                assertTrue(adapter.allHomeFieldsOccupied(i));
                adapter.forceSimpleSpecialActivation("moveOut", i, j);
                //moves one figure out of base
                assertFalse(adapter.allHomeFieldsOccupied(i));
            }
        }
    }

    @Test
    public void testNoFigureInBase() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            for (int j = 0; j < 4; j++) {
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 5 - j);
            }
            assertFalse(adapter.allHomeFieldsOccupied(i));
            adapter.setActivePlayer(i);
            adapter.forceSimpleSpecialActivation("moveOut", i, 0);
            assertNotEquals(i, adapter.getActivePlayer());
        }
    }
}
