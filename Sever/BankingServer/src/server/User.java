package server;

public class User {
	private String name, email, password, address, PPS, balance;

	public User(String n, String e, String p, String a, String pp, String b) {
		name = n;
		email = e;
		password = p;
		address = a;
		PPS = pp;
		balance = b;
	}// end of user

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPPS() {
		return PPS;
	}
	
	public String getBalance() {
		return balance;
	}
}// end of class user
