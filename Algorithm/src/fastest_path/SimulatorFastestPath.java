package fastest_path;

import main.Constants;
import map.GridCell;
import map.MapPanel;
import map.SimulatorMap;
import robot.SimulatorRobot;
import map.ActualMap;
import robot.ActualRobot;

import java.util.ArrayList;

import static main.Constants.Direction.*;

public class SimulatorFastestPath {

	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	private MapPanel map;
	private long startTime;
	private long endTime;
	private PathFinder pathFinder;
	private ArrayList<Constants.Movement> movement = new ArrayList<>();
			
	//constructor
	public SimulatorFastestPath(SimulatorRobot robot, SimulatorMap simulatorMap) {
		this.robot = robot;
		this.simulatorMap = simulatorMap;
		pathFinder = new PathFinder(simulatorMap.getMap());
	}

	public void sendInstructions(){
		ArrayList<GridCell> fastestPath = pathFinder.getShortestPath(1,1,13,18);
		System.out.println("num grid in result: "+fastestPath.size());
		for (GridCell gridCell: fastestPath){
			System.out.println("x:" + gridCell.getHorCoord() + " y: " +gridCell.getVerCoord() +
					" fcost: " + gridCell.getfCost() + " gcost:"+gridCell.getgCost()+" hcost:"+gridCell.gethCost());
		}
		int xParent=0, yParent=0, x=0, y=0;
		GridCell parentGrid;
		Constants.Direction currentDirection;
		for (GridCell gridCell: fastestPath){
			if (gridCell.getParentGrid()!=null){
				parentGrid = gridCell.getParentGrid();
				yParent = parentGrid.getVerCoord();
				xParent = parentGrid.getHorCoord();


				x = gridCell.getHorCoord();
				y = gridCell.getVerCoord();

				currentDirection = pathFinder.getCurrentDirection(parentGrid);
				if (x==xParent){
					if (y>yParent){
						//move up
						movement.add(getRobotMovement(currentDirection, UP));



					}
					else{
						//move down
						movement.add(getRobotMovement(currentDirection, DOWN));
					}
				}
				else{
					if (x>xParent){
						//move right
						movement.add(getRobotMovement(currentDirection, RIGHT));
					}
					else{
						//move left
						movement.add(getRobotMovement(currentDirection, LEFT));
					}

				}
			}




		}

	}

	private Constants.Movement getRobotMovement(Constants.Direction currentDirection, Constants.Direction cardinalDirection){
		return getMovement(currentDirection.bearing-cardinalDirection.bearing);
	}

	public Constants.Movement getMovement(int bearing){
		switch (bearing){
			case 0: robot.turn(UP);
				return Constants.Movement.MOVE_FORWARD;
			case 90: robot.turn(LEFT);
				return Constants.Movement.TURN_LEFT;
			case 270: robot.turn(RIGHT);
				return Constants.Movement.TURN_RIGHT;
			default: return null;
		}
	}
}
