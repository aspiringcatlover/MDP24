package sensor;

import map.MapPanel;

import java.util.ArrayList;
import java.util.Map;

import static main.Constants.*;


public class SimulatorSensor extends Sensor {

	private MapPanel map;

	// constructor
	public SimulatorSensor(RangeType type, SensorLocation location, MapPanel map, Direction direction, int x,int y) {
		super(type, location, direction, x, y);
		this.map = map;
		if (map!=null)
			setSensorInformation();
		// setSensorInformationAllFalse();
	}

	@Override
	public void updateSensor(ArrayList<Boolean> isObstacle) {
		/*
		 * switch (direction){ case NORTH: switch (location){ case LEFT_TOP: } }
		 */
	}

	public void setSensorInformation() {
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		boolean sensorResultGrid;
		boolean sensorBlock;
		switch (direction) {
		case WEST:
			switch (location) {
			case LEFT_UP:
				sensorBlock = false;
				for (int i = 0; i < 5; i++) {
					sensorResultGrid = getObstacleForSensor(y - i, x);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y-i,x));
				}
				break;
			case RIGHT_DOWN:
			case RIGHT_UP:
					sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y + i, x);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);

					// sensorResult.add(getObstacleForSensor(y-i,x));
				}
				break;
			case UP_LEFT:
			case UP_MIDDLE:
			case UP_RIGHT:
				sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y, x - i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x-i));
				}
				break;
				// sensorResult.add(getObstacleForSensor(y+i,x));
			}
		case SOUTH:
			switch (location) {
			case LEFT_UP:
				sensorBlock = false;
				for (int i = 0; i < 5; i++) {
					sensorResultGrid = getObstacleForSensor(y, x + i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x+i));
				}
				break;
			case RIGHT_DOWN:
				case RIGHT_UP:
					sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y, x - i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x+i));
				}
				break;
			case UP_LEFT:
			case UP_RIGHT:
			case UP_MIDDLE:
				sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y - i, x);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y-i, x));
				}
				break;
				// sensorResult.add(getObstacleForSensor(y,x-i));
			}
		case EAST:
			switch (location) {
			case LEFT_UP:
				sensorBlock = false;
				for (int i = 0; i < 5; i++) {
					sensorResultGrid = getObstacleForSensor(y + i, x);
					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);

					// if obstacle present, means sensor block

					// sensorResult.add(getObstacleForSensor(y+i, x));
				}
				sensorResult.add(true);
				/*
				 * System.out.println("result length:" + sensorResult.size()); for (Boolean
				 * b:sensorResult){ System.out.println("sensor result: "+b); }
				 */
				break;
			case RIGHT_DOWN:
				case RIGHT_UP:
					sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y - i, x);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y+i, x));
				}
				break;
			case UP_MIDDLE:
			case UP_RIGHT:
			case UP_LEFT:
				sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y, x + i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x+i));
				}
				break;
				// sensorResult.add(getObstacleForSensor(y-i,x));
			}
		case NORTH:
			switch (location) {
			case LEFT_UP:
				sensorBlock = false;
				for (int i = 0; i < 5; i++) {
					sensorResultGrid = getObstacleForSensor(y, x - i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x-i));
				}
				break;
			case RIGHT_DOWN:
				case RIGHT_UP:
					sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y, x + i);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y, x-i));
				}
				break;
			case UP_LEFT:
			case UP_MIDDLE:
			case UP_RIGHT:
				sensorBlock = false;
				for (int i = 0; i < 2; i++) {
					sensorResultGrid = getObstacleForSensor(y + i, x);

					if (sensorBlock) {
						sensorResult.add(null);
						continue;
					}

					if (sensorResultGrid) {
						sensorBlock = true;
					}
					sensorResult.add(sensorResultGrid);
					// sensorResult.add(getObstacleForSensor(y+i, x));
				}
				break;
				// sensorResult.add(getObstacleForSensor(y,x+i));
			}
		}
		this.obstaclePresent = sensorResult;
	}

	public void setSensorInformationAllFalse() {
		ArrayList<Boolean> sensorResult = new ArrayList<>();
		for (int i = 0; i < gridDistance; i++) {
			sensorResult.add(false);
		}
		this.obstaclePresent = sensorResult;
	}

	public void updateRobotDirection(Direction direction) {
		this.direction = direction;
	}

	public Boolean getObstacleForSensor(int y, int x) {
		if (x < 0 || x > 14 || y < 0 || y > 19)
			return true;
		return map.getGridCell(y, x).getObstacle();
	}

	/*
	 * public ArrayList<Boolean> senseObstacleFromMap(Direction direction){
	 * ArrayList<Boolean> sensorResult = new ArrayList<>(); switch (location){ case
	 * LEFT_TOP: for (int i=0; i<5;i++){ sensorResult.add(map.getGridCell(y,
	 * x).getObstacle()); } break; case LEFT_MIDDLE: for (int) } }
	 */

}
