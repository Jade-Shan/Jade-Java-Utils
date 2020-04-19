package net.jadedungeon.javautil.mongo;

import java.util.List;


public class UserDao extends BaseMongoDao<User> {

	public UserDao(List<MongoServer> serverList) {
		super(serverList);
	}

}
