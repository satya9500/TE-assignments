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
#define port 5600

int main(){
	int clientId , receiveMsgSize;
	
	clientId = socket(AF_INET , SOCK_DGRAM , 0); // creating the socket
	
	if(clientId < 0){	// if the socket creation fails
		cout << "Creation of client socket failed" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr;
	
	bzero(&serverAddr,sizeof(serverAddr));
	
	int len = sizeof(serverAddr);
	
	serverAddr.sin_family = AF_INET;		// assign port and IP address
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(port);
	
	
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
	
	sendto(clientId , input , strlen(input)+1 , 0 , (struct sockaddr*) & serverAddr , len);
	
	ofstream fout;	// opening the file to save the image received
	fout.open("copy_" + s , ios::binary);
	
	
	if(!fout.is_open()){	// if the opening of file fails
		cout << "Error creating the file..!" << endl;
		return 0;
	}
	
	char receiveMessage[MAX];
	int rMsgSize;
	
	while(true){
		
		if((rMsgSize = recvfrom(clientId, receiveMessage , MAX , 0 , (struct sockaddr*) &serverAddr , (socklen_t*) &len)) >= 0){ // receiving the binary cahracters
		
			sendto(clientId,"Received",8,0,(struct sockaddr*)&serverAddr,len);	
			if(strcmp(receiveMessage , "eofSuccess") == 0 || strcmp(receiveMessage , "eofFail") == 0){	// if the file has been received completely
				break;
			}
			
			fout.put(receiveMessage[0]);	// saving the characters received from server in to the file
			
		}
	}
	
	cout << "File received successfully..! " << endl;
	
	fout.close(); // closing the file
	
	close(clientId);
	return 0;
}






