package actual;
import robot.Robot;
import controller.Exploration;

import static main.Constants.*;

import main.Constants.Direction;
import map.Map;

public class ActualExploration extends Exploration{
			
	//constructor
	public ActualExploration(Robot robot, Map map) {
		super(robot,map);
	}
	
	//start exploring maze
	public void explore() {
		while(super.map.getActualCoveragePerc() < map.getGoalCoveragePerc()) {
			realign();
			//sense map and update map descriptor
			senseMap();
			super.rightWallHugging();
		}
	}
	
	//sense map using sensors and update map descriptor where there is obstacles/free explored space
	public void senseMap() {
		
	}
	
	//realign robot
	public void realign() {
		//if robot at corner made by 2 obstacles or walls
		if (robot.hasObstacle(robot.getDirection()) && 
				(robot.hasObstacle(robot.robotLeftDir())|robot.hasObstacle(robot.robotRightDir()))){
			parallelRealign();
		}
		//if distance from wall/obstacle to robot is off
		closenessRealign();
	}
	
	//realignment of robot position to make sure it is parallel with wall
	public void parallelRealign() {
		
	}
		
	//realignment of robot position to make sure distance to robot from wall is proper
	public void closenessRealign() {
		
	}


}
