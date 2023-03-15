package tests.students.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.students.testcases.DeadSpecialTest;
import tests.students.testcases.GrowingSpecialTest;
import tests.students.testcases.InitMysteryTest;
import tests.students.testcases.MoveOutSpecialTest;

public class BFTestsuiteExtended2 {
    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Extended 2");
        suite.addTest(new JUnit4TestAdapter(InitMysteryTest.class));
        suite.addTest(new JUnit4TestAdapter(DeadSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(MoveOutSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(GrowingSpecialTest.class));
        return suite;
    }
}
