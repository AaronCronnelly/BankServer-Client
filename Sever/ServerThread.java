import java.net.Socket;
import java.io.*;


public class ServerThread extends Thread{

	Socket myConnection;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	
	public ServerThread(Socket s)
	{
		myConnection = s;
	}
	
	public void run()
	{
		try
		{
			out = new ObjectOutputStream(myConnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(myConnection.getInputStream());
		
			//Server Comms
			
			sendMessage("Please enter 1 to ADD or 2 to SUBTRACT");
			message = (String)in.readObject();
		
			if(message.equalsIgnoreCase("1"))
			{
				
			}
			
			else if(message.equalsIgnoreCase("2"))
			{
				
			}
			
			
			in.close();
			out.close();
		}
		catch(ClassNotFoundException classnot)
		{
					System.err.println("Data received in unknown format");
		}
		catch(IOException e)
		{
			
		}
		
		
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}
