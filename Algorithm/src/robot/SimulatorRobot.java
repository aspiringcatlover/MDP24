package robot;

import static main.Constants.*;

import sensor.SimulatorSensor;

public class SimulatorRobot{
	
	int x_coord; // wrt middle of robot
	int y_coord;
	Direction direction; // map direction wrt robot's forward direction
	private SimulatorSensor[] sensorArr = new SimulatorSensor[6];

	// constructor
	public SimulatorRobot() {
		x_coord = START_X_COORD;
		y_coord = START_Y_COORD;
		// assuming forward direction of robot is DOWN if right wall hugging
		// in simulation
		direction = Direction.DOWN;
		// initialize sensors for robot
		// 3 short for front
		sensorArr[0] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_LEFT);
		sensorArr[1] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_MIDDLE);
		sensorArr[2] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_RIGHT);
		// 1 short and 1 long for left
		sensorArr[3] = new SimulatorSensor(RangeType.LONG, SensorLocation.LEFT_TOP);
		sensorArr[4] = new SimulatorSensor(RangeType.SHORT, SensorLocation.LEFT_MIDDLE);
		// 1 short for right
		sensorArr[5] = new SimulatorSensor(RangeType.SHORT, SensorLocation.RIGHT_TOP);
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

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public SimulatorSensor getIndividualSensor(int i) {
		return sensorArr[i];
	}

	// helper functions
	// move robot forward
	public void moveForward() {
		switch (direction) {
		case UP:
			y_coord -= 1;
			for (int i = 0; i < sensorArr.length; i++) {
				sensorArr[i].setYCoord(sensorArr[i].getYCoord() - 1);
			}
			break;
		case DOWN:
			y_coord += 1;
			for (int i = 0; i < sensorArr.length; i++) {
				sensorArr[i].setYCoord(sensorArr[i].getYCoord() + 1);
			}
			break;
		case LEFT:
			x_coord -= 1;
			for (int i = 0; i < sensorArr.length; i++) {
				sensorArr[i].setXCoord(sensorArr[i].getXCoord() - 1);
			}
			break;
		case RIGHT:
			x_coord += 1;
			for (int i = 0; i < sensorArr.length; i++) {
				sensorArr[i].setXCoord(sensorArr[i].getXCoord() + 1);
			}
			break;
		default:
			break;
		}
	}

	// turn robot in a specified direction
	public void turn(Direction dir) {
		direction = dir;
		switch (direction) {
		case UP:
			// LEFT_TOP(3)
			sensorArr[3].setXCoord(x_coord - 2);
			sensorArr[3].setYCoord(y_coord - 1);
			// LEFT_MIDDLE(4)
			sensorArr[4].setXCoord(x_coord - 2);
			sensorArr[4].setYCoord(y_coord);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x_coord - 1);
			sensorArr[0].setYCoord(y_coord - 2);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x_coord);
			sensorArr[1].setYCoord(y_coord - 2);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x_coord + 1);
			sensorArr[2].setYCoord(y_coord - 2);
			// RIGHT_TOP(5)
			sensorArr[5].setXCoord(x_coord + 2);
			sensorArr[5].setYCoord(y_coord - 1);
			break;
		case DOWN:
			// LEFT_TOP(3)
			sensorArr[3].setXCoord(x_coord + 2);
			sensorArr[3].setYCoord(y_coord + 1);
			// LEFT_MIDDLE(4)
			sensorArr[4].setXCoord(x_coord + 2);
			sensorArr[4].setYCoord(y_coord);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x_coord + 1);
			sensorArr[0].setYCoord(y_coord + 2);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x_coord);
			sensorArr[1].setYCoord(y_coord + 2);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x_coord - 1);
			sensorArr[2].setYCoord(y_coord + 2);
			// RIGHT_TOP(5)
			sensorArr[5].setXCoord(x_coord - 2);
			sensorArr[5].setYCoord(y_coord + 1);
			break;
		case LEFT:
			// LEFT_TOP(3)
			sensorArr[3].setXCoord(x_coord - 1);
			sensorArr[3].setYCoord(y_coord + 2);
			// LEFT_MIDDLE(4)
			sensorArr[4].setXCoord(x_coord);
			sensorArr[4].setYCoord(y_coord + 2);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x_coord - 2);
			sensorArr[0].setYCoord(y_coord + 1);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x_coord - 2);
			sensorArr[1].setYCoord(y_coord);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x_coord - 2);
			sensorArr[2].setYCoord(y_coord - 1);
			// RIGHT_TOP(5)
			sensorArr[5].setXCoord(x_coord - 1);
			sensorArr[5].setYCoord(y_coord - 2);
			break;
		case RIGHT:
			// LEFT_TOP(3)
			sensorArr[3].setXCoord(x_coord + 1);
			sensorArr[3].setYCoord(y_coord - 2);
			System.out.println(sensorArr[3].getXCoord());
			System.out.println(sensorArr[3].getYCoord());
			// LEFT_MIDDLE(4)
			sensorArr[4].setXCoord(x_coord);
			sensorArr[4].setYCoord(y_coord - 2);
			System.out.println(sensorArr[4].getXCoord());
			System.out.println(sensorArr[4].getYCoord());
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x_coord + 2);
			sensorArr[0].setYCoord(y_coord - 1);
			System.out.println(sensorArr[0].getXCoord());
			System.out.println(sensorArr[0].getYCoord());
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x_coord + 2);
			sensorArr[1].setYCoord(y_coord);
			System.out.println(sensorArr[1].getXCoord());
			System.out.println(sensorArr[1].getYCoord());
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x_coord + 2);
			sensorArr[2].setYCoord(y_coord + 1);
			System.out.println(sensorArr[2].getXCoord());
			System.out.println(sensorArr[2].getYCoord());
			// RIGHT_TOP(5)
			sensorArr[5].setXCoord(x_coord + 1);
			sensorArr[5].setYCoord(y_coord + 2);
			System.out.println(sensorArr[5].getXCoord());
			System.out.println(sensorArr[5].getYCoord());
			break;
		}
	}

	// return map direction to the right of the forward direction of robot
	public Direction robotRightDir() {
		return HelperDir(Direction.RIGHT);
	}

	// return map direction to the left of the forward direction of robot
	public Direction robotLeftDir() {
		return HelperDir(Direction.LEFT);
	}

	// determine forward pointing direction of robot wrt map after turning
	// dir is the direction that the robot wants to turn to

	public Direction HelperDir(Direction dir) {
		// the left of robot is to look from anti-clockwise direction
		// the right of robot is to look from clockwise direction
		Direction[] directions = { Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };
		// index gives index based on current direction
		int index = 0;
		for (int i = 0; i < directions.length; i++) {
			if (directions[i] == direction) {
				index = i;
			}
		}
		int newIndex;
		if (dir == Direction.LEFT) {
			// left index gives index based on robot's left direction
			newIndex = (index == 0) ? directions.length - 1 : index - 1;
		} else {
			// right index gives index based on robot's right direction
			newIndex = (index == directions.length - 1) ? 0 : index + 1;
		}

		return directions[newIndex];
	}
	
	
}
