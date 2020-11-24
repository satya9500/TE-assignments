import java.util.Scanner;

class Subnet{
	public static String intToBinary(int n){ // converts integer to binary form
		String s = "";
		while(n > 0){
			if(n%2 == 1){
				s = '1' + s;
			}
			else{
				s = '0' + s;
			}
			
			n /= 2;
		}
		
		n = s.length();
		n = 8-n;
		
		while(n > 0){
			s = '0' + s;
			n--;
		}
		
		return s;
	}

	public static String getBinaryIp(String ip){	// converts integer ip to binary ip
		String binaryIp = "";
		
		int num = 0;
		int n = ip.length();
		for(int i = 0 ; i < n ; i++){
			char c = ip.charAt(i);
			
			if(c == '.'){
				binaryIp += intToBinary(num) + " ";
				num = 0;
			}	
			else{
				num = (num*10) + (int)(c-'0');
			}
		}
	
		binaryIp += intToBinary(num);
		
		return binaryIp;
	}
	
	public static String getNetworkMask(int n){		// generates network mask
		String networkMask = "";
		int cnt = 0 , x = 0;
		for(int i = 1 ; i <= 32 ; i++){
			if(i <= n){
				networkMask += '1';
			}
			else{
				networkMask += '0';
			}
			
			x++;
			
			if(x == 8){
				cnt++;
				if(cnt < 4){
					networkMask += '.';
				}
				x = 0;
			}
		}
		
		return networkMask;
	}
	
	public static int getReservedBits(int n){	// return the bo. of bits needed to create subnets
		int cnt = 0;
		while(n > 0){
			n /= 2;
			cnt++;
		}
		
		return cnt;
	}
	
	public static String generate(String ip , int mask , String subnet , char append){	// generates the subnet
		String s = "";
		int n = subnet.length();
		
		int cnt = 0 , x = 0;
		for(int i = 1 ; i <= 35 ; i++){
			if(i - cnt <= mask){
				if(ip.length() == 0){
					s += '1';
				}
				else{
					s += ip.charAt(i-1);
				}
			}
			else if(i - cnt <= mask+n){
				s += subnet.charAt(i-cnt-mask-1);
			}
			else{
				s += append;
			}
			
			x++;
			
			if(x == 8){
				cnt++;
				if(cnt < 4){
					s += '.';
				}
				x = 0;
				i++;
			}
		}
		
		return s;
	}
	
	public static String intToString(int n){	// converts integer to string
		String s = "";
		while(n > 0){
			s = (char)((n%10) + '0') + s;
			n /= 10;
		}
		if(s == "")
			s = "0";
		return s;
	}
	
	public static String binaryToInt(String s){		//converts binary to integer ip
		int n = s.length() , cnt = 7 , num = 0;
		
		String res = "";
		
		for(int i = 0 ; i < n ; i++){
			if(s.charAt(i) == '.'){
				res += intToString(num) + '.';

				cnt = 7;
				num = 0;
				
				continue;
			}
			
			if(s.charAt(i) == '1'){
				num = num + (int)Math.pow(2, cnt);
			}
			
			cnt--;
		}
		
		res += intToString(num);					
		
		return res;
	}
	
	public static String getNetworkId(String ip , int mask){	// generates networkid
		String s = "";
		int n = ip.length() , cnt = 0;
		
		for(int i = 0 ; i < n ; i++){
			
			if(cnt < mask){
				s += ip.charAt(i);
			}
			else{
				if(ip.charAt(i) != ' '){
					s += '0';
				}
				else{
					s += " ";
				}	
			}	
			
			if(ip.charAt(i) != ' '){
				cnt++;
			}	
		}
		
		return s;
	}
	
	public static void main(String []args){
		Scanner read = new Scanner(System.in);

        //Taking input for IP-Address
        System.out.print("Enter IP address: ");
        String ip = read.next();

		String binaryIp = getBinaryIp(ip);	
		
		System.out.println("Binary form of Ip : " + binaryIp);
		
		//Taking input for mask
		System.out.print("\nEnter Network Mask: ");
        int mask = read.nextInt();
		
		String networkMask = getNetworkMask(mask);	
		
		System.out.println("\nNetwork Mask : " + networkMask);
		
		System.out.print("\nEnter the number of subnets required : ");
		int subnetCnt = read.nextInt();
		
		//calculating the reserved bits for subnet
		int reservedBits = getReservedBits(subnetCnt-1); // as we are starting from 0 so we need only the bit count for subnetCnt-1
		
		String temp = "" , temp2 = "";
		for(int i = 0 ; i < reservedBits ; i++){
			temp += '1';
		}
		
		String subnetMask = generate(temp2 , mask , temp , '0');
		
		System.out.println("-----------------------------------------------------------------");
		
		System.out.println("\nSubnet Mask after subnetting :\n"  + subnetMask);
		
		//Binary to int ip conversion
		String intIp = binaryToInt(subnetMask); 
		System.out.println(intIp);
		
		//generating the network id
		String networkId = getNetworkId(binaryIp , mask);
		System.out.println("\nNetwork ID : " + networkId);
		
		int count = 0;
		for(int i = 0 ; i < subnetCnt ; i++){
			System.out.println("-----------------------------------------------------------------");
			System.out.println("\nGroup - " + ++count + " :");
			
			String noOfSubnet = intToBinary(i);
			temp = "";
			temp2 = "";
			for(int j = 0 ; j < binaryIp.length() ; j++){
				if(binaryIp.charAt(j) != ' ')
					temp += binaryIp.charAt(j);
				else
					temp += '.';
			}	
				
			int j = 7;
			while(temp2.length() < reservedBits){
				temp2 = noOfSubnet.charAt(j--)+ temp2;
			}
			
			//from
			String binaryFormIp = generate(temp , mask + reservedBits, temp2 , '0');
			System.out.println(binaryFormIp + "         " + binaryToInt(binaryFormIp));
			System.out.println("                       TO                               ");
			
			//To
			binaryFormIp = generate(temp , mask + reservedBits, temp2 , '1');
			System.out.println(binaryFormIp + "         " + binaryToInt(binaryFormIp));
		}
		
	}
	
}














