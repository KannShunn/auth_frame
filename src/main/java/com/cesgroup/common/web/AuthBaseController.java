package com.cesgroup.common.web;

import com.cesgroup.auth.user.entity.User;
import com.cesgroup.core.entity.BaseEntity;
import com.cesgroup.core.entity.TailEntity;
import com.cesgroup.core.service.BaseService;
import com.cesgroup.core.utils.DateUtil;
import com.cesgroup.core.web.BaseController;
import com.cesgroup.login.shiro.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;

public abstract class AuthBaseController<T extends BaseEntity<String>,Service extends BaseService<T>> extends BaseController<T, Service> {


	protected ShiroDbRealm.ShiroUser getCurrentUser()
	{
		ShiroDbRealm.ShiroUser currentUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return currentUser;
	}
	
	
	/** 设置创建人,创建时间等尾部信息 */
	protected void setCreateUser(T entity){
		if(entity instanceof TailEntity){
			TailEntity tailEntity = (TailEntity) entity;
			ShiroDbRealm.ShiroUser user = getCurrentUser();
			tailEntity.setCreateUserId(user.getId());
			tailEntity.setCreateUserName(user.getName());
			tailEntity.setCreateTime(DateUtil.getCurrentDateTime());
		}
	}
	
	/** 设置修改人,修改时间等尾部信息 */
	protected void setUpdateUser(T entity){
		if(entity instanceof TailEntity){
			TailEntity tailEntity = (TailEntity) entity;
			ShiroDbRealm.ShiroUser user = getCurrentUser();
			tailEntity.setUpdateUserId(user.getId());
			tailEntity.setUpdateUserName(user.getName());
			tailEntity.setUpdateTime(DateUtil.getCurrentDateTime());
		}
	}
}
