package controller;
import robot.Robot;


import static main.Constants.*;

import java.util.ArrayList;

import main.Constants.Direction;
import main.Constants.Movement;
import map.GridCell;
import map.Map;
import map.ActualMap;
import static Main.Constants.*;


public abstract class Exploration{
	
	protected Robot robot;
	protected Map map;
	protected ArrayList<Movement> movement = new ArrayList<Movement>();
	private Robot robot;
	private ActualMap map;
			
	//constructor
	public Exploration(Robot robot, ActualMap map) {
		this.robot = robot;
		this.map = map;
	}
	
	//start exploring maze
	public abstract void explore();
	
	//right wall hugging - make sure wall always on right of robot
	public abstract void rightWallHugging();
	
	public boolean stuckInLoop() {
		if (movement.size() >= 4) {
			if (movement.get(movement.size() - 1) == Movement.MOVE_FORWARD &&
	            movement.get(movement.size() - 2) == Movement.TURN_RIGHT &&
	            movement.get(movement.size() - 3) == Movement.MOVE_FORWARD &&
	            movement.get(movement.size() - 4) == Movement.TURN_RIGHT)
	                return true;
			if (movement.get(movement.size() - 1) == Movement.MOVE_FORWARD &&
		        movement.get(movement.size() - 2) == Movement.TURN_LEFT &&
		        movement.get(movement.size() - 3) == Movement.MOVE_FORWARD &&
		        movement.get(movement.size() - 4) == Movement.TURN_LEFT)
		            return true;
		}
		return false;
	}
	
	public void escapeLoop() {
		
	}
	
	//sense map using sensors and update map descriptor where there is obstacles/free explored space
	public void senseMap(boolean isSimulation) {
		switch(robot.getDirection()){
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
	
	//color map for long range sensor
	public void colorMapLongRange(Direction dir, boolean isSimulation) {
		int grid;
		if (isSimulation) {
			grid = GRID_LONG_RANGE_DISTANCE;
		}
		else {
			grid = LONG_RANGE_DISTANCE/GRID_LENGTH;
		}
		for (int i=0; i<grid; i ++) {
			GridCell gridCell;
			switch(dir) {
			case RIGHT:
				gridCell = map.getGridCell(robot.getYCoord()+i, robot.getXCoord());
			case UP:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord()-i);
			case LEFT:
				gridCell = map.getGridCell(robot.getYCoord()-i, robot.getXCoord());
			case DOWN:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord()+i);
			default:
				gridCell = map.getGridCell(robot.getYCoord(), robot.getXCoord());
			}
			//if have obstacle, cant see the grid cell behind
			if (gridCell.getObstacle()) {
				break;
			}
			else {
				gridCell.setExplored(true);
				//then colorMap(GridCell gridCell)
				//assigns a color depending on whether gridCell is obstacle and explored/explored
			}
		}
	}
	
	//color map for front short range sensor
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
				//then colorMap(GridCell gridCell)
				//assigns a color depending on whether gridCell is obstacle and explored/explored
			}
		}
	}
	
	
	
}
