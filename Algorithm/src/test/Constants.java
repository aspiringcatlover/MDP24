package test;

public final class Constants {

	public static final int START_X_COORD = 1; // wrt middle of robot
	public static final int START_Y_COORD = 1;
	public static final int GOAL_X_COORD = 12;
	public static final int GOAL_Y_COORD = 17;

	// map direction wrt robot's forward direction
	public enum Direction {
		UP(0), RIGHT(90), DOWN(180), LEFT(270);

		public final int bearing;

		Direction(int i) {
			this.bearing = i;
		}
	}

	// forward direction of robot
	public enum Movement {
		MOVE_FORWARD, TURN_RIGHT, TURN_LEFT
	}

	public enum checkDirection {
		LEFT, RIGHT
	}

	// map
	// TO NOTE!!!!!
	// fix height (row) to be 15 and width (col) to be 20 acc to sample_map
	public static final int HEIGHT = 15; // in terms of no. of grid
	public static final int WIDTH = 20;
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
		LEFT_TOP(3), LEFT_MIDDLE(4), UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2), RIGHT_TOP(5);

		private int numVal;

		SensorLocation(int numVal) {
	        this.numVal = numVal;
	    }

		public int getNumVal() {
			return numVal;
		}
	}
	public static final int LONG_RANGE_DISTANCE = 90; // range is 19-90cm, technically 19cm-151cm
	public static final int SHORT_RANGE_DISTANCE = 50; // range is 9-50cm, technically 9cm-81cm
	public static final int GRID_LONG_RANGE_DISTANCE = 5; // in terms of number of grids, starting outside of robot
	public static final int GRID_SHORT_RANGE_DISTANCE = 2; // in terms of number of grids, starting outside of robot
	public static final int DISTANCE_FROM_OBSTACLE = 10; // set the preferred distance robot should be away from wall

	// camera
	public static final int ANGLE = 90;

	// public static final String IP_ADDRESS = "127.0.0.1";
	public static final String IP_ADDRESS = "192.168.24.24";
	public static final int PORT = 8080;
	public static final int BUFFER_SIZE = 512;
}
