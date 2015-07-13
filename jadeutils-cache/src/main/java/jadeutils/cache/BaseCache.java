package jadeutils.cache;

public interface BaseCache<T> {
	/**
	 * 创建实例
	 * 
	 * @return 配置完成的实例
	 * @throws Exception
	 */
	public BaseCache<T> createInstance() throws Exception;

	public void shutdown();

	/**
	 * 取得实现类
	 * 
	 * @return 取得实现类
	 */
	public String getImplTypeName();

	/**
	 * 取得缓存记录
	 * 
	 * @param key
	 *            key
	 * @return 缓存记录
	 */
	public Object get(String key);

	/**
	 * 保存缓存记录
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            缓存记录
	 */
	public void set(String key, T value);

	/**
	 * 保存缓存记录
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            缓存记录
	 * @param expire
	 *            超时时间
	 */
	public void set(String key, T value, int expire);

	/**
	 * 删除缓存记录
	 * 
	 * @param key
	 *            key
	 */
	public void delete(String key);

}
