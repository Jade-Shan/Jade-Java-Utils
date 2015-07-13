/**
 * 
 */
package jadeutils.datastructures;

import java.util.List;

/**
 * @author morgan
 * 
 */
public class Category implements TreeNodeItf<Long, Category> {

	private String name;

	private Long id;
	private Long parentNodeId;
	private Category parentNode;
	private List<Category> subNodeList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public Category getParentNode() {
		return parentNode;
	}

	public void setParentNode(Category parentNode) {
		this.parentNode = parentNode;
	}

	public List<Category> getSubNodeList() {
		return subNodeList;
	}

	public void setSubNodeList(List<Category> subNodeList) {
		this.subNodeList = subNodeList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
