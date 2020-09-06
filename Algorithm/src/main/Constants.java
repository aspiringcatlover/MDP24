package main;

public final class Constants {
	
	public static final int START_X_COORD = 1; //wrt bottom left of robot
	public static final int START_Y_COORD = 1;
	public static final int GOAL_X_COORD = 12;
	public static final int GOAL_Y_COORD = 17;
	
	//robot
	//assume start point is (0,0)
	//default steps per sec for actual run
	public static final int STEPS_PER_SEC = 1;
	public enum Direction{UP, DOWN, LEFT, RIGHT} //forward direction of robot
	public enum checkDirection{LEFT,RIGHT}
	
	//map
	public static final int GOAL_COVERAGE_PERC = 100;
	public static final int HEIGHT = 20; //in terms of no. of grid
	public static final int WIDTH = 15;
	public static final int GRID_LENGTH = 10; //10cm
	
	//sensors
	public enum RangeType{SHORT, LONG}
	public enum SensorDir{FRONT, LEFT, RIGHT}
	public static final int LONG_RANGE_DISTANCE = 90; //range is 19-90cm, technically 19cm-151cm
	public static final int SHORT_RANGE_DISTANCE = 50; //range is 9-50cm, technically 9cm-81cm
	public static final int DISTANCE_FROM_OBSTACLE = 10; // set the preferred distance robot should be away from wall
	
	//camera
	public static final int ANGLE = 90;
}
