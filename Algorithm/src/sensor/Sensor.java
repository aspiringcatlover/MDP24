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
    Direction direction; // the direction the sensor is facing

    public Sensor(RangeType type, SensorLocation location) {
        this.type = type;
        this.location = location;
        this.direction = Direction.EAST;
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
                x = 2;
                y = 3;
                break;
            case LEFT_MIDDLE:
                x = 1;
                y = 3;
                break;
            case UP_LEFT:
                x = 3;
                y = 2;
                break;
            case UP_MIDDLE:
                x = 3;
                y = 1;
                break;
            case UP_RIGHT:
                x = 3;
                y = 0;
                break;
            case RIGHT_TOP:
                x = 2;
                y = -1;
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @param direction direction the robot is moving to
     */
    public void robotMove(Direction direction){
        //update direction of the sensor

        //set sensor information (for simulator)
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
