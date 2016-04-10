import time, serial

port =  'COM12'
ard = serial.Serial(port, 9600, timeout=5)

#these are the byte codes we'll send to the arduino

stop = 0b00
forward = 0b01
back = 0b10

noTurn = 0b00
left = 0b01
right = 0b10

noBoost = 0b0
boost = 0b1

noJump = 0b0
jump = 0b1
	#{0 0 boost jump right left backward forward)
csv = open('control.csv')

def parse_line():
	line = csv.readline()
	if not line: return -1
	cmdList = line.split(",")
	return cmdList

c_milli = lambda: int(round(time.time() * 1000))

#setup intial values
speed = stop
turn = noTurn
accel = noBoost
air = noJump

cmds = parse_line()
timeCsv =float(cmds[0])
loop_delta = 0
bit_o = 0

while cmds is not -1:
	#read the time till next event and sleep until it
	delta = float(cmds[0]) - timeCsv
	print("delta {d}\tloop_delta {dl}\tdiff: {diff}".format(d=delta, dl=loop_delta, diff=(delta-loop_delta)))
	time.sleep((delta - loop_delta)/1000) 
	timeLocal = c_milli()
	bit = 0

	if float(cmds[1]) == 1 and accel == noBoost:
		accel = boost
	elif float(cmds[1]) == 0 and accel == boost:
		accel = noBoost

	bit |= accel

	if float(cmds[2]) == 1 and air == noJump:
		air = jump
	elif float(cmds[2]) == 0 and air == jump:
		air = noJump

	bit = bit << 1
	bit |= air

	if float(cmds[3]) > .2 and turn != right:
		turn = right
	elif float(cmds[3]) < -.2 and turn != left:
		turn = left
	elif turn != noTurn:
		turn = noTurn

	bit = bit << 2
	bit |= turn

	if float(cmds[4]) > .2 and speed != forward:
		speed = forward
	elif float(cmds[4]) < -.2 and speed != back:
		speed = back
	elif speed != stop:
		speed = stop

	bit = bit << 2
	bit |= speed

	print("bit: {b}\tbit old:{b2}".format(b=bit, b2=bit_o))
	if bit != bit_o:
		print("sending bit")
		bit_o = bit
		ard.flush()
		ard.write(bytes([bit]))
	
	timeCsv = float(cmds[0])
	cmds = parse_line()
	loop_delta = c_milli() - timeLocal
ard.flush()
ard.write(bytes([128]))
