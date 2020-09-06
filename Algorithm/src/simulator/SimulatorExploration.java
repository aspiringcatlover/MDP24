package simulator;

import map.Map;
import controller.Exploration;
import robot.Robot;

public class SimulatorExploration extends Exploration {

	private long startTime;
	private long endTime;
	
	//constructor
	public SimulatorExploration(Robot robot, Map map, int time_limit_ms) {
		super(robot,map);
		startTime = System.currentTimeMillis();
		endTime = startTime + time_limit_ms;
	}
	
	//start exploring maze
	public void explore() {
		while(map.getActualCoveragePerc() < map.getGoalCoveragePerc() && System.currentTimeMillis() != endTime) {
			//sense map and update map descriptor
			senseMap();
			super.rightWallHugging();
		}
	}
	
	//sense map using sensors and update map descriptor where there is obstacles/free explored space
	public void senseMap() {
		
	}

}
