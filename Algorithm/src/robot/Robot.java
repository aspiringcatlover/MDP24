package robot;

import main.Constants;
import main.Constants.*;
import map.MapPanel;
import sensor.Sensor;
import sensor.SimulatorSensor;

import java.util.ArrayList;

import static main.Constants.Direction.*;

public abstract class Robot {
    Direction direction; //robot current direction
    int x;
    int y;
    Sensor[] sensorArr= new Sensor[6];
    MapPanel map;
    String[] mdfString;


    public Robot(Direction direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public Robot() {

    }



    // Initialise the robot direction, position and the file to debug if applicable
    public void initialise(int x, int y, int direction) {
        /*
        directions: north = 0 , east =1, south = 2, west = 3
         */

        this.x = checkValidX(x);
        this.y = checkValidY(y);

        switch (direction){
            case 0: this.direction=NORTH;
                    break;
            case 1: this.direction=EAST;
                break;
            case 2: this.direction=SOUTH;
                break;
            case 3: this.direction = WEST;
                break;
        }
        System.out.println("ROBOT DIRECTION ... INIT " + this.direction);

       // this.validObstacleValue = false;
        /*
        if (ConnectionSocket.getDebug()) {
            try {
                this.writer = new OutputStreamWriter( new FileOutputStream("Output.txt"), "UTF-8");
                writer.write("");
            }
            catch (Exception e) {
                System.out.println("Unable to write into output");
            }
        }*/
    }

    protected int checkValidX(int x) {
        if (x >= Constants.WIDTH - 1) {
            x = Constants.WIDTH - 2;
        }
        if (x <= 0) {
            x = 1;
        }

        return x;
    }

    protected int checkValidY(int y) {
        if (y >= Constants.HEIGHT - 1) {
            y = Constants.HEIGHT - 2;
        }

        if (y <= 0) {
            y = 1;
        }
        return y;
    }


    public abstract void moveForward();
    public abstract void moveForward(int steps);
    public abstract void turn(Direction dir);
    public abstract void calibrate();
    public abstract Direction robotRightDir();
    public abstract Direction robotLeftDir();
    public abstract Sensor getIndividualSensor(int loc);
    public abstract void initSensor();
    public abstract Direction peekRobotRightDir();
    public abstract Direction peekRobotLeftDir();
    public abstract void calibrateFront();
    public abstract void uTurn();
    public abstract void takePhoto(ArrayList<int[]> coordinates);


    //getter and setter


    public String[] getMdfString() {
        //ask map to calculate
        this.mdfString = map.getMdfString();
        return mdfString;
    }

    public void setMdfString(String[] mdfString) {
        this.mdfString = mdfString;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getXCoord() {
        return x;
    }

    public void setXCoord(int x) {
        this.x = x;
    }

    public int getYCoord() {
        return y;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    public MapPanel getMap() {
        return map;
    }

    public void setMap(MapPanel map) {
        this.map = map;
    }

    public Sensor[] getSensorArr() {
        return sensorArr;
    }

    public void setSensorArr(Sensor[] sensorArr) {
        this.sensorArr = sensorArr;
    }

    public void setWaypoint(int x, int y){
        map.setWayPoint(x,y);
    }


}
