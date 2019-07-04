package jadeutils.web.har;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class HarFactoryJsonImpl implements HarFactory<JSONObject, JSONArray> {

	public Map<String, String> genHeaders(JSONArray jArr) {
		Map<String, String> headers = new HashMap<>();
		for (int i = 0; i < jArr.size(); i++) {
			JSONObject jo = jArr.getJSONObject(i);
			String name = (String) JSONPath.eval(jo, "$.name");
			String value = (String) JSONPath.eval(jo, "$.value");
			if (null != name && null != value) {
				headers.put(name, value);
			}
		}
		return headers;
	}

	public List<HarCookie> genCookies(JSONArray ckArr) {
		List<HarCookie> cookies = new ArrayList<>();
		for (int i = 0; i < ckArr.size(); i++) {
			JSONObject jo = ckArr.getJSONObject(i);
			String name = (String) JSONPath.eval(jo, "$.name");
			String value = (String) JSONPath.eval(jo, "$.value");
			String path = (String) JSONPath.eval(jo, "$.path");
			String domain = (String) JSONPath.eval(jo, "$.domain");
			String expries = (String) JSONPath.eval(jo, "$.expries");
			boolean httpOnly = (boolean) JSONPath.eval(jo, "$.httpOnly");
			boolean secure = (boolean) JSONPath.eval(jo, "$.secure");
			String comment = (String) JSONPath.eval(jo, "$.comment");
			if (null != name && null != value) {
				HarCookie cookie = new HarCookie(name, value, path, domain, expries, httpOnly, secure, comment);
				cookies.add(cookie);
			}
		}
		return cookies;
	}

	public List<String[]> genQueryString(JSONArray qsArr) {
		List<String[]> queryString = new ArrayList<>();
		for (int i = 0; i < qsArr.size(); i++) {
			JSONObject jo = qsArr.getJSONObject(i);
			String name = (String) JSONPath.eval(jo, "$.name");
			String value = (String) JSONPath.eval(jo, "$.value");
			if (null != name && null != value) {
				String[] query = { name, value };
				queryString.add(query);
			}
		}
		return queryString;
	}

	public HarPostData genPostData(JSONObject pdJo) {
		HarPostData result = null;
		if (null != pdJo) {
			String mimeType = (String) JSONPath.eval(pdJo, "$.mimeType");
			String text = (String) JSONPath.eval(pdJo, "$.text");
			List<HarPostDataParam> params = new ArrayList<>();
			result = new HarPostData(mimeType, text, params);
		}
		return result;
	}

	public HarRequest genRequest(JSONObject reqJo) {
		HarRequest request = null;
		if (null != reqJo) {
			String method = (String) JSONPath.eval(reqJo, "$.method");
			String url = (String) JSONPath.eval(reqJo, "$.url");
			List<String[]> queryString = this.genQueryString(//
					(JSONArray) JSONPath.eval(reqJo, "$.queryString"));
			Map<String, String> headers = this.genHeaders(//
					(JSONArray) JSONPath.eval(reqJo, "$.headers"));
			List<HarCookie> cookies = this.genCookies(//
					(JSONArray) JSONPath.eval(reqJo, "$.cookies"));
			HarPostData postData = this.genPostData(//
					(JSONObject) JSONPath.eval(reqJo, "$.postData"));
			if (null != method && null != url) {
				request = new HarRequest(//
						HarHTTPMethod.valueOf(method.toUpperCase()), //
						url, headers, cookies, queryString, postData);
			}
		}
		return request;
	}

	public HarResponse genResponse(JSONObject rspJo) {
		HarResponse response = null;
		if (null != rspJo) {
			response = new HarResponse();
		}
		return response;
	}

	public List<HarEntry> genEntries(JSONArray entArr) {
		List<HarEntry> entries = new ArrayList<>();
		for (int i = 0; i < entArr.size(); i++) {
			JSONObject jo = entArr.getJSONObject(i);
			HarRequest request = this.genRequest(//
					(JSONObject) JSONPath.eval(jo, "$.request"));
			HarResponse response = this.genResponse(//
					(JSONObject) JSONPath.eval(jo, "$.response"));
			HarEntry entry = new HarEntry(request, response);
			entries.add(entry);
		}
		return entries;
	}
}
