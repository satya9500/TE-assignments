#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>
#include<fstream>

using namespace std;

#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>


#define MAX 1024
#define port 5200

void copy(char buff[] , string msg){
	int n = msg.size();
	
	for(int i = 0 ; i < n ; i++)
		buff[i] = msg[i];
		
	buff[n] = '\0';
}

bool check(char buff[]){
	int i = 0;
	if(buff[0] == '-'){
		i = 1;
	}

	for( ; buff[i] != '\0' && i < 9 ; i++){
		if(buff[i] < '0' || buff[i] > '9'){
			return false;
		}
	}
	
	return true;
}

int convert(char buff[]){
	int num = 0 , i = 0 , flag = 0;
	
	if(buff[0] == '-'){
		i = 1;
		flag = 1;
	}
	
	for( ; buff[i] != '\0' && i < 9 ; i++){
		num = num*10 + (int)(buff[i] - '0');
	}
	
	if(flag)
		num = -num;
	
	return num;
}

int main(){
	int serverId = socket(AF_INET , SOCK_STREAM , 0);
	
	if(serverId < 0){
		cout << "Server Creation Failed..!" << endl;
		return 0;
	}	
	
	struct sockaddr_in serverAddr , clientAddr;
	
	//specifying address and type for the server to operate under
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(port);
	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	int bindStatus = bind(serverId , (struct sockaddr*) & serverAddr , sizeof(serverAddr)); // binding the socket created to the port
	
	if(bindStatus < 0){
		cout << "Socket binding has failed" << endl;
		close(serverId);
		return 0;
	}
	
	int listenStatus = listen(serverId , 5); //listen to the client while others are waiting in queue of size 5 
	
	if(listenStatus < 0){	// when queue is full listen fails
		cout << "Listner has failed" << endl;
		close(serverId);
		return 0;
	}
	
	cout << "...Waiting for connections \n\n";
	
	socklen_t len = sizeof(clientAddr);
	int connection;
	if((connection = accept(serverId , (struct sockaddr*) & clientAddr , &len)) < 0){
		cout << "Server didn't accept the request." << endl;
		close(serverId);
		return 0;
	}
	else{
		cout << "Server accepted the request. \n" << endl;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	string msg;			// declarations
	char buff[MAX];
	int rMsgSize , num1 , num2 , choice;
	
	//--------------------------------------------------------------------------------------------------------------------
	
	label:
	
	cout << "**************Calculater started****************" << endl;
	cout << "....asking for the operation from the client!" << endl;
	
	while(true){
	
		msg = "*********Welcome to Arithmetic calculator!*********** \n Choose the Operation to be performed bellow : \n 1)Addition (+) \n 2)Subtraction (-) \n 3)Division (/) \n 4)Multiplication (*) \n Enter your choice (1/2/3/4) : ";	// sending menue message
		
		copy(buff , msg);
			
		send(connection , buff , strlen(buff)+1 , 0);
		rMsgSize  = recv(connection , buff , MAX , 0);
		
		if(rMsgSize <= 0){
			cout << "Error..! operation type didn't receive" << endl;
			msg = "Error";																								//##
			copy(buff , msg);
			
			send(connection , buff , strlen(buff)+1 , 0);
			close(serverId);
			return 0;
		}
			
		cout << "Received operation type : " << buff << "\n\n";
		
		if(!check(buff) || convert(buff) < 1 || convert(buff) > 4){
			msg = "Error..! Invalid Operation, Please choose a valid Integer operation!";										//##
			copy(buff , msg);
		
			send(connection , buff , strlen(buff)+1 , 0);
			rMsgSize  = recv(connection , buff , MAX , 0);
			
			continue; 
		}

		choice = convert(buff);
		break;
		
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
																			// asking for the first operand
	while(true){
		
		cout << "....asking for the first operand from the client!" << endl;
		msg = "First Operand : ";	
		 
		copy(buff , msg);
		
		send(connection , buff , strlen(buff)+1 , 0);
		rMsgSize  = recv(connection , buff , MAX , 0);
		
		if(rMsgSize <= 0){
			cout << "Error..! first operand didn't receive" << endl;
			msg = "Error";																								//##
			copy(buff , msg);
			
			send(connection , buff , strlen(buff)+1 , 0);
			close(serverId);
			return 0;
		}
		
		cout << "Received First operand : " << buff << "\n\n";
		
		if(!check(buff)){
			msg = "Error..! Invalid Operand, Please choose a valid Integer!";													//##
			copy(buff , msg);
		
			send(connection , buff , strlen(buff)+1 , 0);
			rMsgSize  = recv(connection , buff , MAX , 0);
			
			continue; 
		}

		num1 = convert(buff);
		break;
	}
		
	//--------------------------------------------------------------------------------------------------------------------
	
																			// asking for the second operand
	while(true){
		
		cout << "....asking for the second operand from the client!" << endl;
		msg = "Second Operand : ";	
		 
		copy(buff , msg);
		
		send(connection , buff , strlen(buff)+1 , 0);
		rMsgSize  = recv(connection , buff , MAX , 0);
		
		if(rMsgSize <= 0){
			cout << "Error..! second operand didn't receive" << endl;
			msg = "Error";																								//##
			copy(buff , msg);
			
			send(connection , buff , strlen(buff)+1 , 0);
			close(serverId);
			return 0;
		}
		
		cout << "Received Second operand : " << buff << "\n\n";
		
		if(!check(buff)){
			msg = "Error..! Invalid Operand, Please choose a valid Integer!";													//##
			copy(buff , msg);
		
			send(connection , buff , strlen(buff)+1 , 0);
			rMsgSize  = recv(connection , buff , MAX , 0);
			
			continue; 
		}

		num2 = convert(buff);
		break;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
																				// calculating and sending the ans
	int ans;
	switch(choice){
		case 1: ans = num1+num2;
				break;
		case 2: ans = num1-num2;
				break;
		case 3: ans = num1/num2;
				break;
		case 4: ans = num1*num2;
				break;
	}				
	
	cout << "Calculated ans : " << ans << "\n\n";
	cout << "...Sending ans to the client!" << endl;
	
	int flag = 0;
	if(ans < 0){						// removing the '-' sign if number is < 0 before converting to string
		flag = 1;
		ans = -ans;
	}
	
	 
	msg = "";
	if(ans == 0)
	msg = "0";
	while(ans){							// converting integer to string to send
		msg = (char)((ans%10) + '0') + msg;
		ans /= 10;
	}
	
	if(flag){							// if the ans was < 0 , add '-' sign in front of it 
		msg = "-" + msg;
	}
	
	copy(buff , msg);
		
	send(connection , buff , strlen(buff)+1 , 0);
	rMsgSize  = recv(connection , buff , MAX , 0);
	
	if(rMsgSize > 0){
		cout << "Ans sent successfully to the client\n" << endl;
	}
	else{
		cout << "Error Sending the ans..!" << endl;
		msg = "Error";																									//##
		copy(buff , msg);
		
		send(connection , buff , strlen(buff)+1 , 0);
		close(serverId);
		return 0;
	}
	
	//--------------------------------------------------------------------------------------------------------------------											
	
	label1:																		// asking client if it wants to continue
	msg = "Do you want to continue? (y/n) : ";
	copy(buff , msg);
		
	send(connection , buff , strlen(buff)+1 , 0);
	rMsgSize  = recv(connection , buff , MAX , 0);	
	
	if(rMsgSize <= 0){
		cout << "Error, Reciving the choice of client..!" << endl;
		msg = "Error";																									//##
		copy(buff , msg);
		
		send(connection , buff , strlen(buff)+1 , 0);
		close(serverId);
		return 0;
	}
	
	if(buff[0] == 'y' || buff[0] == 'Y'){
		goto label;
	}
	else if(buff[0] == 'n' || buff[0] == 'N'){
		cout << "\nConnection terminated on request from client..! peace :)\n\n";
		msg = "Terminate program";
		copy(buff , msg);
		send(connection , buff , strlen(buff)+1 , 0);
		close(serverId);
		return 0; 
	}
	else{
		msg = "Error..! Please select either 'y' or 'n'";
		copy(buff , msg);
		
		send(connection , buff , strlen(buff)+1 , 0);
		rMsgSize  = recv(connection , buff , MAX , 0);	
		
		goto label1;
	}
									
	//--------------------------------------------------------------------------------------------------------------------
	
	close(serverId);	
	return 0;
}








