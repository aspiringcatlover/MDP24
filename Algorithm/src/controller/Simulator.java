package controller;
import robot.Robot;
<<<<<<< Updated upstream
import simulator.SimulatorExploration;
import map.Map;
=======
import map.ActualMap;
>>>>>>> Stashed changes

public class Simulator{
	
	private int time_limit_ms; 
	private Robot robot;
<<<<<<< Updated upstream
	private Map map;
=======
	private ActualMap map;
	private long startTime;
	private long endTime;
>>>>>>> Stashed changes
	
	//constructor
	public Simulator(Robot robot, int time_limit_ms, ActualMap map) {
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
