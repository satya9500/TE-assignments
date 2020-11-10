   
   # Computer Network
Content Provider: [Shalini Negi](https://github.com/1SilverLining1)
Maintainer : [Shubham Singh](https://github.com/shubham7298)
Proof-read and correction : [Shailesh Kumar Sahu](https://github.com/shellkore)
 #### 1. OSI Model
 1. It stand for open system interconnection.
 2. It consiste of seven layer where each layer consists of set of protocols.
 
    1. **Application layer:**
        * It is used by the application which usage internet.
    like chrome , email , firefox , email. 
    It consists of various protocol:  
            1. web surfing .   http/ https  
            2. file trasfer.    FTP   
            3. email .   SMTP   
            4. virtual terminals. Telnet

    2. **Presentation layer :** 
        * It is responsible for:
            - translation into binary.
            - Data compression .
            - encrpytion and decryption 
	    * It consists of protocol like SSL for the secure socket layer.
			
    3. **Session layer :** 
	    * It is responsible for authentication and session management .
	These three layer jobs are done by the web browser.
		
    4. **Transport layer:  ( segment )** 
        * It is heart of the osi model
        * Functions:
	        - It is used for  conectionless transmission or connection oriented. 
		    - Segmentation -> breaking of data into parts.
		    - Error correction -> if any of the segment is miision it ask for sending again	
		    - flow control

    5. **Network layer: ( packet )** 
	    * It transfer data in form of packet.
	    * Each packet has logical address. 
	    * The task of network layer is : 
		    * Logical addressing -> each ocmputer in the neywrok have ip address and mask. 
		    * path determination :
		    * routing : 
	    * Segment in Network layer is referred as Packet.
        * Network layer is implemented by networking devices such as routers.
	    * protocal used in this are :
	        1. open shortest path first.
            2. border gate way 
            3. Intermediate system to intermediate system.
        * data packet : segment + sent ip adress + recieved ip adress -> logical address: 
    
    6. **Data link layer :  ( frame )** 
        * frame : mac1 + mac2 + ip1  + ip2 adress
        * packets are know as frame in this layer.
    
    7. **Physical layer:** 
        * DATA LINK layer requires MAC address of destination hosts to correctly deliver a frame
	    * NETWORK LAYER requires IP address of destination
	    * TRANSPORT LAYER requires a Port number to correctly deliver the segments of data to the correct process amongst the multiple processes running on a particular host. 
			
3. **What is TCP and  UDP ?** 
		Both of them are used for sending and receiving data. 
		Whenever two wants to communicate with each other in network then the  TCP is used for reliable communication. 
		It is connection oriented protocol. 
        It follow three way hand shaking algo. 
		&nbsp; &nbsp; &nbsp; 1. sync. 
		&nbsp; &nbsp; &nbsp; 2. sync back . 
		&nbsp; &nbsp; &nbsp; 3. Acknowledge. 
				
    | TCP | UDP          |
    | ------------- | ----------- |
    | Transmission Control Protocol (TCP)          | User Datagram Protocol (UDP)|
    |	Conection oriented 	|	connection less     |
    |	reliable 	|		not reliable.      |
    |	slow transmission 	|	Fast transmission.      | 
    |	provide feedback      |       does not provide feedback      |
		
4. **Port no :** 	
			A port number is a 16 bit address used to identify any client-server program uniquely.
 			port number is the logical address of each application or process that uses a network or the Internet to communicate. 
 			There can be many process that use the internet, it is use to uiquely identify the client - server prrgarm in the network.

5. **HTTP and HTTPS** 
    - They are application layer protocol , they are used by the application which use internet . 
	- They are used in web surfing.
	- They are used for getting information from the web server to the client.
	- In fact, hyper-text exchanged using http goes as plain text i.e. anyone between the browser and server can read it relatively easy if one intercepts this exchange of data.
	```
		 port no of http : 80
		 port no of https : 443.
        https = http + cryptographic protocols
    ```
    - In HTTP, URL begins with “http://” whereas URL starts with “https://”
	- HTTP is considered to be unsecure and HTTPS is secure
	- HTTP works at Application Layer and HTTPS works at Transport Layer
	- In HTTP, Encryption is absent and Encryption is present in HTTPS as discussed above
	- HTTP does not require any certificates and HTTPS needs SSL Certificates
        https://www.cloudflare.com/learning/ssl/why-is-http-not-secure/
		
6. **What is HTTP request ?**
	1. It is application layer protocol.
	2. It is like a messenger for the web pages. Whenever two web-application wants to communicate with each other the they use HTTP protocol.
	3. It is used for tranferring image, audio , text and videos. 
	4. It is TCP/IP based protocol.
	5. Features of HTTP
		1. It is connectionless protocol. Once response is sent it breaks the communication and re-establish the communication once it is done.
		2. It is staeless -> It means that they know there current state only.
	 	3. It can deliver any sort of message if the computer are able to understand the message.
	6. Request HTTP mesaage differ from the response HTTP message . 
	7. HTTP consist of three parts.
		1. Start --> Method, URL and HTTP version
		1. Header
               	1. Body
	8. There are various method --> Get, Post etc. https://rapidapi.com/blog/api-glossary/http-request-methods/
	10. There are two main kinds of HTTP messages: requests and responses.
       	11. But it should be noted that this security in https is achieved at the cost of processing time because Web Server and Web Browser needs to exchange encryption keys using Certificates before actual data can be transferred.
		1. HTTP is considered to be unsecure and HTTPS is secure.
		1. HTTP works at application layer and HTTPS works at Transport Layer.
		1. In HTTP, encryption is absent and encryption is present in HTTPS as discussed above.
		1. HTTP does not require any certificates and HTTPS needs SSL certificates.				
				
7. **DNS , IPV4 , IPV6 , IP** 
	https://www.youtube.com/watch?v=8npT9AALbrI
	youtube.com/watch?v=dzPCPsjiUz0\
	
	 What is DNS ?
	 	- It convert domain name to the ip so that web browser can load the internet resource.
		- Every computer/sever has its own IP address .
		- To access a server or computer we must have it's ip address but remembering IP address is difficult.
		- IP adress are mapped to Domain name.
		- To convert Domain name to IP address , the mechanism is called as DNS.
		- DNS is phonebook of the internet
		- Each device connected to the Internet has a unique IP address which other machines use to find the device.
		- There are for type of nameserver : 
			1. DNS recursive resolver: 
			1. Root nameserver 	
			1. TLD DNS
		4. Authoritive.
		https://www.cloudflare.com/learning/dns/what-is-dns/
		
		 DNS : 
		1. Ip address is given to reach device ion the network . 
		2. DNS convert the domain name into ip address. 
		
		DNS resolver handle the conversion of  host name to ip address . 
		first of it sent request to DNS root nameserver. 
		DNS root name return the ip address of the op level domain . 
		DNS resolver sent the request to top leven domain which return the address of exaple.com.
		DNS sent the request to name server which sends the IP address . 
		
		https://www.cloudflare.com/learning/dns/what-is-dns/
	
	
8. A **firewall** is a network security device, either hardware or software-based, which monitors all incoming and outgoing traffic and based on a defined set of security rules it accepts, rejects or drops that specific traffic.

9. **Public IP and Private IP:** 
	- Public ip are provide by the internet service provider and are available there used to communicate outside the network.
	- Private ip are used to communicate within the network.	
	
	
10. **Three way hand shaking protocol:** 
	- syn request is sent by client to the server.
	- the server response by the sync and ack
	- then the client again send the sync
	
	
11. **Application layer Protocols**
		*TELNET* (telecommunication networks) used for terminal emulation   -> it is used for the connecting to remote host.
		*FTP* used for file transfer
		*SMTP*(Simple mail transfer protocol)  It is a part of the TCP/IP protocol. Using a process called “store and forward,” SMTP moves your email on and across networks
		*DNS*(Domain Name Service)  used for converting domain name to corresponding IP address.
		*DHCP*(Dynamic host configuration protocol) Used for assigning IP address to hosts.

12. **IP : IPV4 and ipv6**
    - IP stands for internet  protocol.
    - It is the numerical address. 
    - it is the adresss of the device on the network.
    - It consist of two part first one is host address and second the network address
    - It is of two type -> first is ipv4 and second is ipv6.

- IPV4 : 
			1. It is 32 bit adres which is divide into 4 octet.
			2. each octet can have value fron 0 - 256.
			3. The no of people they can represt is 256 the power 4 
			4. but soon they were left with few ip address as the no of devices on the internet increased.
	
			
		
- IPV6 : 
			1. It is 128 bit.
			2. it is divided into 12 forms.
			
    https://www.guru99.com/difference-ipv4-vs-ipv6.html	

    | IPv4 | IPv6          |
    | ------------- | ----------- |
    | 32-bit         | 128-bit  |
    |	broadcast possible 	|	not possible     |
    |	checksum 	|		no checksum      |
    |	numerical addressing 	|	alpha numeric addressing      | 
    |	bit '.' separeted      |       ':' separated      |

13. **What happens when you send an email to someone?** 

14. **FTP**
	+ FTP stands for file transportation protocol.
	+ FTP is application layer protocol.
	+ It is used for sending and receiving the messages.
	+ It works in the top of TCP/ IP protocol like http and https.

15. **What happend when you search something in browser ?**
	1. It search the ip address in the cache. 
	1. It then search in the OS cache. 
	1. It then search in the router cache.
	1. It then search in the ISP cache.
	1. Then finally recursive nameserver come into picture.
	1. The recursive nameserver  request the root nameserver -> it return the ip address of the top level domain. like -> .com 
	1. Then recursive makerequest to the top level domain for the ip address.
	1. top level domain return the Authoritative name server to the recursive resolver.
	1. Recursive resolver make request to authoritative nameserver.
	1. It update all the cache.

16. **Which protocol is secure for mail transfer? Which protocol is used by Gmail?**
	Gmail use Simple mail transferring protocol(*SMTP*).
		1. It is application layer protocol.
		1. Its port no is 25.
		1. It uses TCP protocol.
		1. SMTP servers use port 25
			+ First the mail will go to the smtp server .
			+ then from the smtp server it will go to the reciever.
			+ SMTP servers are gmail, outlook.
		

17. **Transmission mode in CN :** 
    1. Simplex .   -> one end receive and other end transmit.
    2. half duplex : -> Both end can transmit adn reciecvve at at a time. ex: : Walkie- talkie in
    3. Full Duplex    -> Bith the end can recieve and transmit at the sam etime.

18. **DOS attack :** 
    1. It stamds for Denial of service. 
    2. It mean creating faek traffic in  serve which cause denial of geniun request in site.
    3. when it is  from the single computer then it is DOS attack.
    4. When it is from multiple computer is it called as distributed denial of service attack.
    5. It will eact the server rewsource, like cpu , network bandwithdn as result the real reuest would be denied.

19. **TCP/ IP protocol :** 
	1. It stands for transmission control protocol and internet protocol. 
	2. It is set of protocol  used by the different computer to communicate with each other.
	3. It consist of 4 layer unlike osi consister of 7 layer.
	4. Application : Application , presentation, session layer.
	   Transport Layer: Transport Layer. It support the communication between the diverse computer.
	   Internet Layer: Network Layer. It determines the best path for the transmission.
	   Network Layer:  Data Link Layer and Physical layer. It control the hardware and media that make the internet.
	 Application layer: It support encoing , decoding ,  data compression, generate the data.

    ```
	 Transport layer : segment 
	 Network Layer : Packet
	 data link layer : Frame.
	 Physical Layer : bits
    ```

20. **Network Topology :**
	The arrangement of the network consisting of node and wires is called as Network topology.
		The various network topology are : 
		1. Mesh
		1. Star
		1. Bus
		1. Ring

21. **Various network device :** 
	
	https://www.geeksforgeeks.org/network-devices-hub-repeater-bridge-switch-router-gateways/#Routers
	
	
	
	
	
	
22. **NAT :** 
	It stands for network address translation.
	It convert the private IP adress into public IP address.
	It is used in routers.( NAT is present in the router ) 
	It is used to preserve the limited amount of the IPV4 address.
	To access the internet we must have public IP address.
	We can ask for the public IP address but it will be more expesibve and waste of the ip address.

23. **Repeater :** They are used for connecting two devices and regenerate the signal. It is present in the physical layer.
		It is two port device.
    	***Hub*** :  Hub is used for connecting many device in the network, they have many ports and when a data frame arrives at a port, it is broadcast to every other port, without considering whether it is destined for a particular destination or not.
    ( Hub broadcast the message ) 
   	 ***Switches*** : 
    	They are present in the data link layer. 
    	they are used for connecting many device ands they have many port .
    	Switch can unicast  and broad cast 

    | Hub | Switch          |
    | ------------- | ----------- |
    | operate in the physical layer         | operate in the data link layer  |
    |	non-intelligent network device that sends message to all ports 	|	intelligent network device that sends message to selected destination ports     |
    |	broadcasts messages 	|		supports unicast, multicast and broadcast     |
    |	Transmission mode is half duplex 	|	Transmission mode is full duplex     | 
    |	Collisions may occurs during setup of transmission when more than one computers place data simultaneously in the corresponding ports.     |       Collisions do not occur since the communication is full duplex.      |
    |	They are passive devices, they don’t have any software associated with it      |       They are active devices, equipped with network software.      |
    |	They generally have fewer ports of 4/12.      |      The number of ports is higher – 24/48.     |

Bridges : 
	
Routers : 
	
Gateway : 	It is used for connectng two network that may work on different topology.

   Cookies and DHCP 

   Physical Layer: It is responsible for actual physical transmission of data (through channels). It recieves/transmits signals and then converts it to physical bits (0 & 1). It handles bit-synchronization (using clock), bit-rate control (no.of bits/sec), physical topology and transmission mode (simplex, half-duplex, full-duplex).

 Data-link Layer: It is responsible for Node-to-Node delivery of packets, Framing, Error control, Flow control, Physical Addressing (MAC). Upon recieving packets from network layer, it encapsulates it within a frame with the hardware (MAC) address of the reciever (obtained via ARP ~ Address Resolution Protocol).
		
--------------------------------------------------------------------------------------
 ALL NETWORKING DEVICES
		https://www.geeksforgeeks.org/network-devices-hub-repeater-bridge-switch-router-gateways/#Routers

 TCP 3 WAY HANDSHAKE
	Using SYN, SYN + ACK, ACK
	https://www.geeksforgeeks.org/tcp-3-way-handshake-process/

 DHCP -> Dynamic host configutraion protocol.

DDOS ATTACK
	https://www.geeksforgeeks.org/denial-of-service-ddos-attack/
    PORTS AND SOCKETS
	http://www.steves-internet-guide.com/tcpip-ports-sockets/
	
		
