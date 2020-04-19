package net.jadedungeon.javautil.web.har;

public class HarCookie {
	private String name;
	private String value;
	private String path;
	private String domain;
	private String expries;
	private boolean httpOnly = false;
	private boolean secure = false;
	private String comment;

	public HarCookie(String name, String value, //
			String path, String domain, String expries, //
			boolean httpOnly, boolean secure, String comment) //
	{
		super();
		this.name = name;
		this.value = value;
		this.path = path;
		this.domain = domain;
		this.expries = expries;
		this.httpOnly = httpOnly;
		this.secure = secure;
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "HarCookie [name=" + name + ", value=" + value + ", path=" + path + ", domain=" + domain + ", expries="
				+ expries + ", httpOnly=" + httpOnly + ", secure=" + secure + ", comment=" + comment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((expries == null) ? 0 : expries.hashCode());
		result = prime * result + (httpOnly ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (secure ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		HarCookie other = (HarCookie) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (expries == null) {
			if (other.expries != null)
				return false;
		} else if (!expries.equals(other.expries))
			return false;
		if (httpOnly != other.httpOnly)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (secure != other.secure)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getExpries() {
		return expries;
	}

	public void setExpries(String expries) {
		this.expries = expries;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
