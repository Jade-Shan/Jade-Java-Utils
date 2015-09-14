package testsuite;

import jadeutils.mongo.AnnotationTest;
import jadeutils.mongo.MongoUtilTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AnnotationTest.class, MongoUtilTest.class })
public class UnitTestSuite {

}
