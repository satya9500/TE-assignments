
import java.io.*; 
import java.util.*; 
import java.net.*; 

// Server class 
class Server 
{ 
	// counter for clients 
	public static int clientCount = 0;
	
	static List<Integer> clientId = new ArrayList<>();
	static List<Integer> clientPort = new ArrayList<>();

	public static void main(String[] args) throws IOException 
	{ 
		InetAddress address = InetAddress.getByName("127.0.0.1");
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			try{
			
				// server is listening on port 1234 
				DatagramSocket server = new DatagramSocket(5000);
			
				// creting an array to receive the data
				byte[] msgRecv = new byte[2048];
				
				// creating an object of DatagramPacket to receive the message
				DatagramPacket dpReceive = new DatagramPacket(msgRecv, msgRecv.length);
				//receiving message from client
				server.receive(dpReceive); 

				// getting the client IP address
				
				String convertedMsg = new String(dpReceive.getData() , 0 , dpReceive.getLength());
				
				String temp = convertedMsg;
				
				char currentClientId = convertedMsg.charAt(convertedMsg.length() - 1);
				convertedMsg = convertedMsg.substring(0 , convertedMsg.length() - 1);
				
				if(temp.equals("connected")){
					clientCount++;
					
					System.out.println("\nClient " + clientCount + " Connected");
					
					clientId.add(clientCount);
					//sending the client ID to the client
					String msg = "id-" + Integer.toString(clientCount);
					System.out.println(msg);
					byte[] sendMsg = msg.getBytes();
					
					// extracting the port of the client
					int port = dpReceive.getPort();
				        clientPort.add(port);
				        
			    		//create the datagramPacket for sending the data to the server. 
					DatagramPacket dpSend = new DatagramPacket(sendMsg, sendMsg.length, address, port);
					    
					server.send(dpSend);
				}
				else if(convertedMsg.equals("Bye")){
					int tempIndex = 0;
					for(int i = 0 ; i < clientId.size() ; i++){
						if(clientId.get(i) == currentClientId);
						tempIndex = i;
						break;
					}
					
					clientId.remove(tempIndex);
					clientPort.remove(tempIndex);
					
					System.out.println("\nClient " + currentClientId + " got disconnected");
				}
				else{
					convertedMsg = "client " + currentClientId + " : " + convertedMsg;
					
					for (int i=0; i < clientId.size(); i++) {
						int id = clientId.get(i);
					     
					   	byte[] sendMsg = convertedMsg.getBytes();
				
						//create the datagramPacket for sending the data to the server. 
						DatagramPacket dpSend = new DatagramPacket(sendMsg, sendMsg.length, address, clientPort.get(i));
							
						server.send(dpSend);
            		}
				}
				
				server.close();
			}
			catch(IOException x){
					System.out.println(x);
			}
		} 
	} 
} 

