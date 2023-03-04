package tests.tutor.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.tutor.testcases.DeadSpecialTest;
import tests.tutor.testcases.GrowingSpecialTest;
import tests.tutor.testcases.InitMysteryTest;
import tests.tutor.testcases.MoveOutSpecialTest;

public class BFTestsuiteExtended2 {
    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Minimal");
        suite.addTest(new JUnit4TestAdapter(InitMysteryTest.class));
        suite.addTest(new JUnit4TestAdapter(DeadSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(MoveOutSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(GrowingSpecialTest.class));
        return suite;
    }
}
