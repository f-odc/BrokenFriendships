package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.*;

public class GrowingSpecialTest {
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
    public void testGrowingFunctionality() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 1);
                adapter.setActivePlayer(i);
                adapter.move(i, (j + 1) % 4, 6);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 29);
                adapter.forceSimpleSpecialActivation("growing", i, (j + 1) % 4);
                //other color`s figure beaten
                assertTrue(adapter.occupiesHomeField((i + 1) % 4, j));
                //own figure beaten
                assertTrue(adapter.occupiesHomeField(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 1);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 29);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, (j + 1) % 4, 6);
                adapter.move((i + 1) % 4, (j + 1) % 4, 30);
                adapter.forceSimpleSpecialActivation("growing", (i + 1) % 4, j);
                //own figure in base not beaten
                assertFalse(adapter.occupiesHomeField(i, j));
                //other color`s figure beaten
                assertTrue(adapter.occupiesHomeField((i + 1) % 4, (j + 1) % 4));
            }
        }
    }

    @Test
    public void testGrowingWithOneNeighbor() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.setActivePlayer(i);
                adapter.move(i,j,6);
                adapter.setActivePlayer((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 29);
                adapter.forceSimpleSpecialActivation("growing", i, j);
                //other color`s figure beaten
                assertTrue(adapter.occupiesHomeField((i + 1) % 4, j));
            }
        }
    }

    @Test
    public void testGrowingWithoutNeighbors() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            adapter.forceSimpleSpecialActivation("growing", i, 0);
            assertFalse(adapter.occupiesHomeField(i, 0));
        }
    }
}
