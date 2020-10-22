package robot;

import connection.SocketConnection;
import main.Constants;
import map.GridCell;
import map.MapPanel;
import simulator.SimulatorActualRun;
import simulator.Simulator;
import sensor.ActualSensor;
import sensor.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static main.Constants.*;
import static main.Constants.Direction.*;

public class ActualRobot extends Robot {
	private ActualSensor[] sensorArr = new ActualSensor[6];
	private static ActualRobot actualRobot = null;
	private SocketConnection socketConnection = SocketConnection.getInstance();
	private String[] sensorValues = new String[6];
	private int[] sensePosition = new int[] { -1, -1, -1 };

	// Singleton
	public static ActualRobot getInstance() {
		if (actualRobot == null) {
			actualRobot = new ActualRobot();
		}
		return actualRobot;
	}

	// constructor for actual
	private ActualRobot() {
		super();

		// initialize sensors for robot
		// 3 short for front

		//TODO: pass the simulator map into this robot instead
		MapPanel emptyMap = new MapPanel(Simulator.getSampleMap(1));

		for (int i = 0; i < 3; i++) {
			for (int r = 0; r < 3; r++) {
				emptyMap.setExploredForGridCell(i, r, true);
			}
		}
		this.map = SimulatorActualRun.getRobot().getMap();
	}

