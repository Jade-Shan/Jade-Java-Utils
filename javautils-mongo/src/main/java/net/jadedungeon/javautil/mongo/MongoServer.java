package net.jadedungeon.javautil.mongo;

import java.util.List;

public class MongoServer {
	private String host;
	private int port;
	private List<String[]> authList;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("MongoServer {host=" + host
				+ ", port=" + port + ", authList: [");
		if (null != authList && authList.size() > 0) {
			for (String[] str : authList) {
				sb.append("{");
				if (null != str && str.length > 0) {
					for (int i = 0; i < str.length; i++) {
						sb.append(str[i]).append(",");
					}
				}
				sb.append("}");
			}
		}
		return sb.append("]}").toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result
				+ ((authList == null) ? 0 : authList.hashCode());
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
		MongoServer other = (MongoServer) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		if (authList != null) {
			if (other.authList == null)
				return false;
		} else if (this.authList != other.authList) {
			return false;
		} else if (this.authList.size() != other.authList.size()) {
			return false;
		}
		return true;
	}

	public MongoServer(String host, int port, List<String[]> authList) {
		super();
		this.host = host;
		this.port = port;
		this.authList = authList;
	}

	public MongoServer(String host, int port) {
		new MongoServer(host, port, null);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public List<String[]> getAuthList() {
		return authList;
	}

	public void setAuthList(List<String[]> authList) {
		this.authList = authList;
	}

}
