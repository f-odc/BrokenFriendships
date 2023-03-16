package tests.tutor.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.adapter.BFTestAdapterExtended2;

import static org.junit.Assert.assertTrue;

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
        //force 20 && 20 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        adapter.setFrictionConstant(4);
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

        //force 40 && 20 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(40);
        adapter.setFrictionConstant(4);
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

        //force 200 && 20 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(200);
        adapter.setFrictionConstant(4);
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

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 138d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1> 134d);

        //force 120 && 20 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(120);
        adapter.setFrictionConstant(4);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;

        assertTrue(nextDegree < 120);
        assertTrue(nextDegree > 115);

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 84);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 80);

        //force 1200 && 120 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(1200);
        adapter.setFrictionConstant(4);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;

        assertTrue(nextDegree < 1200);
        assertTrue(nextDegree > 1150);

        for (int i = 0; i < 120; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 130d);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 120d);

        //force 20 && 1200 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        adapter.setFrictionConstant(4);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;

        assertTrue(nextDegree < 22);
        assertTrue(nextDegree > 18);

        for (int i = 0; i < 1200; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 1);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 0);
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

    @Test
    public void testFriction(){
        //force 20 && 1200 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        adapter.setFrictionConstant(8);
        double degreeT0 = 0;
        double degreeT1 = 0;
        double nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;

        assertTrue(nextDegree < 22);
        assertTrue(nextDegree > 18);

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 9);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 6);

        //force 20 && 1200 time units
        adapter.setupWheelOfFortune();
        adapter.setInitialForce(20);
        adapter.setFrictionConstant(80);
        degreeT0 = 0;
        degreeT1 = 0;
        nextDegree = adapter.nextDegree(degreeT1, degreeT0);
        degreeT0 = degreeT1;
        degreeT1 += nextDegree;

        assertTrue(nextDegree < 12);
        assertTrue(nextDegree > 8);

        for (int i = 0; i < 20; i++) {
            nextDegree = adapter.nextDegree(degreeT1, degreeT0);
            degreeT0 = degreeT1;
            degreeT1 = nextDegree;
        }

        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 < 1);
        assertTrue(adapter.nextDegree(degreeT1, degreeT0) - degreeT1 > 0);
    }
}
