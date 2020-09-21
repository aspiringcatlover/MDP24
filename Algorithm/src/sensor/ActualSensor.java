package sensor;


import main.Constants;

import java.util.ArrayList;

public class ActualSensor extends Sensor{


    public ActualSensor(Constants.RangeType type, Constants.SensorLocation location) {
        super(type, location);
    }

    @Override
    public void updateSensor(ArrayList<Boolean> isObstacle) {

    }
}
