package jadeutils.mongo;

import jadeutils.mongo.impl.MongoUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public abstract class BaseMongoDao<T extends MongoModel> implements MongoDao<T> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private MongoClient client;
	private DBCollection collection;
	private String dbName;
	private String collectionName;
	private Class<T> entryClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseMongoDao(List<MongoServer> serverList) {
		try {
			if (null != serverList && serverList.size() > 0) {
				List<ServerAddress> addrlist = new ArrayList<>();
				List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
				for (MongoServer s : serverList) {
					addrlist.add(new ServerAddress(s.getHost(), s.getPort()));
					if (null != s.getAuthList() && s.getAuthList().size() > 0) {
						for (String[] arr : s.getAuthList()) {
							if (null != arr[0] && null != arr[1]
									&& null != arr[2] && arr[0].length() > 0
									&& arr[1].length() > 0
									&& arr[2].length() > 0) //
							{
								MongoCredential credential = MongoCredential
										.createMongoCRCredential(arr[1],
												arr[0], arr[2].toCharArray());
								credentialsList.add(credential);
							}
						}
					}
				}
				if (credentialsList.size() > 0) {
					this.client = new MongoClient(addrlist, credentialsList);
				} else {
					this.client = new MongoClient(addrlist);
				}
			}
			Type genType = this.getClass().getGenericSuperclass();
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();
			entryClass = (Class) params[0];
			MongoDocument md = entryClass.getAnnotation(MongoDocument.class);
			if (null == md) {
				throw new Exception("ERROR: no Annotation on model");
			}
			dbName = md.databaseName();
			collectionName = md.collectionName();
			if (StringUtils.isBlank(collectionName)
					|| StringUtils.isBlank(dbName)) {
				throw new Exception(String.format(
						"ERROR: no Annotation on model: %s, %s", dbName,
						collectionName));
			}
			initCollection();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (null != client) {
			client.close();
		}
	}

	@Override
	public void insert(T obj) throws IllegalArgumentException,
			IllegalAccessException {
		this.collection.insert(MongoUtil.genRecFromModel(obj));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getByMongoId(String Id) throws InstantiationException,
			IllegalAccessException {
		DBObject rec = this.collection.findOne();
		T model = (T) MongoUtil.genModelFromRec(entryClass, rec);
		return model;
	}

	@SuppressWarnings("unchecked")
	public T findOneByCondition(Condition cdt) throws InstantiationException,
			IllegalAccessException {
		DBObject condition = MongoUtil.parseCondition(cdt);
		logger.debug("befor query: "
				+ (null == condition ? null : condition.toString()));
		DBObject rec = this.collection.findOne(condition);
		T model = (T) MongoUtil.genModelFromRec(entryClass, rec);
		return model;
	}

	@Override
	public MongoResultSet<T> findByCondition(Condition cdt)
			throws IllegalArgumentException, IllegalAccessException {
		DBObject condition = MongoUtil.parseCondition(cdt);
		logger.debug("befor query: "
				+ (null == condition ? null : condition.toString()));
		DBCursor cursor = this.collection.find(condition);
		return new MongoResultSet<>(entryClass, cursor);
	}

	@Override
	public void insertOrUpdate(Condition cdt, Condition opt)
			throws IllegalArgumentException, IllegalAccessException {
		this.update(cdt, opt, true, false);
	}

	@Override
	public void updateOne(Condition cdt, Condition opt)
			throws IllegalArgumentException, IllegalAccessException {
		this.update(cdt, opt, false, false);
	}

	@Override
	public void updateAll(Condition cdt, Condition opt)
			throws IllegalArgumentException, IllegalAccessException {
		this.update(cdt, opt, false, true);
	}

	private void update(Condition cdt, Condition opt, boolean upsert,
			boolean multi) throws IllegalArgumentException,
			IllegalAccessException {
		DBObject condition = MongoUtil.parseCondition(cdt);
		logger.debug("befor query: "
				+ (null == condition ? null : condition.toString()));
		this.collection.update(condition, MongoUtil.parseCondition(opt),
				upsert, multi);

	}

	private void initCollection() {
		this.collection = client.getDB(dbName).getCollection(collectionName);
	}
}
