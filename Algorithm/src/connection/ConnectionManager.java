package connection;

import exploration.ExplorationApp;
import fastestPath.FastestPathApp;
import main.Constants;
import robot.ActualRobot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * acts as routing manager. receive command and route it to the correct instruction
 */
public class ConnectionManager {
    private static ActualRobot robot;
    private static ConnectionManager connectionManager = null;
    private SocketConnection socketConnection = SocketConnection.getInstance();
    private static Thread thread = null;
    private static ArrayList<String> buffer = new ArrayList<>();
    private static AtomicBoolean running = new AtomicBoolean(false);
    private static String[] bufferableCommand = new String[] {Constants.IMAGE_ACK};
    private boolean explorationRun=false;

    private ConnectionManager() {

    }
    // Singleton
    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    // Initialise the realrobot here and connect to RPI
    public boolean connectToRPi() {
        if (robot == null){
            //UNCOMMENT THIS
            //robot = ActualRobot.getInstance();
        }

        return socketConnection.connectToRPI();
    }

    // Stop all thread and close the connection
    public void disconnectFromRPI() {
        if (ExplorationApp.getRunning()) {
            ExplorationApp.stopThread();
        }
        socketConnection.closeConnection();
    }

    // Start this thread and poll for the message from RPi
    public void start() {
        running.set(true);
        while(running.get()) {
            //run exploration
            //call the app
            if (ExplorationApp.getRunning() || FastestPathApp.getRunning()) {
                try {
                    thread.join();
                }
                catch (Exception e) {
                    System.out.println("Error in start ConnectionManager");
                }
            }
            else {
                if (!ExplorationApp.getRunning()&&explorationRun){
                    robot = (ActualRobot) ExplorationApp.getRobot();
                   // socketConnection.sendMessage("END EXPLORATION");

                }
                System.out.println("waiting for message??");
                waitingForMessage();
            }
        }
    }

    // Stop this thread with this function
    public void stopCM() {
        running.set(false);
    }

    public static ArrayList<String> getBuffer(){
        return buffer;
    }

    private String waitingForMessage() {
        // Start Exploration/ Fastest Path/ Send_Arena/ Initializing/ Sensor Values
        String s = "";
        boolean complete = false;
        Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
        ExplorationApp explorationApp;
        FastestPathApp fastestPathApp;


        // Check if received a valid message
        while (!complete) {

            s = this.socketConnection.receiveMessage().trim();
            System.out.println(s);

            // Set the robot position only if the correct message is received and the program is not running exploration and fastest path
            if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.contains(Constants.INITIALISING)) {
                complete = true;
                String tmp = s.replace(Constants.INITIALISING + " (", "");
                tmp = tmp.replace(")", "");
                String[] arr = tmp.trim().split(",");
                robot.initialise(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]), (Integer.parseInt(arr[2] )));
                s = "Successfully set the robot's position: " + Integer.parseInt(arr[0]) +
                        "," + Integer.parseInt(arr[1]) + "," + Integer.parseInt(arr[2]);

                System.out.println(s);

                robot.getMap().updateMap(robot.getXCoord(),robot.getYCoord());
            }

            // Start exploration only if the correct message is received and the program is not running exploration and fastest path
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.equals(Constants.START_EXPLORATION) ) {
                s = "Exploration Started";
                explorationRun = true;
                System.out.println("image reg boolean value in connection manager?" + Constants.IMAGE_REC);
                thread = ExplorationApp.getInstance(robot, Constants.TIME, Constants.PERCENTAGE, Constants.SPEED, Constants.IMAGE_REC);
                thread.setPriority(Thread.MAX_PRIORITY);
                try {
                    thread.join();
                }
                catch(Exception e) {
                    System.out.println("Error in start exploration in ConnectionManager");
                }
                complete=true;

            }

            // Start fastestpath only if the correct message is received and the program is not running exploration and fastest path
            // possible bug --> map is not explored.
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.equals(Constants.FASTEST_PATH) ){
                System.out.println("hmmm....");
                //explorationRun=true;
                thread = new FastestPathApp(robot);
                //FastestPathApp.getInstance(robot, robot.getWaypoint(), 1);
                thread.setPriority(Thread.MAX_PRIORITY);
                System.out.println("....hmmm");


                s = "Fastest Path started";
                try {

                    thread.join();

                }
                catch(Exception e) {
                    System.out.println("Error in fastest path in ConnectionManager");
                }
                complete = true;
            }

            // Set waypoint only if the correct message is received and the program is not running exploration and fastest path
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() &&
                    s.contains(Constants.SETWAYPOINT)) {

                complete = true;
                String tmp = s.replace(Constants.SETWAYPOINT + " (", "");
                tmp = tmp.replace(")", "");
                String[] arr = tmp.trim().split(",");
                robot.setWaypoint(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]));
                s = "Successfully received the waypoint: " + Integer.parseInt(arr[0]) +
                        "," + Integer.parseInt(arr[1]);
                //robot.displayMessage(s, 2);
                System.out.println(s);
                robot.getMap().updateMap(robot.getXCoord(),robot.getYCoord());
            }

            // Send mdf arena only if the correct message is received and the program is not running exploration and fastest path
            else if (s.equals(Constants.SEND_ARENA)) {
                String[] arr = robot.getMdfString();
                socketConnection.sendMessage("{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
                        "\"}]}");
                //robot.displayMessage("{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +"\"}]}", 2);
                complete = true;
            }
            else if (s.contains("D")) {

            }
            // Store valid message if the program is running exploration or fastest path
            else {
                // If the command is an acknowledgement or sensor values, put into buffer
                buffer.add(s);
                System.out.println("Placed command" + s + " into buffer");
            }

        }
        return s;
    }
}
