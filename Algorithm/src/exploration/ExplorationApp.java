package exploration;

import connection.SocketConnection;
import main.Constants;
import map.GridCell;
import map.MapPanel;
import simulator.Simulator;
import robot.Robot;
import robot.SimulatorRobot;

import java.util.concurrent.atomic.AtomicBoolean;

import static main.Constants.HEIGHT;
import static main.Constants.WIDTH;

public class ExplorationApp extends Thread{
    private static Robot robot;
    private int time;
    private int percentage;
    private int speed;
    private boolean image_recognition;

    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final AtomicBoolean completed = new AtomicBoolean(false);
    private static ExplorationApp thread = null;


    public ExplorationApp(Robot robot, int time, int percentage, int speed, boolean image_recognition) {
        super("ExplorationThread");
        ExplorationApp.robot = robot;
        this.time = time;
        this.percentage = percentage;
        this.speed = speed;
        this.image_recognition = image_recognition;
        start();
    }

    public static ExplorationApp getInstance(Robot r, int time, int percentage, int speed, boolean image_recognition) {
        thread = new ExplorationApp(r, time, percentage, speed, image_recognition);
        return thread;
    }

    @Override
    public void run() {
        running.set(true);

        // Check if it is the simulator mode
        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);
        System.out.println("???");
        Exploration exploration = new Exploration(robot,time, percentage, speed, image_recognition);
        System.out.println("robot start exploring...");
        robot = exploration.startExploration();
        //robot = exploration.normalExploration();
        //robot = exploration.imageRecognitionExploration();
        MapPanel map = robot.getMap();
        System.out.println("in exploration app");
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++){
                printGridCell(map.getGridCell(row, col));
            }
            System.out.println();
        }
        Simulator.setRobot(robot);
        if (running.get()) {
            completed.set(true);
        }
        else {
            completed.set(false);
        }

        if (!isSimulated) {
            //SocketConnection.getInstance().sendMessage(Constants.END_TOUR);
        }
        stopThread();
    }

    public static Robot getRobot(){
        return robot;
    }
    public static boolean getRunning() {
        return running.get();
    }

    public static boolean getCompleted() {
        return completed.get();
    }


    public void printGridCell(GridCell gridCell) {
        // O for obstacle
        // E for explored
        // U for unexplored
        if (gridCell.getObstacle()) {
            System.out.print("O");
        } else if (gridCell.getExplored()) {
            System.out.print("E");
        } else {
            System.out.print("U");
        }
    }

    public static void stopThread() {
        running.set(false);
        System.out.println("thread ==null");
        thread = null;
    }
}
