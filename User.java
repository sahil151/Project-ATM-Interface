import java.security.MessageDigest;
import java.util.ArrayList;

public class User {

	/**
	 * The first name of the user.
	 */
	private String fName;
	
	/**
	 * The last name of the user.
	 */
	private String lName;
	
	/**
	 * The ID number of the user.
	 */
	private String UUID;
	
	/**
	 * The hash of the user's pin number.
	 */
	private byte pin_Hash[];
	
	/**
	 * The list of accounts for this user.
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create new user
	 */
	public User (String fName, String lName, String pin, Bank TheBank) {
		
		// set user's name
		this.fName = fName;
		this.lName = lName;
		
		// store the pin's MSG5 hash, rather than the original value, for 
		// security reasons
		try {
			MessageDigest msg = MessageDigest.getInstance("MD5");
			this.pin_Hash = msg.digest(pin.getBytes());
		} catch (Exception e) {
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		// fetch a new, unique universal unique ID for the user
		this.UUID = TheBank.fetchNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s %s with ID %s registered.\n", 
				fName,lName,this.UUID);
		
	}
	
	/**
	 * Fetch the user ID number
	 */
	public String fetchUUID() {
		return this.UUID;
	}
	
	/**
	 * Add an account for the user.
	 */
	public void addAccount(Account An_Acct) {
		this.accounts.add(An_Acct);
	}
	
	/**
	 * Fetch the number of accounts the user has.
	 */
	public int num_accounts() {
		return this.accounts.size();
	}
	
	/**
	 * Fetch the balance of a particular account.
	 */
	public double fetchAcctBalance(int Acct_Index) {
		return this.accounts.get(Acct_Index).fetchBalance();
	}
	
	/**
	 * Fetch the UUID of a particular account
	 */
	public String fetchAcctUUID(int Acct_Index) {
		return this.accounts.get(Acct_Index).fetchUUID();
	}
	
	/**
	 * Print transaction history for a particular account.
	 */
	public void printAcctTransHistory(int Acct_Index) {
		this.accounts.get(Acct_Index).printTransHistory();
	}
	
	/**
	 * Add a transaction to a particular account.
	 */
	public void addAcctTransaction(int Acct_Index, double amount, String memo) {
		this.accounts.get(Acct_Index).add_Transaction(amount, memo);
	}
	
	/**
	 * Check whether a given pin matches the true User pin
	 */
	public boolean validate_pin(String A_pin) {
		
		try {
			MessageDigest msg = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(msg.digest(A_pin.getBytes()), 
					this.pin_Hash);
		} catch (Exception e) {
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		return false;
	}
	
	/**
	 * Print summaries for the accounts of this user.
	 */
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.fName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.accounts.get(a).fetchSummaryLine());
		}
		System.out.println();
		
	}
}
