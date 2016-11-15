package com.cesgroup.login.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cesgroup.core.dao.BaseDao;
import com.cesgroup.login.entity.LoginFailedLog;

public interface LoginFailedLogDao extends BaseDao<LoginFailedLog>
{

	@Query(value = "select l from LoginFailedLog l where l.name= :loginName")
	LoginFailedLog getLogByLoginName(@Param("loginName") String loginName);

	@Query(value = "select count(l.id) from LoginFailedLog l where l.name= :loginName")
	Integer getCountByLoginName(@Param("loginName") String loginName);

	@Query(value = "delete from LoginFailedLog l where l.name= :loginName")
	@Modifying
	void deleteByLoginName(@Param("loginName") String loginName);
}
