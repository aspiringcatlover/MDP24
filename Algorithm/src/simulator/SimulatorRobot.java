package simulator;

import static main.Constants.START_X_COORD;
import static main.Constants.START_Y_COORD;
import robot.Robot;

import main.Constants.Direction;

public class SimulatorRobot extends Robot {
	
	private int steps_per_sec;
	
	// constructor for simulation
	public SimulatorRobot(int steps_per_sec) {
		super();
		this.steps_per_sec = steps_per_sec;
	}
	
	//getters and setters
	public void setStepsPerSec(int steps_per_sec) {
		this.steps_per_sec = steps_per_sec;
	}
	
	public int getStepsPerSec() {
		return steps_per_sec;
	}
	
	//check if robot sensor detects an obstacle in the specified direction
	public boolean hasObstacle(Direction dir) {
		return true;
	};
}
