import java.util.Scanner;

public class Subnet_6_3317 {
    public static void main(String []args){
        Scanner scn = new Scanner(System.in);

        //Taking ip address
        System.out.print("Enter IP address: ");
        String ip = scn.next();

        //Converting ip to binary representation
        String binaryIP = convertToBinary(ip);

        //Printing ip address in binary dot notation
        System.out.print("Binary IP: ");
        printBinary(binaryIP);
        System.out.print("\n\n");

        //Taking network mask
        System.out.print("Enter Network Mask: ");
        int maskbSubnetting = scn.nextInt();

        //Creating the Subnet Mask and printing it
        String NetworkMask = "1".repeat(maskbSubnetting) + "0".repeat(32-maskbSubnetting);
        System.out.print("Network Mask   ");        printBinary(NetworkMask);
        System.out.print("\n");

        //Taking input the number of subnetworks required
        System.out.print("Enter number of subnets required: ");        int subnetCount = scn.nextInt();

        //Calculating total number of addresses in the network
        int totalAddresses = (int)Math.pow(2, 32-maskbSubnetting);

        //Calculating maximum number of addresses per subnet
        int addressesPerSubnet = (totalAddresses+subnetCount-1)/subnetCount;

        int addressesPerSubnetPowerOf2 = (int)Math.pow(2,log2(addressesPerSubnet));
        
        //Calculating the mask after subnetting     nsub = 32 - log2(addresses in each subnet)
        int maskAsubnetting = 32 - log2(addressesPerSubnetPowerOf2);

        //creating mask after subnetting and printing it       
        String MaskAfterSubnetting = "1".repeat(maskAsubnetting)+"0".repeat(32-maskAsubnetting);
        System.out.print("Subnet Mask After Subnetting: ");        printBinary(MaskAfterSubnetting);
        System.out.print("      ");                                printInDecimalForm(MaskAfterSubnetting);
        System.out.print("\n");

        //Creating network id by keeping number of bits equal to Network bits as it is and rest bits equal to zero
        String networkId = binaryIP.substring(0,maskbSubnetting) + "0".repeat(32-maskbSubnetting);
        System.out.print("Network ID    ");        printBinary(networkId);
        System.out.print("\n\n");
        
        //Setting starting address equal to network ID
        String startingAddress = networkId;


        int perSubnet = addressesPerSubnetPowerOf2 -1;

        for(int i=1;i<=subnetCount;i++){
            System.out.println("The Group "+i+" IP addresse ranges are");
            //Printing starting address in binary and decimal form
            printBinary(startingAddress); System.out.print("      ");  printInDecimalForm(startingAddress); 
            System.out.println();  

            System.out.println("To");

            //calculating lastaddress by adding (number of addresses in the subnet - 1)
            String lastAddress = add(startingAddress,perSubnet);

            //Printing last address of the subnet in binary and decimal form
            printBinary(lastAddress);  System.out.print("      ");     printInDecimalForm(lastAddress);
            System.out.print("\n\n");

            //Adding 1 to get the starting address of next subnetwork
            startingAddress = add(lastAddress,1);

        }


    }

    //converting to binary
    public static String convertToBinary(String ip){
        String str[] = ip.split("\\.");
        String binaryIP="";
        for(int i=0;i<str.length;i++){
            int num = Integer.parseInt(str[i]);
            String temp = Integer.toBinaryString(num);
            temp = "0".repeat(8-temp.length())+temp;
            binaryIP+=temp;
        }
        return binaryIP;
    }

    //printing binary
    public static void printBinary(String ip){
        for(int i=0;i<ip.length();i++){
            if(i%8==7){
                System.out.print(ip.charAt(i)+".");
            }else{
                System.out.print(ip.charAt(i));
            }                 
        }
           
    }

    //calculating log base 2
    public static int log2(int N) 
	{ 
		int result = (int)(Math.log(N) / Math.log(2)); 
		return result; 
    }

    //printing ip address in decimal notation
    public static void printInDecimalForm(String ip){
        String DecimalIP = "";
        for(int i=0;i<32;i+=8){
            String octet = ip.substring(i,i+8);
            DecimalIP = DecimalIP + Integer.parseInt(octet,2)+".";
        }
        System.out.print(DecimalIP);
    }

    // adding binary strings
    public static String add(String a,int n){
        String b = Integer.toBinaryString(n);
        int sum=0,carry=0;
        String result ="";
        for(int i=a.length()-1, j = b.length()-1;i>=0 || j>=0;i--,j--){
            sum = (a.charAt(i)-'0') + (j>=0?(b.charAt(j)-'0'):0)+carry;
            if(sum==3){
                result = "1" + result;
                carry =1;
            }else if(sum==2){
                result = "0" + result;
                carry = 1;
            }else if(sum==1){
                result = "1" + result;
                carry = 0;
            }else if(sum==0){
                result = "0" + result;
                carry = 0;
            }
        }
        if(carry==1){
            result = "1" + result;
        }
        return result;
    }

}
