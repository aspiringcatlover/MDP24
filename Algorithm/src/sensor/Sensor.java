package sensor;

import main.Constants.*;

import java.util.ArrayList;

import static main.Constants.GRID_LONG_RANGE_DISTANCE;
import static main.Constants.GRID_SHORT_RANGE_DISTANCE;

public abstract class Sensor {
    RangeType type;
    SensorLocation location;
    int gridDistance;
    int x;
    int y;
    ArrayList<Boolean> obstaclePresent;

    public Sensor(RangeType type, SensorLocation location) {
        this.type = type;
        this.location = location;
        switch (type) {
            case LONG:
                gridDistance = GRID_LONG_RANGE_DISTANCE;
                break;
            case SHORT:
                gridDistance = GRID_SHORT_RANGE_DISTANCE;
                break;
            default:
                // System.out.println("Cannot detect sensor");
        }

        //assuming forward direction of robot wrt map is DOWN
        switch(location) {
            case LEFT_TOP:
                x = -1;
                y = 2;
                break;
            case LEFT_MIDDLE:
                x = -1;
                y = 1;
                break;
            case UP_LEFT:
                x = 0;
                y = 3;
                break;
            case UP_MIDDLE:
                x = 1;
                y = 3;
                break;
            case UP_RIGHT:
                x = 2;
                y = 3;
                break;
            case RIGHT_TOP:
                x = 3;
                y = 2;
                break;
        }
    }

    public ArrayList<Boolean> getSensorInformation(){
        return obstaclePresent;
    }

    public abstract void updateSensor(ArrayList<Boolean> isObstacle);

    public void setXCoord(int x_coord) {
        this.x = x_coord;
    }

    public int getXCoord() {
        return x;
    }

    public void setYCoord(int y_coord) {
        this.y = y_coord;
    }

    public int getYCoord() {
        return y;
    }

    public void setType(RangeType type) {
        this.type = type;
    }

    public RangeType getType() {
        return type;
    }

    public void setLocation(SensorLocation location) {
        this.location = location;
    }

    public SensorLocation getLocation() {
        return location;
    }

    public int getGridDistance() {
        return gridDistance;
    }
}
