package com.cesgroup.imp.wotalk.factory;


import com.cesgroup.common.listener.SystemInitListener;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.tomcat.jdbc.pool.DataSource;

public abstract class QueryRunnerFactory {

	private static QueryRunner qr ;

	public static QueryRunner createQueryRunner(){
		DataSource dataSource = (DataSource) SystemInitListener.getBean("dataSource-wotalk");

		if(qr == null){
			qr = new QueryRunner(dataSource);
		}
		return qr;
	}

}
