package controller;
import robot.Robot;
import actual.ActualExploration;
import map.Map;

public class Actual{

	private Robot robot;
	private Map map;
	
	//constructor
	public Actual(Robot robot, Map map) {
		this.robot = robot;
		this.map = map;
	}
	
	//Starts actual
	public void startActual() {
		
		ActualExploration exploration = new ActualExploration(robot, map);
		exploration.explore();
	}

}
