package exploration;

import fastest_path.PathFinder;
import main.Constants;
import map.GridCell;
import map.MapPanel;
import map.SimulatorMap;
import robot.Robot;
import sensor.Sensor;
import sensor.SimulatorSensor;

import java.util.ArrayList;
import java.util.PriorityQueue;

import static main.Constants.HEIGHT;
import static main.Constants.WIDTH;
import static main.Constants.*;

public class Exploration {
    private Robot robot;
    private ArrayList<Constants.Movement> movement = new ArrayList<Constants.Movement>();
    private float goal_percentage;
    private float actual_percentage;
    private long endTime;
    private MapPanel map;


    //TODO: KIV CONSTRUCTOR
    public Exploration(Robot robot, int timeLimitMs, int coveragePercentage){
        this.robot = robot;
        goal_percentage = coveragePercentage;
        actual_percentage = 0;
        long startTime = System.currentTimeMillis();
        endTime = startTime + timeLimitMs;
        this.map = robot.getMap();
    }


    public void explore(){
        GridCell nearestUnexploredGrid;
        while (actual_percentage < goal_percentage && System.currentTimeMillis() != endTime) {
            //System.out.println(actual_percentage);
            //MapPanel map = simulatorMap.getMap();
            //System.out.println("robot_x:" + robot.getXCoord() + " robot_y:" + robot.getYCoord());
            //System.out.println(robot.getDirection());
            senseMap();

            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++){
                    printGridCell(map.getGridCell(row, col));
                }
                System.out.println();
            }
            System.out.println("robot x:"+ robot.getXCoord() + " ,y:"+robot.getYCoord());
            rightWallHugging();
            //fastest path to nearest unexplored grid


