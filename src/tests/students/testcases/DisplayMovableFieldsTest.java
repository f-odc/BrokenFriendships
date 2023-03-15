package tests.students.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended1;

import java.util.List;

import static org.junit.Assert.*;

public class DisplayMovableFieldsTest {
    BFTestAdapterExtended1 adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterExtended1();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testHomeMovementDisplay() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                List<Integer> displayedFields;

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                displayedFields = adapter.displayField(i, j, 1);
                //move out of home with 1
                assertEquals(0, displayedFields.size());

                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                //move out of home with 6
                displayedFields = adapter.displayField(i, j, 6);
                assertEquals(1, displayedFields.size());
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                //displayed field is the correct start field
                assertEquals((int) displayedFields.get(0), adapter.getFigureIndex(i, j));
            }
        }
    }

    @Test
    public void testBaseMovementDisplay() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                List<Integer> displayedFields;

                adapter.resetTurn();
                adapter.resetFigures(i);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                displayedFields = adapter.displayField(i, j, 2);
                //move into or past base
                assertEquals(2, displayedFields.size());

                adapter.resetTurn();
                adapter.resetFigures(i);
                adapter.setActivePlayer(i);
                //base move blocked
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, (j + 1) % 4, 6);
                adapter.move(i, (j + 1) % 4, 39);
                displayedFields = adapter.displayField(i, (j + 1) % 4, 3);
                assertEquals(1, displayedFields.size());
            }
        }
    }

    @Test
    public void testCorrectDisplayed() {
        for (int i = 0; i < 4; i++) {
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            List<Integer> indices = adapter.displayField(i, 0, 6);
            adapter.move(i, 0, 6);
            //only one field gets displayed
            assertEquals(1, adapter.displayField(i, 0, 3).size());
            //displayed fields vary when changing position
            assertNotEquals(indices.get(0), adapter.displayField(i, 0, 3).get(0));
            //figure actually got moved
            assertFalse(adapter.occupiesHomeField(i, 0));
        }
    }

    @Test
    public void testNoDisplay() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 3);
                adapter.setActivePlayer(i);
                adapter.move(i, (j + 1) % 4, 6);
                //cannot select field with own figure
                assertEquals(0, adapter.displayField(i, (j + 1) % 4, 3).size());
            }

            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            //three in base and 1 in home
            for (int z = 0; z < 3; z++) {
                adapter.setActivePlayer(i);
                adapter.move(i, z, 6);
                adapter.move(i, z, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, z, 4 - z);
            }
            //try to move any figure
            assertEquals(0, adapter.displayField(i, 0, 3).size());
            assertEquals(0, adapter.displayField(i, 1, 3).size());
            assertEquals(0, adapter.displayField(i, 2, 3).size());
            assertEquals(0, adapter.displayField(i, 3, 3).size());
        }
    }
}
