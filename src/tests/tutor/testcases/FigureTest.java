package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterMinimal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FigureTest {
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
    public void testFigureNumber() {
        for (int i = 0; i < 4; i++) {
            assertEquals(adapter.getFigureCount(i), 4);
        }
    }

    @Test
    public void testFigureInHome() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                adapter.resetTurn();
                adapter.setActivePlayer(i);
                adapter.resetFigures(i);
                assertTrue(adapter.occupiesHomeField(i, j));
            }
        }
    }

    @Test
    public void testFigureAndHomeColor() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String homeColor = adapter.getColorOfHome(i, j);
                assertEquals(homeColor, adapter.getFigureColor(i, j));
            }
        }
    }

    @Test
    public void testUniqueFigureColor() {
        for (int i = 0; i < 4; i++) {
            String color = adapter.getFigureColor(i, 0);
            List<String> comparison = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                assertEquals(color, adapter.getFigureColor(i, j));
                assertFalse(comparison.contains(adapter.getFigureColor(j, 0)));
                comparison.add(adapter.getFigureColor(j, 0));
            }
        }
    }

    @Test
    public void testHomeFieldOccupation() {
        for (int i = 0; i < 4; i++) {
            assertTrue(adapter.allHomeFieldsOccupied(i));
        }
    }
}
