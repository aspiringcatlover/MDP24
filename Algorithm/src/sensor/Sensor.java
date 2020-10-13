package sensor;

import main.Constants.*;

import java.util.ArrayList;

import static main.Constants.Direction.*;
import static main.Constants.Direction.NORTH;
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

    public Sensor(RangeType type, SensorLocation location, Direction direction, int x, int y) {
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

        switch (direction){
            case WEST:
                switch (location){
                    case LEFT_MIDDLE:
                        this.x = x;
                        this.y = y - 2;
                        break;
                    case RIGHT_DOWN:
                        this.x = x+1;
                        this.y = y+2;
                        break;
                    case UP_LEFT:
                        this.x = x-2;
                        this.y = y-1;
                        break;
                    case UP_MIDDLE:
                        this.x = x - 2;
                        this.y = y;
                        break;
                    case UP_RIGHT:
                        this.x = x-2;
                        this.y = y+1;
                        break;
                    case RIGHT_UP:
                        this.x = x-1;
                        this.y = y+2;
                        break;
                }
                break;
            case EAST:
                switch (location){
                    case LEFT_MIDDLE:
                        this.x = x;
                        this.y = y+2;
                        break;
                    case RIGHT_DOWN:
                        this.x = x-1;
                        this.y = x-2;
                        break;
                    case UP_LEFT:
                        this.x = x + 2;
                        this.y = y+1;
                        break;
                    case UP_MIDDLE:
                        this.x = x+2;
                        this.y =y;
                        break;
                    case UP_RIGHT:
                        this.x = x+2;
                        this.y = y-1;
                        break;
                    case RIGHT_UP:
                        this.x = x+1;
                        this.y = y-2;
                        break;
                }
                break;
            case SOUTH:
                switch (location){
                    case LEFT_MIDDLE:
                        this.x = x+2;
                        this.y = y;
                        break;
                    case RIGHT_DOWN:
                        this.x = x-2;
                        this.y = x+1;
                        break;
                    case UP_LEFT:
                        this.x = x + 1;
                        this.y = y-2;
                        break;
                    case UP_MIDDLE:
                        this.x = x;
                        this.y =y-2;
                        break;
                    case UP_RIGHT:
                        this.x = x-1;
                        this.y = y-2;
                        break;
                    case RIGHT_UP:
                        this.x = x-2;
                        this.y = y-1;
                        break;
                }
                break;
            case NORTH:
                switch (location){
                    case LEFT_MIDDLE:
                        this.x = x-2;
                        this.y = y;
                        break;
                    case RIGHT_DOWN:
                        this.x = x+2;
                        this.y = x-1;
                        break;
                    case UP_LEFT:
                        this.x = x - 1;
                        this.y = y+2;
                        break;
                    case UP_MIDDLE:
                        this.x = x;
                        this.y =y+2;
                        break;
                    case UP_RIGHT:
                        this.x = x+1;
                        this.y = y+2;
                        break;
                    case RIGHT_UP:
                        this.x = x+2;
                        this.y = y+1;
                        break;
                }
                break;
        }
        this.direction = direction;
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
