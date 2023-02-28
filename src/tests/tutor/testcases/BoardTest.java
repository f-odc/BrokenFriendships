package tests.tutor.testcases;

import static org.junit.Assert.*;

import model.board.fields.BoardField;
import model.board.fields.IField;
import model.enums.FieldType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import tests.adapter.AdapterBonus;
import tests.categories.Minimal;

import java.util.List;

public class BoardTest {

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
    public void setupBaseTest() {
        for (int i = 0; i < 4; i++) {
            List<BoardField> baseUnderTest = adapter.getBase(i);
            assertNotNull(baseUnderTest);
            for (BoardField fieldUnderTest : baseUnderTest) {
                assertNotNull(fieldUnderTest.getBaseEntity());
                assertFalse(fieldUnderTest.isOccupied());
                assertFalse(fieldUnderTest.isHomeField());
                assertNull(fieldUnderTest.getCurrentFigure());
            }
        }
    }

    @Category(Minimal.class)
    @Test
    public void setupHomeTest() {
        for (int i = 0; i < 4; i++) {
            List<BoardField> homeUnderTest = adapter.getHome(i);
            assertNotNull(homeUnderTest);
            for (BoardField fieldUnderTest : homeUnderTest) {
                assertNotNull(fieldUnderTest.getBaseEntity());
                assertTrue(fieldUnderTest.isOccupied());
                assertTrue(fieldUnderTest.isHomeField());
                assertNotNull(fieldUnderTest.getCurrentFigure());
            }
        }
    }

    @Category(Minimal.class)
    @Test
    public void setupGameFieldsTest() {
        IField[] fieldsUnderTest = adapter.getGameFields();
        assertNotNull(fieldsUnderTest);
        assertEquals(fieldsUnderTest.length, 40);
        for (int i = 0; i < 40; i++) {
            IField fieldUnderTest = fieldsUnderTest[i];
            assertNotNull(fieldUnderTest);
            assertEquals(fieldUnderTest.getFieldIndex(), i);
            assertFalse(fieldUnderTest.isOccupied());
            assertFalse(fieldUnderTest.isHomeField());
            if (i == 0 || i == 10 || i == 20 || i == 30) {
                assertTrue(fieldUnderTest.isPlayerStartField());
                assertEquals(fieldUnderTest.getType(), FieldType.START);
            }
        }
    }
}
