/**
 * 
 */
package jadeutils.dao;

import java.util.List;

/**
 * @author morgan
 * 
 */
public class ResidentDto extends PageSplitDto {
	public enum Statue {
		ACTIVE, DISABLE
	}

	private String userId;
	private String userName;
	private String passward;
	private String nickname;
	private Statue statue;
	private List<Resident> resultList;
	private List<Resident> modelList;

	public List<Resident> getResultList() {
		return resultList;
	}

	public void setResultList(List<Resident> resultList) {
		this.resultList = resultList;
	}

	public List<Resident> getModelList() {
		return modelList;
	}

	public void setModelList(List<Resident> modelList) {
		this.modelList = modelList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Statue getStatue() {
		return statue;
	}

	public void setStatue(Statue statue) {
		this.statue = statue;
	}

}
