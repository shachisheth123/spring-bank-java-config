package com.capgemini.bankaccount.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankaccount.dao.BankAccountDao;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.util.DbUtil;

import java.sql.Connection;

/**
 * This is called when the service class is called all the queries are present
 * that retrieves data from database.
 *
 */
public class BankAccountDaoImpl implements BankAccountDao {
	
	Connection connection;

	public BankAccountDaoImpl(Connection connection){
		this.connection=connection;
	}

	@Override
	public double getBalance(long accountId) {

		String query = "SELECT account_balance FROM bankaccounts WHERE account_id=" + accountId;
		double balance = -1;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query);

				ResultSet result = statement.executeQuery()) {
			if (result.next()) {
				balance = result.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return balance;
	}

	@Override
	public boolean updateBalance(long accountId, double newBalance) {
		String query = "update bankaccounts SET account_balance=? where account_id=  ?";
		int result;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDouble(1, newBalance);
			statement.setLong(2, accountId);

			result = statement.executeUpdate();
			connection.commit();
			if (result == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteBankAccouont(long accountId) {
		String query = "delete from  bankaccounts where account_id=? ";
		int result;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, accountId);

			result = statement.executeUpdate();

			if (result == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return false;
	}

	@Override
	public boolean addNewAccount(BankAccount account) {
		String query = "INSERT INTO bankaccounts(customer_name , account_type , account_balance) values(? , ? , ?) ";
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, account.getAccountHolderName());
			statement.setString(2, account.getAccountType());
			statement.setDouble(3, account.getAccountBalance());

			int result = statement.executeUpdate();
			if (result == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<BankAccount> findAllBankAccountDetails() {
		String query = "SELECT * FROM bankaccounts";
		List<BankAccount> accounts = new ArrayList<>();
		Connection connection = DbUtil.getConnection();

		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			while (result.next()) {
				long accountId = result.getLong(1);
				String accountHolderName = result.getString(2);
				String accountType = result.getString(3);
				Double accountBalance = result.getDouble(4);

				BankAccount bankAccount = new BankAccount(accountId, accountHolderName, accountType, accountBalance);

				accounts.add(bankAccount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;

	}

	@Override
	public BankAccount findAccountById(long accountId) {
		String query = "SELECT * FROM bankaccounts WHERE account_id = " + accountId;
		BankAccount bankAccount = null;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {

			while (result.next())

				bankAccount = new BankAccount(result.getLong(1), result.getString(2), result.getString(3),
						result.getDouble(4));

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return bankAccount;

	}

	@Override
	public boolean updateBankAccountDetails(long account_id, String customer_name, String account_type) {
		String query = "UPDATE bankaccounts SET customer_name=? , account_type=? WHERE account_id= " + account_id;
		int result;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, customer_name);
			statement.setString(2, account_type);

			result = statement.executeUpdate();
			connection.commit();
			if (result == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}
