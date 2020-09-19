package exploration;

import java.util.ArrayList;

import static main.Constants.*;

import map.GridCell;
import map.MapPanel;
import map.SimulatorMap;
import robot.SimulatorRobot;
import sensor.SimulatorSensor;

public class SimulatorExploration {
	
	/*
	 * for reference
	 * displayDirection(robot.getXCoord(), robot.getYCoord(), robot.getDirection());  
	 * 
	 * public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
		simulatorMap.getMap().displayDirection(ver_coord, hor_coord, dir);
		
	 * simulatorMap.getMap().displayRobotSpace(robot.getXCoord(), robot.getYCoord()); 
	 * simulatorMap.getMap().colorMap(gridCell);
	 */

	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	private ArrayList<Movement> movement = new ArrayList<Movement>();
	private float goal_percentage;
	private float actual_percentage;

	// constructor
	public SimulatorExploration(SimulatorRobot robot, SimulatorMap simulatorMap) {
		this.robot = robot;
		this.simulatorMap = simulatorMap;
		goal_percentage = 100;
		actual_percentage = 0;

	}

	// start exploring maze
	public void explore() {
		// sense map and update map descriptor
		while (actual_percentage < goal_percentage) {
			System.out.println(actual_percentage);
			MapPanel map = simulatorMap.getMap();
			System.out.println("robot_x:" + robot.getXCoord() + " robot_y:" + robot.getYCoord());
			System.out.println(robot.getDirection());
			senseMap();
			for (int row = 0; row < HEIGHT; row++) {
				for (int col = 0; col < WIDTH; col++) {
					printGridCell(map.getGridCell(row, col));
				}
				System.out.println();
			}
			rightWallHugging();
//			try {
//				// ms timeout, wait every 1s
//				int timeout = 1000;
//				Thread.sleep(timeout); // Customize your refresh time
//			} catch (InterruptedException e) {
//			}
			actual_percentage = getActualPerc();
		}
	}

	public void rightWallHugging() {
		System.out.println("has obstacle: " + hasObstacle(robot.getDirection()));
		// if no obstacle on the right, turn right and move forward
		if (!hasObstacle(robot.robotRightDir())) {
			System.out.println("turn right");
			robot.turn(robot.robotRightDir());
			movement.add(Movement.TURN_RIGHT);
			System.out.println("move forward");
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
		}

		// if can move forward, move forward
		else if (!hasObstacle(robot.getDirection())) {
			System.out.println("move forward");
			robot.moveForward();
			movement.add(Movement.MOVE_FORWARD);
		}
		// else, turn left
		else {
			robot.turn(robot.robotLeftDir());
			System.out.println("turn left");
			movement.add(Movement.TURN_LEFT);
		}
	}

