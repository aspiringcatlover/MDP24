package simulator;

import static main.Constants.START_X_COORD;
import static main.Constants.START_Y_COORD;

import actual.ActualSensor;
import robot.Robot;
import robot.RobotSensor;
import main.Constants.Direction;
import main.Constants.RangeType;
import main.Constants.SensorDir;

public class SimulatorRobot extends Robot {
	
	private int steps_per_sec;
	private SimulatorSensor[] sensorArr = new SimulatorSensor[6];
	
	// constructor for simulation
	public SimulatorRobot(int steps_per_sec) {
		super();
		this.steps_per_sec = steps_per_sec;
		// initialize sensors for robot
		// 3 short for front
		for (int i=0; i < 3; i ++) {
			sensorArr[i] = new SimulatorSensor(RangeType.SHORT, SensorDir.FRONT) ;
		}
		// 1 short and 1 long for left
		sensorArr[3] = new SimulatorSensor(RangeType.SHORT, SensorDir.LEFT);
		sensorArr[4] = new SimulatorSensor(RangeType.LONG, SensorDir.LEFT);
		//1 short for right
		sensorArr[5] = new SimulatorSensor(RangeType.SHORT, SensorDir.RIGHT);
	}
	
	//getters and setters
	public void setStepsPerSec(int steps_per_sec) {
		this.steps_per_sec = steps_per_sec;
	}
	
	public int getStepsPerSec() {
		return steps_per_sec;
	}
	
}
