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
        ArrayList<GridCell> path;
        ArrayList<Movement> moveInstructions;
        boolean terminate=false;

        //init start grid to be explored alr


        while (actual_percentage < goal_percentage && System.currentTimeMillis() != endTime) {
            //System.out.println(actual_percentage);
            //MapPanel map = simulatorMap.getMap(); //map for the simulator
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
        System.out.println("percentage covered:" + actual_percentage);
        /*
        senseMap();
        int[] unexplored=unexplored();
        PriorityQueue<GridCell> unexploredGrids = getUnexploredGrid();

        int unexploredGridX, unexploredGridY;
        PathFinder pathFinder;
        int robotX, robotY;

        while (!unexploredGrids.isEmpty()){
            /*
            go to unexplored grid & continue to senseMap
            find out grid is obstacle or what
            then unexploredGrids = getUnexploredGrid();
             */

            /*
            nearestUnexploredGrid = unexploredGrids.poll();
            unexploredGridX = nearestUnexploredGrid.getHorCoord();
            unexploredGridY = nearestUnexploredGrid.getVerCoord();
            System.out.println("unexplored grid:" + nearestUnexploredGrid.getHorCoord()+" " +nearestUnexploredGrid.getVerCoord());*/
            /*pathFinder = new PathFinder(robot.getMap()); //do singleton or smth
            //System.out.println("what happen start:" + robot.getXCoord()+" "+ robot.getYCoord()+" end:"+  nearestUnexploredGrid.getHorCoord()+" "+  nearestUnexploredGrid.getVerCoord());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexplored[1], unexplored[0]);
            //path = checkPath(path);

            /*
            while (path==null){
                clearParent();
                if (unexploredGrids.isEmpty()){
                    //shouldnt happen at all
                    terminate=true;
                    break;

                }
                nearestUnexploredGrid = unexploredGrids.poll();
                path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), nearestUnexploredGrid.getHorCoord(), nearestUnexploredGrid.getVerCoord());
                if (terminate==true)
                    break;
            }*/

            /*clearParent();
            System.out.println("path:");
            /*
            for (GridCell gridCell:path){
                System.out.println(gridCell.getHorCoord()+" "+gridCell.getVerCoord());
            }*/
            /*System.out.println("************");
            if (path!=null){
                moveInstructions = pathFinder.getRobotInstructions(path, robot.getDirection(),robot.getXCoord(), robot.getYCoord() );
                System.out.println("^^^^^movement length"+moveInstructions.size());

                for (Movement movement: moveInstructions){
                    System.out.println("fuck u");
                    if (movement.equals(Movement.MOVE_FORWARD)){
                        if (hasObstacle(robot.getDirection())){
                            //terminate=true;
                            break;
                        }
                        System.out.println("^^^^move forward, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.moveForward();
                        senseMap();
                    }
                    else if (movement.equals(Movement.TURN_RIGHT)){
                        System.out.println("^^^^turn right, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.turn(robot.robotRightDir());
                        senseMap();

                    }
                    else{
                        System.out.println("^^^^move left, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.turn(robot.robotLeftDir());
                        senseMap();
                    }
                /*
                switch (movement){
                    case MOVE_FORWARD: if (hasObstacle(robot.getDirection())){
                                            terminate=true;
                                            break;
                                        }
                                        System.out.println("^^^^move forward, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                                        robot.moveForward();
                                        senseMap();
                                        break;
                    case TURN_RIGHT: /*if (hasObstacle(robot.robotRightDir())){
                                            terminate=true;
                                            break;
                                        }*/
                        /*System.out.println("^^^^turn right, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                                        robot.turn(robot.robotRightDir());
                                        senseMap();
                                        break;
                    case TURN_LEFT: /*if (hasObstacle(robot.robotLeftDir())){
                                        terminate=true;
                                        break;
                                    }*/
                        /*System.out.println("^^^^move left, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                                    robot.turn(robot.robotLeftDir());
                                        senseMap();
                                        break;
                    default: System.out.println("fuck u");
                }

                if (terminate)
                    break;
                */


                    /*System.out.println(movement);
                    System.out.println("robot where u at: "+robot.getXCoord()+ " "+ robot.getYCoord());

                    if ( map.getGridCell(unexplored[0],unexplored[1]).getExplored()) //constantly seeing if the unexplored grid is explored alr in case is a obstacle then the last move cmi
                        break;
                    if ((robot.getYCoord()+1==unexplored[0]||robot.getYCoord()-1==unexplored[0])&&(robot.getXCoord()+1==unexplored[1]||robot.getXCoord()-1==unexplored[1])) //if any part of the robot is in the grid
                        break;

                }
            }

            System.out.println("knn");
            //unexploredGrids = getUnexploredGrid();

            unexploredGrids = getUnexploredGrid();
            actual_percentage = getActualPerc();
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++){
                    printGridCell(map.getGridCell(row, col));
                }
                System.out.println();
            }
            System.out.println("robot x:"+ robot.getXCoord() + " ,y:"+robot.getYCoord());
            System.out.println("percentage covered:" + actual_percentage);
            System.out.println("------------------------------------------------------");
        }*/


           System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord());
        //if robot not at start point get shortest path to start point & move to start point
        if (robot.getXCoord()!=1||robot.getYCoord()!=1){
            PathFinder pathFinder;
            pathFinder = new PathFinder(robot.getMap());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(),1,1);
            if (path==null) //shouldnt happen at all
                return;
            System.out.println("path size"+path.size());
             for(GridCell gridCell:path){
                 System.out.println(gridCell.getHorCoord()+" "+gridCell.getVerCoord());
             }
            moveInstructions = pathFinder.getRobotInstructions(path,robot.getDirection(),robot.getXCoord(), robot.getYCoord() );
            System.out.println("move instruction"+moveInstructions.size());
            for (Movement movement: moveInstructions){
                switch (movement){
                    case MOVE_FORWARD: robot.moveForward();
                        break;
                    case TURN_RIGHT: robot.turn(robot.robotRightDir());
                        break;
                    case TURN_LEFT: robot.turn(robot.robotLeftDir());
                        break;
                }

            }
            clearParent();
        }
        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord());

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

                        if (sensorResult.get(i)==null)
                            break;
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
                        if (sensorResult.get(i)==null)
                            break;
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
                    if (sensorResult.get(i)==null)
                        break;
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
                        if (sensorResult.get(i)==null)
                            break;
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
                        if (sensorResult.get(i)==null)
                            break;
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
                    if (sensorResult.get(i)==null)
                        break;
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
                        //System.out.println("hmm" + i);
                        if (sensorResult.get(i)==null)
                            break;
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
                        if (sensorResult.get(i)==null)
                            break;
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
                    if (sensorResult.get(i)==null)
                        break;
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
                        if (sensorResult.get(i)==null)
                            break;
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
                        if (sensorResult.get(i)==null)
                            break;
                        map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResult.get(i));
                        map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                    }
                }
                // RIGHT_TOP(5)
                sensor = robot.getIndividualSensor(5);
                sensorResult = sensor.getSensorInformation();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    if (sensorResult.get(i)==null)
                        break;
                    map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResult.get(i));
                    map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);
                }
                break;
        }

    }

    private int[] unexplored() {
        MapPanel map = robot.getMap();
        int lowest_cost = 9999;
        int[] cheapest_pos = null;
        for (int i=0; i<19; i++) {
            for (int j=0; j<14; j++) {
                if (!map.getGridCell(i,j).getExplored()) {
                    int cost = Math.abs(robot.getYCoord() - i) + Math.abs(robot.getXCoord() - j);
                    if (cost < lowest_cost) {
                        cheapest_pos = new int[] {i, j};
                        lowest_cost = cost;
                    }
                }
            }
        }
        return cheapest_pos;
    }


    public PriorityQueue<GridCell> getUnexploredGrid(){
        PriorityQueue<GridCell> unexploredGrid = new PriorityQueue<>(11, (o1, o2) -> Integer.compare(o1.getgCost(), o2.getgCost()));
        System.out.println("unexplored grid....pq");
        int curX = robot.getXCoord();
        int curY= robot.getYCoord();
        System.out.println("start from: "+curX+" "+curY);
        int cost;
        GridCell gridCell;
        MapPanel map = robot.getMap();
        for (int y =1; y<18;y++){
            for (int x=1; x<13; x++){
                gridCell = map.getGridCell(y,x);
                if (!gridCell.getExplored()){
                    cost = calculateHeuristicCost(curX,curY,x,y);
                    //update g cost
                    gridCell.setgCost(cost);
                    unexploredGrid.add(gridCell);
                }
            }
        }
        /*
        if (!unexploredGrid.isEmpty())
            System.out.println("peek pq"+unexploredGrid.peek().getHorCoord() + " " +unexploredGrid.peek().getVerCoord());
        System.out.println("----------------------------------get unexplored grid");
        for (GridCell gridCell1:unexploredGrid){
            System.out.println("NOT EXPLORED" + gridCell1.getHorCoord() +" "+gridCell1.getVerCoord());
        }*/

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

    public void clearParent(){
        GridCell gridCell;
        for (int i=0;i<14;i++){
            for (int r=0; r<19;r++){
                gridCell= map.getGridCell(r,i);
                gridCell.setParentGrid(null);
                map.setGridCell(r,i,gridCell);
            }
        }
    }

    public ArrayList<GridCell> checkPath(ArrayList<GridCell> path){
        ArrayList<GridCell> checkedPath = new ArrayList<>();
        for(GridCell gridCell:path){
            if (gridCell.getExplored()&&!gridCell.getObstacle()){
                checkedPath.add(gridCell);
            }
            else
                break;
        }
        return checkedPath;
    }


}
