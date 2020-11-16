import java.util.*;
import java.net.*;
import java.io.*;

class Server{

	public static void main(String args[]){
		try{
			//creating socket and connecting to the client
			ServerSocket server = new ServerSocket(5000);
			
			System.out.println("Waiting for connections...");
			
			//accepting the connection from client
			Socket sockId = server.accept();
			
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
				
					//receiving message from client
					msg = recv.readUTF();
					System.out.print("\nCleint : " + msg);
					System.out.println();
					
					if(msg.equals("Bye")){
						break;
					}
					
					System.out.print("Server : ");
					//reading client message
					msg = input.readLine();
					
					//sending message to server
					send.writeUTF(msg);
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

