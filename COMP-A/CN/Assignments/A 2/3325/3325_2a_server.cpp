#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>


#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>

using namespace std;

#define MAX 500
#define port 5200

int main(){
	int serverSocketHandler = socket(AF_INET , SOCK_STREAM , 0); //creating a socket and assigning it to the socket handler
	if(serverSocketHandler < 0){	// socket methode return -1 if the creation was not successful
		cout << "Socket creation has failed.";
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	//specifying address and type for the server to operate under
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(port);
	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	int bindStatus = bind(serverSocketHandler , (struct sockaddr*) & serverAddr , sizeof(serverAddr));
	
	if(bindStatus < 0){
		cout << "Socket binding has failed" << endl;
		return 0;
	}
	
	int listenStatus = listen(serverSocketHandler , 5); //listen to the client while others are waiting in queue of size 5 
	
	if(listenStatus < 0){	// when queue is full listen fails
		cout << "Listner has failed" << endl;
		return 0;
	}
	
	cout << "...Waiting for connections \n\n";
	
	char buff[MAX];
	int clientSocketHandler;
	
	socklen_t len = sizeof(clientAddr);
	int connection;
	if((connection = accept(serverSocketHandler , (struct sockaddr*) & clientAddr , &len)) < 0){
		cout << "Server didn't accept the request." << endl;
		return 0;
	}
	else{
		cout << "Server accepted the request. \n" << endl;
	}
	
	while(true){ // infinite loop for chatting
		int rMsgSize;
		
		if((rMsgSize  = recv(connection , buff , MAX , 0)) > 0){
			cout << "\nreceived client : " << buff << endl;
			
			if(buff[0] == 'b' && buff[1] == 'y' && buff[2] == 'e'){
				cout << "Server : Bye bro" << endl;
				cout << "\nConnection ended..! peace :)\n" << endl;
				send(connection , buff , strlen(buff)+1 , 0);
				break;
			}
			
			cout << "Server : "; 
			
			char input[MAX];
			string s;
			
			getline(cin , s);
			int n = s.size();
			for(int i = 0 ; i < n ; i++){
				input[i] = s[i];
			}
			
			input[n] = '\0';
			
			send(connection , input , strlen(input)+1 , 0);
		}
	}
	
	close(serverSocketHandler);
	return 0;
	
}
















