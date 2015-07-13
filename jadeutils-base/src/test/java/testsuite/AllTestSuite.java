package testsuite;

import jadeutils.net.HTTPProxyTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UnitTestSuite.class, HTTPProxyTest.class })
public class AllTestSuite {

}
