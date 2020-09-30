package main;

import connection.ConnectionManager;
import fastest_path.SimulatorFastestPath;
import robot.*;
import map.*;
import exploration.*;
import robot.Robot;

import java.awt.*;
import java.awt.event.ActionListener;

import static main.Constants.*;
import static map.SimulatorMap.getSampleMap;

import javax.swing.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

	private static boolean realRun = false;
	private static boolean simulate = false;

	public static void main(String[] args) throws InterruptedException {

		// ImageIcon icon = new ImageIcon(new
		// ImageIcon(Constant.DIALOGICONIMAGEPATH).getImage().getScaledInstance(40, 40,
		// Image.SCALE_DEFAULT));
		int result = JOptionPane.CLOSED_OPTION;
		int debug = JOptionPane.CLOSED_OPTION;
		int simulator = JOptionPane.CLOSED_OPTION;
		while (result == JOptionPane.CLOSED_OPTION) {
			result = JOptionPane.showConfirmDialog(null, "Is this the real run?", "Real Run", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				realRun = true;
				debug = JOptionPane.showConfirmDialog(null, "Print debug?", "Debug", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				while (debug == JOptionPane.CLOSED_OPTION) {
					debug = JOptionPane.showConfirmDialog(null, "Print debug?", "Debug", JOptionPane.YES_NO_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}
				simulator = JOptionPane.showConfirmDialog(null, "Show Simulator?", "Simulator",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				while (simulator == JOptionPane.CLOSED_OPTION) {
					simulator = JOptionPane.showConfirmDialog(null, "Show Simulator?", "Simulator",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				}
				if (simulator == JOptionPane.YES_OPTION) {
					simulate = true;
				}
			}
			if (result == JOptionPane.NO_OPTION) {
				realRun = false;
			}
		}

		if (realRun) {
			SimulatorActualRobot simulatorActualRobot = new SimulatorActualRobot();
			ConnectionManager connectionManager = ConnectionManager.getInstance();
			if (debug == JOptionPane.YES_OPTION) {
				/*
				 * ConnectionSocket.setDebugTrue(); System.out.println("Debug is " +
				 * ConnectionSocket.getDebug());
				 */
			}

			boolean connected = false;
			while (!connected) {
				connected = connectionManager.connectToRPi();
			}
			try {
				connectionManager.start();
				//SimulatorRobot simulatorRobot

			}
			catch (Exception e) {
				connectionManager.stopCM();
				System.out.println("ConnectionManager is stopped");
			}

		} else {
			SimulatorMap simulatorMap = new SimulatorMap();
		}
	}
}

/*
 * SimulatorMap simulatorMap = new SimulatorMap();
 *
 * while (true) {
 *
 * // if in simulation mode if (simulatorMap.getStartSimulation()) {
 *
 * // if in exploration simulation mode if (simulatorMap.getIsExpSelected()) {
 * System.out.println("start simulation exploration"); SimulatorRobot robot =
 * new SimulatorRobot();
 * simulatorMap.getMap().displayRobotSpace(robot.getXCoord(),robot.getYCoord());
 * simulatorMap.getMap().displayDirection(robot.getXCoord(), robot.getYCoord(),
 * robot.getDirection()); SimulatorExploration simulatorExp = new
 * SimulatorExploration(robot, simulatorMap); simulatorExp.explore();
 *
 * //update simulator map simulatorMap.revalidate(); simulatorMap.repaint(); }
 *
 * // if in fastest path simulation mode else {
 * System.out.println("start simulation fastest path"); SimulatorRobot robot =
 * new SimulatorRobot(); SimulatorFastestPath simulatorFastestPath = new
 * SimulatorFastestPath(robot, simulatorMap);
 * simulatorFastestPath.sendInstructions();
 *
 * //break; //how to make it just calculate 1 time and not keep running and
 * running
 *
 * // to fill - fastest path simulation mode
 *
 * simulatorMap.revalidate(); simulatorMap.repaint(); //new simulator map
 * generated after finished //simulatorMap = new SimulatorMap(); }
 *
 * simulatorMap.setStartSimulation(false);
 *
 * } else if (simulatorMap.getStartActual()) {
 *
 * System.out.println("start actual"); // ActualMap map = new ActualMap(); //
 * ActualRobot robot = new ActualRobot(); // Actual actual = new Actual(robot,
 * map); // actual.startActual(); // // //new simulator map generated after
 * finished // simulatorMap = new SimulatorMap(); }
 *
 * }
 */
