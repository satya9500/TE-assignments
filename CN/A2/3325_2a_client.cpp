#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>

using namespace std;

#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>


#define MAX 500
#define port 5200

int main(){
	int clientSocket , serverSocket , receiveMsgSize;
	
	clientSocket = socket(AF_INET , SOCK_STREAM , 0);
	
	if(clientSocket < 0){
		cout << "Creation of client socket failed" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(port);
	
	if(connect(clientSocket ,  (struct sockaddr*) & serverAddr , sizeof(serverAddr)) < 0){
		cout << "Connection Error..!" << endl;
		return 0;
	}
	else{
		cout << "Connection Established..!" << endl;
	}
	
	while(true){
		string s;
		char input[MAX];
		cout << "Client : ";
		getline(cin , s);
		
		int n = s.size();
		for(int i = 0 ; i < n ; i++){
			input[i] = s[i];
		}
		
		input[n] = '\0';
		
		send(clientSocket , input , strlen(input)+1 , 0); 
			
		char receiveMessage[MAX];
		int rMsgSize = recv(clientSocket , receiveMessage , MAX , 0);
		
		if(rMsgSize < 0){
			cout << "Packet recieve failed." << endl;
			return 0;
		}
		else if(rMsgSize == 0){
			cout << "Server is off." << endl;
			return 0;
		}
		
		if(receiveMessage[0] == 'b' && receiveMessage[1] == 'y' && receiveMessage[2] == 'e'){
			cout << "\nConnection ended..! peace :)" << "\n\n";
			break;
		}
		
		cout << "received server : " << receiveMessage << "\n\n";
	}
	
	close(clientSocket);
	
	return 0;
}














