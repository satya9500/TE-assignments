'''
@author:Sehej Bakshi
'''
import sys
import math

addrStr = input("Enter the ip addr :")
cidr = int(input("Enter the cidr number :"))

addr = addrStr.split('.')
print("Address : ",addrStr)

netmask = [0,0,0,0]
count = 0
for block in range(0,4):
	for i in range(7,-1,-1):
		if count == cidr:
			break
		else:
			netmask[block] = netmask[block] + 2**i
			count = count + 1

print("Netmask : ",".".join(map(str,netmask)))

network = [0,0,0,0]

for block in range(4):
	network[block] = int(addr[block]) & netmask[block]

print("Network : ",".".join(map(str,network)))

newnet = []
for block in range(4):
	newnet.append(bin(network[block]).split("0b")[1].zfill(8))

print("Network in binary : ",".".join(map(str,newnet)))



broad = network.copy()

brange = 32-cidr

for i in range(brange):
	broad[3] = broad[3] + 2**i

print("Broadcast : ",".".join(map(str,broad))) 

num = int(input("Enter the number of subnets :"))
ones = math.ceil(float(math.log(num,2)))

num_subnets = 2**ones

# print(num_subnets)

subnetmask = [0,0,0,0]
count = 0
for block in range(0,4):
	for i in range(7,-1,-1):
		if count == cidr + ones:
			break
		else:
			subnetmask[block] = subnetmask[block] + 2**i
			count = count + 1


print("subnetmask : ",".".join(map(str,subnetmask))) 

hop = int((broad[3]-network[3]+1)/num_subnets)

ll = [0]*num_subnets
ul = [0]*num_subnets

max = network[3]

for i in range(num_subnets):
	ll[i] = max
	ul[i] = max+hop-1
	max=max+hop

lb = [0,0,0]

for i in range(3):
	lb[i] = broad[i]

for i in range(num):
	print("Subnet",i,": ",".".join(map(str,lb)),".",str(ll[i]),"--->",".".join(map(str,lb)),".",str(ul[i]))







