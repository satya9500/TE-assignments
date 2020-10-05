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
		cout << "Connection Error..!" << endl;
		return 0;
	}
	else
	{
		cout << "Connection Established..!" << endl;
		cout << "\nType end to leave the connection \n" << endl;
	}
	char ch;
	do
	{
		    int op; double angle,result;char enter[50];
		    bzero(enter,sizeof(enter));
		    recv(clientSocket,&enter,sizeof(enter),0);
      
            cout<<enter;
            cin>>op;
            send(clientSocket ,&op, sizeof(op),0);
            bzero(enter,sizeof(enter));
            recv(clientSocket,enter,sizeof(enter),0);
         
            cout<<enter;
            cin>>angle;
            send(clientSocket ,&angle, sizeof(angle),0);

            recv(clientSocket ,&result, sizeof(result),0);
     
            cout<<"\n Operation result from server = "<<result<<endl;
            
            cout<<"\n Another operation?[y/N]"<<endl;
		    cin>>ch;
		    if (ch!='y')
		    {
			    cout << "\n you left" << "\n\n";
			    break;
		    }
	}while(ch=='y');
	
	close(clientSocket);
	
	return 0;
}
    
