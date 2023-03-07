package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;

public class MoveFourSpecialTest {
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
    public void testPlus4Movement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                adapter.selectField((fieldID + 4) % 40);
                //moves 4 fields forward
                assertEquals((fieldID + 4) % 40, adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testMinus4Movement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                int newIndex = adapter.getFigureIndex(i, j) - 4;
                adapter.selectField(newIndex < 0 ? (40 + newIndex) : newIndex);
                //moves 4 fields backwards
                assertEquals(newIndex < 0 ? (40 + newIndex) : newIndex, adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testNoMovement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                adapter.selectField((fieldID + 1) % 40);
                adapter.selectField((fieldID + 2) % 40);
                adapter.selectField((fieldID + 3) % 40);
                adapter.selectField((fieldID + 5) % 40);
                adapter.selectField(fieldID - 1 < 0 ? 40 + fieldID : fieldID - 1);
                adapter.selectField(fieldID - 2 < 0 ? 40 + fieldID : fieldID - 2);
                adapter.selectField(fieldID - 3 < 0 ? 40 + fieldID : fieldID - 3);
                adapter.selectField(fieldID - 5 < 0 ? 40 + fieldID : fieldID - 5);
                //no movement, when selecting wrong fields
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectField((fieldID + 4) % 40);
                //correct movement still possible
                assertEquals((fieldID + 4) % 40, adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testIntoBaseMovement() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                int fieldID = adapter.getFigureIndex(i, j);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                adapter.selectBaseField((i + 1) % 4, 3);
                //no movement when wrong base is selected
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectBaseField(i, 2);
                //no movement when wrong index is selected
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectBaseField(i, 3);
                //movement when correct base and index selected
                assertTrue(adapter.occupiesBaseField(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                adapter.selectBaseField(i, 2);
                //movement when correct base and index selected
                assertTrue(adapter.occupiesBaseField(i, j));
            }
        }
    }

    @Test
    public void testBeatFigure() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer((i + 1) % 4);
            adapter.resetFigures((i + 1) % 4);
            adapter.move((i + 1) % 4, 0, 6);
            adapter.move((i + 1) % 4, 0, 34);
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            adapter.forceComplicatedSpecialSpawn("MoveFour", i, 0);
            adapter.selectField((adapter.getFigureIndex(i, 0) + 4) % 40);
            //beat figure +4
            assertTrue(adapter.occupiesHomeField((i + 1) % 4, 0));

            adapter.resetTurn();
            adapter.setActivePlayer((i + 1) % 4);
            adapter.resetFigures((i + 1) % 4);
            adapter.move((i + 1) % 4, 0, 6);
            adapter.move((i + 1) % 4, 0, 26);
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            adapter.move(i, 0, 6);
            adapter.forceComplicatedSpecialSpawn("MoveFour", i, 0);
            int fieldID = adapter.getFigureIndex(i, 0);
            adapter.selectField(fieldID - 4 < 0 ? 40 + fieldID - 4 : fieldID - 4);
            //beat figure -4
            assertTrue(adapter.occupiesHomeField((i + 1) % 4, 0));
        }
    }
}
