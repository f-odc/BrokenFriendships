package tests.students.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.students.testcases.DisplayMovableFieldsTest;

public class BFTestsuiteExtended1 {

    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Extended 1");
        suite.addTest(new JUnit4TestAdapter(DisplayMovableFieldsTest.class));
        return suite;
    }
}
