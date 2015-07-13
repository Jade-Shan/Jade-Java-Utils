/**
 * 
 */
package jadeutils.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TreeNodeHelper<K, T> {

	/**
	 * make list to a tree
	 * 
	 * @param root
	 *            root note
	 * @param nodes
	 *            list of all nodes
	 * @param map
	 *            map of nodeId => node
	 * @param cper
	 *            comparator for sort
	 */
	@SuppressWarnings("unchecked")
	public void genTree(T root, List<T> nodes, Map<K, T> map, Comparator<T> cper) {
		TreeNodeItf<K, T> rootNode = (TreeNodeItf<K, T>) root;
		if (null == root || null == nodes || nodes.size() == 0) {
			return;
		} else {
			for (T st : nodes) {
				TreeNodeItf<K, T> n = (TreeNodeItf<K, T>) st;
				if (null != n.getParentNodeId()
						&& n.getParentNodeId().equals(rootNode.getId())) {
					map.put(n.getId(), st);
					n.setParentNode((T) root);
					if (null == rootNode.getSubNodeList()) {
						rootNode.setSubNodeList(//
						new ArrayList<T>());
					}
					rootNode.getSubNodeList().add(st);
				}
			}
		}
		if (null == rootNode.getSubNodeList()
				|| rootNode.getSubNodeList().size() == 0) {
			return;
		} else {
			if (null != cper) {
				Collections.sort(rootNode.getSubNodeList(), cper);
			}
			for (T r : rootNode.getSubNodeList()) {
				this.genTree(r, nodes, map, cper);
			}
		}
	}

}
