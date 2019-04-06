package com.capgemini.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.capgemini.bankaccount.dao.*;
import com.capgemini.bankapp.service.*;
import com.capgemini.bankapp.util.*;
import java.sql.Connection;
import com.capgemini.bankaccount.dao.impl.*;
import com.capgemini.bankapp.service.impl.*;

@Configuration
public class BankAppConfiguration {


	 @Bean
   	 public BankAccountDao getDetails()
  	  {
		BankAccountDaoImpl bankAccountDaoImpl=new BankAccountDaoImpl(connection());
        	return  bankAccountDaoImpl;
    	   } 

	 @Bean(name="bankAccountService")
   	 public BankAccountService getDetail()
  	  {
        	BankAccountServiceImpl bankAccountServiceImpl=new BankAccountServiceImpl(getDetails());
		return bankAccountServiceImpl;
    	   } 

	 @Bean(name="connection")
   	 public Connection connection()
  	  {
        	//return new BankAccountServiceImpl();
		Connection connection=DbUtil.getConnection();
		return connection;
    	   } 


}