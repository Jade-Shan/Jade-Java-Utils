package jadeutils.misc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* 指定特殊的运行器org.junit.runners.Parameterized */
@RunWith(Parameterized.class)
public class ExampleParamTest {

	private int inputVal; // 输入参数
	private boolean expectRst; // 期待的结果

	public ExampleParamTest(int inputVal, boolean expectRst) {
		/* 构造函数完成对输入参数和期待结果变量的赋值 */
		this.inputVal = inputVal;
		this.expectRst = expectRst;
	}

	private MyObj myObj; // 要调试的类

	@Before
	public void setUp() throws Exception {
		/* 调用每个测试方法前都要创建一个新的测试目标对象 */
		this.myObj = new MyObj();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Parameters
	/* 为测试类声明一个注解`@Parameters`，返回值为`Collection的`公共静态方法 */
	public static Collection<Object[]> prepareData() {
		/* 并初始化所有需要测试的参数对。 */
		Object[][] object = { { -1, false }, { 13, true } };
		return Arrays.asList(object);
	}

	@Test
	public void test() {
		System.out.println(expectRst + ",  " + inputVal);
		assertEquals(expectRst, myObj.myFunc(inputVal));
	}

}

class MyObj {

	public boolean myFunc(int n) {
		return n > 0 ? true : false;
	}

}
