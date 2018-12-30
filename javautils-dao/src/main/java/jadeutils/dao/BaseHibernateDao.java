package jadeutils.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("deprecation")
public abstract class BaseHibernateDao<T> implements BaseDao<T> {

	private Class<T> entryClass;

	private HibernateDaoSupport hibernateDaoSupport = new HibernateDaoSupport() {
	};

	public void configHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateDaoSupport.setHibernateTemplate(hibernateTemplate);
	}

	public abstract void setHibernateTemplate(
			HibernateTemplate hibernateTemplate);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseHibernateDao() {
		/*
		 * get entry type
		 */
		Type genType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entryClass = (Class) params[0];
	}

	/*
	 * 格式化异常信息
	 * 
	 * @param e
	 * @param hql
	 * @param conditions
	 * @return
	 */
	public JadeDaoException formatQueryException(Exception e, String hql,
			Map<String, Object> conditions) {
		StringBuffer paras = new StringBuffer("{");
		for (String key : conditions.keySet()) {
			paras.append(key).append("=").append(conditions.get(key))
					.append(",");
		}
		paras.append("}");
		String[] att = { e.getMessage(), hql.toString(), paras.toString() };
		return new JadeDaoException("cms.err.003", att, e);
	}

	@Override
	public List<T> getAll() {
		return this.hibernateDaoSupport.getHibernateTemplate().loadAll(
				this.entryClass);
	}

	@Override
	public T getByIntId(Number id) {
		return this.hibernateDaoSupport.getHibernateTemplate().get(
				this.entryClass, id);
	}

	@Override
	public T getByStringId(String id) {
		return this.hibernateDaoSupport.getHibernateTemplate().get(
				this.entryClass, id);
	}

	@Override
	public void insert(Object model) {
		this.hibernateDaoSupport.getHibernateTemplate().save(model);
	}

	@Override
	public void update(Object model) {
		this.hibernateDaoSupport.getHibernateTemplate().update(model);
	}

	@Override
	public void insertOrUpdate(Object model) {
		this.hibernateDaoSupport.getHibernateTemplate().saveOrUpdate(model);
	}

	@Override
	public void delete(Object model) {
		this.hibernateDaoSupport.getHibernateTemplate().delete(model);
	}

	@SuppressWarnings("rawtypes")
	private void addParams(Query query, Map<String, Object> conditions) {
		for (String key : conditions.keySet()) {
			Object value = conditions.get(key);
			if (value instanceof Collection) {
				query.setParameterList(key, (Collection) value);
			} else {
				query.setParameter(key, value);
			}
		}
	}

	@Override
	public <K> List<K> findBySqlWithPagging(String count, String fields,
			String hql, Map<String, Object> conditions, PageSplitDto dto)
			throws JadeDaoException {
		int countRecNumber = this.getSqlResultCount(count + hql, conditions);
		dto.setCountRecNumber(countRecNumber);
		return this.findBySql(fields + hql, conditions, dto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <K> List<K> findBySql(String sql, Map<String, Object> conditions,
			PageSplitDto dto) throws JadeDaoException {
		Query query = null;
		List<K> list = new ArrayList<K>();
		try {
			query = SessionFactoryUtils.getSession(
					this.hibernateDaoSupport.getHibernateTemplate()
							.getSessionFactory(), true).createSQLQuery(sql);
			this.addParams(query, conditions);
			// 分页
			if (null != dto && dto.getPageSize() > 0 && dto.getCurrPageNo() > 0) {
				query.setFirstResult(PageSpliter.getOffSet(dto.getCurrPageNo(),
						dto.getPageSize()));
				query.setMaxResults(dto.getPageSize());
				PageSpliter.splitPage(dto);
			}
			list = query.list();
		} catch (Exception e) {
			throw formatQueryException(e, sql, conditions);
		}
		return list;
	}

	@Override
	public int getSqlResultCount(String sql, Map<String, Object> conditions)
			throws JadeDaoException {
		int result = 0;
		try {
			Query query = SessionFactoryUtils.getSession(
					this.hibernateDaoSupport.getHibernateTemplate()
							.getSessionFactory(), true).createSQLQuery(sql);
			this.addParams(query, conditions);
			result = ((BigInteger) query.list().get(0)).intValue();
		} catch (Exception e) {
			throw formatQueryException(e, sql, conditions);
		}
		return result;
	}

	@Override
	public <K> List<K> findByHqlWithPagging(String count, String fields,
			String hql, Map<String, Object> conditions, PageSplitDto dto)
			throws JadeDaoException {
		int countRecNumber = this.getHqlResultCount(count + hql, conditions);
		dto.setCountRecNumber(countRecNumber);
		return this.findByHql(fields + hql, conditions, dto);
	}

	/*
	 * 在分页查询前取得结果的总数
	 * 
	 * @param hql
	 * @param conditions
	 * @return
	 * @throws JadeDaoException
	 */

	@Override
	public int getHqlResultCount(String hql, Map<String, Object> conditions)
			throws JadeDaoException {
		Query query = null;
		int result = 0;
		try {
			query = SessionFactoryUtils.getSession(
					this.hibernateDaoSupport.getHibernateTemplate()
							.getSessionFactory(), true).createQuery(hql);
			this.addParams(query, conditions);
			result = ((Long) query.list().get(0)).intValue();
		} catch (Exception e) {
			throw formatQueryException(e, hql, conditions);
		}
		return result;
	}

	/*
	 * 根据分页大小查询
	 * 
	 * @param hql
	 *            查询的hql
	 * @param conditions
	 *            查询条件
	 * @param dto
	 *            保存分页信息的数据结构
	 * @return
	 * @throws JadeDaoException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <K> List<K> findByHql(String hql, Map<String, Object> conditions,
			PageSplitDto dto) throws JadeDaoException {
		Query query = null;
		List<K> list = new ArrayList<K>();
		try {
			query = SessionFactoryUtils.getSession(
					this.hibernateDaoSupport.getHibernateTemplate()
							.getSessionFactory(), true).createQuery(hql);
			this.addParams(query, conditions);
			// 分页
			if (null != dto && dto.getPageSize() > 0 && dto.getCurrPageNo() > 0) {
				query.setFirstResult(PageSpliter.getOffSet(dto.getCurrPageNo(),
						dto.getPageSize()));
				query.setMaxResults(dto.getPageSize());
				PageSpliter.splitPage(dto);
			}
			list = query.list();
		} catch (Exception e) {
			throw formatQueryException(e, hql, conditions);
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T test() {
		String table = entryClass.getName();
		Query query = this.hibernateDaoSupport.getHibernateTemplate()
				.getSessionFactory().getCurrentSession()
				.createQuery("from " + table);
		query.setMaxResults(1);
		List<T> list = query.list();
		return list.size() > 0 ? list.get(0) : null;
	}

}
