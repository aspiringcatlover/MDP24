package sensor;
import map.MapPanel;

import java.util.ArrayList;
import java.util.Map;

import static main.Constants.*;

public class SimulatorSensor extends Sensor{

	private MapPanel map;
	private Direction direction;

	// constructor
	public SimulatorSensor(RangeType type, SensorLocation location) {
		super(type, location);
	}

	@Override
	public void updateSensor(ArrayList<Boolean> isObstacle) {
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		for (int i=0; i<gridDistance;i++){
			sensorResult.add(false);
		}
		/*
		switch (direction){
			case NORTH:
				switch (location){
					case LEFT_TOP:
				}
		}*/
	}

	public void setSensorInformation(){
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		for (int i=0; i<gridDistance;i++){
			sensorResult.add(false);
		}
		this.obstaclePresent = sensorResult;
	}

	public void updateRobotDirection(Direction direction){
		this.direction= direction;
	}

	/*
	public ArrayList<Boolean> senseObstacleFromMap(Direction direction){
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		switch (location){
			case LEFT_TOP:
				for (int i=0; i<5;i++){
					sensorResult.add(map.getGridCell(y, x).getObstacle());
				}
				break;
			case LEFT_MIDDLE:
				for (int)
		}
	}*/
	

}
