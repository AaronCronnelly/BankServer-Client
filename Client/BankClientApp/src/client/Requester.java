package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	String response;
	Scanner input;

	Requester() {
		input = new Scanner(System.in);
	}

	/*** Methods ***/
	void handleSignIn() throws ClassNotFoundException, IOException {
		// welcome
		message = (String) in.readObject();
		System.out.println(message);
		// name
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// password
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);

		menu();
	}

	void handleRegistration() throws ClassNotFoundException, IOException {
		// welcome
		message = (String) in.readObject();
		System.out.println(message);
		// name
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// email
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// password
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// passwordCheck
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// PPS No.
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// balance
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		// address
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
	}

	void menu() throws ClassNotFoundException, IOException {
		// menu
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		message = (String) in.readObject();
		System.out.println(message);
		response = input.next();
		sendMessage(response);
		if (response.equalsIgnoreCase("2")) {
			displayUser();
		}

		message = (String) in.readObject();
		System.err.println(message);
	}

	void displayUser() {
		try {
			// Assume the server sends a signal before sending user data
			String signal = (String) in.readObject();

			// Check if the signal indicates user data is coming
			if (signal.equals("UserData")) {
				while (true) {
					// Read user data from the server
					message = (String) in.readObject();

					// Check if it's the end of user data
					if (message.equals("EndUserData")) {
						break;
					}

					// Display user data
					System.out.println(message);
				}
			} else {
				System.out.println("No user data available.");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void run() {
		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket("127.0.0.1", 2004);
			System.out.println("Connected to localhost in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server

			// Client Comms
			try {
				do {
					message = (String) in.readObject();
					System.out.println(message);
					response = input.next();
					sendMessage(response);

					if (response.equalsIgnoreCase("1")) {
						handleSignIn();
					} else if (response.equalsIgnoreCase("2")) {
						handleRegistration();
					}

					message = (String) in.readObject();
					System.out.println(message);
					response = input.next();
					sendMessage(response);
				} while (response.equalsIgnoreCase("1"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}// end of run

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Requester client = new Requester();
		client.run();
	}
}
