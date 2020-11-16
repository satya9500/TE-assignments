'''
@author: Sehej Bakshi
'''
import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind(('127.0.0.1', 10000))

msg, (ip, port) = sock.recvfrom(100)
print("client connected to ip: " + ip + " and port: " + str(port))
msg=msg.decode()
print("received message: " + msg)
detail="Hello from server"
detail=str.encode(detail)
sock.sendto(detail, (ip,port))



while True:    	
  msg, (ip, port) = sock.recvfrom(100)
  print("client connected to ip: " + ip + " and port: " + str(port))
  msg=msg.decode()
  if "stop" in msg:
	  break
  else:			
	  print("client: " + msg)
	
  msg = input("you: ")
  msg=str.encode(msg)
  sock.sendto(msg, (ip,port))
  msg=msg.decode()
  if "stop" in msg:
	  break
sock.close()
