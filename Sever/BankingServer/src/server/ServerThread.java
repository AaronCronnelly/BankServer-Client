package server;

import java.net.Socket;
import java.io.*; 
import java.util.ArrayList;





public class ServerThread extends Thread { 

	Socket myConnection; 
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	// data
	String name, email, password, passwordCheck, address, PPS, balance;

	public class User {
        String name;
        String email;
        String password;
        String PPS;
        String balance;
        String address;

        public User(String name, String email, String password, String PPS, String balance, String address) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.PPS = PPS;
            this.balance = balance;
            this.address = address;
        }
    }

	
	public ServerThread(Socket s) {
		myConnection = s;
	}
	
	
	 

	public void run() {
		try {
			out = new ObjectOutputStream(myConnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());

			// Server Comms

			sendMessage("Welcome, SignIn(1), Register(2)");
			message = (String) in.readObject();

			if (message.equalsIgnoreCase("1")) {
				/*
				 * If the user choose sign in then they are going to be asked to
				 */
//				sendMessage("Testing entry");
//				message=(String)in.readObject();
//				entry=Integer.parseInt(message);
//				sendMessage("Testing number: "+entry);
				sendMessage("Welcome to sign in");
				sendMessage("Enter Name: ");
				name = (String) in.readObject();
				sendMessage("Enter Password: ");
				password = (String) in.readObject();
				sendMessage("Signing in");

				menu();

			}

			else if (message.equalsIgnoreCase("2")) {// When the user registers they will be brought straigt to the menu
				// Registering
				sendMessage("Wlecome to registeration");
				sendMessage("Please enter Name:");
				name = (String) in.readObject();
				sendMessage("Please enter Email:");
				email = (String) in.readObject();
				sendMessage("Please enter Password:");
				password = (String) in.readObject();
				sendMessage("Please cofirm Password:");
				passwordCheck = (String) in.readObject();
				sendMessage("Please enter PPS NO.:");
				PPS = (String) in.readObject();
				sendMessage("Please enter Balance:");
				balance = (String) in.readObject();
				sendMessage("Please enter Address:");
				address = (String) in.readObject();

				menu();
			}

			in.close();
			out.close();
		} catch (ClassNotFoundException classnot) {
			System.err.println("Data received in unknown format");
		} catch (IOException e) {

		}

	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/****** METHODS ******/
	/* Authentachation */
//unsure if this method will be used yet have to wait and see

	/* Menu */
//This is going to give the auth user the ability to acces other mehtods
	public void menu() {
		try {
			sendMessage("Menu:");
			sendMessage("1. Lodge Money");
			sendMessage("2. Retrieve All Registered Users");
			sendMessage("3. Transfer Money");
			sendMessage("4. View Transaction History");
			sendMessage("5. Update Password");
			sendMessage("6. Exit");
			String choice = (String) in.readObject();
			// Handle user choice
			sendMessage(choice);
			int val = Integer.parseInt(choice);

			switch (val) {
			case 1:
				lodgment();
				break;
			case 2:
				retrive();
				break;
			case 3:
				transfer();
				break;
			case 4:
				transactionHistory();
				break;
			case 5:
				updatePass();
				break;

			default:
				break;
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/* Lodgment */
//Allows the user to lodge money into the account
	public void lodgment() throws ClassNotFoundException {
		sendMessage("In lodgment");
	}

	/* Retrieve all registered users */
//Allow the suer to see all registered users
	public void retrive() {
		sendMessage("In retrive");
	}

	/* Transfer money */
//Allow user to transfare money 
	public void transfer() {
		sendMessage("In transfer");
	}

	/* View transaction history */
//Allow user to view transaction history
	public void transactionHistory() {
		sendMessage("In transaction history");
	}

	/* Update Password */
//Allow user to update passowrd
	public void updatePass() {
		sendMessage("In update password");
	}
}
