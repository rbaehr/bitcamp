#include <Servo.h>

#define LEFTRIGHT 3
#define FORBACK 5
#define JUMPBOOST 6

#define NEUTRAL 30 
#define UP 60
#define DOWN 0
Servo lr;
Servo fb;
Servo jb;

void left() {
  
}

void right() {
  
}

void forw() {
  
}

void back() {
  
}

void boost() {
   
}

void jump(int d) {
  
}

void flip() {
  
}

void resetall(){
  lr.write(NEUTRAL);
  fb.write(NEUTRAL);
  jb.write(NEUTRAL);
}
void setup() {
  Serial.begin(9600); 
  pinMode(13, OUTPUT);
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
