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

void turnOffTheServer(int connection , int flag){

	if(flag){	
		string s = "eofSuccess";
		int n = s.size();
		char buff[MAX];
		
		for(int i = 0 ; i < n ; i++)
			buff[i] = s[i];
			
		buff[n] = '\0';
		
		send(connection , buff , strlen(buff)+1 , 0);
		cout << "\nFile sent successfully and Connection ended..! peace :)" << "\n\n";
	}
	else{
		string s = "eofFail";
		int n = s.size();
		char buff[MAX];
		
		for(int i = 0 ; i < n ; i++)
			buff[i] = s[i];
			
		buff[n] = '\0';
		
		send(connection , buff , strlen(buff)+1 , 0);
		cout << "Error..! Requested file cannot be sent, please check the file name..!" << "\n\n";
	}
	
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
		return 0;
	}
	
	int listenStatus = listen(serverId , 5); //listen to the client while others are waiting in queue of size 5 
	
	if(listenStatus < 0){	// when queue is full listen fails
		cout << "Listner has failed" << endl;
		return 0;
	}
	
	cout << "...Waiting for connections \n\n";
	
	socklen_t len = sizeof(clientAddr);
	int connection;
	if((connection = accept(serverId , (struct sockaddr*) & clientAddr , &len)) < 0){
		cout << "Server didn't accept the request." << endl;
		return 0;
	}
	else{
		cout << "Server accepted the request. \n" << endl;
	}
	
	int rMsgSize;
	char file_name[100];
	while(true){ // infinite loop for chatting
		if((rMsgSize  = recv(connection , file_name , 100 , 0)) > 0){
			cout << "Request from client : " << file_name << "\n\n";
			break;
		}
	}
	
	ifstream in;
	in.open(file_name);
	
	if(!in.is_open()){
		cout << "Error opening the requested file..!" << endl;
		turnOffTheServer(connection , 0);
		close(serverId);
		return 0;
	}
	
	cout << "\n.....sending the requested file!" << endl;
	
	//vector<string> v;
	while(!in.eof()){
		string s;
		char buff[MAX];
		getline(in , s);
		//v.push_back(s);
		int n = s.size();
		
		for(int i = 0 ; i < n ; i++)
			buff[i] = s[i];
			
		buff[n] = '\0';
		//cout << buff << endl;
		send(connection , buff , strlen(buff)+1 , 0);
		rMsgSize  = recv(connection , file_name , 100 , 0); // this  data is not used , it is just receiving something after the send command
		
	}
	
	in.close();
	
	turnOffTheServer(connection , 1);
	
	close(serverId);
	
	return 0;
}


















