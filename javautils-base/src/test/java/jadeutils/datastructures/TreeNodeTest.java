/**
 * 
 */
package jadeutils.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author morgan
 * 
 */
public class TreeNodeTest {
	private Category root = null;

	private List<Category> categoryList;

	private Category createSubNode(Long id, Long parentId, String name) {
		Category c = new Category();
		c.setId(id);
		c.setParentNodeId(parentId);
		c.setName(name);
		return c;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		root = new Category();
		this.categoryList = new ArrayList<Category>();
		long id = 1;
		for (int i = 0; i < 5; i++) {
			Category c = this.createSubNode(id, null, "" + (i + 1));
			this.categoryList.add(c);
			for (int j = 0; j < 5; j++) {
				Category sc = this.createSubNode(id, null, c.getName()
						+ (j + 1));
				this.categoryList.add(sc);
				for (int k = 0; k < 5; k++) {
					Category ssc = this.createSubNode(id, null, sc.getName()
							+ (k + 1));
					this.categoryList.add(ssc);
				}
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenTree() {
		TreeNodeHelper<Long, Category> th = new TreeNodeHelper<Long, Category>();
		Map<Long, Category> itemIdMap = new HashMap<Long, Category>();
		th.genTree(this.root, this.categoryList, itemIdMap,
				new Comparator<Category>() {
					@Override
					public int compare(Category a, Category b) {
						int result = 0;
						if ((null == a || null == a.getId())
								&& (null != b && null != b.getId())) {
							result = 1;
						} else if ((null != a && null != a.getId())
								&& (null == b || null == b.getId())) {
							result = -1;
						} else if ((null != a && null != a.getId())
								&& (null != b && null != b.getId())) {
							return a.getId().compareTo(b.getId());
						}
						return result;
					}

				});
	}
}
