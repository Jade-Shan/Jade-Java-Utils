/**
 * 
 */
package jadeutils.dao;

import java.util.List;

/**
 * @author morgan
 * 
 */
public class PageSplitDto {

	// 总记录数
	private int countRecNumber;
	// 总页数
	private int countPageNumber;
	// 页面记录数
	private int pageSize = 10;
	// 当前页号
	private int currPageNo = 1;
	// 上一页
	private int prePageNo;
	// 下一页
	private int nextPageNo;
	// 之前页面码列表
	private List<String> prePageNoList;
	// 之后的页码列表
	private List<String> posPageNoList;
	// 省略前页号
	private String appPre;
	// 省略后页号
	private String appPos;

	public int getCountRecNumber() {
		return countRecNumber;
	}

	public void setCountRecNumber(int countRecNumber) {
		this.countRecNumber = countRecNumber;
	}

	public int getCountPageNumber() {
		return countPageNumber;
	}

	public void setCountPageNumber(int countPageNumber) {
		this.countPageNumber = countPageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}

	public int getPrePageNo() {
		return prePageNo;
	}

	public void setPrePageNo(int prePageNo) {
		this.prePageNo = prePageNo;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public List<String> getPrePageNoList() {
		return prePageNoList;
	}

	public void setPrePageNoList(List<String> prePageNoList) {
		this.prePageNoList = prePageNoList;
	}

	public List<String> getPosPageNoList() {
		return posPageNoList;
	}

	public void setPosPageNoList(List<String> posPageNoList) {
		this.posPageNoList = posPageNoList;
	}

	public String getAppPre() {
		return appPre;
	}

	public void setAppPre(String appPre) {
		this.appPre = appPre;
	}

	public String getAppPos() {
		return appPos;
	}

	public void setAppPos(String appPos) {
		this.appPos = appPos;
	}
}
