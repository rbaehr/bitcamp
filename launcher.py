from PIL import ImageGrab
from PIL import Image
import time
#from planar import BoundingBox
#(255, 162, 41)
NORMAL = (255, 162, 41)
# (minx,miny), (maxx, miny), (maxx, maxy), (minx, maxy)
#bbox = BoundingBox([(1175,671),(1177,671),(1177,673),(1175,673)])
#img = ImageGrab.grab(bbox)
#print(img.getpixel((0,0)))

time.sleep(7)


while True:
	img = ImageGrab.grab(bbox=(1176,672,1177,673))
	pix = img.getpixel((0,0))

	print(pix)
	if pix != NORMAL:
		print("GO")
		break
	print("dont go")


