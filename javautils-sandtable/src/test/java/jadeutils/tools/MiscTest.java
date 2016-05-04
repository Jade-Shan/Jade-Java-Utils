package jadeutils.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MiscTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		for (int i = 0; i < 20; i++) {
			double w = Math.sin(Math.PI / 4) * i;
			int n = (int) Math.ceil(w) ;
			System.out.println(w + " <> " + Math.ceil(w) + " - " + (w - n));
		}
	}
}
