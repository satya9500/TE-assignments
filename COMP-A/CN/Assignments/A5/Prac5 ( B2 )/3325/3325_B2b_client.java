import java.util.*;
import java.net.*;
import java.io.*;

class Client{
	static char id;

	public static class MessageSender implements Runnable {
		private DatagramSocket client;
		
		//takes input from terminal
		BufferedReader input;
		
		// using the localhost ip to send the data
		InetAddress ip;
		
		MessageSender(DatagramSocket s , String host) {
		    try{
				this.client = s;
				this.input = new BufferedReader(new InputStreamReader(System.in));
				this.ip = InetAddress.getByName(host);
			}catch (Exception e) {
	            System.err.println(e);
	        }
		}
		
		private void sendMessage(String msg) throws Exception {
		    byte[] sendMsg = msg.getBytes();
					
			//create the datagramPacket for sending the data to the server. 
		   	DatagramPacket dpSend = new DatagramPacket(sendMsg, sendMsg.length, ip, 5000);
		   	client.send(dpSend);
		}
		public void run() {
	        try {
	            sendMessage("connected");
	            
	        } catch (Exception e) {
	            System.err.println(e);
	        }
		    
		    while(true){
				try{
					String msg = "";
					System.out.print("\n: ");
					//reading client message
					msg = input.readLine();
					
					msg = msg + Client.id;
					
					sendMessage(msg);
					
					if(msg.equals("Bye"+Client.id)){
						break;
					}
					
				}
				catch(Exception x){
					System.out.println(x);
				}
			}
			
			System.exit(0);	
		}
	}

	public static class MessageReceiver implements Runnable {
		DatagramSocket client;
		
		MessageReceiver(DatagramSocket s) {
		    this.client = s;
		}

		public void run() {
		    while (true) {
		        try {
		            // creting an array to receive the data
					byte[] msgRecv = new byte[2048];
					
					// creating an object of DatagramPacket to receive the message
					DatagramPacket dpReceive = new DatagramPacket(msgRecv, msgRecv.length);
					
					//receiving message from client
					client.receive(dpReceive);
					String convertedMsg = new String(msgRecv);
					
					if(convertedMsg.charAt(0) == 'i' && convertedMsg.charAt(1) == 'd' && Client.id == '!'){
						Client.id = convertedMsg.charAt(3);
					}
					else if(convertedMsg.charAt(7) != Client.id){
						System.out.print("\n" + convertedMsg + "\n\n:");
					}
		        } catch(Exception e) {
		            System.err.println(e);
		        }
		    }
		}
	}

	public static void main(String args[]){
		try{
			Client.id = '!';
			//creating socket and connecting to the server
			DatagramSocket client = new DatagramSocket();
			
			System.out.println("Connected...");
			
			MessageReceiver r = new MessageReceiver(client);
		    MessageSender s = new MessageSender(client , "127.0.0.1");
		    Thread rt = new Thread(r);
		    Thread st = new Thread(s);
		    rt.start(); st.start();
			
		}
		catch(Exception x){
			System.out.println(x.getMessage());
		}
			
	}
}

