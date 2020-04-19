package net.jadedungeon.javautil.net.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.jadedungeon.javautil.net.HTTPProxyTest;

@RunWith(Suite.class)
@SuiteClasses({ UnitTestSuite.class, HTTPProxyTest.class })
public class AllTestSuite {

}
