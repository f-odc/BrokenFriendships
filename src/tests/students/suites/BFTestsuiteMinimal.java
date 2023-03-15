package tests.students.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.students.testcases.*;

public class BFTestsuiteMinimal {

    public static Test suite() {

        TestSuite suite = new TestSuite("Student tests for BF - Minimal");
        suite.addTest(new JUnit4TestAdapter(FieldTest.class));
        suite.addTest(new JUnit4TestAdapter(FigureTest.class));
        suite.addTest(new JUnit4TestAdapter(DiceTest.class));
        suite.addTest(new JUnit4TestAdapter(MoveTest.class));
        suite.addTest(new JUnit4TestAdapter(RoundTest.class));
        return suite;
    }
}
