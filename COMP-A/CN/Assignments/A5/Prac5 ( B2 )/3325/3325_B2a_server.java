import java.util.*;
import java.net.*;
import java.io.*;

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress; 
import java.util.Scanner; 

class Server{

	public static void main(String args[]){
		try{
			//creating socket and connecting to the client
			DatagramSocket server = new DatagramSocket(5000);
			
			System.out.println("Waiting for connection...");
			
			//takes input from terminal
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			String msg = "";
			
			while(true){
				try{
					// creting an array to receive the data
					byte[] msgRecv = new byte[2048];
					
					// creating an object of DatagramPacket to receive the message
					DatagramPacket dpReceive = new DatagramPacket(msgRecv, msgRecv.length);
					
					//receiving message from client
					server.receive(dpReceive);
					
					String convertedMsg = new String(dpReceive.getData() , 0 , dpReceive.getLength());
					
					// printing the received message
					System.out.print("\nCleint : " + convertedMsg);
					System.out.println();
					
					if(convertedMsg.equals("Bye")){
						break;
					}
					
					//---------------------------------------------------
					// preparing to send message back to the client
					
					System.out.print("Server : ");
					//reading server message
					msg = input.readLine();
					
					byte[] sendMsg = msg.getBytes();
					
					// using the localhost ip to send the data
					InetAddress ip = dpReceive.getAddress();
					
					//create the datagramPacket for sending the data. 
				   	DatagramPacket dpSend = new DatagramPacket(sendMsg, sendMsg.length, ip, dpReceive.getPort());
				   	server.send(dpSend);
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

