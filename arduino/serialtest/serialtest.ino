int incomingByte = 0;   // for incoming serial data

void setup() {
        Serial.begin(9600); 
        pinMode(13, OUTPUT);
        // opens serial port, sets data rate to 9600 bps
}

void loop() {
//  #{0 0 boost jump right left backward forward)

        // send data only when you receive data:
        if (Serial.available() > 0) {
                // read the incoming byte:
                incomingByte = Serial.read();
                Serial.flush();

                // say what you got:
                Serial.print("I received: ");
                if(incomingByte == 16){
                  digitalWrite(13, HIGH); 
                  delay(100);
                  digitalWrite(13,LOW); 
                }
                
                Serial.println(incomingByte, DEC);
        }
}
