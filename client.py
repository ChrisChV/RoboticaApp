import socket
import sys
import random

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('192.168.0.46', 8888)) #IP is the server IP

randomNumber = random.randint(0,2)
st = str(randomNumber)
s.send(st)

#s.send(str())


#s.send('Hello')


#for args in sys.argv:
#    if args == "":
#        args = 'no args'
#    else:
#        s.send(args + ' ')
 
print 'goodbye!'
