package com.capgemini.bankapp.service;

import java.util.List;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;

/**
 * bankAccountServiceImpl implements BabkAccountService inteface and provides
 * the implementation for the methods
 *
 */
public interface BankAccountService {

	public double checkBal(long accountId) throws BankAccountNotFoundException;

	public double withdraw(long accountId, double amount) throws 		LowBalanceException,BankAccountNotFoundException;

	public double deposit(long accountId, double amount) throws BankAccountNotFoundException;

	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException;

	public boolean addNewBankAccount(BankAccount account);

	public double fundTransfer(long fromAccount, long toAccount, double amount) throws 		LowBalanceException ,BankAccountNotFoundException;

	public List<BankAccount> findAllBankDetails() ;

	public BankAccount findAccountById(long accountId) throws BankAccountNotFoundException;
	
	public boolean updateBalance(long accountId , double newBalance);
	
	public boolean updateBankAccountDetails(long account_id ,String customer_name ,String account_type) 		throws BankAccountNotFoundException;




}
