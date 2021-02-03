#include <bits/stdc++.h>
#include<fstream>
#include <iomanip>
#include<string>
using namespace std;

int main()
{
	string VALUE , SOURCE , DEST ,  SR_NO , TIME , INFO , PROTOCOL , LENGTH;
	int choice;
	do
	{
		ifstream in("3325_a7_wireshark.csv");
		//Reinitialize Counters
		
		cout<<"\n\n\nEnter which protocol packets you want to see"<<endl;
		cout<<"1.ETHERNET\n2.UDP\n3.TCP\n4.IP\n5.Exit\n\nChoice : ";
		cin>>choice;
		
		string pChoice; //sting to hold user protocol choice
		switch(choice){
			case 1: pChoice="ARP";
			break;
			case 2: pChoice="UDP";
			break;
			case 3: pChoice="TCP";
			break;
			case 4: pChoice="ICMPv6";
			break;
			case 5: {
				in.close();
				break;
			}
			default:{
				cout << "Wrong choice..." << endl;
			}
			break;
		}
		
		int count=-1 , i = 0;
		
		while(in.good()) //traverse ultile the end of file is not reached
		{
			getline(in , SR_NO , ','); //GET STRING TILL ,
			getline(in , TIME , ',');
			getline(in , SOURCE , ',');
			getline(in , DEST ,',');
			getline(in , PROTOCOL , ',');
			getline(in , LENGTH , ',');
			getline(in , INFO ,'\n');

			PROTOCOL = string(PROTOCOL , 1 , PROTOCOL.length()-2); // removing the double inverted collon's
			
			/**************************************printing data in the formnat of time,source,destination,protocol,length,info********************************/
			if(PROTOCOL == "Protocol" || PROTOCOL == pChoice)
			{
				cout << setw(4) << left << i++;
				cout << setw(15) << left << string( TIME, 1, TIME.length()-2 );
				cout << setw(45) << left << string( SOURCE, 1, SOURCE.length()-2 );
				cout << setw(45) << left << string( DEST, 1, DEST.length()-2 );
				cout << setw(12) << left << PROTOCOL;
				cout << setw(8) << left << string( LENGTH, 1, LENGTH.length()-2 );
				cout << string( INFO, 1, INFO.length()-2 )<<"\n";
				count++;
			}
		}
		
		if(choice != 5)
			cout<<"\nPacket Count: "<<count;
	}while(choice != 5);
	return 0;
}

















