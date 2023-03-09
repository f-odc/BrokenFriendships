package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended1;

import java.util.ArrayList;
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
    public void testMovementDisplay() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                List<Integer> displayedFields = new ArrayList<>();
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                //move out of home with 1
                assertEquals(0, adapter.displayField(i, j, 1).size());

                adapter.resetTurn();
                //move out of home with 6
                displayedFields = adapter.displayField(i, j, 6);
                assertEquals(1, displayedFields.size());
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                //displayed field is the correct start field
                assertEquals((int) displayedFields.get(0), adapter.getFigureIndex(i, j));

                adapter.resetTurn();
                adapter.resetFigures(i);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                displayedFields = adapter.displayField(i, j, 2);
                //move into or past base
                assertEquals(2, displayedFields.size());
                adapter.setActivePlayer(i);
                adapter.move(i, (j + 1) % 4, 6);
                adapter.move(i, (j + 1) % 4, 1);
                int indexPastBase = adapter.getFigureIndex(i, (j + 1) % 4);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 2);
                int indexInBase = adapter.getFigureIndex(i, j);
                //one of the displayed fields is the correct index going past the base
                assertTrue(displayedFields.contains(indexPastBase));
                //one of the displayed fields is the correct index going into the base
                assertTrue(displayedFields.contains(indexInBase));

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
                adapter.setActivePlayer(i);
                adapter.move(i, (j + 2) % 4, 6);
                adapter.move(i, (j + 2) % 4, 2);
                //the displayed field is the correct index going past the base
                assertTrue(displayedFields.contains(adapter.getFigureIndex(i, (j + 2) % 4)));
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

            adapter.resetTurn();
            adapter.resetFigures(i);
            adapter.setActivePlayer(i);
            adapter.move(i, 0, 6);
            indices = adapter.displayField(i, 0, 6);
            adapter.move(i, 0, 3);
            //only one field is displayed
            assertEquals(1, adapter.displayField(i, 0, 3).size());
            indices.add(adapter.displayField(i, 0, 3).get(0));
            //same field is recognized to be the same
            assertEquals(indices.get(0), indices.get(1));

            adapter.resetTurn();
            adapter.resetFigures(i);
            adapter.setActivePlayer(i);
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 39);
            indices = adapter.displayField(i, 0, 4);
            //two options are displayed
            assertEquals(2, indices.size());
            //not twice the same option
            assertNotEquals(indices.get(0), indices.get(1));
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
                //select other figure after move out of home
                assertEquals(0, adapter.displayField(i, (j + 1) % 4, 3).size());
                //select correct figure after move out of home
                assertEquals(1, adapter.displayField(i, j, 3).size());
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
