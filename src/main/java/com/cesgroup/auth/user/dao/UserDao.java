package com.cesgroup.auth.user.dao;

import java.util.List;

import com.cesgroup.auth.resource.entity.Resource;
import com.cesgroup.common.global.Constants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cesgroup.auth.user.entity.User;
import com.cesgroup.auth.user.vo.UserGridVo;
import com.cesgroup.core.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关dao
 * 
 * @author niklaus
 *
 */
@Transactional
public interface UserDao extends BaseDao<User>
{
	
	/**
	 * 根据登录名查询有效的用户对象
	 */
	@Query("select u from User u where upper(u.loginName) = upper(:loginName) and u.status = '0'")
	public User getByLoginName(@Param("loginName") String loginName);

	@Modifying
	@Query("update User set status = '"+ Constants.User.OFFJOB+"',offJobDate = :offJobDate where id = :userId")
	public void deleteByUserId(@Param("userId") String userId,@Param("offJobDate") String offJobDate);

	@Query("select u from User u where exists (select ru.userId from RoleUser ru where ru.userId = u.id and ru.roleId = ?1)")
	public List<User> getUsersOfRole(String roleId);
}
