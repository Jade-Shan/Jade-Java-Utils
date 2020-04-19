package net.jadedungeon.javautil.web.har;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class HarJsonUtilTest {
	private HarFactory<JSONObject, JSONArray> factory = new HarFactoryJsonImpl();
	// String jsonStr =
	// HotelUtil.readStringFromText("/var/meituan/mtwx_243_changeci.har");
	String jsonStr = "";// HotelUtil.readStringFromText("/var/meituan/example.har");

	@Test
	public void testGenHeadersFromJson() {
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		@SuppressWarnings("unchecked")
		List<JSONArray> jEntrys = (List<JSONArray>) JSONPath.eval(sampleJsonObject, "$log.entries.request.headers");
		for (int i = 0; i < jEntrys.size(); i++) {
			JSONArray jArr = jEntrys.get(i);
			Map<String, String> headers = factory.genHeaders(jArr);
			System.out.println(headers);
		}
	}

	@Test
	public void testGenCookieFromJson() {
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		@SuppressWarnings("unchecked")
		List<JSONArray> jEntrys = (List<JSONArray>) JSONPath.eval(sampleJsonObject, "$log.entries.request.cookies");
		for (int i = 0; i < jEntrys.size(); i++) {
			JSONArray jArr = jEntrys.get(i);
			List<HarCookie> recs = factory.genCookies(jArr);
			System.out.println(recs);
		}
	}

	@Test
	public void testGenQueryString() {
		String jsonStr = "";// HotelUtil.readStringFromText("/var/meituan/example.har");
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		@SuppressWarnings("unchecked")
		List<JSONArray> jEntrys = (List<JSONArray>) JSONPath.eval(sampleJsonObject, "$log.entries.request.queryString");
		for (int i = 0; i < jEntrys.size(); i++) {
			JSONArray jArr = jEntrys.get(i);
			List<String[]> recs = factory.genQueryString(jArr);
			System.out.println(recs);
		}
	}

	@Test
	public void testGenPostDataFromJson() {
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		@SuppressWarnings("unchecked")
		List<JSONObject> jEntrys = (List<JSONObject>) JSONPath.eval(sampleJsonObject, "$log.entries.request.postData");
		for (int i = 0; i < jEntrys.size(); i++) {
			JSONObject jArr = jEntrys.get(i);
			HarPostData recs = factory.genPostData(jArr);
			System.out.println(recs);
		}
	}

	@Test
	public void testGenRequest() {
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		@SuppressWarnings("unchecked")
		List<JSONObject> jEntrys = (List<JSONObject>) JSONPath.eval(sampleJsonObject, "$log.entries.request");
		for (int i = 0; i < jEntrys.size(); i++) {
			JSONObject jArr = jEntrys.get(i);
			HarRequest recs = factory.genRequest(jArr);
			System.out.println(recs);
		}
	}

	@Test
	public void testGenEntry() {
		JSONObject sampleJsonObject = JSONObject.parseObject(jsonStr);
		//
		JSONArray jEntrys = (JSONArray) JSONPath.eval(sampleJsonObject, "$log.entries");
		List<HarEntry> recs = factory.genEntries(jEntrys);
		System.out.println(recs);
	}

}
