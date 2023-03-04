package tests.tutor.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.tutor.testcases.*;

public class BFTestsuiteExtended1 {

    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Minimal");
        suite.addTest(new JUnit4TestAdapter(DisplayMovableFieldsTest.class));
        return suite;
    }
}
