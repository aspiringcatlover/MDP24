package robot;
import static Main.Constants.*;

import java.io.*;

public class RobotSensor {
	
	private float distance;
	int[] sensorDistance = new int[6];
	
	
	//constructor
	public RobotSensor(OrientationType orientation, RangeType range){
		switch(range) {
		case LONG:
			distance = LONG_RANGE_DISTANCE;
		case SHORT:
			distance = SHORT_RANGE_DISTANCE;
		default:
			System.out.println("Cannot detect sensor");
		}
	}
	
	// getters and setters
	public void setDistance(float distance) {
		this.distance = distance;
	}
		
	public float getDistance() {
		return distance;
	}
			
	public int[] getSensorDistance(OrientationType orientation, RangeType range)) {
		//get robot orientation 
		
		//get sensor data
		sensorDistance[0] = getSensorDistance(FRONT, SHORT);
		sensorDistance[1] = getSensorDistance(FRONT, SHORT);
		sensorDistance[2] = getSensorDistance(FRONT, SHORT);
		sensorDistance[3] = getSensorDistance(LEFT, SHORT);
		sensorDistance[4] = getSensorDistance(LEFT, LONG);
		sensorDistance[5] = getSensorDistance(RIGHT, SHORT);
		
		return sensorDistance;
	}
	
}
