package controller;
import robot.Robot;
import map.Map;

public class Simulator{
	
	private int time_limit_ms; 
	private Robot robot;
	private Map map;
	private long startTime;
	private long endTime;
	
	//constructor
	public Simulator(Robot robot, int time_limit_ms, Map map) {
		this.robot = robot;
		this.time_limit_ms = time_limit_ms;
		this.map = map;
	}
	
	//Starts exploration simulation
	public void startExploration() {
		
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
		while (System.currentTimeMillis() != endTime) {
			Exploration exploration = new Exploration(robot, map);
			exploration.explore();
		}
	}
	
	//Starts fastest path simulation
	public void startFastestPath() {
		
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
		while (System.currentTimeMillis() != endTime) {
			FastestPath fastestPath = new FastestPath(robot, map);
		}
	}
}
