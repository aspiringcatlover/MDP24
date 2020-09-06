package robot;

import static main.Constants.*;

import java.io.*;

public class RobotSensor {

	private RangeType type;
	private float distance;
	private SensorDir location;

	// constructor
	public RobotSensor(RangeType type, SensorDir location) {
		this.type = type;
		switch (type) {
			case LONG:
				distance = LONG_RANGE_DISTANCE;
			case SHORT:
				distance = SHORT_RANGE_DISTANCE;
			default:
				System.out.println("Cannot detect sensor");
		}
		this.location = location;
	}

	// getters and setters
	public void setType(RangeType type) {
		this.type = type;
	}

	public RangeType getType() {
		return type;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDistance() {
		return distance;
	}

	public void setLocation(SensorDir location) {
		this.location = location;
	}

	public SensorDir getLocation() {
		return location;
	}

}
