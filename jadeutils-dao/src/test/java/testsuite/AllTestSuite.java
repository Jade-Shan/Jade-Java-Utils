package testsuite;

import jadeutils.dao.JadeDaoTest;
import jadeutils.mongo.UserDaoTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ UnitTestSuite.class, JadeDaoTest.class, UserDaoTest.class })
public class AllTestSuite {

}
