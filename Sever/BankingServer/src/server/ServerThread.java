package server;

import java.net.Socket;
import java.io.*;

public class ServerThread extends Thread {

	Socket myConnection;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	//data
	String name,email,password,address,PPS;
	double balance;

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
				name=(String)in.readObject();
				sendMessage("Enter Password: ");
				password=(String)in.readObject();
				sendMessage("Signing in");

			}

			else if (message.equalsIgnoreCase("2")) {// When the user registers they will be brought straigt to the menu
				//Registering
				
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
	
	
	/* Lodgment */
//Allows the user to lodge money into the account
	
	/* Retrieve all registered users */
//Allow the suer to see all registered users
	
	/* Transfer money */
//Allow user to transfare money 
	
	/* View transaction history */
//Allow user to view transaction history
	
	/* Update Password */
//Allow user to update passowrd
}

