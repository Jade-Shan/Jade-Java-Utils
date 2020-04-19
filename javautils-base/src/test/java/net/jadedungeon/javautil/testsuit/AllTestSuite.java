package net.jadedungeon.javautil.testsuit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.jadedungeon.javautil.misc.XvfbTest;

@RunWith(Suite.class)
@SuiteClasses({ UnitTestSuite.class, XvfbTest.class })
public class AllTestSuite {

}
