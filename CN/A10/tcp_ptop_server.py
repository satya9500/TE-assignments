'''
@author: Sehej Bakshi
'''
import socket
import sys
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(('localhost',23000))
sock.listen(1)
c, (ip,port) = sock.accept()
while True:    	
	data = c.recv(16)
	dt = data.decode()
	if "stop"==dt:
		break
	else:			
		print("client: " + dt)
	data = input("you: ")
	c.send(str.encode(data))
	if "stop"==data:
		break
sock.close()
