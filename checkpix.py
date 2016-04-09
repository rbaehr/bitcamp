from PIL import ImageGrab
from PIL import Image
import time
#from planar import BoundingBox

# (minx,miny), (maxx, miny), (maxx, maxy), (minx, maxy)
#bbox = BoundingBox([(1175,671),(1177,671),(1177,673),(1175,673)])

img = Image.open("orange.bmp")
print(img.getpixel((0,0)))
#img = ImageGrab.grab(bbox)
#print(img.getpixel((0,0)))


