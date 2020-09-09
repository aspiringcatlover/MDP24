package controller;
import map.ActualMap;
import robot.Robot;

public class FastestPath {

	private Robot robot;
	private ActualMap map;
	private long startTime;
	private long endTime;
			
	//constructor
	public FastestPath(Robot robot, Map map, int time_limit_ms) {
	private ActualMap map;
			
	//constructor
	public FastestPath(Robot robot, ActualMap map) {
		this.robot = robot;
		this.map = map;
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
	}
}
