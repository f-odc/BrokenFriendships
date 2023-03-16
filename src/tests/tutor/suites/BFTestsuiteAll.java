package tests.tutor.suites;

import junit.framework.Test;
import junit.framework.TestSuite;
public class BFTestsuiteAll {
    public static Test suite() {

        TestSuite suite = new TestSuite("All tutor tests for Tanks");

        suite.addTest(BFTestsuiteMinimal.suite());
        suite.addTest(BFTestsuiteExtended1.suite());
        suite.addTest(BFTestsuiteExtended2.suite());
        suite.addTest(BFTestsuiteExtended3.suite());

        return suite;
    }
}
