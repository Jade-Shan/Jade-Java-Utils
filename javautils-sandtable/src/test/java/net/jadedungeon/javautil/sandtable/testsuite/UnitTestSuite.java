package net.jadedungeon.javautil.sandtable.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.jadedungeon.javautil.sandtable.MapUtilsTest;
import net.jadedungeon.javautil.sandtable.MiscTest;
import net.jadedungeon.javautil.sandtable.SandTableTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ MapUtilsTest.class, MiscTest.class, SandTableTest.class })
public class UnitTestSuite {

}
