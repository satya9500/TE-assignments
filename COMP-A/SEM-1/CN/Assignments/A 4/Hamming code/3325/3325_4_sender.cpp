#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>

using namespace std;

#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>


#define MAX 500
#define port 5200

void convertToBinary(char a[] , char c){	// function to convert the character into binary and store it in 'a' char array  
	int u = (int)c;
	for(int i = 0 ; i < 8 ; i++){
		a[i] = (u&(1<<i)) ? '1' : '0';
	}
}

void initcodeWorder(char codeWord[] , char a[]){	// function to initialise the codeword without the redundant bit calculations
	for(int i = 0 ; i < 12 ; i++)
		codeWord[i] = '*';				// this represents that the bit is not assigned yet
	
	for(int i = 0 ; i < 4 ; i++){
		codeWord[(1<<i)-1] = '0';
	}
	
	int j = 7;
	for(int i = 11 ; i >= 0 ; i--){
		if(codeWord[i] == '*')
			codeWord[i] = a[j--];
	}
}

void print(char codeWord[] , int n){	// print the char array
	for(int i = n-1 ; i >= 0 ; i--)
		cout << codeWord[i];
		
	cout << "\n\n";
}

int main(){
	int clientSocket , serverSocket , receiveMsgSize;
	
	clientSocket = socket(AF_INET , SOCK_STREAM , 0);	// creating the socket
	
	if(clientSocket < 0){
		cout << "Creation of client socket failed" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	// providing socket with IP and port
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(port);
	
	if(connect(clientSocket ,  (struct sockaddr*) & serverAddr , sizeof(serverAddr)) < 0){	// connecting to the receiver
		cout << "Connection Error..!" << endl;
		return 0;
	}
	else{
		cout << "Connection Established..!" << endl;
	}
	
	//int rMsgSize = recv(clientSocket , receiveMessage , MAX , 0);
	
	char binary[8] , c;
	cout << "Enter the character : ";
	cin >> c;
	
	convertToBinary(binary , c);
	cout << "Message : ";
	print(binary , 8);
	
	// binary bits are assigned from left to right while sending
	// eg : binary -> 00101 , assignment -> 00101 
	
	char codeWord[12];
	initcodeWorder(codeWord , binary);
	
	//--------------------------------------------------calculating the redundant bits--------------------------------------------------
	
	for(int i = 0 ; i < 4 ; i++){
		int cnt = 0;
		for(int j = 0 ; j < 12 ; j++){
			if(((j+1)&(1<<i))){
				cnt += (int)(codeWord[j]-'0');	// if i'th bit of j is set , add the value of j'th position to cnt
			}
		}
		
		codeWord[(1<<i)-1] = (char)((cnt%2)+'0');	// update the i'th redundant bit position 
	}
	
	
	//-------------------------------------------Sending the message-----------------------------------------------------------------------
	
	cout << "Sending codeword: ";
	print(codeWord , 12);
	
	send(clientSocket , codeWord , strlen(codeWord)+1 , 0); // sending the codeword
	
	cout << "Message Sent Successfully...!" << endl;
	
	close(clientSocket);	// closing the socket
	
	return 0;
}














