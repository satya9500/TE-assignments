'''
@author: Sehej Bakshi
'''
import os

ipRange = []
for i in range(1, 254):
    ipRange.append('192.168.113' + '.' + str(i)) # <-- put your IP range here in the first set of ' '

for e in ipRange:
    print(os.system('nslookup ' + e))
