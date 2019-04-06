package com.capgemini.bankapp.client;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import com.capgemini.spring.configuration.*;

/**
 * Client code for running of the application from where we call the methods
 * from bankServiceImpl
 *
 */
public class BankAppClient {

	//static final Logger logger = Logger.getLogger(BankAppClient.class);

	public static void main(String[] args) throws BankAccountNotFoundException {

		int choice;
		String accountHolderName;
		String accountType;
		double accountBalance;
		double amount;
		long accountId;
		long fromAccountID;
		long toAccountId;
			ApplicationContext context = new AnnotationConfigApplicationContext (BankAppConfiguration.class);
		BankAccountService bankService=context.getBean(BankAccountService.class);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.println("1. Add New BankAccount\n2. Withdraw\n3. Deposit\n4. Fund 				Transfer");
				System.out.println("5. Delete BankAccount\n6. Display All BankAccount 				Details");

				System.out.println(
						"7. Search BankAccount\n8. Check Balance\n 9.Update Balance 				\n  10. Update bank account details \n 11. Exit\n");

				System.out.print("Please enter your choice = ");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
				case 1:
					System.out.println("Enter account holder name");
					accountHolderName = reader.readLine();
					System.out.println("Enter account account Type");
					accountType = reader.readLine();
					System.out.println("Enter account balance");
					accountBalance = Double.parseDouble(reader.readLine());

					BankAccount account = new BankAccount(accountHolderName, 					accountType, accountBalance);

					if (bankService.addNewBankAccount(account))
						System.out.println("Account created successfully..");
					else
						System.out.println("Failed to create account");

					break;

				case 2:
					System.out.println("Enter the account id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("your current balance is" + bankService.checkBal					(accountId));
					System.out.println("enter the amount to withdraw");
					amount = Double.parseDouble(reader.readLine());
					System.out.println("Balance after withdraw" + bankService.checkBal						(accountId));

					try {
						accountBalance = bankService.withdraw(accountId, amount);
						System.out.println("Balance after withdraw" + 					accountBalance);
					} catch (LowBalanceException e) {

						 e.getMessage();
					
					}
					break;

				case 3:
					System.out.println("Enter the account id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("your current balance is" + bankService.checkBal					(accountId));
					System.out.println("enter the amount to be deposited");
					amount = Double.parseDouble(reader.readLine());

					accountBalance = bankService.deposit(accountId, amount);

					System.out.println("Your balance after deposit is " + 						bankService.checkBal(accountId));
					break;

				case 4:
					System.out.println("Enter the account from you have to transfer 						money");
					fromAccountID = Long.parseLong(reader.readLine());
					System.out.println("Enter the account to you have to transfer 					money");
					toAccountId = Long.parseLong(reader.readLine());
					System.out.println("enter the amount to be transfered :");
					amount = Double.parseDouble(reader.readLine());

					try {
						bankService.fundTransfer(fromAccountID, toAccountId, 					amount);
					} catch (LowBalanceException e) {

						 e.printStackTrace();
						
					}
					System.out.println("Balance after fund transfer is" + 								bankService.checkBal(toAccountId));
					break;

				case 5:
					System.out.println("enter account Id to be deleted");
					accountId = Long.parseLong(reader.readLine());
					bankService.deleteBankAccount(accountId);
					break;

				case 6:
					System.out.println("All the details are :" + 									bankService.findAllBankDetails());
					break;

				case 7:
					System.out.println("Enter account ID");
					accountId = Long.parseLong(reader.readLine());

					System.out.println(bankService.findAccountById(accountId));
					break;

				case 8:
					System.out.println("Enter the account id for balance");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Your balance is" + bankService.checkBal					(accountId));

				case 9:
					System.out.println("Enter the account id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the amount for updation");
					amount = Double.parseDouble(reader.readLine());
					if (bankService.updateBalance(accountId, amount))
						System.out.println(" Balance Updated");
					else
						System.out.println("Balance cannot be updated");
					break;

				case 10:
					System.out.println("Enter the account id");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the customer name you want to update");
					accountHolderName = reader.readLine();
					System.out.println("Enter the account type for updation");
					accountType = reader.readLine();
					if (bankService.updateBankAccountDetails(accountId, 						accountHolderName, accountType))
						System.out.println("Updated record is");
					else
						System.out.println("Failed to update record");
					break;

				default:
					break;
				}
			}
		} catch (IOException e) {
			e.getMessage();
		

		}

	}

}
