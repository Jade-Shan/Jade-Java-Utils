package jadeutils.cache.impl;

import jadeutils.cache.BaseCache;

public class EhCacheImpl<T> implements BaseCache<T> {

	@Override
	public BaseCache<T> createInstance() throws Exception {
		return this;
	}

	@Override
	public String getImplTypeName() {
		return this.getClass().getName();
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(String key, Object value, int expire) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub

	}

}
