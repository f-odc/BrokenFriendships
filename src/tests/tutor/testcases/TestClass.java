package tests.tutor.testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.GameplayState;

import javax.xml.transform.stream.StreamSource;

public class TestClass {

    @Before
    public void setUp() {
        System.out.println("Start Test Class!");
        System.out.println("Setup Test Environment!");
    }

    @After
    public void finish(){
        System.out.println("Clear up afterwards!");
    }

    @Test
    public final void firstTest() {
        String testString = "TestSetup";
        assertEquals("TestSetup",testString);
    }
}

