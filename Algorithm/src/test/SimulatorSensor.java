package test;

import static test.Constants.*;

public class SimulatorSensor {

	private RangeType type;
	private SensorLocation location;
	private int gridDistance;
	private int x_coord;
	private int y_coord;

	// constructor
	public SimulatorSensor(RangeType type, SensorLocation location) {
		this.type = type;
		this.location = location;
		switch (type) {
		case LONG:
			gridDistance = GRID_LONG_RANGE_DISTANCE;
			break;
		case SHORT:
			gridDistance = GRID_SHORT_RANGE_DISTANCE;
			break;
		default:
			// System.out.println("Cannot detect sensor");
		}
		//assuming forward direction of robot wrt map is DOWN
		switch(location) {
		case LEFT_TOP:
			x_coord = 3;
			y_coord = 2;
			break;
		case LEFT_MIDDLE:
			x_coord = 3;
			y_coord = 1;
			break;
		case UP_LEFT:
			x_coord = 2;
			y_coord = 3;
			break;
		case UP_MIDDLE:
			x_coord = 1;
			y_coord = 3;
			break;
		case UP_RIGHT:
			x_coord = 0;
			y_coord = 3;
			break;
		case RIGHT_TOP:
			x_coord = -1;
			y_coord = 2;
			break;
		}
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
	
	public void setType(RangeType type) {
		this.type = type;
	}

	public RangeType getType() {
		return type;
	}

	public void setLocation(SensorLocation location) {
		this.location = location;
	}

	public SensorLocation getLocation() {
		return location;
	}
	
	public int getGridDistance() {
		return gridDistance;
	}
	
	

}