            //uncomment to follow time
            /*
            try {
                // ms timeout
                int timeout = (1 / steps_per_sec) * 1000;
                Thread.sleep(timeout); // Customize your refresh time
            } catch (InterruptedException e) {
            }*/
            actual_percentage = getActualPerc();
        }
        PriorityQueue<GridCell> unexploredGrids = getUnexploredGrid();
        PathFinder pathFinder = new PathFinder(robot.getMap());

        while (!unexploredGrids.isEmpty()){
            nearestUnexploredGrid = unexploredGrids.poll();
            pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), nearestUnexploredGrid.getX(), nearestUnexploredGrid.getY());
            /*
            go to unexplored grid & continue to senseMap
            find out grid is obstacle or what
            then unexploredGrids = getUnexploredGrid();
             */
            actual_percentage = getActualPerc();
        }
        //if robot not at start point get shortest path to start point & move to start point


    }


    public void rightWallHugging() {
        System.out.println("has obstacle: " + hasObstacle(robot.getDirection()));
        // if no obstacle on the right, turn right and move forward
        if (!hasObstacle(robot.robotRightDir())) {
            System.out.println("turn right");
            robot.turn(robot.robotRightDir());
            senseMap();
            //simulatorMap.getMap().displayDirection(robot.getXCoord(), robot.getYCoord(), robot.getDirection());

            movement.add(Constants.Movement.TURN_RIGHT);
            System.out.println("move forward");
            robot.moveForward();

            // simulatorMap.getMap().displayRobotSpace(robot.getXCoord(), robot.getYCoord());
            movement.add(Constants.Movement.MOVE_FORWARD);
        }

        // if can move forward, move forward
        else if (!hasObstacle(robot.getDirection())) {
            System.out.println("move forward");
            robot.moveForward();

            //simulatorMap.getMap().displayRobotSpace(robot.getXCoord(), robot.getYCoord());
            movement.add(Constants.Movement.MOVE_FORWARD);
        }
        // else, turn left
        else {
            robot.turn(robot.robotLeftDir());

            //simulatorMap.getMap().displayDirection(robot.getXCoord(), robot.getYCoord(), robot.getDirection());
            System.out.println("turn left");
            movement.add(Constants.Movement.TURN_LEFT);
        }
    }

    public boolean hasObstacle(Direction dir){
        int x,y;
        GridCell gridCell;

        switch (dir){
            case WEST:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkObstacleRow(false,y,x);
            case EAST:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkObstacleRow(false,y,x);
            case SOUTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkObstacleRow(true,y,x);
            case NORTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkObstacleRow(true,y,x);
        }
        return false;
    }

    private boolean checkObstacleRow(boolean xIncrease, int y, int x){
        GridCell gridCell;
        if (xIncrease){
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y,x+i);
                if (gridCell == null || gridCell.getObstacle()) {
                    return true;
                }
            }
        }
        else{
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y+i,x);
                if (gridCell == null || gridCell.getObstacle()) {
                    return true;
                }
            }
        }
        return false;
    }

    // sense map using sensors and update explored space on map
    public void senseMap() {
        ArrayList<Boolean> sensorResult;
        Direction direction = robot.getDirection();
        Sensor sensor;
        GridCell gridCell;
        int x, y;

        switch (direction){
            case WEST:
                // LEFT_TOP(3), LEFT_MIDDLE(4)
                for (int loc = 3; loc < 5; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        //update information from sensor
                        map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResult.get(i));

                        //update explore
                        map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                    }
                }
                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);
                    }
                }
                // RIGHT_TOP(5)
                sensor = robot.getIndividualSensor(5);
                sensorResult = sensor.getSensorInformation();
                x = sensor.getXCoord();
                y = sensor.getYCoord();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResult.get(i));
                    map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);
                }
                break;
            case NORTH:
                // LEFT_TOP(3), LEFT_MIDDLE(4)
                for (int loc = 3; loc < 5; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x=sensor.getXCoord();
                    y=sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x-i) + ", "+y+ "sensor result " + sensorResult.get(i));
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);
                    }
                }
                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + x + ", "+(y+i)+ "sensor result "+ sensorResult.get(i));
                        map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);
                    }
                }
                // RIGHT_TOP(5)
                sensor = robot.getIndividualSensor(5);
                sensorResult = sensor.getSensorInformation();
                x = sensor.getXCoord();
                y = sensor.getYCoord();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResult.get(i));
                    map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, true);
                }
                break;
            case EAST:
                // LEFT_TOP(3), LEFT_MIDDLE(4)
                for (int loc = 3; loc < 5; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);
                    }
                }
                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, true);
                    }
                }
                // RIGHT_TOP(5)
                sensor = robot.getIndividualSensor(5);
                sensorResult = sensor.getSensorInformation();
                x = sensor.getXCoord();
                y = sensor.getYCoord();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResult.get(i));
                    map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                }
                break;
            case SOUTH:
                // LEFT_TOP(3), LEFT_MIDDLE(4)
                for (int loc = 3; loc < 5; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, true);
                    }
                }
                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResult = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                    }
                }
                // RIGHT_TOP(5)
                sensor = robot.getIndividualSensor(5);
                sensorResult = sensor.getSensorInformation();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResult.get(i));
                    map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);
                }
                break;
        }

    }

    public PriorityQueue<GridCell> getUnexploredGrid(){
        PriorityQueue<GridCell> unexploredGrid = new PriorityQueue<>(11, (o1, o2) -> Integer.compare(o1.gethCost(), o2.gethCost()));
        int curX = robot.getXCoord();
        int curY= robot.getYCoord();
        int cost;
        GridCell gridCell;
        MapPanel map = robot.getMap();
        for (int y =0; y<19;y++){
            for (int x=0; x<14; x++){
                gridCell = map.getGridCell(y,x);
                if (!gridCell.getExplored()){
                    cost = calculateHeuristicCost(curX,curY,x,y);
                    //update h cost
                    gridCell.sethCost(cost);
                    unexploredGrid.add(gridCell);
                }
            }
        }
        return unexploredGrid;
    }

    private int calculateHeuristicCost(int xNow, int yNow, int xEnd, int yEnd){
        //manhattan heuristic
        return (Math.abs(xNow-xEnd) + Math.abs(yNow-yEnd));
    }

    public float getActualPerc() {
        int totalGridCells = HEIGHT * WIDTH;
        int gridCellsCovered = 0;
        GridCell gridCell;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                gridCell = map.getGridCell(row, col);
                if (gridCell.getExplored() || gridCell.getObstacle()) {
                    gridCellsCovered += 1;
                }
            }
        }
        // System.out.println((float) gridCellsCovered / totalGridCells * 100);
        return (((float) gridCellsCovered / totalGridCells) * 100);
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
}