	public void initialise(int x, int y, int direction){
		super.initialise(x, y, direction);
		sensorArr[0] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_LEFT, this.direction, x, y);
		sensorArr[1] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_MIDDLE, this.direction, x, y);
		sensorArr[2] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_RIGHT, this.direction, x, y);
		// 1 long for left
		sensorArr[3] = new ActualSensor(Constants.RangeType.LONG, SensorLocation.LEFT_MIDDLE, this.direction, x, y);
		// 2 short for right
		sensorArr[4] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_DOWN, this.direction, x, y);
		sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_UP, this.direction, x, y);
	}

	@Override
	public void turnWithoutSensor(Direction dir) {
		// update coordinates(robot only)
		direction = dir;
		System.out.println("Direction" + direction);

		System.out.println("is there any calibration?");
		//calibrate here
		actualRobot.calibrate();
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
		}
	}

	@Override
	public void moveForwardWithoutSensor() {
		//update coordinates(both robot only)
		socketConnection.sendMessage(MOVE_FORWARD);
		System.out.println("SEND MOVE FORWARD");
		switch (direction) {
			case WEST:
				x -= 1;
				break;
			case EAST:
				x += 1;
				break;
			case SOUTH:
				y -= 1;
				break;
			case NORTH:
				y += 1;
				break;
			default:
				break;
		}
		actualRobot.calibrate();
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
		}
	}

	// check if robot sensor detects an obstacle in the specified direction
	public boolean hasObstacle(Constants.Direction dir) {
		return true;
	}

    //TODO: int step this is for fastest path
    @Override
    public void moveForward() {
		boolean calibrateDone=false;
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
		actualRobot.calibrate();
        /*
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			calibrateDone = true;
			//calibrateCounter++;
		}
		if (calibrateDone){
			try {
				// ms timeout
				Thread.sleep(100); // Customize your refresh time
			} catch (InterruptedException e) {
			}
		}

        updateSensor();
        //acknowledge();
    }

	@Override
	public void moveForward(int steps) {
		socketConnection.sendMessage(steps + "|"); //eg 3|
		System.out.println("SEND MOVE FORWARD x" + steps);
		switch (direction) {
			case WEST:
				x -= steps;
				for (Sensor sensor : sensorArr) {
					//here should update simulator map!!
					sensor.setXCoord(sensor.getXCoord() - steps);
					sensor.setDirection(WEST);
				}
				break;
			case EAST:
				x += steps;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setXCoord(sensor.getXCoord() + steps);
					sensor.setDirection(EAST);
				}
				break;
			case SOUTH:
				y -= steps;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setYCoord(sensor.getYCoord() - steps);
					sensor.setDirection(SOUTH);
				}
				break;
			case NORTH:
				y += steps;
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setYCoord(sensor.getYCoord() + steps);
					sensor.setDirection(NORTH);
				}
				break;
			default:
				break;
		}
		actualRobot.calibrate();
		/*
		//
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/
		//updateSensor();
		//acknowledge();
	}



	@Override
	public void turn(Constants.Direction dir) {
        boolean calibrateDone=false;
		// update coordinates(robot and sensor) + sensemap
		direction = dir;
		System.out.println("Direction" + direction);
		switch (direction) {
		case WEST:
			// LEFT_MId(3)
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
			// LEFT_MID(3)
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
			// LEFT_MID(3)
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
			// LEFT_MID(3)
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
			// System.out.println(sensorArr[5].getYCoord());
			for (sensor.Sensor sensor : sensorArr) {
				sensor.setDirection(NORTH);
			}
			break;
		}




		System.out.println("is there any calibration?");
		//calibrate here
		actualRobot.calibrate();
		/*if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			calibrateDone = true;
			//calibrateCounter= 0;
		}*/
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			calibrateDone = true;
			//calibrateCounter++;
		}


		if (calibrateDone){
            try {
                // ms timeout
                Thread.sleep(100); // Customize your refresh time
            } catch (InterruptedException e) {
            }
        }

		updateSensor();

		/*
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			//calibrateCounter++;
		}

		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/
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
		actualRobot.calibrate();
		/*
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/

		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			//calibrateCounter++;
		}

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
		actualRobot.calibrate();
		/*
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			//calibrateCounter++;
		}

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

	@Override
	public void calibrateFront() {
		socketConnection.sendMessage(Constants.FRONT_CALIBRATION);
	}

	@Override
	public void uTurn() {
		//robot coord doesnt change
		actualRobot.calibrate();
		/*
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}*/
		if (hasObstacleOnFront()){
			System.out.println("obstacle on front...calibrate");
			//calibrate front
			actualRobot.calibrateFront();
			//calibrateCounter++;
		}
		socketConnection.sendMessage(U_TURN);
		System.out.println("SEND U TURN");
		switch (direction) {
			case WEST:

				for (Sensor sensor : sensorArr) {
					//here should update simulator map!!
					sensor.setDirection(EAST);
				}
				direction=EAST;
				break;
			case EAST:
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setDirection(WEST);
				}
				direction=WEST;
				break;
			case SOUTH:
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setDirection(NORTH);
				}
				direction = NORTH;
				break;
			case NORTH:
				for (sensor.Sensor sensor : sensorArr) {
					sensor.setDirection(SOUTH);
				}
				direction = SOUTH;
				break;
			default:
				break;
		}


		actualRobot.calibrate();

		/*
		if (hasObstacleOnRight()){
			System.out.println("obstacle on corner...calibrate");
			actualRobot.calibrate();
			//calibrateCounter= 0;
		}

		try {
			// ms timeout
			Thread.sleep(100); // Customize your refresh time
		} catch (InterruptedException e) {
		}*/

		updateSensor();
	}

	@Override
	public String takePhoto(ArrayList<int[]> coordinates) {
    	//send C[1,1|-1,-1|-1,-1] x,y
    	StringBuilder message= new StringBuilder();
    	message.append("C[");
    	int[] coordinate;
		for (int i=0; i<coordinates.size();i++){
			coordinate = coordinates.get(i);
			if (coordinate==null){
				message.append("-1,-1");
			}
			else{
				message.append(coordinate[0]);
				message.append(",");
				message.append(coordinate[1]);
			}
			if (i!=coordinates.size()-1){
				message.append("|");
			}
		}
		message.append("]");
		socketConnection.sendMessage("\n"+ message.toString()+"\n");
		/*
		try {
			// ms timeout
			Thread.sleep(200); // Customize your refresh time
		} catch (InterruptedException e) {
		}*/
		//String messageReceive = socketConnection.receiveMessage();
		//System.out.println("message receive: " + messageReceive);
		return "";
	}

	@Override
	public ArrayList<Movement> movementForRobotFaceDirection(Direction direction) {
		int bearing;
		ArrayList<Movement> movements = new ArrayList<>();
    	if (this.direction==direction)
    		return null;

		bearing = this.direction.bearing - direction.bearing;

		if (bearing>180){
			bearing = bearing-360;
		}
		else if (bearing<-180){
			bearing = 360+bearing;
		}

		while (bearing!=0){
			//positive turn left, negative turn right
			if (bearing<0){
				movements.add(Movement.TURN_RIGHT);
				bearing+= 90;
			}
			else {
				movements.add(Movement.TURN_LEFT);
				bearing-=90;
			}
		}
		return movements;
	}

	@Override
	public void goToWall() {
		socketConnection.sendMessage("M|");
	}

	public void sendMdfString(){
		String[] arr = getMdfString();
		if (arr!=null){
			System.out.println("send mdf string in actual robot");
			socketConnection.sendMessage("\nM{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
					"\"}]}\n");
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

	private String[] getSensorValue(){
		String s;
		String[] arr = null;
		socketConnection.sendMessage(Constants.SENSE_ALL);
		System.out.println("sense to arudino in update sensor");
		boolean completed = false;

		if  (!completed) {

			s = socketConnection.receiveMessage();
			System.out.println("MESSAGE>>>> " + s);
			arr = s.split(",");
			System.out.println("length of sensor values" + sensorValues.length);
			//break;
		}
		return arr;
	}

	private String[][] getSensorValue(int num){
		StringBuilder s= new StringBuilder();
		String message;
		String[][] arr = new String[SENSOR_VALUES][6];
		String[] arrCheck;
		boolean skip=false;
		int skipCounter=0;
        int length;


		for (int i=0; i<num;i++){
			s.append(SENSE_ALL);
		}

		socketConnection.sendMessage(s.toString());
		System.out.println("sense to arudino in update sensor");
		System.out.println("send multiple sensor request....");
		boolean completed = false;

		if  (!completed) {
			for (int i=0; i<num;i++){
				if (skip){
					if (skipCounter==1){
						skip=false;
					}
					skipCounter--;
					continue;
				}
				message = socketConnection.receiveMessage();
				System.out.println("MESSAGE>>>> " + message);
				//check if 2 msg is joint tgt
				arrCheck = message.split(",");
                arr[i] = Arrays.copyOfRange(arrCheck, 0, 6) ;

                length = 6;
				while (arrCheck.length>length){
				    skip = true;
                    skipCounter++;
                    arr[i+skipCounter] = Arrays.copyOfRange(arrCheck, length, length+6) ;
                    length = length+6;
                }

			}
			System.out.println("length of sensor values" + sensorValues.length);

		}
		System.out.println("CHECK SENSOR VALUES");
		for (int i=0; i<SENSOR_VALUES;i++){
			for (int r=0; r<6; r++){
				System.out.print(arr[i][r]+ " ");

			}
			System.out.println();
		}
		return arr;
	}

	private void updateSensor(){
		ArrayList<Boolean> sensorResult;
		int sensorDistance, numGridsSensor = 0, numGridNotDetected;
		boolean obstacleDetected = false;
    	int[] total = new int[6];

    	//init array
		for (int i=0; i<6;i++){
			total[i] = 0;
		}

    	ArrayList<String[]> sensorValueArrayList= new ArrayList<>();

		String[][] result = getSensorValue(SENSOR_VALUES);
		for (int i=0; i<SENSOR_VALUES;i++){
			sensorValueArrayList.add(result[i]);
		}


		ArrayList<Integer> individualValue1 = new ArrayList<>();
		ArrayList<Integer> individualValue2 = new ArrayList<>();
		ArrayList<Integer> individualValue3 = new ArrayList<>();
		ArrayList<Integer> individualValue4 = new ArrayList<>();
		ArrayList<Integer> individualValue5 = new ArrayList<>();
		ArrayList<Integer> individualValue0 = new ArrayList<>();
		for (String[] indiValue: sensorValueArrayList){
			individualValue0.add(Integer.parseInt(indiValue[0]));
			individualValue1.add(Integer.parseInt(indiValue[1]));
			individualValue2.add(Integer.parseInt(indiValue[2]));
			individualValue3.add(Integer.parseInt(indiValue[3]));
			individualValue4.add(Integer.parseInt(indiValue[4]));
			individualValue5.add(Integer.parseInt(indiValue[5]));
		}

		Collections.sort(individualValue0);
		Collections.sort(individualValue1);
		Collections.sort(individualValue2);
		Collections.sort(individualValue3);
		Collections.sort(individualValue4);
		Collections.sort(individualValue5);

		sensorValues[0]=Integer.toString((individualValue0.get(2)));
		sensorValues[1]=Integer.toString((individualValue1.get(2)));
		sensorValues[2]=Integer.toString((individualValue2.get(2)));
		sensorValues[3]=Integer.toString((individualValue3.get(2)));
		sensorValues[4]=Integer.toString((individualValue4.get(2)));
		sensorValues[5]=Integer.toString((individualValue5.get(2)));




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

	private void updateSensor1() {
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
		ArrayList<Boolean> sensorResult;
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

	public boolean hasObstacleOnFront(){
		System.out.println("check if hav 3 obstacle on the right");
		int x,y;

		switch (actualRobot.getDirection()){
			case WEST:
				x=actualRobot.getXCoord()-2;
				y=actualRobot.getYCoord()-1;
				return checkObstacleRow(false,y,x);
			case EAST:
				x=actualRobot.getXCoord()+2;
				y=actualRobot.getYCoord()-1;
				return checkObstacleRow(false,y,x);
			case SOUTH:
				x=actualRobot.getXCoord()-1;
				y=actualRobot.getYCoord()-2;
				return checkObstacleRow(true,y,x);
			case NORTH:
				x=actualRobot.getXCoord()-1;
				y=actualRobot.getYCoord()+2;
				return checkObstacleRow(true,y,x);
		}
		return true;
	}


	public boolean hasObstacleOnRight(){
		System.out.println("check if hav 3 obstacle on the right");
		int x,y;

		switch (actualRobot.getDirection()){
			case WEST:
				x=actualRobot.getXCoord()-1;
				y=actualRobot.getYCoord()+2;
				return checkObstacleWholeRow(true,y,x);
			case EAST:
				x=actualRobot.getXCoord()-1;
				y=actualRobot.getYCoord()-2;
				return checkObstacleWholeRow(true,y,x);
			case SOUTH:
				x=actualRobot.getXCoord()-2;
				y=actualRobot.getYCoord()-1;
				return checkObstacleWholeRow(false,y,x);
			case NORTH:
				x=actualRobot.getXCoord()+2;
				y=actualRobot.getYCoord()-1;
				return checkObstacleWholeRow(false,y,x);
		}
		return true;
	}

	private boolean checkObstacleWholeRow(boolean xIncrease, int y, int x){
		GridCell gridCell;
		if (xIncrease){
			for (int i=0;i<3;i++){
				gridCell = map.getGridCell(y,x+i);
				if (gridCell != null && !gridCell.getObstacle()) {
					System.out.println("check obstacle row: y=" + y +" "+true);
					return false;
				}
			}
		}
		else{
			for (int i=0;i<3;i++){
				gridCell = map.getGridCell(y+i,x);
				if (gridCell != null && !gridCell.getObstacle()) {
					return false;
				}
			}
		}
		System.out.println("check obstacle row false");
		return true;
	}



	private boolean checkObstacleRow(boolean xIncrease, int y, int x){
		GridCell gridCell;
		if (xIncrease){
			for (int i=0;i<3;i++){
				gridCell = map.getGridCell(y,x+i);
				if (gridCell == null || gridCell.getObstacle()) {
					System.out.println("check obstacle row: y=" + y +" "+true);
					return true;
				}
			}
		}
		else{
			for (int i=0;i<3;i++){
				gridCell = map.getGridCell(y+i,x);
				if (gridCell == null || gridCell.getObstacle()) {
					return true;
				}
			}
		}
		System.out.println("check obstacle row false");
		return false;
	}





}
