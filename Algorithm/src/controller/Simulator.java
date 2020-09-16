package controller;
import robot.Robot;
import robot.SimulatorRobot;
import map.*;
import map.SimulatorMap;
import exploration.SimulatorExploration;
import fastest_path.SimulatorFastestPath;
import map.ActualMap;

public class Simulator{
	
	private int time_limit_ms; 
	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	private MapPanel map;
	
	//constructor
	public Simulator(SimulatorRobot robot, SimulatorMap simulatorMap, int time_limit_ms, MapPanel map) {
		this.robot = robot;
		this.time_limit_ms = time_limit_ms;
		this.map = map;
		this.simulatorMap = simulatorMap;
	}

	/*
	//Starts exploration simulation
	public void startExploration() {
		SimulatorExploration exploration = new SimulatorExploration(robot, simulatorMap, time_limit_ms, map);
		exploration.explore();
	}
	
	//Starts fastest path simulation
	public void startFastestPath() {
		SimulatorFastestPath fastestPath = new SimulatorFastestPath(robot, simulatorMap, map);
	}*/
	
}
