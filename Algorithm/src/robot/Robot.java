package robot;
import static Main.Constants.*;

import controller.*;

public class Robot {
	
	enum Direction{UP, DOWN, LEFT, RIGHT}

	private int x_coord; //wrt bottom left of robot
	private int y_coord;
	private int steps_per_sec;
	private Direction direction; // wrt camera direction
	private RobotSensor[] shortSensorArr = new RobotSensor[4];
	private RobotSensor[] longSensorArr = new RobotSensor[2];
	private RobotCamera camera;
	
	// constructor for simulation
	public Robot(int steps_per_sec) {
		x_coord = START_X_COORD;
		y_coord = START_Y_COORD;
		this.steps_per_sec = steps_per_sec;
		// assuming starting direction is UP if right wall hugging 
		direction = Direction.UP;
		for (int i=0; i < shortSensorArr.length; i ++) {
			shortSensorArr[i] = new RobotSensor(RangeType.SHORT) ;
		}
		for (int i=0; i < longSensorArr.length; i ++) {
			longSensorArr[i] = new RobotSensor(RangeType.LONG);
		}
		RobotCamera camera = new RobotCamera();
	}
	
	//constructor for actual
	public Robot() {
		x_coord = START_X_COORD;
		y_coord = START_Y_COORD;
		this.steps_per_sec = STEPS_PER_SEC;
		// assuming starting direction is UP if right wall hugging 
		direction = Direction.UP;
		for (int i=0; i < shortSensorArr.length; i ++) {
			shortSensorArr[i] = new RobotSensor(RangeType.SHORT) ;
		}
		for (int i=0; i < longSensorArr.length; i ++) {
			longSensorArr[i] = new RobotSensor(RangeType.LONG);
		}
		RobotCamera camera = new RobotCamera();
	}
	
	// getters and setters
	public void setXCoord(int x_coord) {
		this.x_coord = x_coord;
	}
	
	public int getXCoord() {
		return x_coord;
	}
	
	public void setYCoord(int y_coord) {
		this.y_coord = y_coord;
	}
	
	public int getYCoord() {
		return y_coord;
	}
	
	public void setStepsPerSec(int steps_per_sec) {
		this.steps_per_sec = steps_per_sec;
	}
	
	public int getStepsPerSec() {
		return steps_per_sec;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	//helper functions
	
	
	
}
