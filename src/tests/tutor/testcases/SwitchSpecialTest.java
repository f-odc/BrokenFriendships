package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended3;

import static org.junit.Assert.*;


public class SwitchSpecialTest {


    BFTestAdapterExtended3 adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterExtended3();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testNormalSwitch() {

    }

    @Test
    public void testBaseSwitch() {

    }

    @Test
    public void testHomeSwitch() {

    }

    @Test
    public void testNoSwitch() {

    }
}
