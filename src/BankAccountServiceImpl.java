package com.capgemini.bankapp.service.impl;

import java.beans.Transient;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.log4j.Logger;

import com.capgemini.bankaccount.dao.BankAccountDao;
import com.capgemini.bankaccount.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

/**
 * Implementation methods for BankAccountService class . Overided all the
 * unimplemented methods
 *
 */
public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao;
	//static final Logger logger = Logger.getLogger(BankAccountServiceImpl.class);

	public BankAccountServiceImpl(BankAccountDao bankAccountDao) {
		this.bankAccountDao=bankAccountDao;
	}

	@Override
	public double checkBal(long accountId) throws BankAccountNotFoundException {

		double balance = bankAccountDao.getBalance(accountId);
		if (balance >= 0)

			return balance;
		throw new BankAccountNotFoundException("BankAccount doesnt exist");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException();
		balance = balance + amount;
		bankAccountDao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;

	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {
	boolean result=bankAccountDao.deleteBankAccouont(accountId);
	if(result)
	{
		DbUtil.commit();
		return result;
	}
	else
		throw new BankAccountNotFoundException();
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result =bankAccountDao.addNewAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankDetails()  {
	
		 return bankAccountDao.findAllBankAccountDetails();
		
		
	}

	@Override
	public BankAccount findAccountById(long accountId) throws BankAccountNotFoundException {
		BankAccount account=bankAccountDao.findAccountById(accountId);
		if(account!=null)
			return account;
		  throw new BankAccountNotFoundException("Bank Account Doesnt exist");
		
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount)
			throws LowBalanceException, BankAccountNotFoundException {

		try {
			double newBalance = withdraw(fromAccount, amount);

			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		} catch (BankAccountNotFoundException e) {
			e.getMessage();
			DbUtil.rollback();
			throw e;
		}
	}

	public double withdrawForFundTransfer(long accountId, double amount)throws LowBalanceException, 	BankAccountNotFoundException {
	
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank Account not found exception");

		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
		
			return balance;
		} else

			throw new LowBalanceException("You dont have sufficient fund");
	}	

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, 	BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Bank Account not found exception");

		else if (balance - amount >= 0) {
			balance = balance - amount;
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else

			throw new LowBalanceException("You dont have sufficient fund");
	}

	@Override
	public boolean updateBalance(long accountId, double newBalance) {
		boolean result=bankAccountDao.updateBalance(accountId, newBalance);
		if(result)
			DbUtil.commit();
			return result;

	}

	@Override
	public boolean updateBankAccountDetails(long account_id , String customer_name , String 	account_type) throws BankAccountNotFoundException {
		boolean result=bankAccountDao.updateBankAccountDetails(account_id, customer_name, 			account_type);
		if(result)
			return result;
		  throw new BankAccountNotFoundException("Bank Account Doesnt exist");
		
	

	}

}
