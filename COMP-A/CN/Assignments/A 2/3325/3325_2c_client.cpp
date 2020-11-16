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

void copy(char buff[] , string msg){
	int n = msg.size();
	
	for(int i = 0 ; i < n ; i++)
		buff[i] = msg[i];
		
	buff[n] = '\0';
}


int main(){
	int clientId , serverId , receiveMsgSize;
	
	clientId = socket(AF_INET , SOCK_STREAM , 0);
	
	if(clientId < 0){
		cout << "\nCreation of client socket failed" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(port);

	if(connect(clientId ,  (struct sockaddr*) & serverAddr , sizeof(serverAddr)) < 0){
		cout << "\nConnection Error, server is off...!" << endl;
		return 0;
	}
	else{
		cout << "\nConnection Established..!" << endl;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	char rMsg[MAX] , buff[MAX];													// receiving info from the server and acting accordingly
	int rMsgSize;
	string s;
	
	while(true){
		
		if((rMsgSize = recv(clientId , rMsg , MAX , 0)) > 0){
			if(strcmp(rMsg , "Error") == 0){									// if an error occurs
				cout << "\nSome error occurred, connection terminated!" << endl;
				break;
			}
			else if(strcmp(rMsg , "Terminate program") == 0){					// when the program is asked to terminate
				cout << "\nConnection terminated on request! peace:)" << "\n\n";
				break;
			}
			else if(rMsg[0] == 'E'){											// some acceptable error occurs
				cout << "\n" << rMsg << "\n\n";
				
				s = "ARBITRARY STRING";
				copy(buff , s);
				send(clientId , buff , strlen(buff)+1 , 0);
			}
			else if(check(rMsg)){												// if the server retuened a number which is an ans
				//cout << "3#" << endl;
				cout << "\nAns : " << rMsg << "\n\n";
				
				s = "ARBITRARY STRING";
				copy(buff , s);
				send(clientId , buff , strlen(buff)+1 , 0);
			}
			else{
				cout << rMsg; 
				
				cin >> s;
				copy(buff , s);
				send(clientId , buff , strlen(buff)+1 , 0);
			}
			
		}
	}	
		
	close(clientId);

}
