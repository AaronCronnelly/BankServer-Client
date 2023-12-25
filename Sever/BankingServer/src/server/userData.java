package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.crypto.Data;

public class userData {
	private LinkedList<User> newDatas;

	public userData() {
		String line;
		User tempUser;
		String temp[] = new String[7];
		newDatas = new LinkedList<User>();

		try {
			FileReader fReader = new FileReader("UserData.txt");
			BufferedReader bReader = new BufferedReader(fReader);

			while ((line = bReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

				for (int i = 0; i < 7; i++) {
					temp[i] = stringTokenizer.nextToken();
				}
				tempUser = new User(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
				newDatas.add(tempUser);

			}

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end os userdata

	public synchronized void registorUser(String name,String email,String email1,String password,String address,String PPS,String password1,String balance)
	{
		User tempUser=new User(name, address, password, password1, PPS, balance);
		String lineString;
		newDatas.add(tempUser);
		
		try {
			FileWriter frFileWriter=new FileWriter("UserData.txt",true);
			BufferedWriter bufferedWriter=new BufferedWriter(frFileWriter);
			lineString="Name: "+name+", Password: "+password+", PPS No.: "+PPS+", Email: "+email+", Address: "+address+", Balance: "+balance;
			bufferedWriter.write(lineString);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}// end of registorUser

	public synchronized boolean logIn(String email,String password)
	{
		for(User user:newDatas) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true; // Return true if login succeeds
	        }
	    }
	    return false; // Return false if login fails
	}//end of logIn
	
}// end of class
