package jadeutils.dao;

//import static org.junit.Assert.assertTrue;

//import java.util.Date;
//import java.util.List;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/* prepare sql `test.rec.sql` */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:AppCtx*.xml" })
public class JadeDaoTest 
//extends AbstractJUnit4SpringContextTests 
{

//	@Autowired
//	ResidentDao residentDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
//		List<Resident> l = this.residentDao.getAll();
//		if (null != l) {
//			for (Resident r : l) {
//				this.residentDao.delete(r);
//			}
//		}
//		for (int i = 0; i < 10; i++) {
//			Resident r = new Resident();
//			r.setId("UID" + i);
//			r.setNick("user" + i);
//			r.setUserName("user" + i);
//			r.setPassword("user" + 1);
//			r.setStatus(ResidentDto.Statue.ACTIVE.name());
//			r.setCreateTime(new Date());
//			this.residentDao.insert(r);
//		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsert() throws Exception {
//		for (int i = 10; i < 20; i++) {
//			Resident r = new Resident();
//			r.setId("UID" + i);
//			r.setNick("user" + i);
//			r.setUserName("user" + i);
//			r.setPassword("user" + 1);
//			r.setStatus(ResidentDto.Statue.ACTIVE.name());
//			r.setCreateTime(new Date());
//			this.residentDao.insert(r);
//		}
	}

	@Test
	public void test001Instance() throws Exception {
//		assertEquals(10, this.residentDao.getAll().size());
	}

	@Test
	public void testFind() throws Exception {
//		QueryConditions cdts = new QueryConditions();
//		cdts.setCurrPageNo(1);
//		cdts.setPageSize(10);
//		cdts.addCondition("status", ResidentDto.Statue.ACTIVE.name());
//		cdts.addCondition("nick", QueryConditions.MatchType.LK, "%1%");
//		List<Resident> recs = this.residentDao.findBySql(cdts);
//		assertEquals(1, recs.size());
//		recs = this.residentDao.findByHql(cdts);
//		assertEquals(1, recs.size());
	}

}
