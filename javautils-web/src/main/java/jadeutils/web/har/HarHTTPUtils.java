package jadeutils.web.har;

public class HarHTTPUtils {
	// private Executor executor = new Executor();
	// private Browser browser = BrowserFactory.getNewBrowser();
	//
	// public void addCookies(List<HarCookie> cookies) {
	// for (HarCookie c : cookies) {
	// BasicClientCookie cookie = null;
	// if (null == c.getValue()) {
	// cookie = new BasicClientCookie(c.getName(), null);
	// } else {
	// cookie = new BasicClientCookie(c.getName(), c.getValue());
	// }
	// if (StringUtils.isNotBlank(c.getPath())) {
	// cookie.setPath((c.getPath()));
	// }
	// if (StringUtils.isNotBlank(c.getDomain())) {
	// cookie.setDomain((c.getDomain()));
	// }
	// executor.getCookieStore().addCookie(cookie);
	// }
	// }
	//
	// public void addHeaders(Command command, Map<String, String> headers, boolean
	// skipCookies) {
	// for (Entry<String, String> h : headers.entrySet()) {
	// String name = h.getKey();
	// String value = h.getValue();
	// if (name.equalsIgnoreCase("Content-Length")) {
	// // skip content length
	// } else if (skipCookies && name.equalsIgnoreCase("Cookie")) {
	// // skip cookie
	// } else {
	// command.addHeader(name, (value));
	// }
	// }
	// }
	//
	// public void addRequestContent(Command command, List<String[]> queryString,
	// HarPostData postData)//
	// {
	// String sCnt = "";
	// if (null != postData && null != postData.getText() &&
	// postData.getText().length() > 0) {
	// sCnt = (postData.getText());
	// } else if (null != queryString) {
	// List<String> ll = new ArrayList<>();
	// for (String[] q : queryString) {
	// ll.add(q[0] + "=" + q[1]);
	// }
	// if (ll.size() > 0) {
	// sCnt = StringUtils.join(ll, "&");
	// }
	// }
	// command.setContent(sCnt.getBytes());
	// }
	//
	// public Response sendRequest(HarRequest req) {
	// Response response = new Response();
	// Command command = browser.getNewCommand();
	// executor.clearCookie();
	// command.setUrl(req.getUrl());
	// if (HarHTTPMethod.GET == req.getMethod() || HarHTTPMethod.POST ==
	// req.getMethod()) {
	// this.addCookies(req.getCookies());
	// boolean skipCookies = req.getCookies() != null && req.getCookies().size() >
	// 0;
	// this.addHeaders(command, req.getHeaders(), skipCookies);
	// if (HarHTTPMethod.GET == req.getMethod()) {
	// executor.executeGetGzipNoDefaultHeader(command, response);
	// } else if (HarHTTPMethod.POST == req.getMethod()) {
	// executor.executePostGzipNoDefaultHeader(command, response);
	// }
	// }
	// return response;
	// }
	//
	// public static void main(String[] args) throws Exception {
	//
	// }

}
