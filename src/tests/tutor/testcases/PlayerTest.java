package tests.tutor.testcases;

import model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import tests.adapter.AdapterBonus;
import model.board.fields.BoardField;
import org.junit.Test;
import tests.categories.Minimal;

import static org.junit.Assert.*;
public class PlayerTest {

    AdapterBonus adapter;

    @Before
    public void setUp() {
        adapter = new AdapterBonus();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Category(Minimal.class)
    @Test
    public void setupPlayerTest() {
        Player[] playersUnderTest = adapter.getPlayer();
        assertNotNull(playersUnderTest);
        assertEquals(playersUnderTest.length, 4);
        for (int i = 0; i < 4; i++) {
            Player playerUnderTest = playersUnderTest[i];
            //assertTrue(playerUnderTest.allFiguresInBase());
            assertFalse(playerUnderTest.hasWon());

            assertEquals(adapter.getPlayerFigures(playerUnderTest.id).size(), 4);
            for (int j = 0; j < 4; j++) {
                assertNotEquals(adapter.getPlayerID(i), -1);
                assertEquals(adapter.getPlayerFigures(playerUnderTest.id).get(j).getOwnerID(), adapter.getPlayerID(i));
                assertEquals(adapter.getPlayerFigures(playerUnderTest.id).get(j).getStartField(), playerUnderTest.startField);
            }

            assertEquals(playerUnderTest.baseFields.size(), 4);
            for (int j = 0; j < 4; j++) {
                BoardField field = playerUnderTest.baseFields.get(j);
                assertFalse(field.isHomeField());
                assertFalse(field.isOccupied());
                assertNull(field.getCurrentFigure());
            }

            assertEquals(playerUnderTest.homeFields.size(), 4);
            for (int j = 0; j < 4; j++) {
                BoardField field = playerUnderTest.homeFields.get(j);
                assertTrue(field.isHomeField());
                assertTrue(field.isOccupied());
                assertNotNull(field.getCurrentFigure());
            }
        }
    }
}
