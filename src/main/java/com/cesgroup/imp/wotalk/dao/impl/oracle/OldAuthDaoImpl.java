package com.cesgroup.imp.wotalk.dao.impl.oracle;


import com.cesgroup.imp.wotalk.dao.OldAuthDao;
import com.cesgroup.imp.wotalk.entity.OldOrg;
import com.cesgroup.imp.wotalk.entity.OldOrgUser;
import com.cesgroup.imp.wotalk.entity.OldUser;
import com.cesgroup.imp.wotalk.factory.QueryRunnerFactory;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class OldAuthDaoImpl implements OldAuthDao {

	
	@Override
	public List<OldOrg> getOrgList() throws SQLException {
		QueryRunner qr = QueryRunnerFactory.createQueryRunner();

		String sql = "select r.child_id as organize_id, r.organize_id as parent_id, o.organize_name, '2' as organize_type_name,o.show_order " +
					"from t_sys_organize_relation r " +
					"inner join t_sys_organize o " +
					"on r.child_id = o.organize_id ";

		List<OldOrg> oldOrgs = qr.query(sql, new BeanListHandler<OldOrg>(OldOrg.class, new BasicRowProcessor(new GenerousBeanProcessor())));
		return oldOrgs;
	}

	@Override
	public List<OldOrgUser> getOrgUserList() throws SQLException {
		QueryRunner qr = QueryRunnerFactory.createQueryRunner();

		String sql1 = "select u.user_id as user_id, r.organize_id as organize_id " +
					"from t_sys_organize_relation r " +
					"inner join t_sys_user u " +
					"on r.child_id = u.user_id ";
		
		List<OldOrgUser> oldOrgUsers = qr.query(sql1, new BeanListHandler<OldOrgUser>(OldOrgUser.class, new BasicRowProcessor(new GenerousBeanProcessor())));
		
		
		String sql2 = "select u.user_id, '1' as organize_id " + 
					" from t_sys_user u " +
					" where u.user_id not in ( select r.child_id from t_sys_organize_relation r where r.child_type_id = '0000') " +
					" and user_id != 1 " +
					" order by u.show_order";
		
		List<OldOrgUser> list = qr.query(sql2, new BeanListHandler<OldOrgUser>(OldOrgUser.class, new BasicRowProcessor(new GenerousBeanProcessor())));
		
		oldOrgUsers.addAll(list);
		return oldOrgUsers;
	}
	
	@Override
	public List<OldUser> getUserList() throws SQLException {
		QueryRunner qr = QueryRunnerFactory.createQueryRunner();

		String sql1 = "select u.user_id, u.login_name, u.user_name,u.show_order,u.type,u.phone_num,u.user_cryptogram as password " +
					"from t_sys_user u  " +
					"inner join t_sys_organize_relation r " +
					"on u.user_id = r.child_id " +
					"where user_id != 1 " +
					"and r.child_type_id = '0000' " +
					"order by u.show_order";
		List<OldUser> oldUsers = qr.query(sql1, new BeanListHandler<OldUser>(OldUser.class, new BasicRowProcessor(new GenerousBeanProcessor())));
		
		String sql2 = "select u.user_id, u.login_name, u.user_name,u.show_order,u.type,u.phone_num,u.user_cryptogram as password "+
					"from t_sys_user u "+
					"where u.user_id not in ( select r.child_id from t_sys_organize_relation r where r.child_type_id = '0000') "+
					"and user_id != 1 "+
					"order by u.show_order";
		
		List<OldUser> list = qr.query(sql2, new BeanListHandler<OldUser>(OldUser.class, new BasicRowProcessor(new GenerousBeanProcessor())));
		
		oldUsers.addAll(list);
		
		return oldUsers;
	}
}