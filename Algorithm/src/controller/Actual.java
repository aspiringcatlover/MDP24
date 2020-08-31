package controller;
import robot.Robot;
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
		
		Exploration exploration = new Exploration(robot, map);
		
	}

}
