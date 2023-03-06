package tests.tutor.testcases;

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

    //alle sachen wie bei MoveTest -
    //zurück zum figuren auswählen, wenn kein Movement möglich
    //zurück zum figuren auswählen, wenn figur abgewählt
    //nächster Spieler, wenn sich keine Figur bewegen kann

    @Test
    public void testMovementDisplay() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                //move out of home with 1
                assertEquals(0, adapter.displayField(i, j, 1).size());

                adapter.resetTurn();
                //move out of home with 6
                assertEquals(1, adapter.displayField(i, j, 6).size());

                adapter.resetTurn();
                adapter.resetFigures(i);
                adapter.setActivePlayer(i);
                adapter.move(i, j, 6);
                adapter.move(i, j, 39);
                //move into or past base
                assertEquals(2, adapter.displayField(i, j, 2).size());


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
                assertEquals(1, adapter.displayField(i, (j + 1) % 4, 3).size());
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
            //move out of home
            adapter.move(i, 0, 6);
            assertEquals(1, adapter.displayField(i, 0, 3).size());
            assertNotEquals(indices.get(0), adapter.displayField(i, 0, 3).get(0));
            assertFalse(adapter.occupiesHomeField(i, 0));

            adapter.resetTurn();
            adapter.resetFigures(i);
            adapter.setActivePlayer(i);
            adapter.move(i, 0, 6);
            //same field is recognized to be the same
            indices = adapter.displayField(i, 0, 6);
            adapter.move(i, 0, 3);
            assertEquals(1, adapter.displayField(i, 0, 3).size());
            indices.add(adapter.displayField(i, 0, 3).get(0));
            assertEquals(indices.get(0), indices.get(1));

            adapter.resetTurn();
            adapter.resetFigures(i);
            adapter.setActivePlayer(i);
            //move into base
            adapter.move(i, 0, 6);
            adapter.move(i, 0, 39);
            indices = adapter.displayField(i, 0, 4);
            assertEquals(2, indices.size());
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
                assertEquals(1, adapter.displayField(i, j, 3).size());
            }

            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            //three in base and 1 in home, try to move without 6
            for (int z = 0; z < 3; z++) {
                adapter.setActivePlayer(i);
                adapter.move(i, z, 6);
                adapter.move(i, z, 39);
                adapter.setActivePlayer(i);
                adapter.move(i, z, 4 - z);
            }
            assertEquals(0, adapter.displayField(i, 0, 3).size());
            assertEquals(0, adapter.displayField(i, 1, 3).size());
            assertEquals(0, adapter.displayField(i, 2, 3).size());
            assertEquals(0, adapter.displayField(i, 3, 3).size());
        }
    }
}
