import java.net.InetAddress;
import java.util.*; 

public class dns 
{ 
	public static void main(String[] args){ 
		String host; 
		Scanner input = new Scanner(System.in); 
		System.out.print("\n Enter host name: "); 
		host = input.nextLine(); 
		try { 
			InetAddress address = InetAddress.getByName(host);
			System.out.println("IP address: " + address.getHostAddress());
			System.out.println("Host name : " + address.getHostName());  

		} 
		catch (Exception ex) {
		     System.out.println("Could not find " + host); 
		}
	} 
}
