#include <Servo.h>

#define LEFTRIGHT 3
#define FORBACK 5
#define JUMPBOOST 6


#define LR 36
#define FB 45
#define JB 25


Servo lr;
Servo fb;
Servo jb;

void lrreset() {
  //s
  lr.write(LR);
}

void fbreset() {
  //x
    lr.write(FB);
}

void jbreset() {
  //[
  fb.write(JB);
  
}

void left() {
  //s
  lr.write(45);
}

void right() {
  //x
    lr.write(30);
}

void forward() {
  //[
  fb.write(36);
  
}

void backward() {
  //'
  fb.write(52);
  
  
}

void boost() {
   //j
   jb.write(17);
   
}

void jump() {
 //u
  jb.write(32);
}

void flip() {
  
}

void resetall(){
  lr.write(LR);
  fb.write(FB);
  jb.write(JB);
}


void setup() {
  Serial.begin(9600); 
  lr.attach(LEFTRIGHT);
  fb.attach(FORBACK);
  jb.attach(JUMPBOOST);
  resetall();
  
}

void loop() 
{
  if (Serial.available() > 0) {
//  	#{0 0 boost jump right left backward forward)

    int b = Serial.read();
    if ( b&1) {
	    forward();
	  } else if ((b>>1)&1) {
	    backward();
	  } else {
	    fbreset();
	  }
	if ( (b>>2)&1) {
	    left();
	  } else if ( (b>>3)&1) {
	    right();
	  } else {
	    lrreset();
	  }
    if ( (b>>4)&1) {
	    jump();
	  } else if ( (b>>5)&1) {
	    boost();
	  } else {
	    jbreset();
	  }
	  if (b & 128) {
      resetall();
		while ( b != 64) {
			b = Serial.read();
			delay(100);
		}
	  }
  }
}
