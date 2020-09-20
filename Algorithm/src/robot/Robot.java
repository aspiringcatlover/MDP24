package robot;

import main.Constants.*;
import sensor.Sensor;
import sensor.SimulatorSensor;

public abstract class Robot {
    Direction direction; //robot current direction
    int x;
    int y;
    Sensor[] sensorArr= new Sensor[6];


    public Robot(Direction direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public abstract void moveForward();
    public abstract void turn(Direction dir);

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

    public Sensor[] getSensorArr() {
        return sensorArr;
    }

    public void setSensorArr(Sensor[] sensorArr) {
        this.sensorArr = sensorArr;
    }

    public abstract Direction robotRightDir();
    public abstract Direction robotLeftDir();

    public abstract SimulatorSensor getIndividualSensor(int loc);
}
