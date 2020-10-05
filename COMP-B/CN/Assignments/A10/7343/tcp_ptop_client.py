'''
@author: Sehej Bakshi
'''
import socket

import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

sock.connect(('127.0.0.1',23000))
byt = str.encode("hi from client")
sock.send(byt)

while True:
	
	data = sock.recv(16)
	dt = data.decode()
	if "stop"==dt :
		break
	else:			
		print("server: " + dt)

	data = input("you: ")
	data = str.encode(data)
	sock.send(data)
	if "stop"==data.decode():
		break
sock.close()
