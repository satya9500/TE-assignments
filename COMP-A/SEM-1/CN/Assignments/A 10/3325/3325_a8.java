import java.net.*;
import java.io.*;
import java.util.*;

class DNS 
{
	public static void main(String[] args) 
	{
		int n;
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		do
		{
			System.out.println("\n Menu: \n 1. DNS 2. Reverse DNS 3. Exit \n");
			System.out.print("\n Enter your choice :");
			n = Integer.parseInt(System.console().readLine()); 
			if(n==1)
			{
				try 
				{
					 System.out.print("\nEnter Host Name : ");
					 String hname=read.readLine();
					 InetAddress address;
					 address = InetAddress.getByName(hname);
					 System.out.println("Host Name: " + address.getHostName());
					 System.out.println("\nIP: " + address.getHostAddress());
				} 
				catch(IOException ioe) 
				{
				 	ioe.printStackTrace();
				}
			}
			if(n==2)
			{
				try 
				{
				   System.out.print("\nEnter IP address : ");
				   String ipstr = read.readLine();
				   InetAddress ia = InetAddress.getByName(ipstr);
				   System.out.println("IP: "+ipstr);
				   System.out.println("\nHost Name: " +ia.getHostName());
				} 
				catch(IOException ioe) 
				{
				 	ioe.printStackTrace();
				}
			}
		}while(!(n==3));
	}
}

