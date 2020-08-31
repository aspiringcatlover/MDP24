package robot;
import static Main.Constants.*;

import java.io.*;

public class RobotSensor {
	
	private float distance;
	
	//constructor
	public RobotSensor(RangeType type){
		switch(type) {
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
	
}
