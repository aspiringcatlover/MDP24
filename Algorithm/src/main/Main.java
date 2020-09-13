package main;

import robot.*;
import map.*;
import controller.*;
import exploration.*;
import java.awt.*;
import static main.Constants.*;
import javax.swing.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// whether robot is in simulation or not
		// Initialized to true
		boolean inSimulation = true;

		// controls whether robot goes to simulator or actual
		if (inSimulation) {

			// control time limit for simulator
			String time_limit_raw = "60:00";
			String[] parts = time_limit_raw.split(":");
			int mins = Integer.parseInt(parts[0]);
			int sec = Integer.parseInt(parts[1]);
			int time_limit_ms = (mins * 60 + sec) * 1000;

			// control steps per sec of robot
			int steps_per_sec = 1;

			SimulatorRobot robot = new SimulatorRobot();

			// read sample map
			String[][] sample_map = new String[HEIGHT][WIDTH];
			try {
				File myObj = new File("C:\\Users\\shiny\\OneDrive\\Documents\\Y3S1\\Mdp\\MDP24\\MDP24\\Algorithm\\src\\sample_map\\map3.txt");
				//shiny's path: C:\Users\shiny\OneDrive\Documents\Y3S1\Mdp\MDP24\MDP24\Algorithm\src\sample_map
				Scanner myReader = new Scanner(myObj);
				int col = 0;
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] arrOfStr = data.split("");
					for (int row = 0; row < data.length(); row++) {
						sample_map[row][col] = arrOfStr[row];
					}
					col++;
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			SimulatorMap simulatorMap = new SimulatorMap(sample_map);

			SimulatorExploration simulatorExp = new SimulatorExploration(robot, simulatorMap, time_limit_ms, steps_per_sec);

			// check whether robot is in exploration or fastest path mode in simulator
			if (simulatorMap.getOption()) {
				System.out.println("test");
				simulatorExp.explore();
			} 
			else if(!simulatorMap.getOption()) { 
				//do fastest path
			}

		}
		// in actual
		else {
			ActualMap map = new ActualMap();
			ActualRobot robot = new ActualRobot();
			Actual actual = new Actual(robot, map);
			actual.startActual();
		}
	}

}
