package com.uitgis.jms.config;

import javax.transaction.TransactionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;

@Configuration
@EnableTransactionManagement
public class JtaConfig {
	public static final String BEAN_NAME_TRANSACTION_MANANGER = "transactionManager";

	@Bean(name = BEAN_NAME_TRANSACTION_MANANGER)
	public PlatformTransactionManager transactionManager() throws Throwable {
		TransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
		bitronixTransactionManager.setTransactionTimeout(10000);
		BitronixJtaPlatform.transaction = (BitronixTransactionManager) bitronixTransactionManager;
		BitronixJtaPlatform.transactionManager = bitronixTransactionManager;
		return new JtaTransactionManager(bitronixTransactionManager);
	}
}
