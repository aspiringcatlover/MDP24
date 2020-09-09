package fastest_path;

import map.MapPanel;
import map.SimulatorMap;
import robot.SimulatorRobot;
import map.ActualMap;
import robot.ActualRobot;

public class SimulatorFastestPath {

	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	private MapPanel map;
	private long startTime;
	private long endTime;
			
	//constructor
	public SimulatorFastestPath(SimulatorRobot robot, SimulatorMap simulatorMap, MapPanel map) {
		this.robot = robot;
		this.simulatorMap = simulatorMap;
		this.map = map;
	}
}
