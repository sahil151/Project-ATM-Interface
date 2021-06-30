import java.util.Scanner;

public class ATM {
	

	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		
		// intialize Bank
		Bank aBank = new Bank("State Bank of India");
		
		// add a user, which also creates a Savings account
		User aUser = aBank.addUser("Sahil","Verma", "5456");
		Account newAccount = new Account("Current", aUser, aBank);
		aUser.addAccount(newAccount);
		aBank.addAccount(newAccount);
		
		User tempUser;
		
		// continue looping forever
		while (true) {
			
			// stay in login prompt until successful login
			tempUser = ATM.loginPrompt(aBank, sc);
			
			// stay in main menu until user quits
			ATM.printMainMenu(tempUser, sc);
		}
	}
	/**
	 * Print the ATM's login menu.
	 */
	public static User loginPrompt(Bank aBank, Scanner sc) {
		
		String userID;
		String pin;
		User validUser;
		
		do {
			
			System.out.printf("\n\nWelcome to %s\n\n", aBank.fetchName());		
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			validUser = aBank.user_login(userID, pin);
			if (validUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + 
						"Retry .");
			}
			
		} while(validUser == null); 	// continue looping until we have a  
									// successful login
		
		return validUser;
		
	}
	
	/**
	 * Print the ATM's menu for user actions.
	 */
	public static void printMainMenu(User aUser, Scanner sc) {
		// intiliaze
		int option;
		aUser.printAccountsSummary();
		// user menu
		do {
			
			System.out.println("You would  like to ?");
			System.out.println("  1) Show account transaction history");
			System.out.println("  2) Withdraw Money");
			System.out.println("  3) Deposit Money");
			System.out.println("  4) Transfer Money");
			System.out.println("  5) Quit");
			System.out.println();
			System.out.print("Enter option: ");
			option = sc.nextInt();
			
			if (option < 1 || option > 5) {
				System.out.println("Invalid option. Please choose from 1 to 5.");
			}
			
		} while (option < 1 || option > 5);
		switch (option) {
		
		case 1:
			ATM.showTransHistory(aUser, sc);
			break;
		case 2:
			ATM.withdrawMoney(aUser, sc);
			break;
		case 3:
			ATM.depositMoney(aUser, sc);
			break;
		case 4:
			ATM.transferMoney(aUser, sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}
		if (option != 5) {
			ATM.printMainMenu(aUser, sc);
		}
		
	}
	
	/**
	 * Process transferring funds from one account to another.
	 */
	public static void transferMoney(User aUser, Scanner sc) {
		
		int fromAcctIndex;
		int toAcctIndex;
		double amount;
		double acctBal;
		
		// fetch account to transfer from
		do {
			System.out.printf("Enter the number (1 to %d) of the account to " + 
					"transfer from: ", aUser.num_accounts());
			fromAcctIndex = sc.nextInt()-1;
			if (fromAcctIndex < 0 || fromAcctIndex >= aUser.num_accounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcctIndex < 0 || fromAcctIndex >= aUser.num_accounts());
		acctBal = aUser.fetchAcctBalance(fromAcctIndex);
		
		// fetch account to transfer to
		do {
			System.out.printf("Enter the number (1 to %d) of the account to " + 
					"transfer to: ", aUser.num_accounts());
			toAcctIndex = sc.nextInt()-1;
			if (toAcctIndex < 0 || toAcctIndex >= aUser.num_accounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcctIndex < 0 || toAcctIndex >= aUser.num_accounts());
		
		// fetch amount to transfer
		do {
			System.out.printf("Enter the amount to transfer :");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance. ");
			}
		} while (amount < 0 || amount > acctBal);
		
		// finally, do the transfer 
		aUser.addAcctTransaction(fromAcctIndex, -1*amount, String.format(
				"Transfer to account %s", aUser.fetchAcctUUID(toAcctIndex)));
		aUser.addAcctTransaction(toAcctIndex, amount, String.format("Transfer from account %s", aUser.fetchAcctUUID(fromAcctIndex)));
		
	}
	
	/**
	 * Process a fund withdraw from an account.
	 */
	public static void withdrawMoney(User aUser, Scanner sc) {
		
		int acctIndex;
		double amount;
		double acctBal;
		String ministat;
		
		// fetch account to withdraw from
		do {
			System.out.printf("Enter the number (1 to %d) of the account to " + 
					"withdraw from: ", aUser.num_accounts());
			acctIndex = sc.nextInt()-1;
			if (acctIndex < 0 || acctIndex >= aUser.num_accounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (acctIndex < 0 || acctIndex >= aUser.num_accounts());
		acctBal = aUser.fetchAcctBalance(acctIndex);
		
		// fetch amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw : Rs. ");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance.");
			}
		} while (amount < 0 || amount > acctBal);
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// fetch a remarks
		System.out.print("Enter remarks: ");
		ministat = sc.nextLine();
		
		// do the withdrwal
		aUser.addAcctTransaction(acctIndex, -1*amount, ministat);
		
	}
	
	/**
	 * Process a fund deposit to an account.
	 */
	public static void depositMoney(User aUser, Scanner sc) {
		
		int acctIndex;
		double amount;
		String ministat;
		
		// fetch account to withdraw from
		do {
			System.out.printf("Enter the number (1 to %d) of the account to " + 
					"deposit to: ", aUser.num_accounts());
			acctIndex = sc.nextInt()-1;
			if (acctIndex < 0 || acctIndex >= aUser.num_accounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (acctIndex < 0 || acctIndex >= aUser.num_accounts());
		
		// fetch amount to transfer
		do {
			System.out.printf("Enter the amount to deposit: Rs.");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while (amount < 0);
		
		// clear the rest of previous input
		sc.nextLine();
		
		// fetch a memo
		System.out.print("Enter remarks: ");
		ministat = sc.nextLine();
		
		// do the deposit
		aUser.addAcctTransaction(acctIndex, amount, ministat);
		
	}
	
	/**
	 * Show the transaction history for an account.
	 */
	public static void showTransHistory(User aUser, Scanner sc) {
		
		int acctIndex;
		
		// fetch account whose transactions to print
		do {
			System.out.printf("Enter the number (1 to %d) of the account\nwhose " + 
					"transactions you want to see: ", aUser.num_accounts());
			acctIndex = sc.nextInt()-1;
			if (acctIndex < 0 || acctIndex >= aUser.num_accounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (acctIndex < 0 || acctIndex >= aUser.num_accounts());
		
		// print the transaction history
		aUser.printAcctTransHistory(acctIndex);
		
	}

}