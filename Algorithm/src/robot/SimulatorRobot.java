package robot;

import static main.Constants.*;

import map.MapPanel;
import map.SimulatorMap;
import sensor.SimulatorSensor;

import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class SimulatorRobot extends Robot{


	private SimulatorMap simulatorMap; //this is the simulator itself maybe can rename or smth

	// constructor
	public SimulatorRobot() {
		super(Direction.NORTH,START_X_COORD, START_Y_COORD);
		x = START_X_COORD;
		y = START_Y_COORD;
		// assuming forward direction of robot is DOWN if right wall hugging
		// in simulation
		direction = Direction.NORTH;
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

	public void setXCoord(int x) {
		this.x = x;
	}

	public int getXCoord() {
		return x;
	}

	public void setYCoord(int y) {
		this.y = y;
	}

	public int getYCoord() {
		return y;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public SimulatorSensor getIndividualSensor(int i) {
		return (SimulatorSensor) sensorArr[i];
	}

	// helper functions
	// move robot forward
	public void moveForward() {
		switch (direction) {
			case WEST:
				x -= 1;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setXCoord(sensor.getXCoord() - 1);
				}
				break;
			case EAST:
				x += 1;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setXCoord(sensor.getXCoord() + 1);
				}
				break;
			case SOUTH:
				y -= 1;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setYCoord(sensor.getYCoord() - 1);
				}
				break;
			case NORTH:
				y += 1;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setYCoord(sensor.getYCoord() + 1);
				}
				break;
		default:
			break;
		}
	}

	// turn robot in a specified direction
	public void turn(Direction dir) {
		direction = dir;
		System.out.println("Direction" + direction);
		switch (direction) {
			case WEST:
				// LEFT_TOP(3)
				sensorArr[3].setXCoord(x - 1);
				sensorArr[3].setYCoord(y - 2);
				// LEFT_MIDDLE(4)
				sensorArr[4].setXCoord(x);
				sensorArr[4].setYCoord(y - 2);
				// UP_LEFT(0)
				sensorArr[0].setXCoord(x - 2);
				sensorArr[0].setYCoord(y - 1);
				// UP_MIDDLE(1)
				sensorArr[1].setXCoord(x - 2);
				sensorArr[1].setYCoord(y);
				// UP_RIGHT(2)
				sensorArr[2].setXCoord(x -2);
				sensorArr[2].setYCoord(y +1);
				// RIGHT_TOP(5)
				sensorArr[5].setXCoord(x -1);
				sensorArr[5].setYCoord(y +2);
				break;
			case EAST:
				// LEFT_TOP(3)
				sensorArr[3].setXCoord(x + 1);
				sensorArr[3].setYCoord(y + 2);
				// LEFT_MIDDLE(4)
				sensorArr[4].setXCoord(x);
				sensorArr[4].setYCoord(y+2);
				// UP_LEFT(0)
				sensorArr[0].setXCoord(x + 2);
				sensorArr[0].setYCoord(y + 1);
				// UP_MIDDLE(1)
				sensorArr[1].setXCoord(x+2);
				sensorArr[1].setYCoord(y);
				// UP_RIGHT(2)
				sensorArr[2].setXCoord(x +2);
				sensorArr[2].setYCoord(y -1);
				// RIGHT_TOP(5)
				sensorArr[5].setXCoord(x +1);
				sensorArr[5].setYCoord(y -2);
				break;
			case SOUTH:
				// LEFT_TOP(3)
				sensorArr[3].setXCoord(x +2);
				sensorArr[3].setYCoord(y -1);
				// LEFT_MIDDLE(4)
				sensorArr[4].setXCoord(x +2);
				sensorArr[4].setYCoord(y);
				// UP_LEFT(0)
				sensorArr[0].setXCoord(x +1);
				sensorArr[0].setYCoord(y -2);
				// UP_MIDDLE(1)
				sensorArr[1].setXCoord(x);
				sensorArr[1].setYCoord(y-2);
				// UP_RIGHT(2)
				sensorArr[2].setXCoord(x - 1);
				sensorArr[2].setYCoord(y - 2);
				// RIGHT_TOP(5)
				sensorArr[5].setXCoord(x - 2);
				sensorArr[5].setYCoord(y - 1);
				break;
			case NORTH:
				// LEFT_TOP(3)
				sensorArr[3].setXCoord(x -2);
				sensorArr[3].setYCoord(y +1);
				//System.out.println(sensorArr[3].getXCoord());
				//System.out.println(sensorArr[3].getYCoord());
				// LEFT_MIDDLE(4)
				sensorArr[4].setXCoord(x-2);
				sensorArr[4].setYCoord(y);
				//System.out.println(sensorArr[4].getXCoord());
				//System.out.println(sensorArr[4].getYCoord());
				// UP_LEFT(0)
				sensorArr[0].setXCoord(x -1);
				sensorArr[0].setYCoord(y +2);
				//System.out.println(sensorArr[0].getXCoord());
				//System.out.println(sensorArr[0].getYCoord());
				// UP_MIDDLE(1)
				sensorArr[1].setXCoord(x);
				sensorArr[1].setYCoord(y+2);
				//System.out.println(sensorArr[1].getXCoord());
				//System.out.println(sensorArr[1].getYCoord());
				// UP_RIGHT(2)
				sensorArr[2].setXCoord(x + 1);
				sensorArr[2].setYCoord(y + 2);
				//System.out.println(sensorArr[2].getXCoord());
				//System.out.println(sensorArr[2].getYCoord());
				// RIGHT_TOP(5)
				sensorArr[5].setXCoord(x + 2);
				sensorArr[5].setYCoord(y + 1);
				//System.out.println(sensorArr[5].getXCoord());
				//System.out.println(sensorArr[5].getYCoord());
				break;
		}
	}

	// return map direction to the right of the forward direction of robot
	public Direction robotRightDir() {
		return HelperDir(Movement.TURN_RIGHT);
	}

	// return map direction to the left of the forward direction of robot
	public Direction robotLeftDir() {
		return HelperDir(Movement.TURN_LEFT);
	}



	/* --> to translate from coordinate to movement
	public Movement HelperDir(Direction dir){
		if (direction.equals(dir))
			return Movement.MOVE_FORWARD;
		else if (direction.bearing - dir.bearing==90 || direction.bearing - dir.bearing==-270)
			return Movement.TURN_LEFT;
		else
			return Movement.TURN_RIGHT;
	}*/

	// determine forward pointing direction of robot wrt map after turning
	// dir is the direction that the robot wants to turn to
	//--> get robot direction, when robot turn ___
	public Direction HelperDir(Movement movement){
		int bearing;
		if (movement.equals(Movement.TURN_RIGHT)){
			bearing = direction.bearing + 90;
		}
		else{
			bearing = direction.bearing - 90;
			System.out.println("bearing" + bearing);
		}
		switch (bearing){
			case 0:
			case 360:
				return Direction.NORTH;
			case 90: return  Direction.EAST;
			case 180: return Direction.SOUTH;
			case 270:
			case -90:
				return Direction.WEST;
			default: return  null;
		}
	}

	/*
	public Direction HelperDir(Direction dir) {
		// the left of robot is to look from anti-clockwise direction
		// the right of robot is to look from clockwise direction
		Direction[] directions = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
		// index gives index based on current direction
		int index = 0;
		for (int i = 0; i < directions.length; i++) {
			if (directions[i] == direction) {
				index = i;
			}
		}
		int newIndex;
		if (dir == Direction.WEST) {
			// left index gives index based on robot's left direction
			newIndex = (index == 0) ? directions.length - 1 : index - 1;
		} else {
			// right index gives index based on robot's right direction
			newIndex = (index == directions.length - 1) ? 0 : index + 1;
		}

		return directions[newIndex];
	}*/
	
	public void initMap(MapPanel map){
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				simulatorMap.getMap().colorMap(map.getGridCell(row, col));
			}
			System.out.println();
		}
	}


	/*
	//set color for map
	public void setColor() {
		if (explored) {
			setBackground(Color.BLUE);
		}
		else if (obstacle){
			setBackground(Color.RED);
		}
		revalidate();
		repaint();
	}

	//set color for robot
	public void setRobotColor() {
		setBackground(Color.ORANGE);
		revalidate();
		repaint();
	}*/

	/*
	public void displayDirection(Direction direction) {
		switch (direction) {
			case NORTH:
				BasicArrowButton arrowSouth = new BasicArrowButton(BasicArrowButton.EAST);
				add(arrowSouth, BorderLayout.NORTH);
				revalidate();
				repaint();
				break;
			case SOUTH:
				BasicArrowButton arrowNorth = new BasicArrowButton(BasicArrowButton.WEST);
				add(arrowNorth, BorderLayout.NORTH);
				revalidate();
				repaint();
				break;
			case WEST:
				BasicArrowButton arrowEast = new BasicArrowButton(BasicArrowButton.NORTH);
				add(arrowEast, BorderLayout.NORTH);
				revalidate();
				repaint();
				break;
			case EAST:
				BasicArrowButton arrowWest = new BasicArrowButton(BasicArrowButton.SOUTH);
				add(arrowWest, BorderLayout.NORTH);
				revalidate();
				repaint();
				break;
		}
	}*/
}
