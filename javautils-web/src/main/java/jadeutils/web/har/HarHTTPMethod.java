package jadeutils.web.har;

public enum HarHTTPMethod {
	GET(1, "GET"), //
	HEAD(2, "HEAD"), //
	POST(3, "POST"), //
	PUT(4, "PUT"), //
	DELETE(5, "DELETE"), //
	CONNECT(6, "CONNECT"), //
	OPTIONS(7, "OPTIONS"), //
	TRACE(8, "TRACE"), //
	PATCH(9, "PATCH"), //
	MOVE(10, "MOVE"), //
	COPY(11, "COPY"), //
	LINK(12, "LINK"), //
	UNLINK(13, "UNLINK"), //
	WRAPPED(14, "WRAPPED"), //
	EXT(15, "Extension-mothed"); //

	private String name;
	private int code;

	private HarHTTPMethod(int code, String name) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "httpMethod: {" + this.code + ":" + this.name + "}";
	}

}
