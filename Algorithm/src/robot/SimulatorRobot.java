package robot;

import static main.Constants.START_X_COORD;
import static main.Constants.START_Y_COORD;

import main.Constants.Direction;
import main.Constants.RangeType;
import main.Constants.SensorDir;
import sensor.ActualSensor;
import sensor.SimulatorSensor;

public class SimulatorRobot extends Robot {
	
	private SimulatorSensor[] sensorArr = new SimulatorSensor[6];
	
	// constructor for simulation
	public SimulatorRobot() {
		super();
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
	
	
}
