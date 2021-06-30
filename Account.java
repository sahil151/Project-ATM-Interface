import java.util.ArrayList;

public class Account {
	
	/**
	 * The name of the account.
	 */
	private String name;
	
	/**
	 * The account ID number.
	 */
	private String UUID;
	
	/**
	 * The User object that owns this account.
	 */
	private User Holder;
	
	/**
	 * The list of transactions for this account.
	 */
	private ArrayList<Transaction> transactions;
	
	/**
	 * Create new Account instance
	 * @param name		the name of the account
	 * @param Holder	the User object that holds this account
	 * @param TheBank	the bank that issues the account
	 */
	public Account(String name, User Holder, Bank TheBank) {
		
		// set the account name and Holder
		this.name = name;
		this.Holder = Holder;
		
		// fetch next account UUID
		this.UUID = TheBank.fetchNewAccountUUID();
		
		// intialize transactions
		this.transactions = new ArrayList<Transaction>();
		
	}
	
	/**
	 * Fetch the account number.
	 */
	public String fetchUUID() {
		return this.UUID;
	}
	
	/**
	 * Add a new transaction in this account.
	 */
	public void add_Transaction(double amount) {
		
		// create new transaction and add it to our list
		Transaction newTrans = new Transaction(amount, this);
		this.transactions.add(newTrans);
		
	}
	
	/**
	 * Add a new transaction in this account.
	 */
	public void add_Transaction(double amount, String memo) {
		
		// create new transaction and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
	}
	
	/**
	 * Fetch the balance of this account by adding the amounts of the 
	 * transactions.
	 */
	public double fetchBalance() {
		
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.fetchAmount();
		}
		return balance;
		
	}
	
	/**
	 * Fetch summary line for account
	 */
	public String fetchSummaryLine() {
		
		// fetch the account's balance
		double balance = this.fetchBalance();
		
		if (balance >= 0) {
			return String.format("%s : Rs.%.02f : %s", this.UUID, balance, 
					this.name);
		} else {
			return String.format("%s : Rs.(%.02f) : %s", this.UUID, balance, 
					this.name);
		}
		
	}
	
	/**
	 * Print transaction history for account
	 */
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.UUID);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).fetchSummaryLine());
		}
		System.out.println();
		
	}

}