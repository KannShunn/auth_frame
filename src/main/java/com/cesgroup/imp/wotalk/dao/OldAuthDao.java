package com.cesgroup.imp.wotalk.dao;


import com.cesgroup.imp.wotalk.entity.OldOrg;
import com.cesgroup.imp.wotalk.entity.OldOrgUser;
import com.cesgroup.imp.wotalk.entity.OldUser;

import java.sql.SQLException;
import java.util.List;

public interface OldAuthDao {

	List<OldOrg> getOrgList() throws SQLException;

	List<OldOrgUser> getOrgUserList() throws SQLException;

	List<OldUser> getUserList() throws SQLException;

}
