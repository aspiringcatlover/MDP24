package robot;

import connection.SocketConnection;
import main.Constants;
import map.MapPanel;
import map.SimulatorActualRobot;
import map.SimulatorMap;
import sensor.ActualSensor;
import sensor.Sensor;
import sensor.SimulatorSensor;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static main.Constants.Direction.*;
import static main.Constants.MOVE_FORWARD;

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


		//TODO: pass the simulator map into this robot instead
		MapPanel emptyMap = new MapPanel(SimulatorMap.getSampleMap(1));

		for (int i = 0; i < 3; i++) {
			for (int r = 0; r < 3; r++) {
				emptyMap.setExploredForGridCell(i, r, true);
			}
		}
		this.map = SimulatorActualRobot.getRobot().getMap();
	}

	public void initialise(int x, int y, int direction){
		super.initialise(x, y, direction);
		sensorArr[0] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_LEFT, this.direction, x, y);
		sensorArr[1] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_MIDDLE, this.direction, x, y);
		sensorArr[2] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_RIGHT, this.direction, x, y);
		// 1 long for left
		sensorArr[3] = new ActualSensor(Constants.RangeType.LONG, Constants.SensorLocation.LEFT_MIDDLE, this.direction, x, y);
		// 2 short for right
		sensorArr[4] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_DOWN, this.direction, x, y);
		sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_UP, this.direction, x, y);
	}

	// check if robot sensor detects an obstacle in the specified direction
	public boolean hasObstacle(Constants.Direction dir) {
		return true;
	};

    //TODO: int step this is for fastest path
    @Override
    public void moveForward() {
        //update coordinates(both robot and sensor) + sensemap
        socketConnection.sendMessage(MOVE_FORWARD);
        System.out.println("SEND MOVE FORWARD");
        switch (direction) {
            case WEST:
                x -= 1;
                for (Sensor sensor : sensorArr) {
                    //here should update simulator map!!
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
        //acknowledge();
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
			sensorArr[5].setXCoord(x-1);
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
			sensorArr[5].setXCoord(x+1);
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
			sensorArr[5].setYCoord(y-1);
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
			sensorArr[5].setYCoord(y+1);
			// System.out.println(sensorArr[5].getXCoord());
			// System.out.println(sensorArr[5].getYCoord());
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(NORTH);
			}
			break;
		}
		try {
			// ms timeout
			Thread.sleep(2000); // Customize your refresh time
		} catch (InterruptedException e) {
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
    public Constants.Direction peekRobotRightDir(){
		int bearing;
		bearing = direction.bearing + 90;

		switch (bearing) {
			case 0:
			case 360:
				return Constants.Direction.NORTH;
			case 90:
				return Constants.Direction.EAST;
			case 180:
				return SOUTH;
			case 270:
			case -90:
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
	public Constants.Direction peekRobotLeftDir(){
		int bearing;
		bearing = direction.bearing - 90;
		switch (bearing) {
			case 0:
			case 360:
				return Constants.Direction.NORTH;
			case 90:
				return Constants.Direction.EAST;
			case 180:
				return SOUTH;
			case 270:
			case -90:
				return WEST;
			default:
				return null;
		}
	}


	public void sendMdfString(){
		String[] arr = getMdfString();
		if (arr!=null){
			System.out.println("send mdf string in actual robot");
			socketConnection.sendMessage("M{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
					"\"}]}");
		}

	}

	@Override
	public Sensor getIndividualSensor(int loc) {
		//updateSensor();
		return sensorArr[loc];
	}

	@Override
	public void initSensor() {
		for (Sensor sensor : sensorArr) {
			sensor.setDirection(direction);
		}
		System.out.println("init sensor-------");
		updateSensor();
		System.out.println("-------init sensor");
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
		String s;
		String[] arr = null;
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		int sensorDistance, numGridsSensor = 0, numGridNotDetected;
		boolean obstacleDetected = false;
		socketConnection.sendMessage(Constants.SENSE_ALL);
		System.out.println("sense to arudino in update sensor");
		boolean completed = false;

        if  (!completed) {
            s = socketConnection.receiveMessage();
            System.out.println("MESSAGE>>>> " + s);
			sensorValues = s.split(",");
			System.out.println("length of sensor values" + sensorValues.length);
			//break;
        }
//        System.arraycopy(arr, 0, sensorValues, 0, 6);
        this.sensePosition[0] = x;
        this.sensePosition[1] = y;
        this.sensePosition[2] = getDirection().bearing;

        // For each of the sensor value, we will update the map accordingly.
		for (int i = 0; i < 6; i++) {
			System.out.println("SENSOR VALUE" +sensorValues[i]);
			sensorResult = new ArrayList<>();
			double value = Double.parseDouble(sensorValues[i]);

			if (sensorArr[i].getType().equals(Constants.RangeType.SHORT)){
				sensorDistance = Constants.SHORT_RANGE_DISTANCE;
				numGridsSensor= Constants.SHORT_RANGE_DISTANCE/10;
			}
			else if (sensorArr[i].getType().equals(Constants.RangeType.LONG)){
				sensorDistance = Constants.LONG_RANGE_DISTANCE;
				numGridsSensor=Constants.LONG_RANGE_DISTANCE/10;
			}

			double calibrateValue;
			double calibrateNumGridInDeci;
			int calibrateNumGridDetected, numGridDetected;

			//raw value from sensor
			double numGridInDeci = value / 10;
			numGridDetected = (int) Math.floor(numGridInDeci); // TODO: check this
			// find number of grids that it can detect
			/*
			calibrateValue = value -2; //try
			calibrateNumGridInDeci = calibrateValue/10;
			calibrateNumGridDetected = (int) Math.floor(calibrateNumGridInDeci);
			if (calibrateNumGridDetected!=numGridDetected){
				System.out.println("calibrate grid...." + calibrateNumGridDetected);
				numGridDetected = calibrateNumGridDetected;
			}

			calibrateValue = value +2; //try
			calibrateNumGridInDeci = calibrateValue/10;
			calibrateNumGridDetected = (int) Math.floor(calibrateNumGridInDeci);
			if (calibrateNumGridDetected!=numGridDetected){
				System.out.println("calibrate grid...." + calibrateNumGridDetected);
				numGridDetected = calibrateNumGridDetected;
			}*/

			System.out.println("num grid detected" + numGridDetected);
			System.out.println("num of grids suppose to be" + numGridsSensor);
			if (numGridDetected==0){
				System.out.println("grid in front....");
				sensorResult.add(true);
				numGridNotDetected = numGridsSensor - numGridDetected - 1; // 1 is the obstacle
				for (int r = 0; r < numGridNotDetected; r++) {
					sensorResult.add(null);
				}
			}
			else if (numGridDetected >= numGridsSensor) {
				for (int r = 0; r < numGridsSensor; r++) {
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
			System.out.println("SENSOR VALUES IN SENSOR");
			sensorArr[i].updateSensor(sensorResult);
		}

		for (int r=0; r<6; r++){
			System.out.println("________________NEXT");
			for (Boolean sresult: sensorArr[r].getSensorInformation()){
				System.out.println(sresult);
			}
		}

	}


}
