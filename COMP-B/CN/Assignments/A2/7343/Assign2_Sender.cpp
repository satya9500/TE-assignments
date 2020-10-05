//SEHEJ BAKSHI
#include <bits/stdc++.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>

using namespace std;

string strToBinary(string s)
{
	int n = s.length();
	string bin = "";

	for (int i = 0; s[i] != '\0'; i++)
	{
		// convert each char to ASCII value
		int val = s[i];

		// Convert ASCII value to binary
		for (int y = 7; y + 1 > 0; y--)
		{
			if (val >= (1 << y))
			{
				val = val - (1 << y);
				bin.push_back('1');
			}
			else
			{
				bin.push_back('0');
			}
		}
	}
	return bin;
}

// function to compute CRC and codeword
string CRC(string dataword, string divisor)
{
	string encoded = "";
	int l_div = divisor.length();
	int l_dword = dataword.length();

	encoded += dataword;
	
	for (int i = 1; i <= l_div - 1; i++)
		encoded += "0";
		
	cout << "\n After appending 8 zeroes: " << encoded << endl;
	
	for (int j = 0; j <= encoded.length() - l_div;)
	{
		for (int k = 0; k < l_div; k++)
			encoded[k + j] = encoded[k + j] == divisor[k] ? '0' : '1';

		for (; j < encoded.length() && encoded[j] != '1'; j++);
	}

	string remainder = encoded.substr(encoded.length() - l_div + 1);

	cout << " Remainder: " << remainder << endl;

	return remainder;
}

int main()
{
	int clientSocket, serverSocket, receiveMsgSize;

	clientSocket = socket(AF_INET, SOCK_STREAM, 0);

	if (clientSocket < 0)
	{
		cout << "failed to create client socket" << endl;
		return 0;
	}

	struct sockaddr_in serverAddr, clientAddr;

	serverAddr.sin_family = AF_INET;
	serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
	serverAddr.sin_port = htons(8000);

	if (connect(clientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0)
	{
		cout << "Connection Error..!" << endl;
		return 0;
	}
	else
	{
		cout << "Connection Established..!" << endl;
	}
	char ch;

	string dataword, divisor;
	string s;
	cout << "\n Enter String:";
	cin >> s;

	dataword = strToBinary(s);

	cout << "\n Dataword: " << dataword;
	cout << "\n Divisor: "<< "100000111";
	string rem = CRC(dataword, "100000111");
	string cdw = dataword + rem;
	cout << " Codeword : " << dataword + rem << endl;

	const char *cdwd = cdw.c_str();

	send(clientSocket, cdwd, 500, 0);

	char result[20];

	recv(clientSocket, &result, sizeof(result), 0);

	cout << "\n Operation result from server = " << result << endl;

	close(clientSocket);

	return 0;
}
