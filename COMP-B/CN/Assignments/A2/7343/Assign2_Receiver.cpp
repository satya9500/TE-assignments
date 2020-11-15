//SEHEJ BAKSHI
#include <bits/stdc++.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <bitset>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>

using namespace std;

int checksum(string encoded, string divisor)               //function for checking remainder at receiver side
                                                               //accepts dataword and divisor
{
	int l_div = divisor.length();
	string message = encoded.substr(0, encoded.length() - 8);

	for (int j = 0; j <= encoded.length() - l_div;)
	{
		for (int k = 0; k < l_div; k++)
			encoded[k + j] = encoded[k + j] == divisor[k] ? '0' : '1';

		for (; j < encoded.length() && encoded[j] != '1'; j++);
	}
	
	string remainder = encoded.substr(encoded.length() - l_div + 1);

	cout << " Remainder: " << remainder;

	for (char c : encoded.substr(encoded.length() - l_div))
		if (c != '0')
		{
			cout << "\n Error" << endl;
			return -1;
		}

	cout << "\n No Error";
	cout << "\n Dataword: " << message;

	stringstream sstream(message);
	string output = "";
	while (sstream.good())
	{
		bitset<8> bits;
		sstream >> bits;
		char c = char(bits.to_ulong());
		output += c;
	}

	cout << "\n Message was: " << output << endl;
	return 0;
}

int main()
{
	int serverSocketHandler = socket(AF_INET, SOCK_STREAM, 0); //creating a socket and assigning it to the socket handler
	if (serverSocketHandler < 0)
	{ // socket methode return -1 if the creation was not successful
		cout << "Failed to create socket";
		return 0;
	}

	struct sockaddr_in serverAddr, clientAddr;

	//specifying address and type for the server to operate under
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(8000);
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");

	int bindStatus = bind(serverSocketHandler, (struct sockaddr *)&serverAddr, sizeof(serverAddr));

	if (bindStatus < 0)
	{
		cout << "Socket binding failed" << endl;
		return 0;
	}

	int listenStatus = listen(serverSocketHandler, 5); //listen to the client while others are waiting in queue of size 5

	if (listenStatus < 0)
	{ // when queue is full listen fails
		cout << "Listener has failed" << endl;
		return 0;
	}

	cout << "...Waiting for connection \n\n";

	char buff[500];
	int clientSocketHandler;

	socklen_t len = sizeof(clientAddr);
	int connection;
	if ((connection = accept(serverSocketHandler, (struct sockaddr *)&clientAddr, &len)) < 0)
	{
		cout << "Server denied request." << endl;
		return 0;
	}
	else
	{
		cout << " Server accepted request. \n" << endl;
	}

	int op;
	char cdw[500];
	recv(connection, cdw, 500, 0);
	cout << " Received " << cdw << endl;              //receiving codeword

	cout << "\n Enter operation:\n 1:without error \n 2:with error\n 3:exit \n";
	cin >> op;
	string data = "";
	switch (op)
	{
	case 1:
		data = cdw;
		break;
	case 2:
		data = cdw;
		shuffle(data.begin(), data.end(), default_random_engine(0));
		break;
	case 3:
		return 0;
		break;

	default:
		cout << "\n ERROR: Unsupported Operation\n";
	}

	int res = checksum(data, "100000111");          
	
	if (res == -1)
	{
		char result[20] = "Dataword Corrupted";
		send(connection, &result, sizeof(result), 0);
	}
	else if (res == 0)
	{
		char result[20] = "Dataword Received";
		send(connection, &result, sizeof(result), 0);
	}
	
	close(serverSocketHandler);
	return 0;
}
