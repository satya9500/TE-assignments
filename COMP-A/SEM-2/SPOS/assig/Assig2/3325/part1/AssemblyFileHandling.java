import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.util.*;

class AFH {
	public static String center(String text, int len){
		String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
		float mid = (out.length()/2);
		float start = mid - (len/2);
		float end = start + len; 
		return out.substring((int)start, (int)end);
	}
	
	public static boolean checkSpace(char x){
		if((int)x == 9 || (int)x == 32){
			return true;	
		}
		return false;
	}
	
	public static void main(String[] args) {

		String[] opcodes = {"DC", "DS", "START", "END", "ORIGIN", "EQU", "LTORG", "STOP", "ADD", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT"};
		
		try{
			//System.out.println(String.format("%-20s  |  %-20s  |  %-20s", "LABEL", "OPCODE", "OPERANDS"));
			System.out.println(String.format("%s  |  %s  |  %s", center("LABEL", 20), center("OPCODE", 20), center("OPERANDS", 20)));
			System.out.println("------------------------------------------------------------------------------");
			
			File myFile = new File("AssemblyFile.txt");
			Scanner myReader = new Scanner(myFile);
			
			while(myReader.hasNextLine()){
				String data = myReader.nextLine(); // reads each line in the file one by one
				String temp = "";
				int n = data.length();
				//System.out.println(" --> "+ data);
				boolean flag = true, opcodeVisited = false;
				int index = 0;
				String[] ans = new String[3];
				ans[0] = ans[1] = ans[2] = "";
				
				Vector<String> oc = new Vector<String>();
				
				for(int i = 0 ; i < n ; i++){
					char x = data.charAt(i);
					if(!checkSpace(x) || flag == false)
						temp += x;
					
					if((checkSpace(x) || i == n-1) && flag && temp.length() > 0){
						//System.out.println(temp + opcodes[7]);
						boolean contains = false;
						for(int j = 0 ; j < opcodes.length ; j++){
							if(opcodes[j].equals(temp))
								contains = true;	
						}
						
						//System.out.println(contains);
						
						if(contains){ // if the line contains the opcode
							ans[1] = temp;
							temp = "";
							opcodeVisited = true;
						}
						else if(opcodeVisited){
							ans[2] = temp;	
						}
						else{
							ans[0] = temp;
							oc.add(temp);
							temp = "";	
						}
					}
					
					if(!flag){
						ans[2] = temp;	
					}
					
					if(x == ','){
						flag = false;	
					}
				}
				
				System.out.println(String.format("%-20s  |  %-20s  |  %-20s", ans[0], ans[1], ans[2]));
				System.out.println("------------------------------------------------------------------------------\n");
			}
			
				
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
