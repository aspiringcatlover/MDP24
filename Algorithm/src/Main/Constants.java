package Main;

public final class Constants {
	
	public static final int START_X_COORD = 0; //wrt bottom left of robot
	public static final int START_Y_COORD = 0;
	public static final int GOAL_X_COORD = 12;
	public static final int GOAL_Y_COORD = 17;
	
	//robot
	//assume start point is (0,0)
	//default steps per sec for actual run
	public static final int STEPS_PER_SEC = 1;
	
	//map
	public static final int GOAL_COVERAGE_PERC = 100;
	public static final int HEIGHT = 20; //in terms of no. of grid
	public static final int WIDTH = 15;
	
	//sensors
	public enum RangeType{SHORT, LONG}
	public static final int LONG_RANGE_DISTANCE = 90; //range is 19-90cm, technically 19cm-151cm
	public static final int SHORT_RANGE_DISTANCE = 50; //range is 9-50cm, technically 9cm-81cm
	
	//camera
	public static final int ANGLE = 90;
}
