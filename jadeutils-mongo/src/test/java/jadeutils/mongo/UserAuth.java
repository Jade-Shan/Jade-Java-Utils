package jadeutils.mongo;

public class UserAuth {

	@MongoField
	String func;

	@MongoField
	boolean authed;

	public UserAuth(String func, boolean authed) {
		super();
		this.func = func;
		this.authed = authed;
	}

	public UserAuth() {
		super();
	}

	@Override
	public String toString() {
		return "UserAuth [func=" + func + ", authed=" + authed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (authed ? 1231 : 1237);
		result = prime * result + ((func == null) ? 0 : func.hashCode());
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
		UserAuth other = (UserAuth) obj;
		if (authed != other.authed)
			return false;
		if (func == null) {
			if (other.func != null)
				return false;
		} else if (!func.equals(other.func))
			return false;
		return true;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public boolean isAuthed() {
		return authed;
	}

	public void setAuthed(boolean authed) {
		this.authed = authed;
	}

}
