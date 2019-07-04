package jadeutils.web.har;

import java.util.List;
import java.util.Map;

public class HarRequest {
	private HarHTTPMethod method;
	private String url;
	private Map<String, String> headers;
	private List<HarCookie> cookies;
	private List<String[]> queryString;
	private HarPostData postData;

	public HarRequest(HarHTTPMethod method, String url, Map<String, String> headers, List<HarCookie> cookies,
			List<String[]> queryString, HarPostData postData) {
		super();
		this.method = method;
		this.url = url;
		this.headers = headers;
		this.cookies = cookies;
		this.queryString = queryString;
		this.postData = postData;
	}

	@Override
	public String toString() {
		return "HarRequest [" + (method != null ? "method=" + method + ", " : "")
				+ (url != null ? "url=" + url + ", " : "") + (headers != null ? "headers=" + headers + ", " : "")
				+ (cookies != null ? "cookies=" + cookies + ", " : "")
				+ (queryString != null ? "queryString=" + queryString + ", " : "")
				+ (postData != null ? "postData=" + postData : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cookies == null) ? 0 : cookies.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((postData == null) ? 0 : postData.hashCode());
		result = prime * result + ((queryString == null) ? 0 : queryString.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HarRequest other = (HarRequest) obj;
		if (cookies == null) {
			if (other.cookies != null)
				return false;
		} else if (!cookies.equals(other.cookies))
			return false;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (method != other.method)
			return false;
		if (postData == null) {
			if (other.postData != null)
				return false;
		} else if (!postData.equals(other.postData))
			return false;
		if (queryString == null) {
			if (other.queryString != null)
				return false;
		} else if (!queryString.equals(other.queryString))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public HarHTTPMethod getMethod() {
		return method;
	}

	public void setMethod(HarHTTPMethod method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public List<HarCookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<HarCookie> cookies) {
		this.cookies = cookies;
	}

	public List<String[]> getQueryString() {
		return queryString;
	}

	public void setQueryString(List<String[]> queryString) {
		this.queryString = queryString;
	}

	public HarPostData getPostData() {
		return postData;
	}

	public void setPostData(HarPostData postData) {
		this.postData = postData;
	}

}
