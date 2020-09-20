package test;

import static test.Constants.HEIGHT;
import static test.Constants.WIDTH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimulatorMap simulatorMap = new SimulatorMap();
		SimulatorRobot robot = new SimulatorRobot();
		SimulatorExploration simulatorExp = new SimulatorExploration(robot, simulatorMap);
		simulatorExp.explore();
	}

}
