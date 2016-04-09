from PIL import ImageGrab
from PIL import Image
import time, serial, syslog

port = '/dev/ttyACM4'
ard = serial.Serial(port,9600,timeout=5)
#(255, 162, 41)
NORMAL = (255, 162, 41)

time.sleep(7)


while True:
	img = ImageGrab.grab(bbox=(1176,672,1177,673))
	pix = img.getpixel((0,0))

	print(pix)
	if pix != NORMAL:
		print("GO, sent to " + port)
                ard.flush()
                ard.write(1)
		break
	print("dont go")


