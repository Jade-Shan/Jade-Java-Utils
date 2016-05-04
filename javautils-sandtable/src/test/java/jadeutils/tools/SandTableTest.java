package jadeutils.tools;

import static junit.framework.Assert.assertTrue;
import jadeutils.sandtable.model.Area;
import jadeutils.sandtable.model.SandTable;
import jadeutils.sandtable.status.Direction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SandTableTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int w = 500;
		int h = 500;
		SandTable sandTable = new SandTable(w, h);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				sandTable.putArea(new Area(x, y, "GRASS", Direction.d6));
			}
		}
		// AsciiDisplay.showSandTable(sandTable);
		assertTrue(null != sandTable.toString());
	}

}
