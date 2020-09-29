package robot;

import static main.Constants.*;
import static main.Constants.Direction.*;

import map.MapPanel;
import map.SimulatorMap;
import sensor.Sensor;
import sensor.SimulatorSensor;

public class SimulatorRobot extends Robot {

	private MapPanel map;
	private int steps_per_sec;
	//SimulatorMap simulatorMap; // this is the simulator itself maybe can rename or smth

	// constructor
	public SimulatorRobot(MapPanel map, int steps_per_sec) {

		super(Direction.NORTH, START_X_COORD, START_Y_COORD);
		this.steps_per_sec=steps_per_sec;
		// map for the maze to simulate
		this.map = map;
		this.mdfString = map.getMdfString();
		x = START_X_COORD;
		y = START_Y_COORD;
		// assuming forward direction of robot is DOWN if right wall hugging
		// in simulation
		direction = EAST;
		// initialize sensors for robot
		// 3 short for front
		sensorArr[0] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_LEFT, map);
		sensorArr[1] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_MIDDLE, map);
		sensorArr[2] = new SimulatorSensor(RangeType.SHORT, SensorLocation.UP_RIGHT, map);
		// 1 long for left
		sensorArr[3] = new SimulatorSensor(RangeType.LONG, SensorLocation.LEFT_MIDDLE, map);
		// 2 short for right
		sensorArr[4] = new SimulatorSensor(RangeType.SHORT, SensorLocation.RIGHT_DOWN, map);
		sensorArr[5] = new SimulatorSensor(RangeType.SHORT, SensorLocation.RIGHT_MIDDLE, map);

		//simulatorMap = new SimulatorMap();
		MapPanel emptyMap = new MapPanel(SimulatorMap.getSampleMap(1));

		for (int i = 0; i < 3; i++) {
			for (int r = 0; r < 3; r++) {
				emptyMap.setExploredForGridCell(i, r, true);
			}
		}
		map = emptyMap;
		// simulator map is the one that shld be updated
	}

	public MapPanel getMap() {
		return map;
	}

	public Sensor getIndividualSensor(int i) {
		return sensorArr[i];
	}

	// helper functions
	// move robot forward, set sensor coordinate and direction
	public void moveForward() {
		SimulatorSensor simulatorSensor;
		switch (direction) {
		case WEST:
			x -= 1;
			for (Sensor sensor : sensorArr) {

				// here should update simulator map!!
				sensor.setXCoord(sensor.getXCoord() - 1);
				sensor.setDirection(WEST);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case EAST:
			x += 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setXCoord(sensor.getXCoord() + 1);
				sensor.setDirection(EAST);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case SOUTH:
			y -= 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setYCoord(sensor.getYCoord() - 1);
				sensor.setDirection(SOUTH);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case NORTH:
			y += 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setYCoord(sensor.getYCoord() + 1);
				sensor.setDirection(NORTH);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		default:
			break;
		}


		try {
			// ms timeout
			int timeout = 1000/ steps_per_sec;
			Thread.sleep(timeout); // Customize your refresh time
		} catch (InterruptedException e) {
		}

	}

	// turn robot in a specified direction
	public void turn(Direction dir) {
		SimulatorSensor simulatorSensor;
		direction = dir;
		System.out.println("Direction" + direction);
		switch (direction) {
		case WEST:
			// LEFT_MIDDLE(3)
			sensorArr[3].setXCoord(x);
			sensorArr[3].setYCoord(y - 2);
			// RIGHT_DOWN(4)
			sensorArr[4].setXCoord(x + 1);
			sensorArr[4].setYCoord(y + 2);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x - 2);
			sensorArr[0].setYCoord(y - 1);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x - 2);
			sensorArr[1].setYCoord(y);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x - 2);
			sensorArr[2].setYCoord(y + 1);
			// RIGHT_MIDDLE(5)
			sensorArr[5].setXCoord(x);
			sensorArr[5].setYCoord(y + 2);
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(WEST);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case EAST:
			// LEFT_MIDDLE(3)
			sensorArr[3].setXCoord(x);
			sensorArr[3].setYCoord(y + 2);
			// RIGHT_DOWN(4)
			sensorArr[4].setXCoord(x - 1);
			sensorArr[4].setYCoord(y - 2);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x + 2);
			sensorArr[0].setYCoord(y + 1);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x + 2);
			sensorArr[1].setYCoord(y);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x + 2);
			sensorArr[2].setYCoord(y - 1);
			// RIGHT_MIDDLE(5)
			sensorArr[5].setXCoord(x);
			sensorArr[5].setYCoord(y - 2);
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(EAST);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case SOUTH:
			// LEFT_MIDDLE(3)
			sensorArr[3].setXCoord(x + 2);
			sensorArr[3].setYCoord(y);
			// RIGHT_DOWN(4)
			sensorArr[4].setXCoord(x - 2);
			sensorArr[4].setYCoord(y + 1);
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x + 1);
			sensorArr[0].setYCoord(y - 2);
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x);
			sensorArr[1].setYCoord(y - 2);
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x - 1);
			sensorArr[2].setYCoord(y - 2);
			// RIGHT_MIDDLE(5)
			sensorArr[5].setXCoord(x - 2);
			sensorArr[5].setYCoord(y);
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(SOUTH);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		case NORTH:
			// LEFT_MIDDLE(3)
			sensorArr[3].setXCoord(x - 2);
			sensorArr[3].setYCoord(y);
			// System.out.println(sensorArr[3].getXCoord());
			// System.out.println(sensorArr[3].getYCoord());
			// RIGHT_DOWN(4)
			sensorArr[4].setXCoord(x + 2);
			sensorArr[4].setYCoord(y - 1);
			// System.out.println(sensorArr[4].getXCoord());
			// System.out.println(sensorArr[4].getYCoord());
			// UP_LEFT(0)
			sensorArr[0].setXCoord(x - 1);
			sensorArr[0].setYCoord(y + 2);
			// System.out.println(sensorArr[0].getXCoord());
			// System.out.println(sensorArr[0].getYCoord());
			// UP_MIDDLE(1)
			sensorArr[1].setXCoord(x);
			sensorArr[1].setYCoord(y + 2);
			// System.out.println(sensorArr[1].getXCoord());
			// System.out.println(sensorArr[1].getYCoord());
			// UP_RIGHT(2)
			sensorArr[2].setXCoord(x + 1);
			sensorArr[2].setYCoord(y + 2);
			// System.out.println(sensorArr[2].getXCoord());
			// System.out.println(sensorArr[2].getYCoord());
			// RIGHT_MIDDLE(5)
			sensorArr[5].setXCoord(x + 2);
			sensorArr[5].setYCoord(y);
			// System.out.println(sensorArr[5].getXCoord());
			// System.out.println(sensorArr[5].getYCoord());
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(NORTH);
				simulatorSensor = (SimulatorSensor) sensor; // downcasting
				simulatorSensor.setSensorInformation();
			}
			break;
		}
		/*map.displayDirection(y,x,direction);
		map.updateMap(x,y);*/
		//sleep to simulate

		try {
			// ms timeout
			int timeout = 1000/ steps_per_sec;
			Thread.sleep(timeout); // Customize your refresh time
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void calibrate() {

	}

	// return map direction to the right of the forward direction of robot
	public Direction robotRightDir() {
		return HelperDir(Movement.TURN_RIGHT);
	}

	// return map direction to the left of the forward direction of robot
	public Direction robotLeftDir() {
		return HelperDir(Movement.TURN_LEFT);
	}

	/*
	 * --> to translate from coordinate to movement public Movement
	 * HelperDir(Direction dir){ if (direction.equals(dir)) return
	 * Movement.MOVE_FORWARD; else if (direction.bearing - dir.bearing==90 ||
	 * direction.bearing - dir.bearing==-270) return Movement.TURN_LEFT; else return
	 * Movement.TURN_RIGHT; }
	 */

	// determine forward pointing direction of robot wrt map after turning
	// dir is the direction that the robot wants to turn to
	// --> get robot direction, when robot turn ___
	public Direction HelperDir(Movement movement) {
		int bearing;
		if (movement.equals(Movement.TURN_RIGHT)) {
			bearing = direction.bearing + 90;
		} else {
			bearing = direction.bearing - 90;
			System.out.println("bearing" + bearing);
		}
		switch (bearing) {
		case 0:
		case 360:
			return Direction.NORTH;
		case 90:
			return Direction.EAST;
		case 180:
			return SOUTH;
		case 270:
		case -90:
			return WEST;
		default:
			return null;
		}
	}

	/*
	 * public Direction HelperDir(Direction dir) { // the left of robot is to look
	 * from anti-clockwise direction // the right of robot is to look from clockwise
	 * direction Direction[] directions = { Direction.NORTH, Direction.EAST,
	 * Direction.SOUTH, Direction.WEST}; // index gives index based on current
	 * direction int index = 0; for (int i = 0; i < directions.length; i++) { if
	 * (directions[i] == direction) { index = i; } } int newIndex; if (dir ==
	 * Direction.WEST) { // left index gives index based on robot's left direction
	 * newIndex = (index == 0) ? directions.length - 1 : index - 1; } else { //
	 * right index gives index based on robot's right direction newIndex = (index ==
	 * directions.length - 1) ? 0 : index + 1; }
	 * 
	 * return directions[newIndex]; }
	 */


	public void initMap(MapPanel map) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {

				this.map.setGridCell(row, col,map.getGridCell(row, col) );
			}
			System.out.println();
		}
	}



	/*
	 * //set color for map public void setColor() { if (explored) {
	 * setBackground(Color.BLUE); } else if (obstacle){ setBackground(Color.RED); }
	 * revalidate(); repaint(); }
	 * 
	 * //set color for robot public void setRobotColor() {
	 * setBackground(Color.ORANGE); revalidate(); repaint(); }
	 */

	/*
	 * public void displayDirection(Direction direction) { switch (direction) { case
	 * NORTH: BasicArrowButton arrowSouth = new
	 * BasicArrowButton(BasicArrowButton.EAST); add(arrowSouth, BorderLayout.NORTH);
	 * revalidate(); repaint(); break; case SOUTH: BasicArrowButton arrowNorth = new
	 * BasicArrowButton(BasicArrowButton.WEST); add(arrowNorth, BorderLayout.NORTH);
	 * revalidate(); repaint(); break; case WEST: BasicArrowButton arrowEast = new
	 * BasicArrowButton(BasicArrowButton.NORTH); add(arrowEast, BorderLayout.NORTH);
	 * revalidate(); repaint(); break; case EAST: BasicArrowButton arrowWest = new
	 * BasicArrowButton(BasicArrowButton.SOUTH); add(arrowWest, BorderLayout.NORTH);
	 * revalidate(); repaint(); break; } }
	 */
}
