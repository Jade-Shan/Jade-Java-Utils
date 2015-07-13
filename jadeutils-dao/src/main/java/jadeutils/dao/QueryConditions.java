package jadeutils.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryConditions extends PageSplitDto {

	public enum MatchType {
		EQ("="), NE("!="), LE("<="), GE(">="), //
		LT("<"), GT(">"), LK("like");//
		private String name;

		private MatchType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum LinkType {
		AND, OR
	};

	class Condition {
		LinkType linkType;
		String field;
		MatchType matchType;
		String lable;
		Object value;

		public Condition(LinkType linkType, String field, MatchType matchType,
				String lable, Object value) {
			super();
			this.linkType = linkType;
			this.field = field;
			this.matchType = matchType;
			this.lable = lable;
			this.value = value;
		}
	}

	private List<Condition> conditions = new ArrayList<>();

	private String defaultTable;
	private Map<String, String> fieldTableMap;

	public void configure(String defaultTable, Map<String, String> fieldTableMap) {
		this.defaultTable = defaultTable;
		this.fieldTableMap = fieldTableMap;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(" where 1=1 ");
		for (Condition c : conditions) {
			String table = null;
			if (null != this.fieldTableMap) {
				table = this.fieldTableMap.get(c.field);
			}
			table = null == table ? this.defaultTable : table;
			sb.append(String.format(" %s %s.%s %s :%s", c.linkType, table,
					c.field, c.matchType.name, c.lable));
		}
		return sb.toString();
	}

	public Map<String, Object> generageParamMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		for (Condition c : conditions) {
			params.put(c.lable, c.value);
		}
		return params;
	}

	public void addCondition(LinkType linkType, String field,
			MatchType matchType, String lable, Object value) {
		this.conditions.add(new Condition(linkType, field, matchType, lable,
				value));
	}

	public void addCondition(LinkType linkType, String field,
			MatchType matchType, Object value) {
		this.conditions.add(new Condition(linkType, field, matchType, field,
				value));
	}

	public void addCondition(String field, MatchType matchType, String lable,
			Object value) {
		this.conditions.add(new Condition(LinkType.AND, field, matchType,
				lable, value));
	}

	public void addCondition(String field, MatchType matchType, Object value) {
		this.conditions.add(new Condition(LinkType.AND, field, matchType,
				field, value));
	}

	public void addCondition(String field, Object value) {
		this.conditions.add(new Condition(LinkType.AND, field, MatchType.EQ,
				field, value));
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

}
