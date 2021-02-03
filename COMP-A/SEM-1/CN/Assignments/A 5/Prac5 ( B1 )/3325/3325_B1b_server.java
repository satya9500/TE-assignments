
import java.io.*; 
import java.util.*; 
import java.net.*; 

// Server class 
class Server 
{ 
	// counter for clients 
	public static int clientCount = 0;
	static boolean flag = false;

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(5000); 
		
		Socket s; 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			// Accept the incoming connection request from client
			s = ss.accept(); 
			clientCount++;
			flag = true;
			
			System.out.println("\nClient " + clientCount + " Connected"); 
			
			// obtain input and output streams 
			DataInputStream recv = new DataInputStream(s.getInputStream()); 	//Receive message from client
			DataOutputStream send = new DataOutputStream(s.getOutputStream()); 	//Send message to client 

			// Create a new handler object for handling this request for this perticular client. 
			ClientHandler manageClient = new ClientHandler(s,"client " + clientCount, recv, send); 

			// Create a new Thread with this object. 
			Thread t = new Thread(manageClient); 

			// start the thread. 
			t.start();

		} 
	} 
} 

// ClientHandler class 
class ClientHandler implements Runnable 
{ 
	Scanner read = new Scanner(System.in); 
	private String clientName; 
	final DataInputStream recv; 
	final DataOutputStream send; 
	Socket s; 
	
	// constructor 
	public ClientHandler(Socket s, String clientName, DataInputStream recv, DataOutputStream send) { 
		this.recv = recv; 
		this.send = send; 
		this.clientName = clientName; 
		this.s = s; 
	} 

	@Override
	public void run() { 

		String receivedMessage; 
		while (true) 
		{ 
			try
			{ 
				// receive the message from this client
				receivedMessage = recv.readUTF(); 
				
				if(receivedMessage.equals("Bye")){ 
					System.out.println("\n" + clientName + " got Disconnected...");
					this.s.close(); 
					break; 
				} 
			
				System.out.println("\n" + clientName + " : " + receivedMessage); 
			
				System.out.print("Server : ");
				String sendMessage = read.nextLine();

				send.writeUTF(sendMessage);				
				
			} catch (IOException error) { 
				
				error.printStackTrace(); 
			} 
			
		} 
		try
		{ 
			// closing resources 
			this.recv.close(); 
			this.send.close(); 
			
		}catch(IOException error){ 
			error.printStackTrace(); 
		} 
	} 
} 

