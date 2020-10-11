package main;

import connection.ConnectionManager;
import map.*;
import javax.swing.*;

public class Main {

	private static boolean realRun = false;
	private static boolean simulate = false;

	public static void main(String[] args) throws InterruptedException {

		int result = JOptionPane.CLOSED_OPTION;
		int debug = JOptionPane.CLOSED_OPTION;
		int simulator = JOptionPane.CLOSED_OPTION;
		while (result == JOptionPane.CLOSED_OPTION) {
			result = JOptionPane.showConfirmDialog(null, "Is this the real run?", "Real Run", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				realRun = true;
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
