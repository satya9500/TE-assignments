'''
@author: Sehej Bakshi
'''
import socket
def get_host_ip():
    try:
        host_name = socket.gethostname()
        host_ip = socket.gethostbyname(host_name)
        print('Host Name: ', host_name)
        print('Host IP: ', host_ip)
    except:
        print('Not Valid')

get_host_ip()
