package tests.students.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterMinimal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FieldTest {
    BFTestAdapterMinimal adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterMinimal();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testBases() {
        for (int i = 0; i < 4; i++) {
            assertEquals(adapter.getNumberOfBaseFields(i), 4);
        }
    }

    @Test
    public void testHomes() {
        for (int i = 0; i < 4; i++) {
            assertEquals(adapter.getNumberOfHomeFields(i), 4);
        }
    }

    @Test
    public void testGameFields() {
        assertEquals(adapter.getNumberOfGameFields(), 40);
    }

    @Test
    public void testStartFields() {
        assertEquals(adapter.getNumberOfStartFields(), 4);
    }

    @Test
    public void testStartFieldColor() {
        List<String> comparison = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            assertFalse(comparison.contains(adapter.getColorOfStartField(i)));
            comparison.add(adapter.getColorOfHome(i, 0));
        }
    }
}
