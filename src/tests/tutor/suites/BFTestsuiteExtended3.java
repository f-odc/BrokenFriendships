package tests.tutor.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.tutor.testcases.*;

public class BFTestsuiteExtended3 {

    public static Test suite() {

        TestSuite suite = new TestSuite("Tutor tests for BF - Extended 3");
        suite.addTest(new JUnit4TestAdapter(BedSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(BombSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(MoveFourSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(PlusToThreeSpecialTest.class));
        suite.addTest(new JUnit4TestAdapter(SwitchSpecialTest.class));
        return suite;
    }
}
