package connection;

import exploration.ExplorationApp;
import fastest_path.FastestPathApp;
import main.Constants;
import robot.ActualRobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class ConnectionManager {
    private static ActualRobot robot;
    private static ConnectionManager connectionManager = null;
    private SocketConnection socketConnection = SocketConnection.getInstance();
    //private static Thread thread = null;
    private static ArrayList<String> buffer = new ArrayList<String>();
    private static AtomicBoolean running = new AtomicBoolean(false);
    private static String[] bufferableCommand = new String[] {Constants.IMAGE_ACK};

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
            robot = ActualRobot.getInstance();
        }

        return socketConnection.connectToRPI();
    }

    // Stop all thread and close the connection
    public void disconnectFromRPI() {
        /*
        if (ExplorationThread.getRunning()) {
            ExplorationThread.stopThread();
        }
        if (FastestPathThread.getRunning()) {
            FastestPathThread.stopThread();
        }*/
        socketConnection.closeConnection();
    }

    // Start this thread and poll for the message from RPi
    public void start() {
        running.set(true);
        while(running.get()) {
            //run exploration
            //call the app

            /*
            if (ExplorationThread.getRunning() || FastestPathThread.getRunning()) {
                try {
                    thread.join();
                }
                catch (Exception e) {
                    System.out.println("Error in start ConnectionManager");
                }
            }
            else {
                waitingForMessage();
            }*/
            waitingForMessage();
        }
    }

    // Stop this thread with this function
    public void stopCM() {
        running.set(false);
    }

    public static ArrayList<String> getBuffer(){
        return buffer;
    }

    public String waitingForMessage() {
        // Start Exploration/ Fastest Path/ Send_Arena/ Initializing/ Sensor Values
        String s = "";
        boolean complete = false;
        Pattern sensorPattern = Pattern.compile("\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+[|]{1}\\d+");
        ExplorationApp explorationApp;
        FastestPathApp fastestPathApp;


        // Check if received a valid message
        while (!complete) {
            //robot.displayMessage("Waiting for orders", 2); display message is for simulator tgt with real run

            s = this.socketConnection.receiveMessage().trim();
            System.out.println(s);
            //robot.displayMessage("Received message: " + s, 2);

            // Set the robot position only if the correct message is received and the program is not running exploration and fastest path
            if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.contains(Constants.INITIALISING)) {
                Pattern p_s_s = Pattern.compile(Constants.INITIALISING + "[\\s]\\([1-9],[1-9],[0-3]{1}\\)");
                Pattern p_d_s = Pattern.compile(Constants.INITIALISING + "[\\s]\\([1][0-8],[1-9],[0-3]{1}\\)");
                Pattern p_s_d = Pattern.compile(Constants.INITIALISING + "[\\s]\\([1-9],[1][0-4],[0-3]{1}\\)");
                Pattern p_d_d = Pattern.compile(Constants.INITIALISING + "[\\s]\\([1][0-8],[1][0-4],[0-3]{1}\\)");
                if ((p_s_s.matcher(s).matches() || p_d_s.matcher(s).matches() || p_s_d.matcher(s).matches() || p_d_d.matcher(s).matches())){
                    complete = true;
                    String tmp = s.replace(Constants.INITIALISING + " (", "");
                    tmp = tmp.replace(")", "");
                    String[] arr = tmp.trim().split(",");
                    robot.initialise(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]), (Integer.parseInt(arr[2]) + 1 ) % 4);
                    s = "Successfully set the robot's position: " + Integer.parseInt(arr[0]) +
                            "," + Integer.parseInt(arr[1]) + "," + Integer.parseInt(arr[2]);
                    //robot.displayMessage(s, 2);
                }
            }

            // Start exploration only if the correct message is received and the program is not running exploration and fastest path
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.equals(Constants.START_EXPLORATION) ) {
                s = "Exploration Started";
                explorationApp = new ExplorationApp(robot, Constants.TIME, Constants.PERCENTAGE, Constants.SPEED, Constants.IMAGE_REC);


                //thread.setPriority(Thread.MAX_PRIORITY);

                complete = true;

                try {
                    explorationApp.run();
                    //get robot
                    robot = (ActualRobot) explorationApp.getRobot();
                    //thread.join();
                }
                catch(Exception e) {
                    System.out.println("Error in start exploration in ConnectionManager");
                }
            }

            // Start fastestpath only if the correct message is received and the program is not running exploration and fastest path
            //TODO: and map have to be explored?
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() && s.equals(Constants.FASTEST_PATH) ){

                fastestPathApp = new FastestPathApp(robot);
                //FastestPathApp.getInstance(robot, robot.getWaypoint(), 1);
                //thread.setPriority(Thread.MAX_PRIORITY);

                s = "Fastest Path started";
                try {
                    fastestPathApp.run();
                    //thread.join();

                }
                catch(Exception e) {
                    System.out.println("Error in fastest path in ConnectionManager");
                }
                complete = true;
            }

            // Set waypoint only if the correct message is received and the program is not running exploration and fastest path
            else if (!ExplorationApp.getRunning() && !FastestPathApp.getRunning() &&
                    s.contains(Constants.SETWAYPOINT)) {
                Pattern wp_s_s = Pattern.compile(Constants.SETWAYPOINT + " \\([1-9],[1-9]\\)");
                Pattern wp_d_s = Pattern.compile(Constants.SETWAYPOINT + " \\([1-9],[1][0-8]\\)");
                Pattern wp_s_d = Pattern.compile(Constants.SETWAYPOINT + " \\([1][0-4],[1-9]\\)");
                Pattern wp_d_d = Pattern.compile(Constants.SETWAYPOINT + " \\([1][0-4],[1][0-8]\\)");
                if ((wp_s_s.matcher(s).matches() || wp_d_s.matcher(s).matches() || wp_s_d.matcher(s).matches() || wp_d_d.matcher(s).matches())){
                    complete = true;
                    String tmp = s.replace(Constants.SETWAYPOINT + " (", "");
                    tmp = tmp.replace(")", "");
                    String[] arr = tmp.trim().split(",");
                    robot.setWaypoint(Integer.parseInt(arr[1]), Integer.parseInt(arr[0]));
                    s = "Successfully received the waypoint: " + Integer.parseInt(arr[0]) +
                            "," + Integer.parseInt(arr[1]);
                    //robot.displayMessage(s, 2);
                }
            }

            // Send mdf arena only if the correct message is received and the program is not running exploration and fastest path
            else if (s.equals(Constants.SEND_ARENA)) {
                String[] arr = robot.getMdfString();
                socketConnection.sendMessage("{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
                        "\"}]}");
                //robot.displayMessage("{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +"\"}]}", 2);
                complete = true;
            }

            // Store valid message if the program is running exploration or fastest path
            else if (Arrays.asList(bufferableCommand).contains(s) || sensorPattern.matcher(s).matches()) {
                // If the command is an acknowledgement or sensor values, put into buffer
                buffer.add(s);
                System.out.println("Placed command" + s + " into buffer");
            }
            else {
                System.out.println("Unknown command: " + s);
            }

        }
        return s;
    }

}
