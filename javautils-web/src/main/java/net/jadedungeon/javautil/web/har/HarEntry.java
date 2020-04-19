package net.jadedungeon.javautil.web.har;

public class HarEntry {
	private HarRequest request;
	private HarResponse response;

	/**
	 * @return the request
	 */
	public HarRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HarRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HarResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HarResponse response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HarEntry other = (HarEntry) obj;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HarEntry [" + (request != null ? "request=" + request + ", " : "")
				+ (response != null ? "response=" + response : "") + "]";
	}

	public HarEntry(HarRequest request, HarResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

}
