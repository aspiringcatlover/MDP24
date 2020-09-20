package main;

import fastest_path.SimulatorFastestPath;
import robot.*;
import map.*;
import exploration.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static main.Constants.*;
import javax.swing.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

	public static void main(String[] args) throws InterruptedException {

		SimulatorMap simulatorMap = new SimulatorMap();
		
		while (true) {

			// if in simulation mode
			if (simulatorMap.getStartSimulation()) {

				// if in exploration simulation mode 
				if (simulatorMap.getIsExpSelected()) {
					System.out.println("start simulation exploration");
					SimulatorRobot robot = new SimulatorRobot();
					simulatorMap.getMap().displayRobotSpace(robot.getXCoord(),robot.getYCoord());
					simulatorMap.getMap().displayDirection(robot.getXCoord(), robot.getYCoord(), robot.getDirection());
					SimulatorExploration simulatorExp = new SimulatorExploration(robot, simulatorMap);
					simulatorExp.explore();
		
					//update simulator map 
					simulatorMap.revalidate();
					simulatorMap.repaint();
				}

				// if in fastest path simulation mode
				else {
					System.out.println("start simulation fastest path");
					SimulatorRobot robot = new SimulatorRobot();
					SimulatorFastestPath simulatorFastestPath = new SimulatorFastestPath(robot, simulatorMap);
					simulatorFastestPath.sendInstructions(); 

					//break; //how to make it just calculate 1 time and not keep running and running

					// to fill - fastest path simulation mode

					simulatorMap.revalidate();
					simulatorMap.repaint();
					//new simulator map generated after finished
					//simulatorMap = new SimulatorMap();
				}

				simulatorMap.setStartSimulation(false);

			}
			else if (simulatorMap.getStartActual()) {

				System.out.println("start actual");
//				ActualMap map = new ActualMap();
//				ActualRobot robot = new ActualRobot();
//				Actual actual = new Actual(robot, map);
//				actual.startActual();
//				
//				//new simulator map generated after finished
//				simulatorMap = new SimulatorMap();
			}
			
		}
		
	}
}
