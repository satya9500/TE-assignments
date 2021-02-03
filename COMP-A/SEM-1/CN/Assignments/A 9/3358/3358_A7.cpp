#include <iostream>
#include<fstream>
#include <iomanip>
#include<string>
using namespace std;

int main()
{
 cout << "***** PACKET ANALYZER *****" << endl;
 string value, sr_no,time,source,destination,info,protocol,len;
 int count=-1,i=0;



 int choice;
 do
 {
  ifstream file("3358_wireshark.csv");
  //Reinitialize Counters
  count=-1;
  i=0;
 cout<<"\nEnter which protocol packets you want to see"<<endl;
 cout<<"1.IP\n2.UDP\n3.TCP\n4.Ethernet\n0Exit!!!\nChoice:"<<endl;
 cin>>choice;
 string protocolChoice; //sting to hold user packet choice
 switch(choice){
 case 1: protocolChoice="ICMPv6";
 break;
 case 2: protocolChoice="UDP";
 break;
 case 3: protocolChoice="TCP";
 break;
 case 4: protocolChoice="ARP";
 break;
 default: protocolChoice="ARP";
 break;
 }
 /**************************************reading file****************************************************************************************/
 while(file.good()) //LOOP UNTIL FILE HAS CONTENT
 {
  getline(file,sr_no,','); //GET STRING TILL ,
  getline(file,time,',');
  getline(file,source,',');
  getline(file,destination,',');
  getline(file,protocol,',');
  getline(file,len,',');
  getline(file,info,'\n');

  protocol=string(protocol,1,protocol.length()-2);
/**************************************printing data in the formnat of time,source,destination,protocol,length,info********************************/
  if(protocol=="Protocol"||protocol==protocolChoice)
  {
   cout <<setw(4)<<left<<i++;
   cout <<setw(12)<<left<< string( time, 1, time.length()-2 );
   cout << setw(30)<<left<<string( source, 1, source.length()-2 );
   cout << setw(32)<<left<<string( destination, 1, destination.length()-2 );
   cout <<setw(8)<<left<<protocol;
   cout <<setw(8)<<left<< string( len, 1, len.length()-2 );
   cout << string( info, 1, info.length()-2 )<<"\n";
   count++;
  }
 }
 file.close();
 cout<<"\nTotal Packet Count: "<<count;
 }while(choice!=0);
 return 0;
}
