package com.cesgroup.imp.wotalk.service.impl;

import com.cesgroup.auth.org.entity.Org;
import com.cesgroup.auth.user.dao.OrgUserDao;
import com.cesgroup.auth.user.dao.RoleUserDao;
import com.cesgroup.auth.user.entity.OrgUser;
import com.cesgroup.auth.user.entity.RoleUser;
import com.cesgroup.auth.user.entity.User;
import com.cesgroup.common.global.Constants;
import com.cesgroup.common.listener.SystemInitListener;
import com.cesgroup.core.service.impl.NonEntityServiceImpl;
import com.cesgroup.core.utils.PinYinUtil;
import com.cesgroup.core.utils.Util;
import com.cesgroup.imp.wotalk.dao.OldAuthDao;
import com.cesgroup.imp.wotalk.entity.OldOrg;
import com.cesgroup.imp.wotalk.entity.OldOrgUser;
import com.cesgroup.imp.wotalk.entity.OldUser;
import com.cesgroup.imp.wotalk.factory.DaoFactory;
import com.cesgroup.imp.wotalk.service.ImportAuthService;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImportAuthServiceImpl extends NonEntityServiceImpl implements ImportAuthService {

	private static final Integer OLD_ZXFZ_ID = -1;
	private static final Integer OLD_ZXDZ_ID = -5;
	private static final Integer OLD_JX_ID = -10;
	private static final String ZXXX_ID = "2";
	private static final String NEW_ZXFZ_ID = "3";
	private static final String NEW_ZXDZ_ID = "4";
	private static final String NEW_JX_ID = "5";


	@Override
	public void oneKeyImportAuthData(){
		System.out.println("开始从wotalk中获取数据..");
		this.clearAllData();
		this.importOrg();
		this.importOrgUser();
		this.importUser();
		System.out.println("导入完毕");
	}

	public void importOrg(){
		OldAuthDao oldAuthDao = DaoFactory.createOldAuthDao();

		try {
			List<OldOrg> oldOrgs = oldAuthDao.getOrgList();
			System.out.println("正在导入组织");
			int maxProgress = oldOrgs.size();
			int currentProgress = 1;

			Org zxxx = new Org();
			zxxx.setId(ZXXX_ID);
			zxxx.setpId(Constants.Org.TOP);
			zxxx.setName("中信信息发展股份有限公司");
			zxxx.setShowOrder(1L);
			zxxx.setOrganizeCode("zxxxfzgfyxgs");
			zxxx.setAbbreviation("zxxxfzgfyxgs");
			zxxx.setOrganizeTypeId(Long.valueOf(Constants.Org.UNIT_TYPE));
			zxxx.setStatus(Constants.Org.IN_USE);
			zxxx.setIsSystem(Constants.Common.NO);
			zxxx.setUnitId(ZXXX_ID);

			saveNewOrg(zxxx);

			Org xxfz = new Org();
			xxfz.setId(NEW_ZXFZ_ID);
			xxfz.setpId(ZXXX_ID);
			xxfz.setName("信息发展");
			xxfz.setShowOrder(2L);
			xxfz.setOrganizeCode("zxfz");
			xxfz.setAbbreviation("xxfz");
			xxfz.setOrganizeTypeId(Long.valueOf(Constants.Org.DEPARTMENT_TYPE));
			xxfz.setStatus(Constants.Org.IN_USE);
			xxfz.setIsSystem(Constants.Common.NO);
			xxfz.setUnitId(ZXXX_ID);

			saveNewOrg(xxfz);

			Org zxdz = new Org();
			zxdz.setId(NEW_ZXDZ_ID);
			zxdz.setpId(ZXXX_ID);
			zxdz.setName("中信电子");
			zxdz.setShowOrder(3L);
			zxdz.setOrganizeCode("zxdz");
			zxdz.setAbbreviation("zxdz");
			zxdz.setOrganizeTypeId(Long.valueOf(Constants.Org.DEPARTMENT_TYPE));
			zxdz.setStatus(Constants.Org.IN_USE);
			zxdz.setIsSystem(Constants.Common.NO);
			zxdz.setUnitId(ZXXX_ID);

			saveNewOrg(zxdz);

			Org jx = new Org();
			jx.setId(NEW_JX_ID);
			jx.setpId(ZXXX_ID);
			jx.setName("上海金鑫系统计算机工程有限公司");
			jx.setShowOrder(4L);
			jx.setOrganizeCode("shjxxtjsjgcyxgs");
			jx.setAbbreviation("shjxxtjsjgcyxgs");
			jx.setOrganizeTypeId(Long.valueOf(Constants.Org.DEPARTMENT_TYPE));
			jx.setStatus(Constants.Org.IN_USE);
			jx.setIsSystem(Constants.Common.NO);
			jx.setUnitId(ZXXX_ID);

			saveNewOrg(jx);

			for (OldOrg oldOrg : oldOrgs) {
				Org newOrg = convert(null,oldOrg);

				saveNewOrg(newOrg);
                currentProgress++;
                if(maxProgress % currentProgress == 10){
                    System.out.println("组织导入: " + Math.round( currentProgress * 100.0 / maxProgress ) + "%");
                }
            }
			System.out.println("导入组织完成");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void saveNewOrg(Org newOrg){
		String sql = "insert into t_auth_org(organize_id,parent_id,organize_name,show_order,abbreviation,organize_code,organize_type_id,status,unit_id,is_system) values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1,newOrg.getId());
		query.setParameter(2,newOrg.getpId());
		query.setParameter(3,newOrg.getName());
		query.setParameter(4,newOrg.getShowOrder());
		query.setParameter(5,newOrg.getAbbreviation());
		query.setParameter(6,newOrg.getOrganizeCode());
		query.setParameter(7,newOrg.getOrganizeTypeId());
		query.setParameter(8,newOrg.getStatus());
		query.setParameter(9,newOrg.getUnitId());
		query.setParameter(10,newOrg.getIsSystem());


		query.executeUpdate();

	}

	private Org convert(Org newOrg, OldOrg oldOrg){
		if(newOrg == null){
			newOrg = new Org();
		}
		if(oldOrg != null){
			if(oldOrg.getParentId() == OLD_ZXFZ_ID){
				newOrg.setpId(NEW_ZXFZ_ID);
			}
			else if(oldOrg.getParentId() == OLD_ZXDZ_ID){
				newOrg.setpId(NEW_ZXDZ_ID);
			}
			else if(oldOrg.getParentId() == OLD_JX_ID){
				newOrg.setpId(NEW_JX_ID);
			}
			else{
				newOrg.setpId(oldOrg.getParentId() + "");
			}
			newOrg.setId(oldOrg.getOrganizeId() + "");
			newOrg.setName(oldOrg.getOrganizeName());
			newOrg.setOrganizeCode(PinYinUtil.getFirstSpell(oldOrg.getOrganizeName()));
			newOrg.setOrganizeTypeId(Long.valueOf(Constants.Org.DEPARTMENT_TYPE));
			newOrg.setShowOrder(oldOrg.getShowOrder() == null ? 0L : Long.valueOf(oldOrg.getShowOrder()));
			newOrg.setStatus(Constants.Org.IN_USE);
			newOrg.setIsSystem(Constants.Common.NO);
			newOrg.setUnitId(ZXXX_ID);

			return newOrg;
		}
		return null;
	}

	private OrgUser convert(OrgUser newOrgUser, OldOrgUser oldOrgUser){
		if(newOrgUser == null){
			newOrgUser = new OrgUser();
		}
		if(oldOrgUser != null){
			newOrgUser.setUserId(oldOrgUser.getUserId() + "");
			newOrgUser.setOrganizeId(oldOrgUser.getOrganizeId() + "");
			newOrgUser.setUserType(Constants.User.FULLTIME);
			newOrgUser.setUnitId(ZXXX_ID);

			return newOrgUser;
		}
		return null;
	}


	public void importOrgUser(){
		OldAuthDao oldAuthDao = DaoFactory.createOldAuthDao();

		try {
			List<OldOrgUser> orgUsers = oldAuthDao.getOrgUserList();
			System.out.println("正在导入组织用户");
			OrgUserDao orgUserDao = SystemInitListener.getBean(OrgUserDao.class);
			int maxProgress = orgUsers.size();
			int currentProgress = 1;
			for (OldOrgUser oldOrgUser : orgUsers) {
				OrgUser newOrgUser = convert(null, oldOrgUser);
				orgUserDao.save(newOrgUser);

                currentProgress++;
                if(maxProgress % currentProgress == 10){
                    System.out.println("组织用户导入: " + Math.round( currentProgress * 100.0 / maxProgress ) + "%");
                }
            }
			System.out.println("导入组织用户完成");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public void importUser(){

		OldAuthDao oldAuthDao = DaoFactory.createOldAuthDao();

		RoleUserDao roleUserDao = SystemInitListener.getBean(RoleUserDao.class);
		OrgUserDao orgUserDao = SystemInitListener.getBean(OrgUserDao.class);
		try {
			List<OldUser> oldUsers = oldAuthDao.getUserList();
			System.out.println("正在导入用户");
			int maxProgress = oldUsers.size();
			int currentProgress = 1;

			for (OldUser oldUser : oldUsers) {
				User newUser = convert(null,oldUser);
                saveNewUser(newUser);

				//为用户绑定默认角色
				RoleUser defaultRoleUser = new RoleUser();

				defaultRoleUser.setRoleId(Constants.Role.DEFAULT_ROLE);
				defaultRoleUser.setUserId(newUser.getId());
				defaultRoleUser.setUnitId(ZXXX_ID);
				defaultRoleUser.setIsTempAccredit(Constants.User.IS_TEMP_ACCREDIT_NO);
				roleUserDao.save(defaultRoleUser);

                currentProgress++;
                if(maxProgress % currentProgress == 10){
                    System.out.println("用户导入: " + Math.round( currentProgress * 100.0 / maxProgress ) + "%");
                }
            }

			//创建中信管理员
			User adminUser = new User();
			adminUser.setId("99");
			adminUser.setLoginName("zxxx");
			adminUser.setPassword(Util.MD5Encrypt(Constants.User.DEFAULT_PASSWORD));
			adminUser.setName("中信信息管理员");
			adminUser.setFlagAction(Constants.User.UNLOCK);
			adminUser.setStatus(Constants.User.ONJOB);
			adminUser.setUnitId(ZXXX_ID);
			adminUser.setIsAdmin(Constants.Common.YES);
			adminUser.setIsSystem(Constants.Common.NO);
			adminUser.setEmail("");
			adminUser.setShowOrder(0L);
			adminUser.setTelephone("");
			saveNewUser(adminUser);

			//为管理员绑定组织
			OrgUser adminOrgUser = new OrgUser();
			adminOrgUser.setUserId(adminUser.getId());
			adminOrgUser.setOrganizeId(ZXXX_ID);
			adminOrgUser.setUserType(Constants.User.FULLTIME);
			adminOrgUser.setUnitId(ZXXX_ID);
			orgUserDao.save(adminOrgUser);

			//为管理员绑定默认角色
			RoleUser defaultRoleUser = new RoleUser();

			defaultRoleUser.setRoleId(Constants.Role.DEFAULT_ROLE);
			defaultRoleUser.setUserId(adminUser.getId());
			defaultRoleUser.setUnitId(Constants.User.SUPER_UNITID);
			defaultRoleUser.setIsTempAccredit(Constants.User.IS_TEMP_ACCREDIT_NO);
			roleUserDao.save(defaultRoleUser);

			//给管理员授予单位管理员角色
			RoleUser unitRoleUser = new RoleUser();

			unitRoleUser.setRoleId(Constants.Role.UNITSYS_ROLE);
			unitRoleUser.setUserId("99");
			unitRoleUser.setUnitId(Constants.User.SUPER_UNITID);
			unitRoleUser.setIsTempAccredit(Constants.User.IS_TEMP_ACCREDIT_NO);
			roleUserDao.save(unitRoleUser);

			System.out.println("导入用户完成");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private User convert(User newUser, OldUser oldUser){
		if(newUser == null){
			newUser = new User();
		}
		if(oldUser != null){

			newUser.setId(oldUser.getUserId() + "");
			newUser.setLoginName(oldUser.getLoginName());
			newUser.setPassword(oldUser.getPassword());
			newUser.setName(oldUser.getUserName());
			newUser.setShowOrder(oldUser.getShowOrder() == null ? 0L : Long.valueOf(oldUser.getShowOrder()));
			newUser.setMobile(oldUser.getPhoneNum());
			newUser.setEmail(oldUser.getType());
			newUser.setFlagAction(Constants.User.UNLOCK);
			newUser.setStatus(Constants.User.ONJOB);
			newUser.setUnitId(ZXXX_ID);
			newUser.setIsAdmin(Constants.Common.NO);
			newUser.setIsSystem(Constants.Common.NO);


			return newUser;
		}
		return null;
	}

	private void saveNewUser(User newUser){
		String sql = "insert into t_auth_user(user_id,login_name,password,user_name,flag_action,email,mobile,show_order,status,unit_id,is_admin,is_system) values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1,newUser.getId());
		query.setParameter(2,newUser.getLoginName());
		query.setParameter(3,newUser.getPassword());
		query.setParameter(4,newUser.getName());
		query.setParameter(5,newUser.getFlagAction());
		query.setParameter(6,newUser.getEmail());
		query.setParameter(7,newUser.getMobile());
		query.setParameter(8,newUser.getShowOrder());
		query.setParameter(9,newUser.getStatus());
		query.setParameter(10,newUser.getUnitId());
		query.setParameter(11,newUser.getIsAdmin());
		query.setParameter(12,newUser.getIsSystem());


		query.executeUpdate();

	}

	public void clearAllData(){

		List<String> sqls = new ArrayList<String>();
		String sql1 = "delete from t_auth_org where organize_id <> '"+Constants.Org.TOP+"'";
		sqls.add(sql1);

		String sql2 = "delete from t_auth_org_user ";
		sqls.add(sql2);

		String sql3 = "delete from t_auth_user where user_id <> '"+Constants.User.SUPERADMIN_ID+"'";
		sqls.add(sql3);

		String sql4 = "delete from t_auth_role_user where user_id <> '"+Constants.User.SUPERADMIN_ID+"'";
		sqls.add(sql4);

		Query query = null;
		for (int i = 0; i < sqls.size(); i++) {
			query = entityManager.createNativeQuery(sqls.get(i));
			query.executeUpdate();
		}

	}
}
