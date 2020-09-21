package fastest_path;

import main.Constants;
import map.GridCell;
import map.SimulatorMap;
import robot.SimulatorRobot;

import java.util.ArrayList;

import static main.Constants.Direction.*;

public class SimulatorFastestPath {

	private SimulatorRobot robot;
	private SimulatorMap simulatorMap;
	//private MapPanel map;
	private long startTime;
	private long endTime;
	private PathFinder pathFinder;
	private ArrayList<Constants.Movement> movement = new ArrayList<>();
	private int steps_per_sec;
			
	//constructor
	public SimulatorFastestPath(SimulatorRobot robot, SimulatorMap simulatorMap) {
		this.robot = robot;
		this.simulatorMap = simulatorMap;
		pathFinder = new PathFinder(simulatorMap.getMap());
		startTime = System.currentTimeMillis();
		endTime = startTime + simulatorMap.getTimeLimitMs();
		steps_per_sec = simulatorMap.getStepsPerSec();
		System.out.println("steps per sec: " + steps_per_sec);
	}

	public void sendInstructions() throws InterruptedException {
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
			if (System.currentTimeMillis() == endTime) //time is up
				break;

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
						movement.add(getRobotMovement(currentDirection, NORTH));
						displayDirection(yParent,xParent, NORTH);
					}
					else{
						//move down
						movement.add(getRobotMovement(currentDirection, SOUTH));
						displayDirection(yParent,xParent, SOUTH);
					}
				}
				else{
					if (x>xParent){
						//move right
						movement.add(getRobotMovement(currentDirection, WEST));
						displayDirection(yParent,xParent, WEST);
					}
					else{
						//move left
						movement.add(getRobotMovement(currentDirection, EAST));
						displayDirection(yParent,xParent, EAST);
					}

				}
			}
			Thread.sleep((1/steps_per_sec)*1000); //move for each second
		}

	}

	private Constants.Movement getRobotMovement(Constants.Direction currentDirection, Constants.Direction cardinalDirection) throws InterruptedException {
		return getMovement(currentDirection.bearing-cardinalDirection.bearing);
	}

	public Constants.Movement getMovement(int bearing) throws InterruptedException {
		switch (bearing){
			case 0: robot.turn(NORTH);
				return Constants.Movement.MOVE_FORWARD;
			case 90: robot.turn(WEST);
				Thread.sleep((1/steps_per_sec)*2000); //for turning
				return Constants.Movement.TURN_LEFT;
			case 270: robot.turn(EAST);
				Thread.sleep((1/steps_per_sec)*2000); //for turning
				return Constants.Movement.TURN_RIGHT;
			default: return null;
		}
	}

	public void displayDirection(int ver_coord, int hor_coord, Constants.Direction dir) {
		simulatorMap.getMap().displayDirection(ver_coord, hor_coord, dir);
	}


}
