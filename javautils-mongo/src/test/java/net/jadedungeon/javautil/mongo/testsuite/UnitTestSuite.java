package net.jadedungeon.javautil.mongo.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.jadedungeon.javautil.mongo.AnnotationTest;
import net.jadedungeon.javautil.mongo.MongoUtilTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AnnotationTest.class, MongoUtilTest.class })
public class UnitTestSuite {

}
