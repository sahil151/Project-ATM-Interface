import java.util.Date;

public class Transaction {
	
	/**
	 * The amount of this transaction.
	 */
	private double amount;
	
	/**
	 * The time and date of this transaction.
	 */
	private Date timestamp;
	
	/**
	 * A memo for this transaction.
	 */
	private String memo;
	
	/**
	 * The account in which the transaction was performed.
	 */
	private Account in_Acct;
	
	/**
	 * Create a new transaction.
	 * @param amount		the dollar amount transacted
	 * @param in_Acct	the account the transaction belongs to
	 */
	public Transaction(double amount, Account in_Acct) {
		
		this.amount = amount;
		this.in_Acct = in_Acct;
		this.timestamp = new Date();
		this.memo = "";
		
	}
	
	/**
	 * Create a new transaction with a memo.
	 * @param amount	the dollar amount transacted
	 * @param memo		the memo for the transaction
	 * @param in_Acct	the account the transaction belongs to
	 */
	public Transaction(double amount, String memo, Account in_Acct) {
		
		// call the single-arg constructor first
		this(amount, in_Acct);
		
		this.memo = memo;
		
	}
	
	/**
	 * Fetch the transaction amount.
	 */
	public double fetchAmount() {
		return this.amount;
	}
	
	/**
	 * Fetch a string summarizing the transaction
	 */
	public String fetchSummaryLine() {
		
		if (this.amount >= 0) {
			return String.format("%s, Rs.%.02f : %s", 
					this.timestamp.toString(), this.amount, this.memo);
		} else {
			return String.format("%s, Rs.(%.02f) : %s", 
					this.timestamp.toString(), -this.amount, this.memo);
		}
	}

}
