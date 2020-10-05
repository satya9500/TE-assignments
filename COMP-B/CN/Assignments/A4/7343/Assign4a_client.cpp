//SEHEJ BAKSHI
#include<bits/stdc++.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>

using namespace std;


int main()
{
	int clientSocket , serverSocket , receiveMsgSize;
	
	clientSocket = socket(AF_INET , SOCK_STREAM , 0);
	
	if(clientSocket < 0)
	{
		cout << "failed to create client socket" << endl;
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(8000);
	
	if(connect(clientSocket ,  (struct sockaddr*) & serverAddr , sizeof(serverAddr)) < 0)
	{
		cout << "Connection Error" << endl;
		return 0;
	}
	else
	{
		cout << "Connection Established..!" << endl;
		cout << "\ntype end to leave the connection \n" << endl;
	}
	
	while(true)
	{
		string s;
		char input[500];
		cout << "Client : ";
		getline(cin , s);
		
		int n = s.size();
		for(int i = 0 ; i < n ; i++)
		{
			input[i] = s[i];
		}
		
		input[n] = '\0';
		send(clientSocket , input , strlen(input)+1 , 0); 
		
		if(input[0] == 'e' and input[1] == 'n' and input[2]== 'd' )
		{
			cout << "\n you left" << "\n\n";
			break;
		}
			
		char receiveMessage[500];
		int rMsgSize = recv(clientSocket , receiveMessage , 500 , 0);
		
		if(rMsgSize < 0)
		{
			cout << "Packet reception failed." << endl;
			return 0;
		}
		
	
		cout << "Server : " << receiveMessage << "\n\n";
	}
	
	close(clientSocket);
	
	return 0;
}














