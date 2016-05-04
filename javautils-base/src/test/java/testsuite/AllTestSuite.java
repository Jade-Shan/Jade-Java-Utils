package testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jadeutils.misc.XvfbTest;

@RunWith(Suite.class)
@SuiteClasses({ UnitTestSuite.class, XvfbTest.class })
public class AllTestSuite {

}
