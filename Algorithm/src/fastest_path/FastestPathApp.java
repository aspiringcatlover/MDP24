package fastest_path;

import connection.SocketConnection;
import main.Constants;
import map.GridCell;
import map.MapPanel;
import robot.Robot;
import robot.SimulatorRobot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class FastestPathApp extends Thread{
    private Robot robot;
    private long startTime;
    private long endTime;
    private PathFinder pathFinder;
    private ArrayList<Constants.Movement> movement = new ArrayList<>();

    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final AtomicBoolean completed = new AtomicBoolean(false);


    public FastestPathApp(Robot robot){
        this.robot = robot;
        pathFinder = new PathFinder(robot.getMap());
        start();
    }

    @Override
    public void run(){
        running.set(true);

        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);

        //get waypoint from map in the robot
        int[] waypoint = robot.getMap().getWayPoint();
        System.out.println("way point" + waypoint[0]+" "+waypoint[1]);
        ArrayList<GridCell> fastestPath1 = pathFinder.getShortestPathWithWaypoint(robot.getXCoord(), robot.getYCoord(),waypoint[0], waypoint[1]);


        //convert path to instructins

        if (running.get()) {
            completed.set(true);
        }
        else {
            completed.set(false);
        }
        running.set(false);
        ArrayList<Constants.Movement> robotMovements1 = pathFinder.getRobotInstructions(fastestPath1, robot.getDirection(),robot.getXCoord(), robot.getYCoord());
        if (!isSimulated){

            arudinoInstructions(robotMovements1);
            if (SocketConnection.checkConnection()) {
                SocketConnection.getInstance().sendMessage(Constants.END_TOUR);
                //r.displayMessage("Sent message: " + Constants.END_TOUR, 1);
            }
        }
        else{
            //update the simulator
            simulatorInstructions(robotMovements1);

        }
    }

    public static boolean getRunning() {
        return running.get();
    }

    public static boolean getCompleted() {
        return completed.get();
    }

    public void simulatorInstructions(ArrayList<Constants.Movement> robotMovements){
        Constants.Direction direction;
        for (Constants.Movement move: robotMovements){
            if (move== Constants.Movement.MOVE_FORWARD){
                robot.moveForward();
            }
            else if (move == Constants.Movement.TURN_LEFT){
                robot.turn(robot.robotLeftDir());
            }
            else if (move== Constants.Movement.TURN_RIGHT){
                robot.turn(robot.robotRightDir());
            }
            direction = robot.getDirection();
            robot.getMap().updateMap(robot.getXCoord(),robot.getYCoord());
            robot.getMap().displayDirection(robot.getYCoord(),robot.getXCoord(),direction);
        }

    }

    public void arudinoInstructions(ArrayList<Constants.Movement> robotMovements){
        // Append all the movement message into one full string and send at once
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Constants.Movement move : robotMovements) {
            if (move == Constants.Movement.MOVE_FORWARD) {
                count++;
            } else if (count > 0) {
                sb.append("W").append(count).append("|");
                if (move == Constants.Movement.TURN_RIGHT) {
                    sb.append(Constants.TURN_RIGHT);
                    count = 1;
                } else if (move == Constants.Movement.TURN_LEFT) {
                    sb.append(Constants.TURN_LEFT);
                    count = 1;
                } else {
                    System.out.println("Error!");
                    return;
                }
            }
        }
        if (count > 0) {
            sb.append("W").append(count).append("|");
        }
        String msg = sb.toString();
        //robot.displayMessage("Message sent for FastestPath real run: " + msg, 2);
        SocketConnection.getInstance().sendMessage(msg);
    }

}
