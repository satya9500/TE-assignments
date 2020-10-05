//SEHEJ BAKSHI
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

int main()
{
	int serverId = socket(AF_INET , SOCK_DGRAM , 0); // UDP type socket 
	
	if(serverId < 0)
	{
		cout << "Server Creation Failed..!" << endl;
		return 0;
	}	
	
	struct sockaddr_in serverAddr , clientAddr;
	
	bzero(&serverAddr,sizeof(serverAddr));
	
	int len = sizeof(clientAddr);
	
	//specifying address and type for the server to operate under
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(port);
	serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	int bindStatus = bind(serverId , (struct sockaddr*) & serverAddr , len); // binding the socket created to the port
	
	if(bindStatus < 0)
	{
		cout << "Socket binding has failed" << endl;
		return 0;
	}
	
	int rMsgSize;
	char file_name[100];
	
	socklen_t clientAddrLen;
	
	while(true)
	{ // infinite loop for chatting
		if(rMsgSize  = recvfrom(serverId , file_name , 100 , 0 , (struct sockaddr*) & clientAddr , (socklen_t*) &len) > 0)
		{
			cout << "Request from client : " << file_name << "\n\n";
			break;
		}
	}
	
	ifstream in;
	in.open(file_name , ios::binary); // opening file in binary format
	
	if(!in.is_open())
	{
		cout << "Error opening the requested file..!" << endl;
		close(serverId);
		return 0;
	}
	
	cout << "\n.....sending the requested file!" << endl;
	
	in.seekg(0);	// pointing the in at the start of the file
	
	while(in.good())
	{	// while the file doesnot end
		char c;
		in.get(c);	// get each character of file one by one
	
		char buff[MAX];
		
		buff[0] = c;
		buff[1] = '\0';	// save each character in buffer to send to the client
		
		//fout.put(buff[0]);

		sendto(serverId , buff , strlen(buff)+1 , 0 , (struct sockaddr*) &clientAddr , len); // send each character to the client
		recvfrom(serverId,buff , MAX ,0,NULL,NULL);
					
	}
	
	char buff[MAX];
	string s = "eofSuccess";
	int n = s.size();
		
	for(int i = 0 ; i < n ; i++)
		buff[i] = s[i];
		
	buff[n] = '\0';
	sendto(serverId , buff , strlen(buff)+1 , 0 , (struct sockaddr*) &clientAddr , len);	// tell the client that the file has been sent
	
	
	cout << "File successfully sent..!" << endl;
	
	in.close();	// close the file
	
	close(serverId); // free the socket
	
	return 0;
}


















