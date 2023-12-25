package server;

public class User {
	private String name,email,password,address,PPS,balance;
	
	public User(String n,String e,String p,String a,String pp,String b)
	{
		name=n;
		email=e;
		password=p;
		address=a;
		PPS=pp;
		balance=b;
	}//end of user
	
	 public String getEmail() {
	        return email;
	    }

	    public String getPassword() {
	        return password;
	    }
	
}//end of class user
