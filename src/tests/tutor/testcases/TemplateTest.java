package tests.tutor.testcases;

import static org.junit.Assert.*;

import model.board.Board;
import model.enums.Phase;
import model.global;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import tests.adapter.AdapterBonus;
import tests.adapter.AdapterMinimal;
import tests.adapter.AdapterTemplate;
import tests.categories.Minimal;
import tests.categories.Template;

public class TemplateTest {

    AdapterBonus adapter;

    @Before
    public void setUp() {
        adapter = new AdapterBonus();
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

    @Category(Template.class)
    @Test
    public final void globalNotNullTest(){
        adapter.initializeBoard();
        assertNotNull(global.BOARD);
        assertNotNull(global.entityManager);
        assertNotNull(global.activePlayer);
        assertNotNull(global.rounds);
        assertNotNull(global.phase);
    }

    @Category(Minimal.class)
    @Test
    public final void rightPhaseAndState(){
        assertEquals(0, global.activePlayer);
        assertEquals(0, global.rounds);
        assertEquals(Phase.DICE_PHASE, global.phase);
        assertEquals(1, global.GAMEPLAY_STATE);
    }

    @Category(Minimal.class)
    @Test
    public final void boardTest(){
        assertNotNull(adapter.initializeBoard());
    }
}

