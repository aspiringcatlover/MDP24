package robot;

import main.Constants;
import sensor.ActualSensor;

public class ActualRobot{
    private ActualSensor[] sensorArr = new ActualSensor[6];
    private static ActualRobot actualRobot = null;
    private RobotCamera camera;

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
        /*
        for (int i=0; i < 3; i ++) {
            sensorArr[i] = new ActualSensor(Constants.RangeType.SHORT, SensorDir.FRONT) ;
        }
        // 1 short and 1 long for left
        sensorArr[3] = new ActualSensor(Constants.RangeType.SHORT, SensorDir.LEFT);
        sensorArr[4] = new ActualSensor(Constants.RangeType.LONG, SensorDir.LEFT);
        //1 short for right
        sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, SensorDir.RIGHT);
        RobotCamera camera = new RobotCamera();*/
    }

    //check if robot sensor detects an obstacle in the specified direction
    public boolean hasObstacle(Constants.Direction dir) {
        return true;
    };



}
