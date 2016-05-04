package jadeutils.tools;

import jadeutils.sandtable.tools.MapUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDistanceTwoPoint() {
		double d = MapUtils.getDistanceByCoords(39.95676, 116.401394, 36.63014,
				114.499574);
		Assert.assertTrue(405800 < d && d < 405900);
	}

	@Test
	public void testGetLongtitudeDistance() {
		double d = MapUtils.getLongtitudeDistance(0.1, 30);
		double dd = MapUtils.getDistanceByCoords(30, 0, 30, 0.1);
		Assert.assertTrue(Math.abs(d - dd) < 50);
	}
	
	@Test
	public void testGetlatitudeDistance(){
		double d = MapUtils.getLatitueDistance(0.1);
		double dd = MapUtils.getDistanceByCoords(0, 120, 0.1, 120);
		Assert.assertTrue(Math.abs(d - dd) < 50);
	}

}
