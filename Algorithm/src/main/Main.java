package main;
import robot.*;
import map.*;
import controller.*;
import exploration.*;
import java.awt.*;
import javax.swing.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// whether robot is in simulation or not
		// Initialized to true
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
			
			//decide whether robot is in exploration or fastest path mode in simulator
			boolean isExploring = true;
					
			SimulatorRobot robot = new SimulatorRobot(steps_per_sec); 
			
			SimulatorMap simulatorMap = new SimulatorMap();
			
			MapPanel map = new MapPanel();
			
			SimulatorExploration simulatorExp = new SimulatorExploration(robot, simulatorMap, time_limit_ms, map);
			
			if (isExploring) {
				//for testing
				simulatorExp.displayDirection(robot.getXCoord(), robot.getYCoord(), robot.getDirection()); 
				simulatorExp.explore();
	
			}
//			else {
//				simulatorExp.startFastestPath();
//			}
		}
		//in actual
		else {
			ActualMap map = new ActualMap();
			ActualRobot robot = new ActualRobot();
			Actual actual = new Actual(robot, map);
			actual.startActual();
		}
		
	}

}
