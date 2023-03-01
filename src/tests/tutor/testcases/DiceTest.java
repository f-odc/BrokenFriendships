package tests.tutor.testcases;

import eea.engine.entity.Entity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.newdawn.slick.geom.Vector2f;
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

    @Category(Minimal.class)
    @Test
    public void testGetValue() {
        int value = adapter.throwDice();
        assertEquals(value, adapter.getDiceValue());
    }

    @Category(Minimal.class)
    @Test
    public void testSetAndGetPosition() {
        Vector2f[] positionsArray = {adapter.calculateDicePositions(1, 2), adapter.calculateDicePositions(9, 2), adapter.calculateDicePositions(9, 8), adapter.calculateDicePositions(1, 8)};
        for (int i = 0; i < 4; i++) {
            adapter.setDicePosition(i);
            assertEquals(adapter.getDicePosition(), positionsArray[i]);
        }
    }

    @Category(Minimal.class)
    @Test
    public void testDiceEntity() {
        Entity dice = adapter.getDiceEntity();
        assertNotNull(adapter.getDiceEntity());
        assertEquals(adapter.getDicePosition(), dice.getPosition());
    }
}
