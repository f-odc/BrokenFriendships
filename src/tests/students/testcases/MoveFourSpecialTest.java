package tests.students.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
                adapter.selectTwoFields(fieldID, (fieldID + 4) % 40);
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
                adapter.selectTwoFields(adapter.getFigureIndex(i, j), newIndex < 0 ? (40 + newIndex) : newIndex);
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
                adapter.selectTwoFields(fieldID, (fieldID + 1) % 40);
                adapter.selectTwoFields(fieldID, (fieldID + 2) % 40);
                adapter.selectTwoFields(fieldID, (fieldID + 3) % 40);
                adapter.selectTwoFields(fieldID, (fieldID + 5) % 40);
                adapter.selectTwoFields(fieldID, fieldID - 1 < 0 ? 40 + fieldID : fieldID - 1);
                adapter.selectTwoFields(fieldID, fieldID - 2 < 0 ? 40 + fieldID : fieldID - 2);
                adapter.selectTwoFields(fieldID, fieldID - 3 < 0 ? 40 + fieldID : fieldID - 3);
                adapter.selectTwoFields(fieldID, fieldID - 5 < 0 ? 40 + fieldID : fieldID - 5);
                //no movement, when selecting wrong fields
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectTwoFields(fieldID, (fieldID + 4) % 40);
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
                adapter.selectBaseField((i + 1) % 4, j, 3);
                //no movement when wrong base is selected
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectBaseField(i, j, 2);
                //no movement when wrong index is selected
                assertEquals(fieldID, adapter.getFigureIndex(i, j));
                adapter.selectBaseField(i, j, 3);
                //movement when correct base and index selected
                assertTrue(adapter.occupiesBaseField(i, j));

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.forceComplicatedSpecialSpawn("MoveFour", i, j);
                adapter.selectBaseField(i, j, 2);
                //backward movement when correct base and index selected
                assertTrue(adapter.occupiesBaseField(i, j));
            }
        }
    }
}
