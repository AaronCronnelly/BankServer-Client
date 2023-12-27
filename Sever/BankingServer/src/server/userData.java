package server;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class UserData {
	private LinkedList<User> userDatas;

	public UserData() {
		userDatas = new LinkedList<>();
		loadUserDataFromFile();
	} // end of UserData

	// Registration method
	public synchronized boolean registerUser(String name, String email, String password, String address, String PPS,
			String balance) {
		for (User user : userDatas) {
			if (user.getEmail().equals(email)) {
				return false; // Registration failed, email already exists
			}
		}

		// Create a new user and add it to the list
		User newUser = new User(name, email, password, address, PPS, balance);
		userDatas.add(newUser);

		// Save all user information to the file
		saveUserDataToFile();

		return true; // Registration successful
	} // end of registerUser

	// Login method
	public synchronized boolean logIn(String email, String password) {
		for (User user : userDatas) {
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
				for (int i = 0; i < temp.length && stringTokenizer.hasMoreTokens(); i++) {
					temp[i] = stringTokenizer.nextToken();
				}
				tempUser = new User(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
				userDatas.add(tempUser);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of loadUserDataFromFile

	// Save all user data to file
	private void saveUserDataToFile() {
		System.out.println("Attempting to save user data to file.");
		try {
			List<String> lines = new ArrayList<>();
			for (User user : userDatas) {
				String lineString = user.getName() + "," + user.getEmail() + "," + user.getPassword() + ","
						+ user.getAddress() + "," + user.getPPS() + "," + user.getBalance();
				lines.add(lineString);
			}

			Files.write(Paths.get("UserData.txt"), lines, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);

			System.out.println("File write successful."); // Add this line for debugging

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("File write failed.");
		}
	} // end of saveUserDataToFile

	// update updateUserDataFile
	private void updateUserDataFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("UserData.txt"));
			List<String> updatedLines = new ArrayList<>();

			for (String line : lines) {
				StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
				String userEmail = stringTokenizer.nextToken().trim();

				User currentUser = findCurrentUser(userEmail);

				if (currentUser != null) {
					// Update the balance if the user is found
					line = currentUser.getName() + "," + currentUser.getEmail() + "," + currentUser.getPassword() + ","
							+ currentUser.getAddress() + "," + currentUser.getPPS() + "," + currentUser.getBalance();
				}

				updatedLines.add(line);
			}

			// Write the updated lines back to the file
			Files.write(Paths.get("UserData.txt"), updatedLines, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end of updateUserDataFile

	// Update user balance
	public synchronized void updateBalance(String email, String newBalance) {
		User currentUser = findCurrentUser(email.trim());
		if (currentUser != null) {
			currentUser.setBalance(newBalance);
			updateUserDataFile(); // Call the method to update the file
		} else {
			System.out.println("Error: Current user not found during balance update.");
		}
	} // end of updateBalance

	// Find user by email
	public User findCurrentUser(String email) {
		for (User user : userDatas) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null; // User not found
	} // end of findCurrentUser

	// getAllUserrs
	public synchronized LinkedList<User> getAllUsers() {
		return new LinkedList<>(userDatas);
	}// end of getAllUsers

} // end of class
