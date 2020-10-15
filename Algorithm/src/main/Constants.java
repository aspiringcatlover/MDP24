package main;

public final class Constants {

	public static final int START_X_COORD = 1; // wrt middle of robot
	public static final int START_Y_COORD = 1;
	public static final int GOAL_X_COORD = 12;
	public static final int GOAL_Y_COORD = 17;

	// Actual run constraints
	public static final int TIME = 300000;
	public static final int PERCENTAGE = 100;
	public static final int SPEED = 9999;
	public static final boolean IMAGE_REC = false;

	// map direction wrt robot's forward direction
	public enum Direction {
		NORTH(0), EAST(90), SOUTH(180), WEST(270);

		public final int bearing;

		Direction(int i) {
			this.bearing = i;
		}
	}

	// forward direction of robot
	public enum Movement {
		MOVE_FORWARD, TURN_RIGHT, TURN_LEFT, FRONT_CALIBRATION, RIGHT_CALIBRATION
	}

	public enum checkDirection {
		LEFT, RIGHT
	}

	// map
	// TO NOTE!!!!!
	// fix height (row) to be 15 and width (col) to be 20 acc to sample_map
	public static final int HEIGHT = 20; // in terms of no. of grid
	public static final int WIDTH = 15;
	public static final int GRID_LENGTH = 10; // 10cm
	public static final int GRID_APART = 1; // min distance between obstacle and robot

	// sensors
	// 3 front sensors: 3 short range
	// 2 left sensors: 1 short range, 1 long range
	// 1 right sensor: 1 short range
	public enum RangeType {
		SHORT, LONG
	}
	//location of sensor and number in array
	public enum SensorLocation {
		LEFT_MIDDLE(3), RIGHT_DOWN(4), UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2), RIGHT_UP(5);

		private int numVal;

		SensorLocation(int numVal) {
	        this.numVal = numVal;
	    }

		public int getNumVal() {
			return numVal;
		}
	}


	public static final double[][] SENSOR_RANGES = {{13.54, 21.46}, //UL
												{10.0, 19.35},  //UM
												{12, 21.555},   //UR
												{19.4, 24.8, 32.15, 43, 51.75, 62.0},  //LT
												{12.85, 22.7},   //LM
												{13.65, 25.25}   //RT
												};

	public static final int LONG_RANGE_DISTANCE = 70; // range is 19-90cm, technically 19cm-151cm
	public static final int SHORT_RANGE_DISTANCE = 20; // range is 9-50cm, technically 9cm-81cm
	public static final int GRID_LONG_RANGE_DISTANCE = 5; // in terms of number of grids, starting outside of robot
	public static final int GRID_SHORT_RANGE_DISTANCE = 2; // in terms of number of grids, starting outside of robot
	public static final int DISTANCE_FROM_OBSTACLE = 10; // set the preferred distance robot should be away from wall

	// camera
	public static final int ANGLE = 90;

	//public static final String IP_ADDRESS = "127.0.0.1";
	public static final String IP_ADDRESS = "192.168.24.24";
	public static final int PORT = 8080;
	public static final int BUFFER_SIZE = 512;


	public static final String START_EXPLORATION = "ES|";
	public static final String FASTEST_PATH = "FS|";
	public static final String IMAGE_STOP = "I";
	public static final String SEND_ARENA = "SendArena";
	public static final String INITIALISING = "starting";
	public static final String SETWAYPOINT = "waypoint";
	public static final String SENSE_ALL = "Z|";
	public static final String MOVE_FORWARD = "W|";
	public static final String TURN_LEFT = "A|";
	public static final String TURN_RIGHT = "D|";
	public static final String CALIBRATE = "L|";
	public static final String RIGHTALIGN = "B|";
	public static final String END_TOUR = "N";
	public static final String U_TURN="S|";
	public static final String FRONT_CALIBRATION = "V|";
	public static final String FASTEST_PATH_END = "M|";

	// Connection Acknowledge
	public static final String IMAGE_ACK = "D";

	//num of sensor values to average
	public static final int SENSOR_VALUES = 5;

	//num grid for image range
	public static final int IMAGE_RANGE = 2;
}
