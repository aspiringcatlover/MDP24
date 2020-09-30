package sensor;


import main.Constants;

import java.util.ArrayList;

public class ActualSensor extends Sensor{


    public ActualSensor(Constants.RangeType type, Constants.SensorLocation location, Constants.Direction direction, int x, int y) {

        super(type, location, direction, x, y);
    }

    @Override
    public void updateSensor(ArrayList<Boolean> isObstacle) {
        this.obstaclePresent = isObstacle;
    }
}
