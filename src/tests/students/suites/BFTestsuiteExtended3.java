package tests.students.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.students.testcases.BedSpecialTest;
import tests.students.testcases.BombSpecialTest;
import tests.students.testcases.MoveFourSpecialTest;
import tests.students.testcases.SwitchSpecialTest;
import tests.students.testcases.PlusToThreeSpecialTest;

public class BFTestsuiteExtended3 {

    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Extended 3");
        suite.addTest(new JUnit4TestAdapter(BedSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(BombSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(MoveFourSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(PlusToThreeSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(SwitchSpecialTest.class));
        return suite;
    }
}
