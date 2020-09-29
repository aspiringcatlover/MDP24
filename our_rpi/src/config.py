LOCALE = 'UTF-8'

# Android BT connection settings
RFCOMM_CHANNEL = 7
RPI_MAC_ADDR = 'B8:27:EB:AF:DC:B9'
UUID = '00001101-0000-1000-8000-00805F9B34FB' #need to change to our tablet one
ANDROID_SOCKET_BUFFER_SIZE = 512

# Algorithm Wifi connection settings
WIFI_IP = '192.168.24.24'
WIFI_PORT = 8080
ALGORITHM_SOCKET_BUFFER_SIZE = 512

# Arduino USB connection settings
SERIAL_PORT = '/dev/ttyACM0'
BAUD_RATE = 57600

# Image Recognition Settings
STOPPING_IMAGE = 'stop_image_processing.png'

IMAGE_WIDTH = 1920
IMAGE_HEIGHT = 1080
IMAGE_FORMAT = 'bgr'

BASE_IP = 'tcp://192.168.15.'
PORT = ':5555'

IMAGE_PROCESSING_SERVER_URLS = {
    'cheyanne': BASE_IP + '54' + PORT,
    'elbert': BASE_IP + '00' + PORT,  # don't have elbert's ip address yet
    'jason': BASE_IP + '52' + PORT,
    'joshua': BASE_IP + '93' + PORT,
    'mingyang': BASE_IP + '74' + PORT,
    'reuben': BASE_IP + '00' + PORT,  # don't have reuben's ip address yet
    'winston': BASE_IP + '55' + PORT,
    'yingting': BASE_IP + '90' + PORT,
}
