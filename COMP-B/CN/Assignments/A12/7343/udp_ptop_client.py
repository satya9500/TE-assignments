'''
@author: Sehej Bakshi
'''
import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
data="Hi from client"
data=str.encode(data)
sock.sendto(data, ('127.0.0.1', 10000))
while True:    	
  msg, (ip, port) = sock.recvfrom(100)
  msg=msg.decode()
  if "stop" in msg:
	  break
  else:			
	  print("server: " + msg)
	
  msg =input("you: ")
  msg=str.encode(msg)
  sock.sendto(msg, (ip,port))
  msg=msg.decode()
  if "stop" in msg:
	  break
sock.close()
