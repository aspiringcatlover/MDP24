package controller;
import robot.Robot;

import static main.Constants.*;

import main.Constants.Direction;
import map.Map;

public abstract class Exploration{
	
	protected Robot robot;
	protected Map map;
			
	//constructor
	public Exploration(Robot robot, Map map) {
		this.robot = robot;
		this.map = map;
	}
	
	//start exploring maze
	public abstract void explore();
	
	//sense map using sensors and update map descriptor where there is obstacles/free explored space
	public abstract void senseMap();
	
	//right wall hugging - make sure wall always on right of robot
	public void rightWallHugging() {
		//if no obstacle on the right, turn right and move forward
		if (robot.hasObstacle(robot.robotRightDir()) == false) {
			robot.turn(robot.robotRightDir());
			robot.moveForward();
		}
		//if can move forward, move forward
		else if (robot.hasObstacle(robot.getDirection()) == false) {
			robot.moveForward();
		}
		//else, turn left
		else {
			robot.turn(robot.robotLeftDir());
		}
	}

}
