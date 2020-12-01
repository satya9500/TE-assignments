import java.net.*;
import java.util.*;

class IPDemo
{
 public static void main(String[] args){
  String host;
  Scanner ch = new Scanner(System.in);  
  System.out.print("1.Enter Host Name \n2.Enter IP address \nChoice=");
  int choice = ch.nextInt();
  if(choice==1)
  {  
  Scanner input = new Scanner(System.in);
  System.out.print("\n Enter host name: ");
  host = input.nextLine();
   try {
            InetAddress[] myHost = InetAddress.getAllByName(host);
            for(InetAddress Host:myHost){
                System.out.println(Host.getHostAddress());
            }
        } catch (UnknownHostException ex) {
           System.out.println("Could not find " + host);
        }
  }
  else
  {
  Scanner input = new Scanner(System.in);
  System.out.print("\n Enter IP address: ");
  host = input.nextLine();
  try {
   InetAddress address = InetAddress.getByName(host);
   System.out.println(address.getHostName());   
  }
  catch (UnknownHostException ex) {
       System.out.println("Could not find " + host);
  }
  }
  
 }
}
