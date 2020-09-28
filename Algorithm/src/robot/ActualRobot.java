package robot;

import connection.SocketConnection;
import main.Constants;
import map.MapPanel;
import map.SimulatorMap;
import sensor.ActualSensor;
import sensor.Sensor;
import sensor.SimulatorSensor;

public class ActualRobot extends Robot{
    private ActualSensor[] sensorArr = new ActualSensor[6];
    private static ActualRobot actualRobot = null;
    private RobotCamera camera;
    private SocketConnection socketConnection = SocketConnection.getInstance();

    // Singleton
    public static ActualRobot getInstance() {
        if (actualRobot == null) {
            actualRobot = new ActualRobot();
        }
        return actualRobot;
    }

    //constructor for actual
    public ActualRobot() {
        super();

        // initialize sensors for robot
        // 3 short for front
        sensorArr[0] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_LEFT);
        sensorArr[1] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_MIDDLE);
        sensorArr[2] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_RIGHT);
        // 1 short and 1 long for left
        sensorArr[3] = new ActualSensor(Constants.RangeType.LONG, Constants.SensorLocation.LEFT_MIDDLE);
        sensorArr[4] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.LEFT_DOWN);
        // 1 short for right
        sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_MIDDLE);
        MapPanel emptyMap = new MapPanel(SimulatorMap.getSampleMap(1));

        for (int i=0;i<3;i++){
            for (int r=0;r<3;r++){
                emptyMap.setExploredForGridCell(i,r,true);
            }
        }
        this.map = emptyMap;
    }

    //check if robot sensor detects an obstacle in the specified direction
    public boolean hasObstacle(Constants.Direction dir) {
        return true;
    };

    @Override
    public void moveForward() {
        socketConnection.sendMessage("W" + 1+ "|");
        senseMap();
    }

    @Override
    public void turn(Constants.Direction dir) {

    }

    @Override
    public void calibrate() {

    }

    @Override
    public Constants.Direction robotRightDir() {
        return null;
    }

    @Override
    public Constants.Direction robotLeftDir() {
        //send instruction to arduino to turn left
        return null;
    }

    @Override
    public Sensor getIndividualSensor(int loc) {
        return sensorArr[loc];
    }

    public void senseMap(){
        //get sensor value
        //update map
    }


}
