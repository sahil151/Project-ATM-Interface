import java.util.ArrayList;
import java.util.Random;


public class Bank {
	
	/**
	 * The name of the bank.
	 */
	private String name;
	
	/**
	 * The account Holders of the bank.
	 */
	private ArrayList<User> users;
	
	/**
	 * The accounts of the bank.
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new Bank object with empty lists of users and accounts.
	 */
	public Bank(String name) {
		
		this.name = name;
		
		// init users and accounts
		users = new ArrayList<User>();
		accounts = new ArrayList<Account>();
		
	}
	
	/**
	 * Generate a new universally unique ID for a user.
	 */
	public String fetchNewUserUUID() {
		
		// intiliaze
		String UUID;
		Random rand = new Random();
		int length = 6;
		boolean non_unique;
		
		// continue looping until we fetch a unique ID
		do {
			
			// generate the number
			UUID = "";
			for (int c = 0; c < length; c++) {
				UUID += ((Integer)rand.nextInt(10)).toString();
			}
			
			// check to make sure it's unique
			non_unique = false;
			for (User u : this.users) {
				if (UUID.compareTo(u.fetchUUID()) == 0) {
					non_unique = true;
					break;
				}
			}
			
		} while (non_unique);
		
		return UUID;
	}
	
	/**
	 * Generate a new universally unique ID for an account
	 */
	public String fetchNewAccountUUID() {
		
		// intialize
		String UUID;
		Random rand = new Random();
		int length = 10;
		boolean non_unique = false;
		
		// continue looping until we fetch a unique ID
		do {
			
			// generate the number
			UUID = "";
			for (int c = 0; c < length; c++) {
				UUID += ((Integer)rand.nextInt(10)).toString();
			}
			
			// check to make sure it's unique
			for (Account a : this.accounts) {
				if (UUID.compareTo(a.fetchUUID()) == 0) {
					non_unique = true;
					break;
				}
			}
			
		} while (non_unique);
		
		return UUID;
				
	}

	/**
	 * Create a new user of the bank.
	 */
	public User addUser(String fName, String lName, String pin) {
		
		// create a new User object and add it to our list
		User newUser = new User(fName, lName, pin, this);
		this.users.add(newUser);
		
		// create a savings account for the user and add it to our list
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
		
	}
	
	/**
	 * Add an existing account for a particular User.
	 */
	public void addAccount(Account newAccount) {
		this.accounts.add(newAccount);
	}
	
	/**
	 * Fetch the User object associated with a particular userID and pin, if they
	 * are valid.
	 * @param userID	the user UUID to log in
	 * @param pin		the associate pin of the user
	 * @return			the User object, if login is successfull, or null, if 
	 * 					it is not
	 */
	public User user_login(String userID, String pin) {
		
		// search through list of users
		for (User u : this.users) {
			
			// if we find the user, and the pin is correct, return User object
			if (u.fetchUUID().compareTo(userID) == 0 && u.validate_pin(pin)) {
				return u;
			}
		}
		
		return null;
		
	}
	
	/**
	 * Fetch the name of the bank
	 */
	public String fetchName() {
		return this.name;
	}

}
