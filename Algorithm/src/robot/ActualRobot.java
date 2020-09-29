package robot;

import connection.SocketConnection;
import main.Constants;
import map.MapPanel;
import map.SimulatorMap;
import sensor.ActualSensor;
import sensor.Sensor;
import sensor.SimulatorSensor;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static main.Constants.Direction.*;

public class ActualRobot extends Robot {
	private ActualSensor[] sensorArr = new ActualSensor[6];
	private static ActualRobot actualRobot = null;
	private RobotCamera camera;
	private SocketConnection socketConnection = SocketConnection.getInstance();
	protected String[] sensorValues = new String[6];
	protected int[] sensePosition = new int[] { -1, -1, -1 };

	// Singleton
	public static ActualRobot getInstance() {
		if (actualRobot == null) {
			actualRobot = new ActualRobot();
		}
		return actualRobot;
	}

	// constructor for actual
	public ActualRobot() {
		super();

		// initialize sensors for robot
		// 3 short for front
		sensorArr[0] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_LEFT);
		sensorArr[1] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_MIDDLE);
		sensorArr[2] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_RIGHT);
		// 1 long for left
		sensorArr[3] = new ActualSensor(Constants.RangeType.LONG, Constants.SensorLocation.LEFT_MIDDLE);
		// 2 short for right
		sensorArr[4] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_DOWN);
		sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_MIDDLE);
		MapPanel emptyMap = new MapPanel(SimulatorMap.getSampleMap(1));

		for (int i = 0; i < 3; i++) {
			for (int r = 0; r < 3; r++) {
				emptyMap.setExploredForGridCell(i, r, true);
			}
		}
		this.map = emptyMap;
	}

	// check if robot sensor detects an obstacle in the specified direction
	public boolean hasObstacle(Constants.Direction dir) {
		return true;
	};

	// TODO: int step this is for fastest path
	@Override
	public void moveForward() {
		// update coordinates(both robot and sensor) + sensemap
		socketConnection.sendMessage(1 + "|");
		switch (direction) {
		case WEST:
			x -= 1;
			for (Sensor sensor : sensorArr) {
				// here should update simulator map!!
				sensor.setXCoord(sensor.getXCoord() - 1);
				sensor.setDirection(WEST);
			}
			break;
		case EAST:
			x += 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setXCoord(sensor.getXCoord() + 1);
				sensor.setDirection(EAST);
			}
			break;
		case SOUTH:
			y -= 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setYCoord(sensor.getYCoord() - 1);
				sensor.setDirection(SOUTH);
			}
			break;
		case NORTH:
			y += 1;
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setYCoord(sensor.getYCoord() + 1);
				sensor.setDirection(NORTH);
			}
			break;
		default:
			break;
		}
		updateSensor();
		// acknowledge();
	}

	@Override
	public void turn(Constants.Direction dir) {
		// update coordinates(robot and sensor) + sensemap
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
			}
			break;
		}
		updateSensor();
	}

	@Override
	public void calibrate() {
		socketConnection.sendMessage(Constants.CALIBRATE);
		/*
		 * if (sr != null) { sr.displayMessage("Sent message: " + Constant.CALIBRATE,
		 * 1); } acknowledge();
		 */
	}

	@Override
	public Constants.Direction robotRightDir() {
		socketConnection.sendMessage(Constants.TURN_RIGHT);
		// acknowledge();
		int bearing;
		bearing = direction.bearing + 90;

		switch (bearing) {
		case 0:
		case 360:
			direction = NORTH;
			return Constants.Direction.NORTH;
		case 90:
			direction = EAST;
			return Constants.Direction.EAST;
		case 180:
			direction = SOUTH;
			return SOUTH;
		case 270:
		case -90:
			direction = WEST;
			return WEST;
		default:
			return null;
		}

	}

	@Override
	public Constants.Direction robotLeftDir() {
		socketConnection.sendMessage(Constants.TURN_LEFT);
		// send instruction to arduino to turn left
		// acknowledge();
		int bearing;
		bearing = direction.bearing - 90;
		switch (bearing) {
		case 0:
		case 360:
			direction = NORTH;
			return Constants.Direction.NORTH;
		case 90:
			direction = EAST;
			return Constants.Direction.EAST;
		case 180:
			direction = SOUTH;
			return SOUTH;
		case 270:
		case -90:
			direction = WEST;
			return WEST;
		default:
			return null;
		}
	}

	@Override
	public Sensor getIndividualSensor(int loc) {
		updateSensor();
		return sensorArr[loc];
	}

	private void updateSensor() {
		/*
		 * get sensor value from arudino, put them into ArrayList<boolean> into their
		 * respective sensor true --> obstacle present false --> obstacle not present
		 * null --> cannot detected as block by obstacle
		 * 
		 * arrangement of sensor values: UL, UM, UR, LT (long sensor for this), LM, RT
		 */

		// Accept only all integer sensor values or all double sensor values
		Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
		Pattern sensorPattern2 = Pattern.compile(
				"\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+");
		String s;
		String[] arr = null;
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		int sensorDistance, numGridsSensor = 0, numGridNotDetected;
		boolean obstacleDetected = false;
		socketConnection.sendMessage(Constants.SENSE_ALL);
		boolean completed = false;

		while (!completed) {
			s = socketConnection.receiveMessage().trim();
			if (sensorPattern.matcher(s).matches() || sensorPattern2.matcher(s).matches()) {
				arr = s.split("\\|");
				break;
			}
		}
		System.arraycopy(arr, 0, sensorValues, 0, 6);
		this.sensePosition[0] = x;
		this.sensePosition[1] = y;
		this.sensePosition[2] = getDirection().bearing;

		// For each of the sensor value, we will update the map accordingly.
		for (int i = 0; i < 6; i++) {
			System.out.println("SENSOR VALUE" + sensorValues[i]);
			sensorResult = new ArrayList<>();
			double value = Double.parseDouble(sensorValues[i]);

			if (sensorArr[i].getType().equals(Constants.RangeType.SHORT)) {
				sensorDistance = Constants.SHORT_RANGE_DISTANCE;
				numGridsSensor = Constants.SHORT_RANGE_DISTANCE / 10;
			} else if (sensorArr[i].getType().equals(Constants.RangeType.LONG)) {
				sensorDistance = Constants.LONG_RANGE_DISTANCE;
				numGridsSensor = Constants.LONG_RANGE_DISTANCE / 10;
			}

			// find number of grids that it can detect
			double numGridInDeci = value / 10;
			int numGridDetected = (int) Math.floor(numGridInDeci); // TODO: check this
			if (numGridDetected == numGridsSensor) {
				for (int r = 0; r < numGridDetected; r++) {
					sensorResult.add(false);
				}
			} else {
				for (int r = 0; r < numGridDetected; r++) {
					sensorResult.add(false);
				}
				numGridNotDetected = numGridsSensor - numGridDetected - 1; // 1 is the obstacle
				sensorResult.add(true);
				for (int r = 0; r < numGridNotDetected; r++) {
					sensorResult.add(null);
				}

			}
			sensorArr[i].updateSensor(sensorResult);
		}

	}

}
