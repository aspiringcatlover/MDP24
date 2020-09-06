package controller;
import map.Map;
import robot.Robot;

public class FastestPath {

	private Robot robot;
	private Map map;
	private long startTime;
	private long endTime;
			
	//constructor
	public FastestPath(Robot robot, Map map, int time_limit_ms) {
		this.robot = robot;
		this.map = map;
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
	}
}
