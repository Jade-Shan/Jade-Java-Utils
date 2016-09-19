package jadeutils.misc;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

/**
 *
 * @author
 * @version 0.1
 * @date 2012-5-5
 *
 */
public class SimpleTest {

	/*
	 * =====================================================
	 * 
	 * Mockit可以直接mock接口与类
	 * 
	 * =====================================================
	 */

	@Test
	public void simpleTest() {

		// 创建mock对象，参数可以是类，也可以是接口
		@SuppressWarnings("unchecked")
		List<String> list = mock(List.class);

		// 设置方法的预期返回值
		when(list.get(0)).thenReturn("helloworld");

		String result = list.get(0);
		// 验证方法调用(是否调用了get(0))
		verify(list).get(0);

		// junit测试
		Assert.assertEquals("helloworld", result);
	}

	/*
	 * =====================================================
	 * 
	 * Mock出来的类可以很方便地用来填满抽象类中未实现的方法。 这样用不用每个手动写上空实现了。
	 * 
	 * =====================================================
	 */

	/**
	 * 抽象类，
	 * 
	 * @author jade
	 *
	 */
	abstract class MyAbs {
		public String methodUnderTest() {
			return "hello";
		}

		protected abstract void methodIDontCareAbout();
	}

	@Test
	public void shouldFailOnNullIdentifiers() {
		// `Mockito.CALLS_REAL_METHODS`给抽象方法都加上默认的实现
		// （不覆盖已经实现的方法），就不用自己动手写了
		MyAbs my = Mockito.mock(MyAbs.class, Mockito.CALLS_REAL_METHODS);
		Assert.assertEquals("hello", my.methodUnderTest());
	}

	/*
	 * =====================================================
	 * 
	 * 参数匹配器可以用来定义与检查传入的参数
	 * 
	 * =====================================================
	 */
	@Test
	public void argumentMatcherTest() {
		@SuppressWarnings("unchecked")
		List<String> list = mock(List.class);

		when(list.get(anyInt())).thenReturn("hello", "world");

		String result = list.get(0) + list.get(1);
		verify(list, times(2)).get(anyInt());

		Assert.assertEquals("helloworld", result);
	}

	/*
	 * ===============================================================
	 * 
	 * `argThat()`方法用来创建自定义类的匹配器
	 * 
	 * ===============================================================
	 */
	class MyModel {
		private String name;

		public MyModel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	class MyModelService {

		public String func(MyModel m) {
			return m.getName();
		}

	}

	class IsMyModel extends ArgumentMatcher<MyModel> {

		@Override
		public boolean matches(Object arg) {
			return arg instanceof MyModel;
		}

	}

	@Test
	public void testMyModelArg() {
		MyModelService srv = mock(MyModelService.class);
		when(srv.func(argThat(new IsMyModel()))).thenReturn("hello");

		String result = srv.func(new MyModel("Not hello"));

		verify(srv).func(argThat(new IsMyModel()));
		Assert.assertEquals("hello", result);
	}

	/*
	 * =====================================================
	 * 
	 * verify是用来检查调用的步骤与参数的，不管返回结果是否正确。返回结果的检查是JUnit的工作
	 * 
	 * =====================================================
	 */
	@Test
	public void verifyInvocate() {
		@SuppressWarnings("unchecked")
		List<String> mockedList = mock(List.class);

		// using mock
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		/*
		 * 基本的验证方法`verify`方法验证mock对象是否有没有调用`mockedList.add("once")`方法。
		 * 不关心其是否有返回值，如果没有调用测试失败。
		 */
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");// 默认调用一次,times(1)可以省略

		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		// never()等同于time(0),一次也没有调用
		verify(mockedList, times(0)).add("never happened");

		// atLeastOnece/atLeast()/atMost()
		verify(mockedList, atLeastOnce()).add("three times");
		verify(mockedList, atLeast(2)).add("twice");
		verify(mockedList, atMost(5)).add("three times");
	}

	/*
	 * =====================================================
	 * 
	 * 如果在Stubbing时用了参数匹配器，那么在verify时也要用
	 * 
	 * =====================================================
	 */
	@Test
	public void argumentMatcherTest2() {
		@SuppressWarnings("unchecked")
		Map<Integer, String> map = mock(Map.class);

		// anyString()替换成"hello"就会报错
		when(map.put(anyInt(), anyString())).thenReturn("hello");

		// eq("world")替换成"world"也会报错
		map.put(1, "world");
		verify(map).put(eq(1), eq("world"));
	}
}
