package com.capgemini.bankaccount.dao;

import java.util.List;

import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountDao {
	
	public double getBalance(long accountId);
	public boolean updateBalance(long accountId , double newBalance);
	public boolean deleteBankAccouont(long accountId);
	public boolean addNewAccount(BankAccount account);
	public List<BankAccount> findAllBankAccountDetails();
	public BankAccount findAccountById(long accountId);
	public boolean updateBankAccountDetails(long account_id , String customer_name , 	String account_type);

	
}
