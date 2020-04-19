package net.jadedungeon.javautil.mongo.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.jadedungeon.javautil.mongo.UserDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UnitTestSuite.class, UserDaoTest.class })
public class AllTestSuite {

}
