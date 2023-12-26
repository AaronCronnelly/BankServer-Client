package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.crypto.Data;

public class userData {
	private LinkedList<User> newDatas;

	public userData() {
		newDatas = new LinkedList<>();
		loadUserDataFromFile();
	}// end os userdata

	public synchronized boolean registerUser(String name, String email, String password, String address,
			String PPS, String balance) {
		for (User user : newDatas) {
			if (user.getEmail().equals(email)) {
				return false; // Registration failed, email already exists
			}
		}

		// Create a new user and add it to the list
		User newUser = new User(name, email, password, address, PPS, balance);
		newDatas.add(newUser);

		// Save the new user information to the file
		saveUserDataToFile(newUser);

		return true; // Registration successful
	}// end of registorUser

	public synchronized boolean logIn(String email, String password) {
		for (User user : newDatas) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true; // Return true if login succeeds
			}
		}
		return false; // Return false if login fails
	}// end of logIn

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
	}// end of loadUserDataFromFile

	private void saveUserDataToFile(User user) {
		try (FileWriter frFileWriter = new FileWriter("UserData.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(frFileWriter)) {
			String lineString = user.getName() + "," + user.getEmail() + "," + user.getPassword() + ","
					+ user.getAddress() + "," + user.getPPS() + "," + user.getBalance();
			bufferedWriter.write(lineString);
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end of saveUserDataToFile

}// end of class
