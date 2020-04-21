package net.jadedungeon.javautil.testsuit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.jadedungeon.javautil.base.BinaryCoderTest;
import net.jadedungeon.javautil.base.MD5CoderTest;
import net.jadedungeon.javautil.datastructures.TreeNodeTest;
import net.jadedungeon.javautil.image.ImageUtilsTest;
import net.jadedungeon.javautil.misc.MessageMapTest;
import net.jadedungeon.javautil.misc.RegexTest;
import net.jadedungeon.javautil.reflect.BeanUtilsTest;
import net.jadedungeon.javautil.text.JsonUtilsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BinaryCoderTest.class, MD5CoderTest.class,
		TreeNodeTest.class, ImageUtilsTest.class, JsonUtilsTest.class,
		MessageMapTest.class, RegexTest.class, BeanUtilsTest.class })
public class UnitTestSuite {

}
