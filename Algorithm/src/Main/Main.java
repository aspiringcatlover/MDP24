package Main;
import robot.*;
import map.*;
import controller.*;

public class Main {

	public static void main(String[] args) {
		
		// whether robot is in simulation or not
		boolean inSimulation = true; 
		
		// controls whether robot goes to simulator or actual
		if (inSimulation) {
			
			//control time limit for simulator
			String time_limit_raw = "60:00";
			String[] parts = time_limit_raw.split(":");
			int mins = Integer.parseInt(parts[0]);
			int sec = Integer.parseInt(parts[1]);
			int time_limit_ms = (mins * 60 + sec) * 1000;
			
			//control steps per sec of robot
			int steps_per_sec = 1;
			
			//decide coverage percentage for map in simulator
			int coverage_perc = 100;
			
			//decide whether robot is in exploration or fastest path mode in simulator
			boolean isExploring = true;
					
			Robot robot = new Robot(steps_per_sec); 
			
			Map map = new Map(coverage_perc);
			
			Simulator simulator = new Simulator(robot, time_limit_ms, map);
			
			if (isExploring) {
				simulator.startExploration();
	
			}
			else {
				simulator.startFastestPath();
			}
		}
		//in actual
		else {
			Robot robot = new Robot(); 
			Map map = new Map();
			Actual actual = new Actual(robot, map);
			actual.startActual();
		}
		

	}

}
