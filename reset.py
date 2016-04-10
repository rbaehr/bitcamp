import time, serial

port =  'COM12'
ard = serial.Serial(port, 9600, timeout=5)
ard.write(bytes([64]))
