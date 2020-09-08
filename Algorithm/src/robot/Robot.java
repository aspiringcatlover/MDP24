package robot;
import static main.Constants.*;
import java.util.ArrayList;

import controller.*;
<<<<<<< Updated upstream
import main.Constants.Direction;
=======
>>>>>>> Stashed changes
import map.GridCell;

public abstract class Robot {

	protected int x_coord; //wrt bottom left of robot
	protected int y_coord;
	protected Direction direction; // forward direction of robot
	
	// constructor
	public Robot() {
		x_coord = START_X_COORD;
		y_coord = START_Y_COORD;
		// assuming forward direction of robot is LEFT if right wall hugging 
		direction = Direction.LEFT;
	}
	
	// getters and setters
	public void setXCoord(int x_coord) {
		this.x_coord = x_coord;
	}
	
	public int getXCoord() {
		return x_coord;
	}
	
	public void setYCoord(int y_coord) {
		this.y_coord = y_coord;
	}
	
	public int getYCoord() {
		return y_coord;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	//helper functions
	public boolean blocked() {
		//get robot coordinate
		//get sensor data from front, left and right
		
		boolean frontblocked = GridCell.isBlocked();
		boolean leftblocked = GridCell.isBlocked();
		boolean rightblocked = GridCell.isBlocked();
		
		if (frontblocked && leftblocked && rightblocked) {
			return true;
			//move back to previous gridcell
		}
		else
			return false;
	}
	
<<<<<<< Updated upstream
	//move robot forward
	public void moveForward() {
		switch(direction){
			case UP:
				y_coord += 1;
				break;
			case DOWN:
				y_coord -= 1;
				break;
			case LEFT:
				x_coord -= 1;
				break;
			case RIGHT:
				x_coord += 1;
				break;
			default:
				break;
		}
	}
	
	//turn robot in a specified direction
	public void turn(Direction dir) {
		direction = dir;
	}
	
	//return direction to the right of the forward direction of robot
	public Direction robotRightDir() {
		return HelperDir(Direction.RIGHT);
	}
	
	//return direction to the left of the forward direction of robot
	public Direction robotLeftDir() {
		return HelperDir(Direction.LEFT);
=======
	public boolean explored() {
		//get robot coordinate
		//get sensor data from front, left and right
		
		boolean frontexplored = GridCell.hasExplored();
		boolean leftexplored = GridCell.hasExplored();
		boolean rightexplored = GridCell.hasExplored();
			
>>>>>>> Stashed changes
	}
	
	public Direction HelperDir(Direction dir) {
		//the left of robot is to look from anti-clockwise direction
		//the right of robot is to look from clockwise direction
		Direction [] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
		//index gives index based on current direction
		int index = 0;
		for (int i = 0; i < directions.length; i++) {
			if (directions[i] == direction) {
				index = i;
			}
		}
		int newIndex;
		if (dir == Direction.LEFT) {
			//left index gives index based on robot's left direction
			newIndex = (index+1)%4;
		}
		else {
			//right index gives index based on robot's right direction
			newIndex = (index-1)%4;
		}
		return directions[newIndex];
	}

}
