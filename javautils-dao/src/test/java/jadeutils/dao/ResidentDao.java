/*
 * 
 */
package jadeutils.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

/*
 * @author morgan
 * 
 */

@SuppressWarnings("deprecation")
@Component("residentDao")
public class ResidentDao extends BaseHibernateDao<Resident> {

	@Override
	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		System.out.println(hibernateTemplate);
		this.configHibernateTemplate(hibernateTemplate);
	}

	public List<Resident> findByHql(QueryConditions cdts)
			throws JadeDaoException {
		String count = "select count(r) ";
		String fields = "select r ";
		String sql = "from Resident r ";
		cdts.configure("r", null);
		Map<String, Object> conditions = cdts.generageParamMap();
		sql = sql + cdts.toString();
		List<Resident> resultList = this.findByHqlWithPagging(count, fields,
				sql, conditions, cdts);
		return resultList;
	}

	public List<Resident> findBySql(QueryConditions cdts)
			throws JadeDaoException {
		List<Resident> resultList = new ArrayList<Resident>();
		String count = "select count(id) ";
		String fields = "select id, userName, password, nick, status, createTime ";
		String sql = "from resident r ";
		cdts.configure("r", null);
		Map<String, Object> conditions = cdts.generageParamMap();
		sql = sql + cdts.toString();
		List<Object[]> recs = this.findBySqlWithPagging(count, fields, sql,
				conditions, cdts);
		if (null != recs && recs.size() > 0) {
			for (Object[] rec : recs) {
				Resident r = new Resident();
				int c = 0;
				r.setId(null == rec[c] ? null : (String) rec[c]);
				c++;
				r.setUserName(null == rec[c] ? null : (String) rec[c]);
				c++;
				r.setPassword(null == rec[c] ? null : (String) rec[c]);
				c++;
				r.setNick(null == rec[c] ? null : (String) rec[c]);
				c++;
				r.setStatus(null == rec[c] ? null : (String) rec[c]);
				c++;
				resultList.add(r);
			}
		}
		return resultList;
	}
}
