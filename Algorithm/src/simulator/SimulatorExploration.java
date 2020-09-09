package simulator;

import map.Map;
import static main.Constants.*;

import java.util.ArrayList;

import controller.Exploration;
import main.Constants.Direction;
import robot.Robot;
import map.GridCell;

public class SimulatorExploration extends Exploration {

	private long startTime;
	private long endTime;
	private ArrayList<Movement> movement = new ArrayList<Movement>();
	
	//constructor
	public SimulatorExploration(Robot robot, Map map, int time_limit_ms) {
		super(robot,map);
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
	}
	
	//start exploring maze
	public void explore() {
		while(map.getActualCoveragePerc() < map.getGoalCoveragePerc() && System.currentTimeMillis() != endTime) {
			//sense map and update map descriptor
			//true to mean in simulation
			senseMap(true);
			rightWallHugging();
			if (stuckInLoop()) {
				escapeLoop();
			}
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
	
	//check if have obstacle in front of it with a difference of GRID_APART
	public boolean hasObstacle(Direction dir) {
		int x_coord;
		int y_coord;
		switch(dir) {
			case UP:
				x_coord = robot.getXCoord();
				y_coord = robot.getYCoord()+1+GRID_APART;
			case DOWN:
				x_coord = robot.getXCoord();
				y_coord = robot.getYCoord()-1-GRID_APART;
			case LEFT:
				x_coord = robot.getXCoord()-1-GRID_APART;
				y_coord = robot.getYCoord();
			case RIGHT:
				x_coord = robot.getXCoord()+1+GRID_APART;
				y_coord = robot.getYCoord();
			default:
				x_coord = robot.getXCoord();
				y_coord = robot.getYCoord();
		}
		if (map.getGridCell(x_coord, y_coord).getObstacle() == true) {
			return true;
		}
		return false;
 	}
	
	
}
