package main;

import connection.ConnectionManager;
import simulator.Simulator;
import simulator.SimulatorActualRun;

import javax.swing.*;

public class Main {

	private static boolean realRun = false;
	private static boolean simulate = false;
	public static boolean imageRegRun = false;


	public static void main(String[] args) {
		int imageReg = JOptionPane.CLOSED_OPTION;
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

			//imageReg = JOptionPane.showConfirmDialog(null, "Image?", "Debug", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			while (imageReg == JOptionPane.CLOSED_OPTION) {
				imageReg = JOptionPane.showConfirmDialog(null, "Image reg exploration?", "Debug", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (imageReg==JOptionPane.YES_OPTION){
					imageRegRun= true;
				}
				if (imageReg==JOptionPane.NO_OPTION){
					imageRegRun= false;
				}

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
