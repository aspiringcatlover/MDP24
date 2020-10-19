package main;

import connection.ConnectionManager;
import simulator.Simulator;
import simulator.SimulatorActualRun;

import javax.swing.*;

public class Main {

	private static boolean realRun = false;
	private static boolean simulate = false;

	public static void main(String[] args) {

		int result = JOptionPane.CLOSED_OPTION;
		while (result == JOptionPane.CLOSED_OPTION) {
			result = JOptionPane.showConfirmDialog(null, "Is this the real run?", "Real Run", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				realRun = true;
			}
			if (result == JOptionPane.NO_OPTION) {
				realRun = false;
			}
		}

		if (realRun) {
			SimulatorActualRun simulatorActualRobot = new SimulatorActualRun();
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
			Simulator simulatorMap = new Simulator();
		}
	}
}
