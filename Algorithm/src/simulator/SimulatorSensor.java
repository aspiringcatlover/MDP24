package simulator;
import static main.Constants.*;

import main.Constants.RangeType;
import main.Constants.SensorDir;
import robot.RobotSensor;

public class SimulatorSensor extends RobotSensor{
	
	private float gridDistance;
	
	public SimulatorSensor(RangeType type, SensorDir location) {
		super(type,location);
		switch (super.getType()) {
		case LONG:
			gridDistance = GRID_LONG_RANGE_DISTANCE;
		case SHORT:
			gridDistance = GRID_SHORT_RANGE_DISTANCE;
		default:
			System.out.println("Cannot detect sensor");
		}
	}
}
