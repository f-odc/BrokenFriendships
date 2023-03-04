package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;
import tests.adapter.BFTestAdapterMinimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DiceTest {


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
    public void testDiceValues() {
        for (int i = 0; i < 100; i++) {
            assertTrue(adapter.getDiceThrow() > 0);
            assertTrue(adapter.getDiceThrow() < 7);
        }
    }

    @Test
    public void testDicePosition() {
        List<Vector2f> dicePositions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            assertFalse(dicePositions.contains(adapter.getDicePosition(i)));
            dicePositions.add(adapter.getDicePosition(i));
        }
    }
}
