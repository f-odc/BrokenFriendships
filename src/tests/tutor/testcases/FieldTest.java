package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterMinimal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    public void testBaseColor() {
        for (int i = 0; i < 4; i++) {
            String color = adapter.getColorOfBase(i,0);
            List<String> comparison = new ArrayList<>();
            for (int j = 0; j < 4;j++){
                assertEquals(adapter.getColorOfBase(i, j), color);
                assertFalse(comparison.contains(adapter.getColorOfBase(j,0)));
                comparison.add(adapter.getColorOfBase(j,0));
            }
        }
    }

    @Test
    public void testHomeColor() {
        for (int i = 0; i < 4; i++) {
            String color = adapter.getColorOfHome(i,0);
            List<String> comparison = new ArrayList<>();
            for (int j = 0; j < 4;j++){
                assertEquals(adapter.getColorOfHome(i, j), color);
                assertFalse(comparison.contains(adapter.getColorOfHome(j,0)));
                comparison.add(adapter.getColorOfHome(j,0));
            }
        }
    }

    @Test
    public void testStartFieldColor() {
        for (int i = 0; i < 4; i++) {
            List<String> comparison = new ArrayList<>();
            assertFalse(comparison.contains(adapter.getColorOfStartField(i)));
            comparison.add(adapter.getColorOfHome(i,0));
        }
    }
}
