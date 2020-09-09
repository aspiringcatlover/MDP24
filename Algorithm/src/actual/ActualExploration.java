package actual;
import robot.Robot;
import controller.Exploration;

import static main.Constants.*;

import main.Constants.Direction;
import main.Constants.Movement;
import map.GridCell;
import map.Map;

public class ActualExploration extends Exploration{
	
	
			
	//constructor
	public ActualExploration(Robot robot, Map map) {
		super(robot,map);
	}
	
	//start exploring maze
	public void explore() {
		while(map.getActualCoveragePerc() < map.getGoalCoveragePerc()) {
			realign();
			//sense map and update map descriptor
			//false to mean not inSimulation
			senseMap(false);
			rightWallHugging();
		}
	}
	
	public void rightWallHugging() {
		//if no obstacle on the right, turn right and move forward
		if (hasObstacle(robot.robotRightDir()) == false) {
			robot.turn(robot.robotRightDir());
			movement.add(Movement.TURN_RIGHT);
			//displayTurn(Direction robot.getDirection)
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
			//displayMove(int robot.x_coord, int robot.y_coord)
		}
		//if can move forward, move forward
		else if (hasObstacle(robot.getDirection()) == false) {
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
			//displayMove(int robot.x_coord, int robot.y_coord)
		}
		//else, turn left
		else {
			robot.turn(robot.robotLeftDir());
			movement.add(Movement.TURN_LEFT);
			//displayTurn(Direction robot.getDirection)
		}
	}
	
	public boolean hasObstacle(Direction dir) {
		return true;
 	}
	
	//realign robot
	public void realign() {
		//if robot at corner made by 2 obstacles or walls
		if (hasObstacle(robot.getDirection()) && 
				(hasObstacle(robot.robotLeftDir())|hasObstacle(robot.robotRightDir()))){
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
