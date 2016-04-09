#include <Servo.h>

#define LEFTRIGHT 3
#define FORBACK 5
#define JUMPBOOST 6

Servo lr;
Servo fb;
Servo jb;

void left() {
  
}

void right() {
  
}

void for() {
  
}

void back() {
  
}

void boost() {
   
}

void jump(int d) {
  
}

void flip() {
  
}

void setup() {
  Serial.begin(9600); 
  pinMode(13, OUTPUT);
  lr.attach(LEFTRIGHT);
  fb.attach(FORBACK);
  jb.attach(JUMPBOOST);
  
}

void loop() {
  if (Serial.available() > 0) {
    int b = Serial.read();
    if ( b == 1) {
      // Ready to go
    }
  }
}
