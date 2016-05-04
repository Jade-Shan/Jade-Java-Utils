/**
 * 
 */
package jadeutils.reflect;

/**
 * @author SHAN013
 * 
 */
public class Guest {
	private String id;
	private String nickName;
	private Integer age;
	private Boolean isAdmin;
	private Boolean actived;

	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", age=" + age
				+ ", isAdmin=" + isAdmin + ", actived=" + actived + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actived == null) ? 0 : actived.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
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
		Guest other = (Guest) obj;
		if (actived == null) {
			if (other.actived != null)
				return false;
		} else if (!actived.equals(other.actived))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getActived() {
		return actived;
	}

	public void setActived(Boolean actived) {
		this.actived = actived;
	}

}
