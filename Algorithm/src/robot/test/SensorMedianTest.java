package robot.test;

import main.Constants;

import java.util.ArrayList;
import java.util.Collections;

import static main.Constants.SENSOR_VALUES;

public class SensorMedianTest {
    public static void main(String[] args) {
        ArrayList<Boolean> sensorResult = new ArrayList<>();
        int sensorDistance, numGridsSensor = 0, numGridNotDetected;
        boolean obstacleDetected = false;

        double roundUp =Math.ceil(111/4.0);
        System.out.println((int) roundUp*4);

        ArrayList<String[]> sensorValueArrayList = new ArrayList<>();
        String[] mockSensorValus = new String[10];
        mockSensorValus[0] = "1,2,3,4,5,6";
        mockSensorValus[1] = "6,5,4,3,2,1";
        mockSensorValus[2] = "12,4,9,100,2,10";
        mockSensorValus[3] = "11,100,200,400,500,600";
        mockSensorValus[4] = "16,2,32,4,52,6";
        mockSensorValus[5] = "60,5,4,32,22,1";
        mockSensorValus[6] = "180,4,9,100,2,10";
        mockSensorValus[7] = "12,100,200,400,500,600";
        mockSensorValus[8] = "18,27,3,4,56,6";
        mockSensorValus[9] = "100,5,46,3,2,16";

        for (int i = 0; i < 10; i++) {
            sensorValueArrayList.add(mockSensorValus[i].split(","));
        }
        System.out.println(sensorValueArrayList.get(0).length);


        ArrayList<Integer> individualValue1 = new ArrayList<>();
        ArrayList<Integer> individualValue2 = new ArrayList<>();
        ArrayList<Integer> individualValue3 = new ArrayList<>();
        ArrayList<Integer> individualValue4 = new ArrayList<>();
        ArrayList<Integer> individualValue5 = new ArrayList<>();
        ArrayList<Integer> individualValue0 = new ArrayList<>();
        for (String[] indiValue : sensorValueArrayList) {
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

        System.out.println(Integer.toString((individualValue0.get(4) + individualValue0.get(5)) / 2));
        System.out.println(Integer.toString((individualValue1.get(4) + individualValue1.get(5)) / 2));
        System.out.println(Integer.toString((individualValue2.get(4) + individualValue2.get(5)) / 2));
        System.out.println(Integer.toString((individualValue3.get(4) + individualValue3.get(5)) / 2));
        System.out.println(Integer.toString((individualValue4.get(4) + individualValue4.get(5)) / 2));
        System.out.println(Integer.toString((individualValue5.get(4) + individualValue5.get(5)) / 2));


    }
}
