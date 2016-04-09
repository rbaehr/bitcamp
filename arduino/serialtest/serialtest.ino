int incomingByte = 0;   // for incoming serial data

void setup() {
        Serial.begin(9600); 
        pinMode(13, OUTPUT);
        // opens serial port, sets data rate to 9600 bps
}

void loop() {

        // send data only when you receive data:
        if (Serial.available() > 0) {
                // read the incoming byte:
                incomingByte = Serial.read();

                // say what you got:
                Serial.print("I received: ");
                digitalWrite(13, HIGH);
                Serial.println(incomingByte, DEC);
        }
}
