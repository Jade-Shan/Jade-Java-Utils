/*
 * 
 */
package jadeutils.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @author morgan
 * 
 */
@Entity
@Table(name = "resident")
public class Resident implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String userName;
	private String password;
	private String nick;
	private String status;
	private Date createTime;

	/*
	 * 
	 */
	public Resident() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
