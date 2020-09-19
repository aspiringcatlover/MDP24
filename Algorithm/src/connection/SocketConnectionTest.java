package connection;

import javax.swing.*;

public class SocketConnectionTest {

    public static void main(String[] args) {
        int result = JOptionPane.CLOSED_OPTION;
        int debug = JOptionPane.CLOSED_OPTION;
        int simulator = JOptionPane.CLOSED_OPTION;
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        if (debug == JOptionPane.YES_OPTION) {
            SocketConnection.setDebugTrue();
            System.out.println("Debug is " + SocketConnection.getDebug());
        }

        boolean connected = false;
        while (!connected) {
            connected = connectionManager.connectToRPi();
        }
        try {
            connectionManager.start();
        }
        catch (Exception e) {
            connectionManager.stopCM();
            System.out.println("ConnectionManager is stopped");
        }

    }




}
