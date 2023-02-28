package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import tests.adapter.AdapterBonus;
import tests.categories.Minimal;

import static org.junit.Assert.*;
public class DiceTest {

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
    public void testDiceRandomness() {
        int[] results = new int[6];
        for (int i = 0; i < 5000; i++) {
            results[adapter.throwDice() - 1]++;
        }
        for (int i = 0; i < 6; i++) {
            assertTrue(results[i] > 300);
        }
    }
}
