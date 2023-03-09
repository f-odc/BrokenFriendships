package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;

public class PlusToThreeSpecialTest {
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
    public void testCorrectMovement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField((fieldID + 1) % 40);
                //select + 1
                assertEquals((fieldID + 1) % 40, adapter.getFigureIndex(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField((fieldID + 2) % 40);
                //select + 2
                assertEquals((fieldID + 2) % 40, adapter.getFigureIndex(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField((fieldID + 3) % 40);
                //select + 3
                assertEquals((fieldID + 3) % 40, adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testSelectAgain() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField((fieldID + 4) % 40);
                adapter.selectField((fieldID + 3) % 40);
                //first select + 4, then + 3
                assertEquals((fieldID + 3) % 40, adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testBeatFigure() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer((i + 1) % 4);
                adapter.resetFigures((i + 1) % 4);
                adapter.move((i + 1) % 4, j, 6);
                adapter.move((i + 1) % 4, j, 31);
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField(adapter.getFigureIndex(i, j) + 1);
                //select + 1, where opponent figure is
                assertTrue(adapter.occupiesHomeField((i + 1) % 4, j));
            }
        }
    }

    @Test
    public void testNoPossibleMovement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.selectField(i);
                adapter.resetFigures(i);
                for (int z = j; z == (z + 3) % 4; z = (z + 1) % 4) {
                    adapter.setActivePlayer(i);
                    adapter.move(i, z, 6);
                    adapter.move(i, z, z + 1);
                }
                adapter.setActivePlayer(i);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("PlusToThree", i, j);
                adapter.selectField((fieldID + 1) % 40);
                adapter.selectField((fieldID + 2) % 40);
                adapter.selectField((fieldID + 3) % 40);
                //select +1,+2,+3, where own figures are
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                for (int z = j; z == (z + 3) % 4; z = (z + 1) % 4) {
                    assertFalse(adapter.occupiesHomeField(i, z));
                }
            }
        }
    }

    @Test
    public void testIntoBase() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 39);
            adapter.forceComplicatedSpecialSpawn("PlusToThree", i, 0);
            adapter.selectBaseField(i, 0, 2);
            //can enter base with PlusToThree
            assertTrue(adapter.occupiesBaseField(i, 0));
        }
    }
}
