package controller;
import robot.Robot;
import map.Map;
import static Main.Constants.*;

public class Exploration{
	
	private Robot robot;
	private Map map;
			
	//constructor
	public Exploration(Robot robot, Map map) {
		this.robot = robot;
		this.map = map;
	}
	
	//start exploring maze
	public void explore() {
		while(map.getActualCoveragePerc() < map.getGoalCoveragePerc()) {
			rightWallHugging();
		}
	}
	
	//right wall hugging - make sure wall always on right of robot
	public void rightWallHugging() {
		
	}


}
