package sensor;
import static main.Constants.LONG_RANGE_DISTANCE;
import static main.Constants.SHORT_RANGE_DISTANCE;

import main.Constants.RangeType;
import main.Constants.SensorDir;

public class ActualSensor extends RobotSensor{

	private float distance;
	
	public ActualSensor(RangeType type, SensorDir location) {
		super(type,location);
		switch (super.getType()) {
		case LONG:
			distance = LONG_RANGE_DISTANCE;
		case SHORT:
			distance = SHORT_RANGE_DISTANCE;
		default:
			//System.out.println("Cannot detect sensor");
	}
	}
	
}
