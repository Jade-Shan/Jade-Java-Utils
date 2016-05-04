/**
 * 
 */
package jadeutils.cache;

/**
 * @author morgan
 * 
 */
public class BaseCacheFactory<T> {

	/**
	 * 取得配置完成的缓存实例
	 * 
	 * @param cacheClass
	 *            实现类
	 * @return 配置完成的缓存实例
	 * @throws Exception
	 */
	public BaseCache<T> createCache(Class<BaseCache<T>> cacheClass)
			throws Exception {
		BaseCache<T> cache = cacheClass.newInstance();
		return cache.createInstance();
	}

	/**
	 * 取得配置完成的缓存实例
	 * 
	 * @param cacheClassName
	 *            实现类的名称
	 * @return 配置完成的缓存实例
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  BaseCache<T> createCache(String cacheClassName) throws Exception {
		return ((BaseCache<T>) Class.forName(cacheClassName).newInstance())
				.createInstance();
	}
}
