import urllib
import cv2
import numpy as np
import time
from keras.models import load_model
from keras.preprocessing import image
import socket
import sys
import random
import os


# Replace the URL with your own IPwebcam shot.jpg IP:port
url='http://192.168.8.100:8080/shot.jpg'

model = load_model('model.h5')

while True:
    # Use urllib to get the image from the IP camera
    imgResp = urllib.urlopen(url)
    
    # Numpy to convert into a array
    imgNp = np.array(bytearray(imgResp.read()),dtype=np.uint8)
   

    # Finally decode the array to OpenCV usable format ;) 
    #img = cv2.resize(imgNp, dsize=(120, 160), interpolation=cv2.INTER_CUBIC)
    #img = cv2.imdecode(imgNp, -1)
    img_width = 120
    img_height = 160
    #img_resized = cv2.resize(img,(160,120))

    #img_resized = image.img_to_array(img_resized)

    #test_image = np.expand_dims(img_resized, axis = 0)
    #test_image = test_image.reshape(img_width, img_height)
    

    #print("shape", test_image.shape)
    #P = model.predict(test_image)

    #test_image= image.load_img(url, target_size = (img_width, img_height))
    test_image = cv2.imdecode(imgNp, -1)
    test_image = cv2.resize(test_image,(160,120))
    test_image = image.img_to_array(test_image)
    test_image = np.expand_dims(test_image, axis = 0)
    #test_image = test_image.reshape(img_width, img_height)
    result = model.predict(test_image)
    print(result)

#    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#    s.connect(('192.168.8.103', 8888)) #IP is the server IP

    if(result[0][0] <0):
        print("IZQUIERDA!")
        os.system('spd-say "LEFT"')
#        s.send("0")
    else:
        print("DERECHA!")
        os.system('spd-say "RIGHT"')
#        s.send("2")


#    s.shutdown(2)
#    s.close()


#    s.close()

    #st = str(randomNumber)
    # s.send(st)


    time.sleep(0.5)
    print("enviauu")
    
    img = cv2.imdecode(imgNp, -1)
	
	# put the image on screen
    cv2.imshow('IPWebcam',img)

    #To give the processor some less stress
    

    # Quit if q is pressed
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
