package net.jadedungeon.javautil.text;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HtmlUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHtmlP() {
		assertEquals("\nThe Nobel(撒娇的空间卡死的快乐? Peace Prize for 2008 " //
				+ "was given to Martti Ahtisaari. He was the president of " //
				+ "Finland from 1994 to 2000. He won the prize for his work " //
				+ "in solving international conflicts for more than 30 years. \n" //
				+ "     his work has made a more peaceful world in Nobel spirit, " //
				+ "the officer said, he has won the prize.",
				HtmlUtil.html2text("<p>The Nobel(撒娇的空间卡死的快乐? Peace Prize "
						+ "for 2008 was given to Martti Ahtisaari. He was the president " //
						+ "of Finland from 1994 to 2000. He won the prize for his work " //
						+ "in solving international conflicts for more than 30 years. </p>" //
						+ "<p>&nbsp;&nbsp; &nbsp; his work has made a more peaceful world " //
						+ "in Nobel spirit, the officer said, he has won the prize.</p>"));
	}

	@Test
	public void testHtmlH() {
		assertEquals("\n====================\n理论基础\n====================\n" //
				+ "\n在这里1V可以定义为产生1A电流来做1W的功所需要的压力。"//
				+ "如果脱离电子学来看： ",
				HtmlUtil.html2text("<h2 id=\"toc_0.1\">理论基础</h2>" //
						+ "<p>在这里1V可以定义为产生1A电流来做1W的功所需要的压力。" //
						+ "如果脱离电子学来看： </p>"));
	}

	@Test
	public void testHtmlLi() {
		assertEquals("\n * 1W等于每秒1J的功。" //
				+ "\n * 1J等于1N的力的作用下，前进1m所需要的功。" //
				+ "\n * 1N等于每秒让1kg的物体加速1 m/s所需要的力。",
				HtmlUtil.html2text("<ul><li>1W等于每秒1J的功。</li>" //
						+ "<li>1J等于1N的力的作用下，前进1m所需要的功。</li>"//
						+ "<li>1N等于每秒让1kg的物体加速1 m/s所需要的力。</li></ul>"));
	}

	@Test
	public void testTable() {
		assertEquals("" + "毫瓦表示法\t瓦特表示法\t千瓦表示法\t兆瓦表示法\t\n"//
				+ "1 mW\t0.001 W\t0.000,001 kW\t0.000,000,001 MW\t\n" //
				+ "1000 mW\t1 W\t0.001 kW\t0.000,001 MW\t\n" //
				+ "1,000,000 mW\t1000 W\t1 kW\t0.001 MW\t\n"//
				+ "1,000,000,000 mW\t1000,000 W\t1000 kW\t1 MW\t\n",
				HtmlUtil.html2text("<table>" //
						+ "<tr><th>毫瓦表示法</th><th>瓦特表示法</th><th>千瓦表示法</th><th>兆瓦表示法</th></tr>"
						+ "<tr><td>1 mW</td><td>0.001 W</td><td>0.000,001 kW</td><td>0.000,000,001 MW</td></tr>"
						+ "<tr><td>1000 mW</td><td>1 W</td><td>0.001 kW</td><td>0.000,001 MW</td></tr>"
						+ "<tr><td>1,000,000 mW</td><td>1000 W</td><td>1 kW</td><td>0.001 MW</td></tr>"
						+ "<tr><td>1,000,000,000 mW</td><td>1000,000 W</td><td>1000 kW</td><td>1 MW</td></tr>"
						+ "</table>"));
	}

}
