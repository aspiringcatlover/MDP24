package exploration;
import robot.Robot;

import static main.Constants.*;
import java.util.ArrayList;
import main.Constants.Direction;
import main.Constants.Movement;
import map.GridCell;
import map.ActualMap;


public abstract class Exploration{
	
	protected ArrayList<Movement> movement = new ArrayList<Movement>();
	
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
	
}
