package tests.students.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.*;

public class CE_TaskTest {

    BFTestAdapterExtended2 adapter;

    @Before
    public void setUp() {
        adapter = new BFTestAdapterExtended2();
        adapter.initializeGame();
    }

    @After
    public void finish() {
        adapter.stopGame();
    }

    @Test
    public void testNextDegree() {
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        double degreeT0 = 0;
        double degreeT1 = 0;
        double nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;
        assertTrue(nextDegree < 20);
        assertTrue(nextDegree > 18);

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 14.5d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 12.5d);

        adapter.setupWheelOfFortune();
        adapter.setInitialForce(40);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        assertTrue(nextDegree < 40);
        assertTrue(nextDegree > 39);

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 28d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 26d);

        adapter.setupWheelOfFortune();
        adapter.setInitialForce(200);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        assertTrue(nextDegree < 200);
        assertTrue(nextDegree > 195);
        double d = nextDegree;

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
            d += nextDegree;
        }
        System.out.println(d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 138d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1> 134d);
    }

    @Test
    public void testCorrectRotationNumber(){
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        double degreeT0 = 0;
        double degreeT1 = 0;
        double nextDegree = adapter.nextDegree(degreeT1, degreeT0);

        for (int i = 0; i < 200; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }
        
        assertTrue( nextDegree / 360 < 3.2d);
        assertTrue( nextDegree / 360 > 2.7d);

        adapter.setupWheelOfFortune();
        adapter.setInitialForce(40);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);

        for (int i = 0; i < 200; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue( nextDegree / 360 < 6.2d);
        assertTrue( nextDegree / 360 > 5.8d);

        adapter.setupWheelOfFortune();
        adapter.setInitialForce(200);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);

        for (int i = 0; i < 200; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue( nextDegree / 360 < 31d);
        assertTrue( nextDegree / 360 > 29d);
    }
}
