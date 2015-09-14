package jadeutils.mongo;

public class Condition {
	private String key;
	private Object value;
	private Link link;

	private Condition(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public static Condition newCondition(Option option, Object value) {
		return newCondition(option.getName(), value);
	}

	public static Condition newCondition(String key, Object value) {
		return new Condition(key, value);
	}

	public Condition append(LinkType type, Condition condition) {
		this.link = new Link(type, condition);
		return this;
	}

	public enum LinkType {
		AND, OR
	}

	public class Link {
		private LinkType type;
		private Condition condition;

		public Link(LinkType type, Condition condition) {
			this.type = type;
			this.condition = condition;
		}

		public LinkType getType() {
			return type;
		}

		public void setType(LinkType type) {
			this.type = type;
		}

		public Condition getCondition() {
			return condition;
		}

		public void setCondition(Condition condition) {
			this.condition = condition;
		}

	}

	public enum Option {
		NE("$ne"), LTE("$lte"), GTE("$gte"), //
		LT("$lt"), GT("$gt");//
		private String name;

		private Option(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Link getLink() {
		return link;
	}

}
