import java.util.*;
import java.net.*;
import java.io.*;

class Client{

	public static void main(String args[]){
		try{
			//creating socket and connecting to the server
			Socket sockId = new Socket("127.0.0.1" , 5000);
			
			System.out.println("Connected...");
			
			//takes input from terminal
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			//sends message to the server
			DataOutputStream send = new DataOutputStream(sockId.getOutputStream());
			
			//receives message from server
			DataInputStream recv = new DataInputStream(sockId.getInputStream());
			
			String msg = "";
			
			while(true){
				try{
					System.out.print("\nClient : ");
					//reading client message
					msg = input.readLine();
					
					//sending message to server
					send.writeUTF(msg);
					
					if(msg.equals("Bye")){
						break;
					}
					
					//receiving message from server
					msg = recv.readUTF();
					System.out.print("Server : " + msg + "\n");
				}
				catch(IOException x){
					System.out.println(x);
				}
			}	
		}
		catch(Exception x){
			System.out.println(x.getMessage());
		}
			
	}
}