	// check if have obstacle in front of it
	public boolean hasObstacle(Direction dir) {
		int x_coord;
		int y_coord;
		GridCell gridCell;
		switch (dir) {
		case UP:
			// left of front coord
			x_coord = robot.getXCoord() - 1;
			y_coord = robot.getYCoord() - 2;
			for (int i = 0; i < 3; i++) {
				gridCell = simulatorMap.getMap().getGridCell(y_coord, x_coord + i);
				if (gridCell == null || gridCell.getObstacle()) {
					return true;
				}
			}
			break;
		case DOWN:
			// right of front coord
			x_coord = robot.getXCoord() - 1;
			y_coord = robot.getYCoord() + 2;
			for (int i = 0; i < 3; i++) {
//				System.out.println(x_coord + i);
//				System.out.println(y_coord);
//				System.out.println(simulatorMap.getMap().getGridCell(y_coord, x_coord + i).getObstacle());
				gridCell = simulatorMap.getMap().getGridCell(y_coord, x_coord + i);
				if (gridCell == null || gridCell.getObstacle()) {
					return true;
				}
			}
			break;
		case LEFT:
			// right of front coord
			x_coord = robot.getXCoord() - 2;
			y_coord = robot.getYCoord() - 1;
			for (int i = 0; i < 3; i++) {
				gridCell = simulatorMap.getMap().getGridCell(y_coord + i, x_coord);
				if (gridCell == null || gridCell.getObstacle()) {
					return true;
				}
			}
			break;
		case RIGHT:
			// left of front coord
			x_coord = robot.getXCoord() + 2;
			y_coord = robot.getYCoord() - 1;
			for (int i = 0; i < 3; i++) {
				gridCell = simulatorMap.getMap().getGridCell(y_coord + i, x_coord);
				if (gridCell == null || gridCell.getObstacle()) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	// sense map using sensors and update map descriptor where there is
	// obstacles/free explored space
	public void senseMap() {
		Direction direction = robot.getDirection();
		SimulatorSensor simSensor;
		GridCell gridCell;
		switch (direction) {
		case UP:
			// LEFT_TOP(3), LEFT_MIDDLE(4)
			for (int loc = 3; loc < 5; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() - i);
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
			for (int loc = 0; loc < 3; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() - i, simSensor.getXCoord());
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// RIGHT_TOP(5)
			simSensor = robot.getIndividualSensor(5);
			for (int i = 0; i < simSensor.getGridDistance(); i++) {
				gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() + i);
				if (gridCell == null || gridCell.getObstacle())
					break;
				gridCell.setExplored(true);
			}
			break;
		case RIGHT:
			// LEFT_TOP(3), LEFT_MIDDLE(4)
			for (int loc = 3; loc < 5; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() - i, simSensor.getXCoord());
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
			for (int loc = 0; loc < 3; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() + i);
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// RIGHT_TOP(5)
			simSensor = robot.getIndividualSensor(5);
			for (int i = 0; i < simSensor.getGridDistance(); i++) {
				gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() + i, simSensor.getXCoord());
				if (gridCell == null || gridCell.getObstacle())
					break;
				gridCell.setExplored(true);
			}
			break;
		case DOWN:
			// LEFT_TOP(3), LEFT_MIDDLE(4)
			for (int loc = 3; loc < 5; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() + i);
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
			for (int loc = 0; loc < 3; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() + i, simSensor.getXCoord());
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// RIGHT_TOP(5)
			simSensor = robot.getIndividualSensor(5);
			for (int i = 0; i < simSensor.getGridDistance(); i++) {
				gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() - i);
				if (gridCell == null || gridCell.getObstacle())
					break;
				gridCell.setExplored(true);
			}
			break;
		case LEFT:
			// LEFT_TOP(3), LEFT_MIDDLE(4)
			for (int loc = 3; loc < 5; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() + i, simSensor.getXCoord());
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
			for (int loc = 0; loc < 3; loc++) {
				simSensor = robot.getIndividualSensor(loc);
				for (int i = 0; i < simSensor.getGridDistance(); i++) {
					gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord(), simSensor.getXCoord() - i);
					if (gridCell == null || gridCell.getObstacle())
						break;
					gridCell.setExplored(true);
				}
			}
			// RIGHT_TOP(5)
			simSensor = robot.getIndividualSensor(5);
			for (int i = 0; i < simSensor.getGridDistance(); i++) {
				gridCell = simulatorMap.getMap().getGridCell(simSensor.getYCoord() - i, simSensor.getXCoord());
				if (gridCell == null || gridCell.getObstacle())
					break;
				gridCell.setExplored(true);
			}
			break;
		}

	}

	public void printGridCell(GridCell gridCell) {
		// O for obstacle
		// E for explored
		// U for unexplored
		if (gridCell.getObstacle()) {
			System.out.print("O");
		} else if (gridCell.getExplored()) {
			System.out.print("E");
		} else {
			System.out.print("U");
		}
	}

	public float getActualPerc() {
		int totalGridCells = HEIGHT * WIDTH;
		int gridCellsCovered = 0;
		GridCell gridCell;
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				gridCell = simulatorMap.getMap().getGridCell(row, col);
				if (gridCell.getExplored() || gridCell.getObstacle()) {
					gridCellsCovered += 1;
				}
			}
		}
		// System.out.println((float) gridCellsCovered / totalGridCells * 100);
		return (((float) gridCellsCovered / totalGridCells) * 100);
	}

}
