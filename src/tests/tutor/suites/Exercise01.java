package tests.tutor.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.tutor.testcases.TestClass;

public class Exercise01 {

    public static Test suite() {
        TestSuite suite = new TestSuite("Tutor tests for BrokenFriendships - Task 1");
        // add all test classes for exercise
        suite.addTest(new JUnit4TestAdapter(TestClass.class));
        return suite;
    }

}
