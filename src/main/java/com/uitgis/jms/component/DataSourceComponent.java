package com.uitgis.jms.component;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uitgis.jms.config.dbprop.AbsDbProps;
import com.uitgis.jms.def.Profile;

import bitronix.tm.resource.jdbc.PoolingDataSource;

@Component
public class DataSourceComponent {
	@Autowired
	private PropsComponent propsComponent;

	public PoolingDataSource getXADataSource(AbsDbProps dbProps) {
		System.out.println(">>>>>>>>: " + dbProps.toString());
		PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
		bitronixDataSourceBean.setMaxPoolSize(5);
		bitronixDataSourceBean.setAllowLocalTransactions(true); // using for postgresql

		Profile activeProfile = propsComponent.getActiveProfile();
		if (activeProfile == Profile.MYSQL)
			bitronixDataSourceBean.setClassName("com.mysql.cj.jdbc.MysqlXADataSource");
		else
			bitronixDataSourceBean.setClassName("org.postgresql.xa.PGXADataSource");

		Properties properties = new Properties();
		properties.put("user", dbProps.getUsername());
		properties.put("password", dbProps.getPassword());
		properties.put("url", dbProps.getJdbcUrl());
		bitronixDataSourceBean.setDriverProperties(properties);
		return bitronixDataSourceBean;
	}
}
