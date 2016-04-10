#include <Servo.h>

#define LEFTRIGHT 3
#define FORBACK 5
#define JUMPBOOST 6


Servo lr;
Servo fb;
Servo jb;

void left() {
  //s
  lr.write(45);
}

void right() {
  //x
    lr.write(30);
}

void forw() {
  //[
  fb.write(36);
  
}

void back() {
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
  lr.write(36);
  fb.write(45);
  jb.write(25);
}


void setup() {
  Serial.begin(9600); 
  lr.attach(LEFTRIGHT);
  fb.attach(FORBACK);
  jb.attach(JUMPBOOST);
  resetall();
  
}

void loop() {
{ 
  if (Serial.available() > 0) {
    int b = Serial.parseInt();
    lr.write(b);
    jb.write(b);
    fb.write(b);
    }
} 
}
