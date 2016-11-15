package com.cesgroup.imp.wotalk.factory;


import com.cesgroup.imp.wotalk.dao.OldAuthDao;
import com.cesgroup.imp.wotalk.dao.impl.oracle.OldAuthDaoImpl;

public abstract class DaoFactory {

	private static OldAuthDao oldAuthDao;


	public static OldAuthDao createOldAuthDao(){
		if(oldAuthDao == null){
			oldAuthDao = new OldAuthDaoImpl();
		}
		return oldAuthDao;
	}
	
}
