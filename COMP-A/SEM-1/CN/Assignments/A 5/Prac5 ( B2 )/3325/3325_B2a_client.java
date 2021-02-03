import java.util.*;
import java.net.*;
import java.io.*;

class Client{

	public static void main(String args[]){
		try{
			//creating socket and connecting to the server
			DatagramSocket client = new DatagramSocket();
			
			System.out.println("Connected...");
			
			//takes input from terminal
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			// using the localhost ip to send the data
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			
			String msg = "";
			
			while(true){
				try{
					System.out.print("\nClient : ");
					//reading client message
					msg = input.readLine();
					
					byte[] sendMsg = msg.getBytes();
					
					//create the datagramPacket for sending the data to the server. 
				   	DatagramPacket dpSend = new DatagramPacket(sendMsg, sendMsg.length, ip, 5000);
				   	client.send(dpSend);
					
					if(msg.equals("Bye")){
						break;
					}
					
					// creting an array to receive the data
					byte[] msgRecv = new byte[2048];
					
					// creating an object of DatagramPacket to receive the message
					DatagramPacket dpReceive = new DatagramPacket(msgRecv, msgRecv.length);
					
					//receiving message from client
					client.receive(dpReceive);
					String convertedMsg = new String(msgRecv);
					
					System.out.print("Server : " + convertedMsg + "\n");
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

