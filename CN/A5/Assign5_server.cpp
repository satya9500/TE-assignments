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
	int serverSocketHandler = socket(AF_INET , SOCK_STREAM , 0); //creating a socket and assigning it to the socket handler
	if(serverSocketHandler < 0)
	{	// socket methode return -1 if the creation was not successful
		cout << "failed to create socket";
		return 0;
	}
	
	struct sockaddr_in serverAddr , clientAddr;
	
	//specifying address and type for the server to operate under
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(8000);
	serverAddr.sin_addr.s_addr =  inet_addr("127.0.0.1");
	
	int bindStatus = bind(serverSocketHandler , (struct sockaddr*) & serverAddr , sizeof(serverAddr));
	
	if(bindStatus < 0)
	{
		cout << "Socket binding failed" << endl;
		return 0;
	}
	
	int listenStatus = listen(serverSocketHandler , 5); //listen to the client while others are waiting in queue of size 5 
	
	if(listenStatus < 0)
	{	// when queue is full listen fails
		cout << "Listener has failed" << endl;
		return 0;
	}
	
	cout << "...Waiting for connection \n\n";
	
	char buff[500];
	int clientSocketHandler;
	
	socklen_t len = sizeof(clientAddr);
	int connection;
	if((connection = accept(serverSocketHandler , (struct sockaddr*) & clientAddr , &len)) < 0)
	{
		cout << "Server denied request." << endl;
		return 0;
	}
	else
	{
		cout << "Server accepted request. \n" << endl;
        }
         
    while(true)
    {
    
	    int op; double angle,result,val;
	    val = 3.1428 / 180; /* char enter1[50],enter_an[5]*/;
	    
	    //string enter = "Enter operation:\n 1:sin \n 2:cos\n 3:tan \n";
	    char enter[]="Enter operation:\n 1:sin \n 2:cos\n 3:tan \n";
	    //strcpy(enter1,enter.c_str());
	    send(connection,&enter,sizeof(enter),0);
	    recv(connection, &op,(sizeof(op)),0);
	    
	    //string enter2 = "Enter angle in degree:";
	    char enter2[] = "Enter angle in degree: ";
	    //strcpy(enter_an,enter2.c_str());
	    send(connection,enter2,sizeof(enter2),0);
	    
	    recv(connection, &angle, sizeof(angle),0);
	    
	    switch(op) 
	    {
		    case 1: result=sin(angle*val);
		             cout<<"result "<<result;
		             break;
		    case 2:result=cos(angle*val);
		     cout<<"result "<<result;
		            break;
		    case 3:result=tan(angle*val);
		     cout<<"result "<<result;
		            break;
		    
		 default: 
		            cout<<"\nERROR: Unsupported Operation\n";
	    }

    	   send(connection,&result,sizeof(result),0);
		
    }
	
	close(serverSocketHandler);
	return 0;
	
}
 


