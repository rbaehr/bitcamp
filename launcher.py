from PIL import ImageGrab
from PIL import Image
#from planar import BoundingBox

# (minx,miny), (maxx, miny), (maxx, maxy), (minx, maxy)
#bbox = BoundingBox([(1175,671),(1177,671),(1177,673),(1175,673)])
img = ImageGrab.grab(bbox=(1176,1176,672,672))
#img = ImageGrab.grab(bbox)
print(bbox)
