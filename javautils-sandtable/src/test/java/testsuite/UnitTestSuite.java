package testsuite;

import jadeutils.tools.MapUtilsTest;
import jadeutils.tools.MiscTest;
import jadeutils.tools.SandTableTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ MapUtilsTest.class, MiscTest.class, SandTableTest.class })
public class UnitTestSuite {

}
