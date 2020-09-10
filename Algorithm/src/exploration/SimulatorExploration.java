package exploration;

import map.SimulatorMap;
import static main.Constants.*;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;
import robot.Robot;
import robot.SimulatorRobot;
import map.GridCell;
import map.MapPanel;

public class SimulatorExploration extends Exploration {

	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	private MapPanel map;
	private long startTime;
	private long endTime;
	private ArrayList<Movement> movement = new ArrayList<Movement>();

	// constructor
	public SimulatorExploration(SimulatorRobot robot, SimulatorMap simulatorMap, int time_limit_ms, MapPanel map) {
		this.robot = robot;
		this.simulatorMap = simulatorMap;
		this.map = map;
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
	}

	// start exploring maze
	public void explore() {
		while (simulatorMap.getActualCoveragePerc() < simulatorMap.getGoalCoveragePerc()
				&& System.currentTimeMillis() != endTime) {
			// sense map and update map descriptor
			// true to mean in simulation
			senseMap(true);
			rightWallHugging();
			if (stuckInLoop()) {
				escapeLoop();
			}
		}
	}

	public void rightWallHugging() {
		// if no obstacle on the right, turn right and move forward
		if (hasObstacle(robot.robotRightDir()) == false) {
			robot.turn(robot.robotRightDir());
			movement.add(Movement.TURN_RIGHT);
			// displayTurn(Direction robot.getDirection)
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
			// displayMove(int robot.x_coord, int robot.y_coord)
		}
		// if can move forward, move forward
		else if (hasObstacle(robot.getDirection()) == false) {
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
			// displayMove(int robot.x_coord, int robot.y_coord)
		}
		// else, turn left
		else {
			robot.turn(robot.robotLeftDir());
			movement.add(Movement.TURN_LEFT);
			// displayTurn(Direction robot.getDirection)
		}
	}

	// check if have obstacle in front of it with a difference of GRID_APART
	public boolean hasObstacle(Direction dir) {
		int x_coord;
		int y_coord;
		switch (dir) {
		case UP:
			x_coord = robot.getXCoord();
			y_coord = robot.getYCoord() + 1 + GRID_APART;
		case DOWN:
			x_coord = robot.getXCoord();
			y_coord = robot.getYCoord() - 1 - GRID_APART;
		case LEFT:
			x_coord = robot.getXCoord() - 1 - GRID_APART;
			y_coord = robot.getYCoord();
		case RIGHT:
			x_coord = robot.getXCoord() + 1 + GRID_APART;
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

	// sense map using sensors and update map descriptor where there is
	// obstacles/free explored space
	public void senseMap(boolean isSimulation) {
		switch (robot.getDirection()) {
		case RIGHT:
			colorMapLongRange(Direction.RIGHT, isSimulation);
			colorMapShortRange(Direction.RIGHT, isSimulation);
		case UP:
			colorMapLongRange(Direction.UP, isSimulation);
			colorMapShortRange(Direction.UP, isSimulation);
		case LEFT:
			colorMapLongRange(Direction.LEFT, isSimulation);
			colorMapShortRange(Direction.LEFT, isSimulation);
		case DOWN:
			colorMapLongRange(Direction.DOWN, isSimulation);
			colorMapShortRange(Direction.DOWN, isSimulation);
		}
	}

	// color map for long range sensor
	public void colorMapLongRange(Direction dir, boolean isSimulation) {
		int grid;
		if (isSimulation) {
			grid = GRID_LONG_RANGE_DISTANCE;
		} else {
			grid = LONG_RANGE_DISTANCE / GRID_LENGTH;
		}
		for (int i = 0; i < grid; i++) {
			GridCell gridCell;
			switch (dir) {
			case RIGHT:
				gridCell = map.getGridCell(robot.getYCoord() + i, robot.getXCoord());
			case UP:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord() - i);
			case LEFT:
				gridCell = map.getGridCell(robot.getYCoord() - i, robot.getXCoord());
			case DOWN:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord() + i);
			default:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord());
			}
			// if have obstacle, cant see the grid cell behind
			if (gridCell.getObstacle()) {
				break;
			} else {
				gridCell.setExplored(true);
				// assigns a color depending on whether gridCell is obstacle and
				// explored/explored
//				colorMap(GridCell gridCell);
			}
		}
	}

	// color map for front short range sensor
	public void colorMapShortRange(Direction dir, boolean isSimulation){
			int grid;
			if (isSimulation) {
				grid = GRID_SHORT_RANGE_DISTANCE;
			}
			else {
				grid = SHORT_RANGE_DISTANCE/GRID_LENGTH;
			}
			for (int i=0; i<grid; i ++) {
				GridCell gridCell;
				switch(dir) {
				case RIGHT:
					gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord()+i);
				case UP:
					gridCell = map.getGridCell(robot.getYCoord()+i, robot.getXCoord());
				case LEFT:
					gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord()-i);
				case DOWN:
					gridCell = map.getGridCell(robot.getYCoord()-i, robot.getXCoord());
				default:
					gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord());
				}
				//if have obstacle, cant see the grid cell behind
				if (gridCell.getObstacle()) {
					break;
				}
				else {
					gridCell.setExplored(true);
					//assigns a color depending on whether gridCell is obstacle and explored/explored
					map.colorMap(gridCell);
				}
			}
		}
	
	//display direction of robot
	public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
		map.markArrow(map.getGridCell(ver_coord, hor_coord), Direction dir);
	}
	
	public void displayMove(int ver_coord, int hor_coord, Movement m) {
		// set new coordinates of robot
		switch (m) {
		case MOVE_FORWARD:
			robot.setYCoord(ver_coord - 1);
			break;
		case TURN_LEFT:
			robot.setXCoord(hor_coord + 1);
			break;
		case TURN_RIGHT:
			robot.setXCoord(hor_coord - 1);
			break;
		}
		//set new direction of robot
		switch (m) {
        case MOVE_FORWARD:
        	robot.setDirection(Direction.UP);
            break;
        case TURN_LEFT:
        	robot.setDirection(Direction.LEFT);
            break;
        case TURN_RIGHT:
        	robot.setDirection(Direction.RIGHT);
            break;
		}
		//mark direction on new robot center 
		map.markArrow(map.getGridCell(robot.getYCoord(), robot.getXCoord()), robot.getDirection());
	}

}
