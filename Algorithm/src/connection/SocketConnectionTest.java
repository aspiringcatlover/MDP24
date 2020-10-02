package connection;

import main.Constants;
import sensor.ActualSensor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SocketConnectionTest {

    public static void main(String[] args) {
        ActualSensor[] sensorArr = new ActualSensor[6];
        int result = JOptionPane.CLOSED_OPTION;
        int debug = JOptionPane.CLOSED_OPTION;
        int simulator = JOptionPane.CLOSED_OPTION;
        String[] sensorValues = new String[6];
        int[] sensePosition = new int[]{-1, -1, -1};
        Constants.Direction direction = Constants.Direction.NORTH;
        int x =0;
        int y=0;
        // initialize sensors for robot
        // 3 short for front
        sensorArr[0] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_LEFT, direction, x, y);
        sensorArr[1] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_MIDDLE, direction, x, y);
        sensorArr[2] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.UP_RIGHT, direction, x, y);
        // 1 long for left
        sensorArr[3] = new ActualSensor(Constants.RangeType.LONG, Constants.SensorLocation.LEFT_MIDDLE, direction, x, y);
        // 2 short for right
        sensorArr[4] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_DOWN, direction, x, y);
        sensorArr[5] = new ActualSensor(Constants.RangeType.SHORT, Constants.SensorLocation.RIGHT_UP, direction, x, y);
        String s;
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        if (debug == JOptionPane.YES_OPTION) {
            SocketConnection.setDebugTrue();
            System.out.println("Debug is " + SocketConnection.getDebug());
        }

        boolean connected = false;
        while (!connected) {
            connected = connectionManager.connectToRPi();
        }
        try {
            //connectionManager.start();
            while (connected) {
                SocketConnection socketConnection = SocketConnection.getInstance();
                System.out.println("CONNECTED TO SOCKET");

                socketConnection.sendMessage(Constants.SENSE_ALL);

                Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
                Pattern sensorPattern2 = Pattern.compile(
                        "\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+[.]\\d+[|]{1}\\d+");
                String[] arr = null;
                ArrayList<Boolean> sensorResult = new ArrayList<>();
                int sensorDistance, numGridsSensor = 0, numGridNotDetected;
                boolean obstacleDetected = false;

                boolean completed = false;

                while (!completed) {
                    s = socketConnection.receiveMessage().trim();
                    System.out.println("sensor receive: " + s);
                    /*
                    if (sensorPattern.matcher(s).matches() || sensorPattern2.matcher(s).matches()) {
                        arr = s.split("\\|");
                        break;
                    }*/
                    arr = s.split(",");
                    for (int i=0; i<arr.length; i++){
                        System.out.println("ARRAY COPY" + arr[i]);
                    }
                    System.arraycopy(arr, 0, sensorValues, 0, 6);
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

                        // find number of grids that it can detect
                        double numGridInDeci = value / 10;
                        int numGridDetected = (int) Math.floor(numGridInDeci); // TODO: check this

                        System.out.println("num grid detected" + numGridDetected);
                        System.out.println("num of grids suppose to be" + numGridsSensor);
                        if (numGridDetected==0){
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
                        sensorArr[i].updateSensor(sensorResult);
                    }

                    System.out.println("SENSOR VALUES IN SENSOR");
                    for (int i=0; i<6; i++){
                        System.out.println("________________NEXT");
                        for (Boolean sresult: sensorArr[i].getSensorInformation()){
                            System.out.println(sresult);
                        }
                    }


                    completed = true;
                    break;

                }
                System.out.println("HELLO ");


                /*
                s = socketConnection.receiveMessage().trim();
                System.out.println("message receive" + s);
                socketConnection.sendMessage(s);*/
            }
        } catch (Exception e) {
            connectionManager.stopCM();
            System.out.println("ConnectionManager is stopped");
        }



        //send msg

        /*for check list
        while(true){
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            socketConnection.sendMessage(msg);
        }*/


    }




}
