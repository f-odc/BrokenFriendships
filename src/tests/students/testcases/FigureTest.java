package tests.students.testcases;

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
            adapter.resetTurn();
            adapter.setActivePlayer(i);
            adapter.resetFigures(i);
            for (int j = 0; j < 4; j++) {
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
}
