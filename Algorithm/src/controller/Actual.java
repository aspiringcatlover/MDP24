package controller;
import robot.Robot;
import exploration.ActualExploration;
import map.ActualMap;


public class Actual{

	private Robot robot;
	private ActualMap map;
	
	//constructor
	public Actual(Robot robot, ActualMap map) {
		this.robot = robot;
		this.map = map;
	}

	/*
	//Starts actual
	public void startActual() {
		
		ActualExploration exploration = new ActualExploration(robot, map);
		exploration.explore();
	}*/

}
