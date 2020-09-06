package controller;
import robot.Robot;
import simulator.SimulatorExploration;
import map.Map;

public class Simulator{
	
	private int time_limit_ms; 
	private Robot robot;
	private Map map;
	
	//constructor
	public Simulator(Robot robot, int time_limit_ms, Map map) {
		this.robot = robot;
		this.time_limit_ms = time_limit_ms;
		this.map = map;
	}
	
	//Starts exploration simulation
	public void startExploration() {
		SimulatorExploration exploration = new SimulatorExploration(robot, map, time_limit_ms);
		exploration.explore();
	}
	
	//Starts fastest path simulation
	public void startFastestPath() {
		FastestPath fastestPath = new FastestPath(robot, map, time_limit_ms);
	}
}
