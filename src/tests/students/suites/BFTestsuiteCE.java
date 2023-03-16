package tests.students.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.students.testcases.CE_TaskTest;

public class BFTestsuiteCE {
    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - CE");
        suite.addTest(new JUnit4TestAdapter(CE_TaskTest.class));
        return suite;
    }
}
