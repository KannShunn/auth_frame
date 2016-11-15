package com.cesgroup.login.shiro;

import com.cesgroup.auth.resource.entity.Resource;
import com.cesgroup.auth.user.entity.User;
import com.cesgroup.auth.user.service.UserService;
import com.cesgroup.common.global.Constants;
import com.google.common.base.Objects;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016-7-20.
 */
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;



    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        User user = userService.getUserByLoginName(token.getUsername());
        if (user != null)
        {
            if (Constants.User.LOCKED.equals(user.getFlagAction())){
                throw new DisabledAccountException();
            }
            //设置盐值, 盐值也是从数据表中获取的
            ByteSource credentialsSalt = null;
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName(), user.getUnitId(), user.getIsAdmin(),user.getUrlPath()), user.getPassword(), credentialsSalt, getName());
        }
        else
        {
            throw new UnknownAccountException();
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //从Principal中获取用户的登录信息
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if(Constants.User.SUPERADMIN_ID.equals(shiroUser.getId())){//超级管理员 始终拥有所有权限
            info.addStringPermission("*");
            return info;
        }
        List<Resource> resourceList = userService.getResourcesByUserId(shiroUser.getId(),shiroUser.getUnitId());
        if (resourceList != null)
        {
            for (Resource resource : resourceList)
            {
                info.addStringPermission(resource.getResUrl());
            }
        }
        return info;
    }

    public void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
    }
    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable
    {
        private static final long serialVersionUID = -1373760761780840081L;
        private String id; //用户id
        private String loginName; //用户登录名
        private String name; //用户名
        private String unitId; //单位id
        private String isAdmin; //是否管理员
        private String urlPath; //头像地址


        public ShiroUser(String id, String loginName, String name, String unitId, String isAdmin,String urlPath) {
            this.id = id;
            this.loginName = loginName;
            this.name = name;
            this.unitId = unitId;
            this.isAdmin = isAdmin;
            this.urlPath = urlPath;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(String isAdmin) {
            this.isAdmin = isAdmin;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString()
        {
            return loginName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode()
        {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null)
            {
                if (other.loginName != null)
                {
                    return false;
                }
            }
            else if (!loginName.equals(other.loginName))
            {
                return false;
            }
            return true;
        }
    }
}
