package sensor;

import static main.Constants.*;

import java.io.*;

public abstract class RobotSensor {

	protected RangeType type;
	protected SensorDir location;

	// constructor
	public RobotSensor(RangeType type, SensorDir location) {
		this.type = type;
		this.location = location;
	}

	// getters and setters
	public void setType(RangeType type) {
		this.type = type;
	}

	public RangeType getType() {
		return type;
	}

	public void setLocation(SensorDir location) {
		this.location = location;
	}

	public SensorDir getLocation() {
		return location;
	}

}
