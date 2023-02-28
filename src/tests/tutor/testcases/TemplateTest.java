package tests.tutor.testcases;

import static org.junit.Assert.*;

import model.board.Board;
import model.enums.Phase;
import model.global;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.TestAdapterMinimal;

public class TemplateTest {

    TestAdapterMinimal adapter;

    @Before
    public void setUp() {
        adapter = new TestAdapterMinimal();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public final void boardNotNullTest() {
        Board testObject = adapter.initializeBoard();
        assertNotNull(testObject);
        assertNotNull(testObject.getDice());
    }
    @Test
    public final void globalNotNullTest(){
        adapter.initializeBoard();
        assertNotNull(global.BOARD);
        assertNotNull(global.entityManager);
        assertNotNull(global.activePlayer);
        assertNotNull(global.rounds);
        assertNotNull(global.phase);
    }

    @Test
    public final void rightPhaseAndState(){
        assertEquals(0, global.activePlayer);
        assertEquals(0, global.rounds);
        assertEquals(Phase.DICE_PHASE, global.phase);
        assertEquals(1, global.GAMEPLAY_STATE);
    }
}

