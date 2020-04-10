package jadeutils.database.orm;

import java.util.Date;

public class Record<K> {

	private K id;
	private Date createTime;
	private Date lastChangeTime;

	public Record() {
		super();
	}

	public Record(K id, Date createTime, Date lastChangeTime) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.lastChangeTime = lastChangeTime;
	}

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastChangeTime() {
		return lastChangeTime;
	}

	public void setLastChangeTime(Date lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}

}
