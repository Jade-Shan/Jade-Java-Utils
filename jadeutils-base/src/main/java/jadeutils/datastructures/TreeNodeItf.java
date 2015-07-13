/**
 * 
 */
package jadeutils.datastructures;

import java.util.List;

/**
 * @author morgan
 * 
 */
public interface TreeNodeItf<K, T> {
	public K getId();

	public void setId(K id);

	public K getParentNodeId();

	public void setParentNodeId(K parentNodeId);

	public T getParentNode();

	public void setParentNode(T parentNode);

	public List<T> getSubNodeList();

	public void setSubNodeList(List<T> subNodeList);
}
