package exploration;

import connection.SocketConnection;
import main.Constants;
import robot.Robot;
import robot.SimulatorRobot;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExplorationApp extends Thread{
    private Robot robot;
    private int time;
    private int percentage;
    private int speed;
    private boolean image_recognition;

    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final AtomicBoolean completed = new AtomicBoolean(false);
    //private static ExplorationThread thread = null;


    public ExplorationApp(Robot robot, int time, int percentage, int speed, boolean image_recognition) {
        super("ExplorationThread");
        this.robot = robot;
        this.time = time;
        this.percentage = percentage;
        this.speed = speed;
        this.image_recognition = image_recognition;
        start();
    }

    @Override
    public void run() {
        running.set(true);

        // Check if it is the simulator mode
        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);
        System.out.println("???");
        Exploration exploration = new Exploration(robot,time, percentage, speed, image_recognition);
        System.out.println("robot start exploring...");
        robot = exploration.explore();
        //exploration.Exploration(robot, time, percentage, speed, image_recognition);
        if (running.get()) {
            completed.set(true);
        }
        else {
            completed.set(false);
        }
        running.set(false);

        if (!isSimulated){
            if (SocketConnection.checkConnection()) {
                // Send the MDF String at the end when it is completed
                String[] arr2 = robot.getMdfString();
                SocketConnection.getInstance().sendMessage("M{\"map\":[{\"explored\": \"" + arr2[0] + "\",\"length\":" + arr2[1] + ",\"obstacle\":\"" + arr2[2] +
                        "\"}]}");
                SocketConnection.getInstance().sendMessage(Constants.END_TOUR);
            }
        }
        else{
            //simulator
        }
        while (!Thread.currentThread().isInterrupted()) {
            /*
            try {

                exploredMap.simulatedReveal(robot, testMap);
                gui.refreshGUI(robot, exploredMap);

                // Run exploration for one step
                boolean done = explorer.executeOneStep(robot, exploredMap);

                exploredMap.simulatedReveal(robot, testMap);
                gui.refreshGUI(robot, exploredMap);

                if (done)
                    break;

                Thread.sleep(1000/speed);
            } catch (InterruptedException e) {
                break;
            }*/
        }
    }

    public Robot getRobot(){
        return robot;
    }
    public static boolean getRunning() {
        return running.get();
    }

    public static boolean getCompleted() {
        return completed.get();
    }
}
