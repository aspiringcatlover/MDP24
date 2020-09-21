package sensor;
import map.MapPanel;

import java.util.ArrayList;
import java.util.Map;

import static main.Constants.*;
import static main.Constants.SensorLocation.LEFT_MIDDLE;

public class SimulatorSensor extends Sensor{

	private MapPanel map;

	// constructor
	public SimulatorSensor(RangeType type, SensorLocation location, MapPanel map) {
		super(type, location);
		this.map = map;
		this.direction = Direction.NORTH;
		setSensorInformation();
		//setSensorInformationAllFalse();
	}


	@Override
	public void updateSensor(ArrayList<Boolean> isObstacle) {
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
		switch (direction){
			case WEST:
				switch (location){
					case LEFT_TOP:
						for (int i=0; i<5;i++){
							sensorResult.add(getObstacleForSensor(y-i,x));
						}
						break;
					case LEFT_MIDDLE:
						for (int i=0;i<2;i++){
							sensorResult.add(getObstacleForSensor(y-i,x));
						}
						break;
					case UP_LEFT:
					case UP_MIDDLE:
					case UP_RIGHT:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y, x-i));
						}
						break;
					case RIGHT_TOP:
						for (int i=0;i<2;i++){
							sensorResult.add(getObstacleForSensor(y+i,x));
						}
						break;
				}
			case SOUTH:
				switch (location){
					case LEFT_TOP:
						for (int i=0; i<5; i++){
							sensorResult.add(getObstacleForSensor(y, x+i));
						}
						break;
					case LEFT_MIDDLE:
						for (int i=0;i<2;i++){
							sensorResult.add(getObstacleForSensor(y, x+i));
						}
						break;
					case UP_LEFT:
					case UP_RIGHT:
					case UP_MIDDLE:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y-i, x));
						}
						break;
					case RIGHT_TOP:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y,x-i));
						}
						break;
				}
			case EAST:
				switch (location){
					case LEFT_TOP:
						for (int i=0; i<5; i++){
							sensorResult.add(getObstacleForSensor(y+i, x));
						}
						break;
					case LEFT_MIDDLE:
						for (int i=0;i<2;i++){
							sensorResult.add(getObstacleForSensor(y+i, x));
						}
						break;
					case UP_MIDDLE:
					case UP_RIGHT:
					case UP_LEFT:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y, x+i));
						}
						break;
					case RIGHT_TOP:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y-i,x));
						}
						break;
				}
			case NORTH:
				switch (location){
					case LEFT_TOP:
						for (int i=0; i<5; i++){
							sensorResult.add(getObstacleForSensor(y, x-i));
						}
						break;
					case LEFT_MIDDLE:
						for (int i=0;i<2;i++){
							sensorResult.add(getObstacleForSensor(y, x-i));
						}
						break;
					case UP_LEFT:
					case UP_MIDDLE:
					case UP_RIGHT:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y+i, x));
						}
						break;
					case RIGHT_TOP:
						for (int i=0; i<2;i++){
							sensorResult.add(getObstacleForSensor(y,x+i));
						}
						break;
				}
		}
		this.obstaclePresent = sensorResult;
	}

	public void setSensorInformationAllFalse(){
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		for (int i=0; i<gridDistance;i++){
			sensorResult.add(false);
		}
		this.obstaclePresent = sensorResult;
	}

	public void updateRobotDirection(Direction direction){
		this.direction= direction;
	}

	public Boolean getObstacleForSensor(int y, int x){
		if (x<0||x>14||y<0||y>19)
			return true;
		return map.getGridCell(y,x).getObstacle();
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
