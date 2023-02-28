package tests.tutor.suites;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import tests.tutor.testcases.TemplateTest;

public class TestSuiteMinimal {

    public static Test suite() {
        TestSuite suite = new TestSuite("Student tests for BrokenFriendships (minimal)");
        suite.addTest(new JUnit4TestAdapter(TemplateTest.class));
        return suite;
    }

}
