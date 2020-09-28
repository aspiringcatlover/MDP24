package connection;

import main.Constants;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SocketConnectionTest {

    public static void main(String[] args) {
        int result = JOptionPane.CLOSED_OPTION;
        int debug = JOptionPane.CLOSED_OPTION;
        int simulator = JOptionPane.CLOSED_OPTION;
        String s;
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
            //connectionManager.start();
            while (connected){
                SocketConnection socketConnection = SocketConnection.getInstance();
                s = socketConnection.receiveMessage().trim();
                System.out.println("message receive" + s);
                socketConnection.sendMessage(s);
            }
        }
        catch (Exception e) {
            connectionManager.stopCM();
            System.out.println("ConnectionManager is stopped");
        }
        SocketConnection socketConnection = SocketConnection.getInstance();
        //send msg
        while(true){
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            socketConnection.sendMessage(msg);
        }


    }




}
