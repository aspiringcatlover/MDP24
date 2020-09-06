import serial

#Script to communicate between arduino and rpi via sending of messages using serial


class Arduino:
    def __init__(self, serial_port=SERIAL_PORT, baud_rate=BAUD_RATE):
        self.serial_port = serial_port
        self.baud_rate = baud_rate
        self.connection = None

def connect(self):
    while True:
        try:
            self.connection = serial.Serial(self.serial_port, self.baud_rate)



def disconnect(self):

def read(self):

def write(self, message):

