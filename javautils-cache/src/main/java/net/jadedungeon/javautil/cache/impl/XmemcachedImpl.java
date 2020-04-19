package net.jadedungeon.javautil.cache.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import net.jadedungeon.javautil.cache.BaseCache;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XmemcachedImpl<T> implements BaseCache<T> {

	// 配置文件位置
	private static final String CONF_FILE_PROP = "jade-xmemcache.properties";
	//
	private String address;
	private String port;
	// 访问服务器的超时
	private long timeout;
	// 数据保存时间，单位是秒。0表示永久存储（默认是一个月）
	private int expire;

	private MemcachedClient client;

	@Override
	public BaseCache<T> createInstance() throws Exception {
		InputStream confIn = new BufferedInputStream(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(CONF_FILE_PROP));
		Properties p = new Properties();
		p.load(confIn);
		this.address = p.getProperty("memcached.host", "localhost");
		this.port = p.getProperty("memcached.port", "11211");
		this.timeout = Long.parseLong(p.getProperty("memcached.timeout", "5000"));
		this.expire = Integer.parseInt(p.getProperty("memcached.expire", "30"));
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses(this.address + ":" + this.port));
		this.client = builder.build();
		return this;
	}

	@Override
	public void shutdown() {
		try {
			this.client.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getImplTypeName() {
		return this.getClass().getName();
	}

	@Override
	public Object get(String id) {
		Object result = null;
		try {
			result = this.client.get(id, this.timeout);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void set(String key, Object value) {
		this.set(key, value, this.expire);
	}

	@Override
	public void set(String key, Object value, int expire) {
		try {
			this.client.set(key, expire, value, this.timeout);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String key) {
		try {
			this.client.delete(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}

}
