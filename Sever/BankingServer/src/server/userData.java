package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class userData {
	private LinkedList<User> newDatas;

	public userData() {
		newDatas = new LinkedList<>();
		loadUserDataFromFile();
	} // end of UserData

	// Registration method
	public synchronized boolean registerUser(String name, String email, String password, String address, String PPS,
			String balance) {
		for (User user : newDatas) {
			if (user.getEmail().equals(email)) {
				return false; // Registration failed, email already exists
			}
		}

		// Create a new user and add it to the list
		User newUser = new User(name, email, password, address, PPS, balance);
		newDatas.add(newUser);

		// Save all user information to the file
		saveUserDataToFile();

		return true; // Registration successful
	} // end of registerUser

	// Login method
	public synchronized boolean logIn(String email, String password) {
		for (User user : newDatas) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true; // Return true if login succeeds
			}
		}
		return false; // Return false if login fails
	} // end of logIn

	// Load user data from file into memory
	private void loadUserDataFromFile() {
		String line;
		User tempUser;
		String temp[] = new String[6]; // Assuming 6 fields, adjust as needed

		try (FileReader fReader = new FileReader("UserData.txt");
				BufferedReader bReader = new BufferedReader(fReader)) {

			while ((line = bReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
				for (int i = 0; i < temp.length; i++) {
					temp[i] = stringTokenizer.nextToken();
				}
				tempUser = new User(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
				newDatas.add(tempUser);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of loadUserDataFromFile

	// Save all user data to file
	private void saveUserDataToFile() {
		try (FileWriter frFileWriter = new FileWriter("UserData.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(frFileWriter)) {

			for (User user : newDatas) {
				String lineString = user.getName() + "," + user.getEmail() + "," + user.getPassword() + ","
						+ user.getAddress() + "," + user.getPPS() + "," + user.getBalance();
				bufferedWriter.write(lineString);
				bufferedWriter.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of saveUserDataToFile

	// Update user balance
	public void updateBalance(String email, String newBalance) {
		User currentUser = findCurrentUser(email);
		if (currentUser != null) {
			currentUser.setBalance(newBalance);
			saveUserDataToFile();
		}
	} // end of updateBalance

	// Find user by email
	public User findCurrentUser(String email) {
		for (User user : newDatas) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null; // User not found
	} // end of findCurrentUser

}// end of class
