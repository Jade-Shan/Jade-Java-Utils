package jadeutils.web.har;

import java.util.List;

public class HarPostData {
	private String mimeType;
	private String text;
	private List<HarPostDataParam> params;

	public HarPostData(String mimeType, String text, List<HarPostDataParam> params) {
		super();
		this.mimeType = mimeType;
		this.text = text;
		this.params = params;
	}

	@Override
	public String toString() {
		return "HarPostData [" + (mimeType != null ? "mimeType=" + mimeType + ", " : "")
				+ (text != null ? "text=" + text + ", " : "") + (params != null ? "params=" + params : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		HarPostData other = (HarPostData) obj;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<HarPostDataParam> getParams() {
		return params;
	}

	public void setParams(List<HarPostDataParam> params) {
		this.params = params;
	}

}
