#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>
#include<fstream>

using namespace std;

#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>


#define MAX 2048
#define port 5200

int main(){
	int clientId , serverId , receiveMsgSize;
	
	clientId = socket(AF_INET , SOCK_STREAM , 0);
	
	if(clientId < 0){
		cout << "Creation of client socket failed" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(port);

	if(connect(clientId ,  (struct sockaddr*) & serverAddr , sizeof(serverAddr)) < 0){
		cout << "Connection Error, server is off...!" << endl;
		return 0;
	}
	else{
		cout << "Connection Established..!" << endl;
	}
	
	//accepting the file to open.
	cout << "Enter the file name you want to open : "; // the file name you entre should be present at the server file location
	string s;
	cin >> s;
	
	int n = s.size();
	char input[MAX];
	input[n+1];
	for(int i = 0 ; i < n ; i++){
		input[i] = s[i];
	}
	
	input[n] = '\0';
	// sending the file name to the server
	send(clientId , input , strlen(input)+1 , 0);
	
	ofstream fout;
	fout.open("fileReceived.txt");
	
	if(!fout.is_open()){
		cout << "Error creating the file..!" << endl;
		return 0;
	}
	
	char receiveMessage[MAX];
	int rMsgSize;
	
	while(true){
		
		if((rMsgSize = recv(clientId , receiveMessage , MAX , 0)) > 0){
			//cout << receiveMessage << endl;
			if(strcmp(receiveMessage , "eofSuccess") == 0 || strcmp(receiveMessage , "eofFail") == 0){
				break;
			}
			fout << receiveMessage << endl;
			send(clientId , input , strlen(input)+1 , 0);
		}
	}
	
	fout.close();
	
	if(strcmp(receiveMessage , "eofSuccess") == 0){
		cout << "File is saved as ""fileReceived.txt"" in the same directory." << endl;
		cout << "\nFile successfully received and Connection ended..! peace :)" << "\n\n";
	}
	else if(strcmp(receiveMessage , "eofFail") == 0){
		cout << "\nError..! file not received..!" << "\n\n";
	}
	
	close(clientId);
	return 0;
}






