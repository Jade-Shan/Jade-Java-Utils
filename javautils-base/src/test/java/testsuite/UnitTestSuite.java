package testsuite;

import jadeutils.code.BinaryCoderTest;
import jadeutils.code.MD5CoderTest;
import jadeutils.datastructures.TreeNodeTest;
import jadeutils.image.ImageUtilsTest;
import jadeutils.json.JsonUtilsTest;
import jadeutils.misc.MessageMapTest;
import jadeutils.misc.RegexTest;
import jadeutils.reflect.BeanUtilsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BinaryCoderTest.class, MD5CoderTest.class,
		TreeNodeTest.class, ImageUtilsTest.class, JsonUtilsTest.class,
		MessageMapTest.class, RegexTest.class, BeanUtilsTest.class })
public class UnitTestSuite {

}
