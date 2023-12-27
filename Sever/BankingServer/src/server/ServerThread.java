package server;

import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.io.*;

public class ServerThread extends Thread {

	Socket myConnection;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	String message1;
	String message2;
	String message3;
	String message4;
	String message5;
	String message6;
	String message7;
	UserData userListData;
	String currentUserEmail; // Add this variable to store the email of the current user

	public ServerThread(Socket s, UserData userListData) {
		myConnection = s;
		this.userListData = userListData;
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
				sendMessage("Welcome to sign in");
				sendMessage("Enter Email: ");
//				message1 = (String) in.readObject();
				currentUserEmail = (String) in.readObject(); // Store the email for the current session
				sendMessage("Enter Password: ");
				message2 = (String) in.readObject();

				if (userListData.logIn(currentUserEmail.trim(), message2.trim())) {
					sendMessage("Login successful. Accessing menu...");
					menu();
				} else {
					sendMessage("Login failed. Please try again.");
				}
			}

			else if (message.equalsIgnoreCase("2")) {// When the user registers they will be brought straigt to the menu
			}
			sendMessage("Welcome to registration");
			sendMessage("Please enter Name:");
			message1 = (String) in.readObject();
			sendMessage("Please enter Email:");
			message2 = (String) in.readObject();
			sendMessage("Please enter Password:");
			message3 = (String) in.readObject();
			sendMessage("Please enter Address:");
			message4 = (String) in.readObject();
			sendMessage("Please enter PPS NO.:");
			message5 = (String) in.readObject();
			sendMessage("Please enter Balance:");
			message6 = (String) in.readObject();

			if (userListData.registerUser(message1, message2, message3, message4, message5, message6)) {
				sendMessage("Registration successful. You can now log in.");
			} else {
				sendMessage("Registration failed. Email or PPS already exists. Please try again.");
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
			if (!myConnection.isClosed()) {
				out.writeObject(msg);
				out.flush();
				System.out.println("server>" + msg);
			} else {
				System.out.println("Debug: Socket is closed. Message not sent: " + msg);
			}
		} catch (SocketException e) {
			System.out.println("Debug: Socket closed unexpectedly. Message not sent: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}// end of sendMessage

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

	}// end of menu

	/* Lodgment */
//Allows the user to lodge money into the account
	public void lodgment() throws ClassNotFoundException {
		try {
			sendMessage("Enter the amount to lodge:");
			String amountStr = (String) in.readObject();

			// Retrieve the current user from userData
			User currentUser = userListData.findCurrentUser(currentUserEmail);
			if (currentUser != null) {
				// Convert the user's balance to a numeric type
				double currentBalance = Double.parseDouble(currentUser.getBalance());
				double lodgmentAmount = Double.parseDouble(amountStr);

				// Perform the lodgment
				currentBalance += lodgmentAmount;

				// Convert the updated balance back to String and update the user's balance
				currentUser.updateBalance(String.valueOf(currentBalance));

				sendMessage("Lodgment successful. Updated balance: " + currentUser.getBalance());
			} else {
				sendMessage("Error: Current user not found during lodgment.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end fo lodgment

	/* Retrieve all registered users */
//Allow the user to see all registered users
	public void retrive() {
		try {
			sendMessage("Retrieving all registered users...");

			// Get all registered users from UserData
			LinkedList<User> allUsers = userListData.getAllUsers();

			// Check if there are any users
			if (allUsers.isEmpty()) {
				sendMessage("No registered users found.");
			} else {
				// Iterate through the list of users and send their details to the client
				for (User user : allUsers) {
					String userDetails = "Name: " + user.getName() + ", Email: " + user.getEmail() + ", PPS: "
							+ user.getPPS();
					sendMessage(userDetails);
				}
			}

			// Signal the end of user retrieval
			sendMessage("EndRetrieve");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of retrive

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
