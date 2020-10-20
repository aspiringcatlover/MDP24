package simulator;

import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

import exploration.ExplorationApp;
import fastestPath.FastestPathApp;
import main.*;
import map.GridCell;
import map.MapPanel;
import robot.Robot;
import robot.SimulatorRobot;

public class SimulatorActualRun extends JFrame {

    private static final long serialVersionUID = 1L;
    private int goal_coverage_perc;
    private int actual_coverage_perc;
    private MapPanel map;
    private boolean isExpSelected;
    private int mapChoice;
    private int steps_per_sec;
    private int time_limit_ms;
    private int waypoint_x;
    private int waypoint_y;
    private String mdf_string_ent;
    private String[][] sample_map;
    private boolean startSimulation;
    private boolean startActual;
    private boolean startExp;
    private boolean startFp;
    private ExplorationApp explorationApp;
    private FastestPathApp fastestPathApp;
    private static Robot robot;
    private boolean mapExplored=false;
    private Thread tSimExplore;

    // constructor
    public SimulatorActualRun() {
        // default sample map shown is map 1
        mapChoice = 1;

        String[][] sample_map;

        //startActual = false;
        // default sample map shown is map 1
        sample_map = getSampleMap(mapChoice);

        actual_coverage_perc = 0;
        map = new MapPanel(sample_map);

        // simulator frame
        setTitle("Maze Simulator");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(10000, 10000);
        add(map);
        //refreshMap();
        robot = new SimulatorRobot(map, 9999, null);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    // get sample map

    private static String[][] getSampleMap(int mapChoice){
        String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
        try {
            String path_name = new File("").getAbsolutePath();
            path_name = System.getProperty("user.dir")+"/Algorithm/src/sampleMapTxt/map" + mapChoice + ".txt";
//			System.out.println(path_name);
            //path_name = "src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
            //C:\Users\CeciliaLee\IdeaProjects\MDP24\Algorithm\src\sample_map\map2.txt
            File myObj = new File(path_name);
            Scanner myReader = new Scanner(myObj);
            int col = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split("");
                for (int row = 0; row < arrOfStr.length; row++) {
                    temp_sample_map[row][col] = arrOfStr[row];
                }
                col++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return temp_sample_map;
    }


    public void startExploration(Robot robot, int time, int percentage, int speed, boolean image_recognition){

        if (tSimExplore == null || !tSimExplore.isAlive()) {
            tSimExplore = new Thread(new ExplorationApp(robot, time,percentage, speed, image_recognition));
            //tSimExplore.start();
            System.out.println("start simulator map once");
        } else {
            tSimExplore.interrupt();
        }

		/*
		if (!tSimExplore.isAlive()){
			this.robot = ExplorationApp.getRobot();
			System.out.println("ROBOT IS PASSED TO FASTEST PATH");
		}*/


        //get robot this might have problem because asychronous
//        this.robot = explorationApp.getRobot();
    }

    public void startFastestPath(){
		/*
		map = Exploration.getMap();
		robot.setMap(map);*/
        map = robot.getMap();
        System.out.println("fastest path map");
        for (int col = 0; col < 15; col++) {
            for (int row = 0; row < 20; row++){
                printGridCell(map.getGridCell(row, col));
            }
            System.out.println();
        }
        fastestPathApp = new FastestPathApp(robot);
        fastestPathApp.start();
    }

    // refresh new map according to map choice
    public void refreshMap() {
        getContentPane().remove(map);
        sample_map = getSampleMap(1);
        map = new MapPanel(sample_map);
        add(map);
        getContentPane().invalidate();
        getContentPane().validate();
    }

    private void printGridCell(GridCell gridCell) {
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

    // getters and setters
    public void setGoalCoveragePerc(int goal_coverage_perc) {
        this.goal_coverage_perc = goal_coverage_perc;
    }

    public int getGoalCoveragePerc() {
        return goal_coverage_perc;
    }

    public void setActualCoveragePerc(int actual_coverage_perc) {
        this.actual_coverage_perc = actual_coverage_perc;
    }

    public int getActualCoveragePerc() {
        return actual_coverage_perc;
    }

    public void setMap(MapPanel map) {
        this.map = map;
    }

    public MapPanel getMap() {
        return map;
    }

    public boolean getIsExpSelected() {
        return isExpSelected;
    }

    public void setMapChoice(int mapChoice) {
        this.mapChoice = mapChoice;
    }

    public int getMapChoice() {
        return mapChoice;
    }

    public void setStepsPerSec(int steps_per_sec) {
        this.steps_per_sec = steps_per_sec;
    }

    public int getStepsPerSec() {
        return steps_per_sec;
    }

    public void setTimeLimitMs(int time_limit_ms) {
        this.time_limit_ms = time_limit_ms;
    }

    public int getTimeLimitMs() {
        return time_limit_ms;
    }

    public void setStartSimulation(boolean startSimulation) {
        this.startSimulation = startSimulation;
    }

    public boolean getStartSimulation() {
        return startSimulation;
    }

    public boolean getStartActual() {
        return startActual;
    }

    public boolean getStartExp() {
        return startExp;
    }

    public boolean getStartFp() {
        return startFp;
    }

    public int getWaypointX() {
        return waypoint_x;
    }

    public int getWaypointY() {
        return waypoint_y;
    }

    public static Robot getRobot() {
        return robot;
    }

    public static void setRobot(Robot robot) {
        //SimulatorMap.robot = robot;
    }

    public String getMdfStringEnt() {
        return mdf_string_ent;
    }

}
