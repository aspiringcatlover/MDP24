package fastest_path;

import map.ActualMap;
import robot.ActualRobot;

public class ActualFastestPath {

	private ActualRobot robot;
	private ActualMap map;
	private long startTime;
	private long endTime;
	
	//constructor
	public FastestPath(ActualRobot robot, ActualMap map) {
		this.robot = robot;
		this.map = map;
	}
}
