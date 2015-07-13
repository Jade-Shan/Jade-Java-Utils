package jadeutils.mongo;

public interface MongoDao<T extends MongoModel> {
	public void close();

	public void insert(T obj) throws IllegalArgumentException,
			IllegalAccessException;

	public void insertOrUpdate(Condition cdt, Condition opt) throws IllegalArgumentException, IllegalAccessException;

	public void updateOne(Condition cdt, Condition opt) throws IllegalArgumentException, IllegalAccessException;

	public void updateAll(Condition cdt, Condition opt) throws IllegalArgumentException, IllegalAccessException;

	public T getByMongoId(String Id) throws InstantiationException,
			IllegalAccessException;

	public T findOneByCondition(Condition cdt) throws InstantiationException,
			IllegalAccessException;

	public MongoResultSet<T> findByCondition(Condition cdt) throws IllegalArgumentException, IllegalAccessException;
}
