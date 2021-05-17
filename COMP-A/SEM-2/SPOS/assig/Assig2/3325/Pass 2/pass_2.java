import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.util.*;

class Pass2 {
	static String[] opcodes = {"DC", "DS", "START", "END", "ORIGIN", "EQU", "LTORG", "STOP", "ADD", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT"};
	
	// Generating the opecode map
	
	static Map<String, Integer> opcodeValue = new HashMap<String, Integer>();
	
	public static void generateOpcodeValue(){
		// Declarative statements
		opcodeValue.put("DC", 1);
		opcodeValue.put("DS", 2);
		
		// Assembler Directives
		opcodeValue.put("START", 1);
		opcodeValue.put("END", 2);
		opcodeValue.put("ORIGIN", 3);
		opcodeValue.put("EQU", 4);
		opcodeValue.put("LTORG", 5);
		
		// Imperative Statements
		opcodeValue.put("STOP", 0);
		opcodeValue.put("ADD", 1);
		opcodeValue.put("SUB", 2);
		opcodeValue.put("MULT", 3);
		opcodeValue.put("MOVER", 4);
		opcodeValue.put("MOVEM", 5);
		opcodeValue.put("COMP", 6);
		opcodeValue.put("BC", 7);
		opcodeValue.put("DIV", 8);
		opcodeValue.put("READ", 9);
		opcodeValue.put("PRINT", 10);
	}
	
	//--------------------------------------------------------------------
	
	// Intermediate code declarations
	static Vector<Integer> lcValue = new Vector<Integer>();
	
	// **************************
	static class OpcodeType{
		String type;
		int code;
		OpcodeType(){
			type = "";
			code = -1;	
		}	
	}
	
	static Vector<OpcodeType> codeType = new Vector<OpcodeType>();
	
	//***************************
	
	static Vector<Integer> register = new Vector<Integer>();
	
	//***************************
	
	static class OperandType{
		String type;
		int value;
		
		OperandType(){
			type = "";
			value = -1;	
		}
	}
	
	static Vector<OperandType> operandType = new Vector<OperandType>();
	
	//--------------------------------------------------------------------
	
	// Declaring Symbol table
	static class Symbol{
		String symbol;
		int address;
		
		Symbol(){
			symbol = "";
			address = -1;	
		}	
	}
	
	static Vector<Symbol> symbolTable = new Vector<Symbol>();
	
	//--------------------------------------------------------------------
	
	// Declaring Literal Table
	static class Literal{
		int literal;
		int address;
		
		Literal(){
			literal = -1;
			address = -1;	
		}	
	}
	
	static Vector<Literal> literalTable = new Vector<Literal>();
	
	//--------------------------------------------------------------------
	
	// Declaring Pool Table
	static class Pool{
		int pool;
		int count;	
		
		Pool(){
			pool = -1;
			count = -1;	
		}
	}
	
	static Vector<Pool> poolTable = new Vector<Pool>();
	
	//--------------------------------------------------------------------
	
	public static void printSymbolTable(){
		int n = symbolTable.size();
		System.out.println(String.format("%s  |  %s  |  %s", center("S.No", 5), center("SYMBOL", 15), center("ADDRESS", 10)));	
		System.out.println("----------------------------------------------");	
		
		for(int i = 0 ; i < n ; i++){
			System.out.println(String.format("%-5d  |  %-15s  | %-10d", i, symbolTable.get(i).symbol, symbolTable.get(i).address));	
			System.out.println("----------------------------------------------");
		}
	}
	
	public static void printPoolTable(){
		int n = poolTable.size();
		System.out.println(String.format("%s  |  %s  |  %s", center("S.No", 5), center("POOL", 15), center("COUNT", 10)));	
		System.out.println("----------------------------------------------");	
		
		for(int i = 0 ; i < n ; i++){
			System.out.println(String.format("%-5d  |  %-15d  | %-10d", i, poolTable.get(i).pool, poolTable.get(i).count));	
			System.out.println("----------------------------------------------");
		}
	}
	
	public static void printLiteralTable(){
		int n = literalTable.size();
		System.out.println(String.format("%s  |  %s  |  %s", center("S.No", 5), center("Literal", 15), center("ADDRESS", 10)));	
		System.out.println("----------------------------------------------");	
		
		for(int i = 0 ; i < n ; i++){
			System.out.println(String.format("%-5d  |  %-15d  | %-10d", i, literalTable.get(i).literal, literalTable.get(i).address));	
			System.out.println("----------------------------------------------");
		}
	}
	
	public static void printHorizontalLine(){
		System.out.println("\033[0m------------------------------------------------------------------------------------------------------------------------------    ------------------------------------------\033[1m");		
	}
	
	public static void printTableWithIC(Vector<String[]> table){
		int ltorgCnt = 0;
		
		System.out.println(String.format("\033[1m%s  =>  %s\n", center("PASS - 1", 125), center("PASS - 2", 35)));		
		
		printHorizontalLine();
		
		System.out.println(String.format("%s  |  %s  |  %s  |  %s  |  %s  |  %s  |  %s  =>  %s  |  %s  |  %s", center("LABEL", 15), center("OPCODE", 15), center("OPERANDS", 15), center("LC", 10), center("CODE", 15), center("REGISTER", 10), center("OPERAND_TYPE", 15), center("CODE", 6), center("REGISTER", 10), center("ADDRESS", 10)));		
		
		printHorizontalLine();
		
		int n = table.size();
		for(int i = 0 ; i < n ; i++){
			String lcResult = "LC = " + Integer.toString(lcValue.get(i));
			
			String codeResult = "";
			if(!codeType.get(i).type.equals("")){
				codeResult = "(" + codeType.get(i).type + " , " + codeType.get(i).code + ")";	
			}
			
			String registerResult = "";
			if(register.get(i) != -1){
				registerResult = Integer.toString(register.get(i));	
			}
			
			String operandResult = "";
			if(operandType.get(i).value != -1){
				operandResult = "(" + operandType.get(i).type + " , " + operandType.get(i).value + ")";	
			}
			
			String opCode = table.get(i)[1];
			String machineCode = "", machineRegister = "", machineValue = "";
			if(!opCode.equals("START") && !opCode.equals("END") && !opCode.equals("EQU") && !opCode.equals("DS") && !opCode.equals("DC")){
				if(opCode.equals("LTORG")){
					// machine code
					machineCode = "0";
					
					// machine register
					machineRegister = "0";	
					
					// machine Address
					int start = poolTable.get(ltorgCnt).pool;
					int end = poolTable.get(ltorgCnt).pool + poolTable.get(ltorgCnt).count;
					//machineValue = Integer.toString(start) + ", " + Integer.toString(end);
					for(int j = start ; j < end ; j++){
						machineValue += "'" + literalTable.get(j).literal + "'";
						if(j != end-1){
							machineValue += ", ";	
						}	
					}
					
					//---------------------------
					ltorgCnt++;
				}
				else{
					// machine Code
					machineCode = Integer.toString(codeType.get(i).code);
					
					// machine Register
					if(register.get(i) != -1){
						machineRegister = Integer.toString(register.get(i));	
					}
					else{
						machineRegister = "0";	
					}
					
					// machine Address or Value
					if(operandType.get(i).value != -1){
						if(operandType.get(i).type.equals("L")){
							machineValue = Integer.toString(literalTable.get(operandType.get(i).value).address);
						}
						else if(operandType.get(i).type.equals("S")){
							machineValue = Integer.toString(symbolTable.get(operandType.get(i).value).address);
						}
					}
				}
			}
			
			System.out.println(String.format("%-15s  |  %-15s  |  %-15s  |  %-10s  |  %-15s  |  %-10s  |  %-15s  =>  %s  |  %s  |  %s", table.get(i)[0], table.get(i)[1], table.get(i)[2], lcResult, codeResult, registerResult, operandResult, center(machineCode, 6), center(machineRegister, 10), center(machineValue, 10)));
			
			printHorizontalLine();
		}
		
		System.out.println("\n\n");
		System.out.println("SYMBOL TABLE : \n");
		printSymbolTable();
		
		System.out.println("\n\n");
		System.out.println("LITERAL TABLE : \n");
		printLiteralTable();
		
		System.out.println("\n\n");
		System.out.println("POOL TABLE : \n");
		printPoolTable();
	}
	
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
	
	public static String[] seperate(String data){
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
		
		if(ans[1].equals("DC") || ans[1].equals("DS") || ans[1].equals("EQU")){
			ans[2] = ans[0] + " , " + ans[2];
			ans[0] = "";	
		}
		
		return ans;
	}
	
	public static Vector<String> seperateOperands(String data){
		Vector<String> ans = new Vector<String>();
		
		int n = data.length();
		String temp = "";
		
		for(int i = 0 ; i < n ; i++){
			char x = data.charAt(i);
			
			if(!checkSpace(x) && x!= ','){
				temp += x;	
			}
			
			if(checkSpace(x) || x == ',' || i == n-1){
				if(temp.length() > 0){
					ans.add(temp);	
				}
				
				temp = "";
			}	
		}
		
		return ans;	
	}
	
	public static Vector<String> seperateOperandsSpecial(String data){
		Vector<String> ans = new Vector<String>();
		
		int n = data.length();
		String temp = "";
		
		for(int i = 0 ; i < n ; i++){
			char x = data.charAt(i);
			
			if(!checkSpace(x) && x != '+' && x != '-'){
				temp += x;	
			}	
			
			if(x == '+' || x == '-' || i == n-1){
				if(temp.length() > 0){
					ans.add(temp);	
				}
				
				temp = "";
			}
			
			if(x == '+')
				ans.add("+");
			else if(x == '-')
				ans.add("-");	
		}
		
		return ans;		
	}
	
	public static void addLable(String lable, int lc){
		if(lable.length() == 0)
			return;
		
		Symbol tempSymbol = new Symbol();
		tempSymbol.symbol = lable;
		tempSymbol.address = lc;
		
		symbolTable.add(tempSymbol);
	}
	
	public static int updateLiterals(Queue<Integer> pendingLiterals, int lc){
		while(pendingLiterals.size() != 0){
			Literal tempLiteral = new Literal();
			int literal = pendingLiterals.remove();
			tempLiteral.literal = literal;
			tempLiteral.address = lc++; 
			
			literalTable.add(tempLiteral); 
		}
		
		return lc;
	}
	
	public static int updatePoolTable(int literalCnt, int poolCnt){
		Pool tempPool = new Pool();
		tempPool.pool = poolCnt;
		tempPool.count = literalCnt;
		
		poolTable.add(tempPool);
		
		poolCnt += literalCnt;
		return poolCnt;
	}
	
	public static void updateSymbolTable(String symbol, int lc){
		for(Symbol item : symbolTable){
			if(item.symbol.equals(symbol)){
				item.address = lc;	
			}	
		}
	}
	
	public static void addRegister(String reg){
		switch(reg){
			case "AREG" :{
				register.add(1);
				break;	
			}
			case "BREG" :{
				register.add(2);
				break;	
			}
			case "CREG" :{
				register.add(3);
				break;	
			}
			case "DREG" :{
				register.add(4);
				break;	
			}	
		}	
	}
	
	public static void addSymbolOrLiteral(String data,Queue<Integer> pendingLiterals){
		if(data.charAt(0) == '='){ // then it is a literal
			String temp = "";
			for(int i = 0 ; i < data.length() ; i++){
				char x = data.charAt(i);
				if(x >= '0' && x <= '9'){
					temp += x;	
				}	
			}
			
			int x = Integer.parseInt(temp);
			
			int pos = -1;
			int index = 0;
			for(Integer item : pendingLiterals){
				if(item == x){
					pos = index;
					break; 	
				}
				index++;
			}
			
			if(pos == -1){
				pendingLiterals.add(x);
				pos = pendingLiterals.size()-1;	
			}
			
			OperandType tempOperandType = new OperandType();
			tempOperandType.type = "L";
			tempOperandType.value = literalTable.size() + pos;
			
			operandType.add(tempOperandType);
		}	
		else{ // it is a symbol
			int pos = -1;
			int index = 0;
			for(Symbol item : symbolTable){
				if(item.symbol.equals(data)){
					pos = index;
					break; 	
				}
				index++;
			}
			
			if(pos == -1){
				Symbol tempSymbol = new Symbol();
				tempSymbol.symbol = data;
				
				symbolTable.add(tempSymbol);
				pos = symbolTable.size()-1;
			}
			
			OperandType tempOperandType = new OperandType();
			tempOperandType.type = "S";
			tempOperandType.value = pos;
			
			operandType.add(tempOperandType);
		}
	}
	
	
	public static void generateIc(Vector<String[]> table){
		int n = table.size();
		int lc = 0;
		int poolCnt = 0;
		
		Queue<Integer> pendingLiterals = new LinkedList<>();
		
		for(int i = 0 ; i < n ; i++){
			String[] arr = table.get(i);
			
			addLable(arr[0], lc);
			
			OpcodeType tempOpcodeType = new OpcodeType();
			OperandType tempOperandType = new OperandType();
			
			switch(arr[1]){
				case "DC":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					updateSymbolTable(temp.get(0), lc);
					
					tempOpcodeType.type = "DL";
					tempOpcodeType.code = 1;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					tempOperandType.type = "c";
					String tempValue = "";
					for(int j = 1 ; j < temp.get(1).length()-1 ; j++){
						tempValue += temp.get(1).charAt(j);	
					}
					tempOperandType.value = Integer.parseInt(tempValue);
					operandType.add(tempOperandType);
					
					lc++;
					
					break;	
				}
				case "DS":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					updateSymbolTable(temp.get(0), lc);
					
					int value = Integer.parseInt(temp.get(1));
					
					tempOpcodeType.type = "DL";
					tempOpcodeType.code = 2;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					tempOperandType.type = "c";
					tempOperandType.value = value;
					operandType.add(tempOperandType);
					
					lc += value;
					
					break;	
				}
				
				//-----------------------------
				
				case "START":{
					lcValue.add(lc);
					
					tempOpcodeType.type = "AD";
					tempOpcodeType.code = 1;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					lc = Integer.parseInt(arr[2]);
					
					break;	
				}
				case "END":{
					lcValue.add(lc);
					
					tempOpcodeType.type = "AD";
					tempOpcodeType.code = 2;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					// updating the poolTable
					poolCnt = updatePoolTable(pendingLiterals.size(), poolCnt);
					
					// updating the literalTable
					lc = updateLiterals(pendingLiterals, lc);
					
					break;	
				}
				case "ORIGIN":{
					Vector<String> temp = seperateOperandsSpecial(arr[2]);
					//System.out.println(temp);
					lcValue.add(lc);
					
					tempOpcodeType.type = "AD";
					tempOpcodeType.code = 3;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					//updating the lc
					if(temp.size() == 3){
						boolean flag = false;
						
						int operand1 = 0;
						
						for(int j = 0 ; j < symbolTable.size() ; j++){
							if(temp.get(0).equals(symbolTable.get(j).symbol)){
								flag = true;
								operand1 = symbolTable.get(j).address;
								tempOperandType.type = "S";	
								tempOperandType.value = j;
							}
						}
						if(!flag){
							operand1 = Integer.parseInt(temp.get(0));	
						}
						
						int operand2 = 0;	
						flag = false;
						for(int j = 0 ; j < symbolTable.size() ; j++){
							if(temp.get(2).equals(symbolTable.get(j).symbol)){
								flag = true;
								operand2 = symbolTable.get(j).address;	
							}
						}
						if(!flag){
							operand2 = Integer.parseInt(temp.get(2));	
						}
						
						lc = operand1 + operand2;
					}
					else{
						boolean flag = false;
						
						int operand1 = 0;
						
						for(int j = 0 ; j < symbolTable.size() ; j++){
							if(temp.get(0).equals(symbolTable.get(j).symbol)){
								flag = true;
								operand1 = symbolTable.get(j).address;
								tempOperandType.type = "S";	
								tempOperandType.value = j;	
							}
						}
						if(!flag){
							operand1 = Integer.parseInt(temp.get(0));
							
							tempOperandType.type = "c";	
							tempOperandType.value = operand1;
						}
						
						lc = operand1;
					}
					
					operandType.add(tempOperandType);
					
					break;	
				}
				case "EQU":{
					
					Vector<String> temp = seperateOperands(arr[2]);
					
					int operand2 = 0;	
					boolean flag = false;
					for(int j = 0 ; j < symbolTable.size() ; j++){
						if(temp.get(1).equals(symbolTable.get(j).symbol)){
							flag = true;
							operand2 = symbolTable.get(j).address;	
						}
					}
					if(!flag){
						operand2 = Integer.parseInt(temp.get(1));	
					}
					
					for(int j = 0 ; j < symbolTable.size() ; j++){
						if(temp.get(0).equals(symbolTable.get(j).symbol)){
							symbolTable.get(j).address = operand2;
						}
					}
					
					// -------------------------------------------------
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "AD";
					tempOpcodeType.code = 4;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					lc++;
					
					break;	
				}
				case "LTORG":{
					lcValue.add(lc);
					
					tempOpcodeType.type = "AD";
					tempOpcodeType.code = 5;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					// updating the poolTable
					poolCnt = updatePoolTable(pendingLiterals.size(), poolCnt);
					
					// updating the literalTable
					lc = updateLiterals(pendingLiterals, lc);
					
					break;	
				}
				
				//-----------------------------------
				
				case "STOP":{
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 0;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					lc++;
					break;	
				}
				case "ADD":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 1;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "SUB":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 2;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "MULT":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 3;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "MOVER":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 4;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					break;	
				}
				case "MOVEM":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 5;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "COMP":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 6;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "BC":{
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 7;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					lc++;
					
					break;	
				}
				case "DIV":{
					Vector<String> temp = seperateOperands(arr[2]);
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 8;
					codeType.add(tempOpcodeType);
					
					addRegister(temp.get(0));
					
					addSymbolOrLiteral(temp.get(1), pendingLiterals);
					
					lc++;
					
					break;	
				}
				case "READ":{
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 9;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					operandType.add(tempOperandType);
					
					lc++;
					
					break;	
				}
				case "PRINT":{
					String temp = arr[2];
					
					lcValue.add(lc);
					
					tempOpcodeType.type = "IS";
					tempOpcodeType.code = 10;
					codeType.add(tempOpcodeType);
					
					register.add(-1);
					
					boolean flag = false;
					int index = 0;
					for(Symbol item : symbolTable){
						if(item.symbol.equals(temp)){
							flag = true;
							break;	
						}
						index++;
					}
					
					if(flag){
						tempOperandType.type = "S";
						tempOperandType.value = index;	
					}
					else{
						tempOperandType.type = "C";
						tempOperandType.value = Integer.parseInt(temp);	
					}
					
					operandType.add(tempOperandType);
					
					lc++;	
					
					break;	
				}
				
			}	
		}
	}
	
	public static void main(String[] args) {
		
		try{
			
			generateOpcodeValue();
			
			File myFile = new File("AssemblyFile.txt");
			Scanner myReader = new Scanner(myFile);
			
			Vector<String[]> table = new Vector<String[]>();
			
			while(myReader.hasNextLine()){
				String data = myReader.nextLine(); // reads each line in the file one by one
				
				table.add(seperate(data));
			}
			
			generateIc(table);
			
			printTableWithIC(table);
				
		}catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
