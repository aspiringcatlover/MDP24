package main;

public final class Constants {
	
	public static final int START_X_COORD = 1; //wrt bottom left of robot
	public static final int START_Y_COORD = 1;
	public static final int GOAL_X_COORD = 12;
	public static final int GOAL_Y_COORD = 17;
	
	//robot
	//assume start point is (0,0)
	//default steps per sec for actual run
	public enum Direction{UP, DOWN, LEFT, RIGHT} //forward direction of robot
	public enum Movement{MOVE_FORWARD, TURN_RIGHT, TURN_LEFT}
	public enum checkDirection{LEFT,RIGHT}
	
	//map
	public static final int HEIGHT = 20; //in terms of no. of grid
	public static final int WIDTH = 15;
	public static final int GRID_LENGTH = 10; //10cm
	public static final int GRID_APART = 1; //min distance between obstacle and robot
	
	//sensors
	//3 front sensors: 3 short range
	//2 left sensors: 1 short range, 1 long range
	//1 right sensor: 1 short range
	public enum RangeType{SHORT, LONG}
	public enum SensorDir{FRONT, LEFT, RIGHT}
	public static final int LONG_RANGE_DISTANCE = 90; //range is 19-90cm, technically 19cm-151cm
	public static final int SHORT_RANGE_DISTANCE = 50; //range is 9-50cm, technically 9cm-81cm
	public static final int GRID_LONG_RANGE_DISTANCE = 9; //in terms of number of grids, inc itself
	public static final int GRID_SHORT_RANGE_DISTANCE = 5; //in terms of number of grids, inc itself
	public static final int DISTANCE_FROM_OBSTACLE = 10; // set the preferred distance robot should be away from wall
	
	//camera
	public static final int ANGLE = 90;
}
