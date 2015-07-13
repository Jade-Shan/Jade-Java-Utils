package jadeutils.mongo;

import java.util.List;
import java.util.Map;

@MongoDocument(databaseName = "jade-utils-test", collectionName = "user")
public class User extends MongoModel {

	@MongoField
	private String id;

	@MongoField
	private String name;

	@MongoField
	private int level;

	@MongoField(ElemType = UserAuth.class)
	private List<UserAuth> authList;

	@MongoField
	private List<String> listExample;

	@MongoField
	private Map<String, String> mapExample;

	@MongoField
	private boolean onDuby;

	public boolean isOnDuby() {
		return onDuby;
	}

	public void setOnDuby(boolean onDuby) {
		this.onDuby = onDuby;
	}

	public User() {
		super();
	}

	public User(String id, String name, int level, List<UserAuth> authList,
			List<String> listExample, Map<String, String> mapExample,
			boolean onDuby) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.authList = authList;
		this.listExample = listExample;
		this.mapExample = mapExample;
		this.onDuby = onDuby;
	}

	@Override
	public String toString() {
		return "User [_id=" + getMongoId() + ", id=" + id + ", name=" + name
				+ ", level=" + level + ", authList=" + authList
				+ ", listExample=" + listExample + ", mapExample=" + mapExample
				+ ", onDuby=" + onDuby + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authList == null) ? 0 : authList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result
				+ ((listExample == null) ? 0 : listExample.hashCode());
		result = prime * result
				+ ((mapExample == null) ? 0 : mapExample.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (onDuby ? 1231 : 1237);
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
		User other = (User) obj;
		if (authList == null) {
			if (other.authList != null)
				return false;
		} else if (!authList.equals(other.authList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (listExample == null) {
			if (other.listExample != null)
				return false;
		} else if (!listExample.equals(other.listExample))
			return false;
		if (mapExample == null) {
			if (other.mapExample != null)
				return false;
		} else if (!mapExample.equals(other.mapExample))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onDuby != other.onDuby)
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<UserAuth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<UserAuth> authList) {
		this.authList = authList;
	}

	public List<String> getListExample() {
		return listExample;
	}

	public void setListExample(List<String> listExample) {
		this.listExample = listExample;
	}

	public Map<String, String> getMapExample() {
		return mapExample;
	}

	public void setMapExample(Map<String, String> mapExample) {
		this.mapExample = mapExample;
	}

}
