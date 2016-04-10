import time, serial

port =  'dev/ttyACM4'
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
timeCsv = cmds[0]
loop_delta = 0
bit_o = 0

while cmds is not -1:
	#read the time till next event and sleep until it
	timeLocal = c_milli()
	delta = cmds[1] - timeCsv
	time.sleep(delta / 1000 - loop_delta) 
	bit = 0

	if float(cmds[1]) == 1 and accel == noBoost:
		accel = boost
	elif float(cmds[1]) == 0 and accel == boost:
		accel = noBoost

	bit &= accel

	if float(cmds[2]) == 1 and air == noJump:
		air = jump
	elif float(cmds[2]) == 0 and air == jump:
		air = noJump

	bit = bit << 1
	bit &= air

	if float(cmds[3]) > .2 and turn != right:
		turn = right
	elif float(cmds[3]) < -.2 and turn != left:
		turn = left
	elif turn != noTurn:
		turn = noTurn

	bit = bit << 2
	bit &= turn

	if float(cmds[4]) > .2 and speed != forward:
		speed = forward
	elif float(cmds[4]) < -.2 and speed != back:
		speed = back
	elif speed != stop:
		speed = stop

	bit = bit << 2
	bit &= speed

	if bit != bit_o:
		bit_o = bit
		ard.write(bit)
		ard.flush()

	cmds = parse_line()
	loop_delta = c_milli() - timeLocal