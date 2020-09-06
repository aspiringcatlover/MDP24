package robot;
import static main.Constants.*;

import controller.*;
import main.Constants.Direction;
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
		//the right of robot is to look from clockwise direction
		Direction [] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
		//index gives index of array based on current direction
		int index = 0;
		for (int i = 0; i < directions.length; i++) {
	        if (directions[i] == direction) {
	            index = i;
	        }
	    }
		//right index gives index based on robot's right direction
		int rightIndex = (index+1)%4;
		return directions[rightIndex];
	}
	
	//return direction to the left of the forward direction of robot
	public Direction robotLeftDir() {
		//the left of robot is to look from anti-clockwise direction
		Direction [] directions = {Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT};
		//index gives index of array based on current direction
		int index = 0;
		for (int i = 0; i < directions.length; i++) {
			if (directions[i] == direction) {
				index = i;
			}
		}
		//left index gives index based on robot's left direction
		int leftIndex = (index+1)%4;
		return directions[leftIndex];
	}
	
	//check if robot sensor detects an obstacle in the specified direction
	public abstract boolean hasObstacle(Direction dir);
	

}
