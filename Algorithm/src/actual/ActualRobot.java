package actual;

import static main.Constants.START_X_COORD;
import static main.Constants.START_Y_COORD;
import static main.Constants.STEPS_PER_SEC;

import main.Constants.Direction;
import main.Constants.RangeType;
import main.Constants.SensorDir;
import robot.RobotCamera;
import robot.RobotSensor;
import robot.Robot;

public class ActualRobot extends Robot {

	private RobotSensor[] sensorArr = new RobotSensor[6];
	private RobotCamera camera;
	
	//constructor for actual
	public ActualRobot() {
		super();
		// initialize sensors for robot
		// 3 short for front
		for (int i=0; i < 3; i ++) {
			sensorArr[i] = new RobotSensor(RangeType.SHORT, SensorDir.FRONT) ;
		}
		// 1 short and 1 long for left
		sensorArr[3] = new RobotSensor(RangeType.SHORT, SensorDir.LEFT);
		sensorArr[4] = new RobotSensor(RangeType.LONG, SensorDir.LEFT);
		//1 short for right
		sensorArr[5] = new RobotSensor(RangeType.SHORT, SensorDir.RIGHT);
		RobotCamera camera = new RobotCamera();
		}
	
	//check if robot sensor detects an obstacle in the specified direction
	public boolean hasObstacle(Direction dir) {
		return true;
	};
	
}
