package exploration;

import Image.Surface;
import Image.VisibleSurface;
import fastest_path.PathFinder;
import main.Constants;
import map.GridCell;
import map.MapPanel;
import robot.ActualRobot;
import robot.Robot;
import robot.SimulatorRobot;
import sensor.Sensor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

import static main.Constants.HEIGHT;
import static main.Constants.WIDTH;
import static main.Constants.*;

public class Exploration {
    private Robot robot;
    private ArrayList<Constants.Movement> movement = new ArrayList<>();
    private float goal_percentage;
    private float actual_percentage;
    private long endTime;
    private static MapPanel map;
    private int steps_per_sec;
    private boolean imageRecognition;
    private VisibleSurface visibleSurface;
    //private MapPanel exploringMap;


    //TODO: KIV CONSTRUCTOR
    public Exploration(Robot robot, int timeLimitMs, int coveragePercentage, int steps_per_sec, boolean imageRecognition){
        this.robot = robot;
        goal_percentage = coveragePercentage;
        actual_percentage = 0;
        long startTime = System.currentTimeMillis();
        endTime = startTime + timeLimitMs;
        map = robot.getMap();
        this.steps_per_sec = steps_per_sec;
        this.imageRecognition = imageRecognition;
        map.setSteps_per_sec(steps_per_sec);
    }


    public Robot explore(){
        GridCell nearestUnexploredGrid;
        ArrayList<GridCell> path ;
        ArrayList<Movement> moveInstructions;
        boolean terminate=false;
        boolean start = false;
        int calibrateCounter =0;



        //init start grid to be explored alr
        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);
        for (int i = 0; i < 3; i++) {
            for (int r = 0; r < 3; r++) {
                map.setExploredForGridCell(i, r, true);
            }
        }
        //init

        robot.initSensor();
        System.out.println("EXPLORATION-FINISH INITIALISE SENSOR");


        while (actual_percentage < goal_percentage && System.currentTimeMillis() <endTime) {
            System.out.println("robot direction .... " + robot.getDirection());
            System.out.println("before sense map");
            //aft sense map calibrate
            senseMap();
           // robot.setMap(map);
                System.out.println("after sense map");
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++){
                    printGridCell(map.getGridCell(row, col));
                }
                System.out.println();
            }
            System.out.println("robot x:"+ robot.getXCoord() + " ,y:"+robot.getYCoord() + "direction" + robot.getDirection());
            if (robot.getXCoord()==1&&robot.getYCoord()==1&&start){
                System.out.println("break right wall hugging");
                break;
            }
            start=true;
            System.out.println("percentage covered:" + actual_percentage);
            //check back of robot for unexplored grid

            robot.setMap(map);
            rightWallHugging();
            //fastest path to nearest unexplored grid

          actual_percentage = getActualPerc();
        }
        System.out.println("percentage covered:" + actual_percentage);
        resetRobotDirectionToEast();

        PriorityQueue<GridCell> unexploredGrids = getUnexploredGrid();

        int unexploredGridX, unexploredGridY;
        PathFinder pathFinder;
        int robotX, robotY;
        int counter=0;

        while (!unexploredGrids.isEmpty()&&actual_percentage < goal_percentage&& System.currentTimeMillis() < endTime){
            /*
            go to unexplored grid & continue to senseMap
            find out grid is obstacle or what
            then unexploredGrids = getUnexploredGrid();
             */

            //System.out.println("unexplored grid: ");
            for (GridCell gridCell:unexploredGrids){
                System.out.println(gridCell.getHorCoord() + " "+gridCell.getVerCoord());
            }

            System.out.println("POLLLLLLL");
            nearestUnexploredGrid = unexploredGrids.poll();
            unexploredGridX = nearestUnexploredGrid.getHorCoord();
            unexploredGridY = nearestUnexploredGrid.getVerCoord();
            System.out.println("unexplored grid:" + nearestUnexploredGrid.getHorCoord()+" " +nearestUnexploredGrid.getVerCoord());

            pathFinder = new PathFinder(map); //do singleton or smth
            System.out.println("what happen start:" + robot.getXCoord()+" "+ robot.getYCoord()+" end:"+  nearestUnexploredGrid.getHorCoord()+" "+  nearestUnexploredGrid.getVerCoord());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, unexploredGridY);

            //path = checkPath(path);


            while (path==null){
                System.out.println("wtf is goin on");
                //try all the 4 grid beside if there is path
                for (int i=1; i<=2;i++){
                    if (unexploredGridX==0||unexploredGridY==0||unexploredGridX==14||unexploredGridY==19){
                        System.out.println("corner");
                        if (unexploredGridX==0){
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 2, unexploredGridY);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 1, unexploredGridY + index);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;

                        }
                        else if(unexploredGridX==14){
                            System.out.println("CORNER GRID WTD");
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*
                                    pathFinder = new PathFinder(map);
                                    System.out.println("is thr path? 12 "+ unexploredGridY);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 12, unexploredGridY);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    System.out.println("is thr path? 13 "+ unexploredGridY+index);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 13, unexploredGridY + index);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                        else if(unexploredGridY==0){
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 2);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,1);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                        else if (unexploredGridY==19){
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 17);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,18);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                    }
                    else{
                        for (int y=-1;y<2; y++){
                            for (int x=-1; x<2;x++){
                                if (x==0&&y==0)
                                    continue;
                                pathFinder = new PathFinder(map);
                                System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                System.out.println("check surrounding grid: "+checkSurroundingGrid(unexploredGridX + x, unexploredGridY + y));
                                System.out.println("check robot coordinate x"+(robot.getXCoord()!=unexploredGridX+x));
                                System.out.println("check robot coordinate y"+(robot.getYCoord()!=unexploredGridY+y));
                                if ((robot.getXCoord()!=unexploredGridX+x||robot.getYCoord()!=unexploredGridY+y)&&checkSurroundingGrid(unexploredGridX+x, unexploredGridY+y )){
                                    System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+x, unexploredGridY+y);
                                }

                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                        for (int y=-2;y<3; y++){
                            for (int x=-2; x<3;x++){
                                if ((x==2&&y==0)||(x==-2&&y==0)||(x==0&&y==-2)||(x==0&&y==2)){
                                    pathFinder = new PathFinder(map);
                                    System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                    if (robot.getXCoord()!=unexploredGridX+x||robot.getYCoord()!=unexploredGridY+y&&checkSurroundingGrid(unexploredGridX+x, unexploredGridY+y )){
                                        System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+x, unexploredGridY+y);
                                    }

                                    if (path!=null)
                                        break;
                                }
                            }
                            if (path!=null)
                                break;
                        }
                    }

                }
                if (path!=null)
                    break;

                //clearParent();
                if (unexploredGrids.isEmpty()){
                    //shouldnt happen at all
                    terminate=true;
                    break;

                }
                pathFinder = new PathFinder(map); //do singleton or smth
                System.out.println("get next unexplored grid in list: " + unexploredGrids.peek().getHorCoord() + " "+unexploredGrids.peek().getVerCoord());
                nearestUnexploredGrid = unexploredGrids.poll();
                unexploredGridX = nearestUnexploredGrid.getHorCoord();
                unexploredGridY = nearestUnexploredGrid.getVerCoord();
                System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +nearestUnexploredGrid.getHorCoord()+" "+ nearestUnexploredGrid.getVerCoord());
                path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), nearestUnexploredGrid.getHorCoord(), nearestUnexploredGrid.getVerCoord());
                if (path==null) {
                    //try all the 4 grid beside if there is path
                    for (int i = 1; i <= 2; i++) {
                        if (unexploredGridX==0){
                            for (int index=-2;index<4;index+=2){
                                if (index!=0){
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 1, unexploredGridY + index);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;

                        }
                        else if(unexploredGridX==14){
                            System.out.println("CORNER GRID WTD");
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 12, unexploredGridY);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 13, unexploredGridY + index);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                        else if(unexploredGridY==0){
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                   /* pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 2);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,1);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }
                        else if (unexploredGridY==19){
                            for (int index=-2;index<4;index+=2){
                                if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 17);*/
                                }else{
                                    pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,18);
                                }
                                if (path!=null)
                                    break;
                            }
                            if (path!=null)
                                break;
                        }

                         else {
                            for (int y = -1; y < 2; y++) {
                                for (int x = -1; x < 2; x++) {
                                    if (x == 0 && y == 0)
                                        continue;
                                    pathFinder = new PathFinder(map);
                                    System.out.println("robot current position: " + robot.getXCoord() + " " + robot.getYCoord() + " grid to go to: " + (unexploredGridX + x) + " " + (unexploredGridY + y));
                                    if ((robot.getXCoord() != unexploredGridX + x || robot.getYCoord() != unexploredGridY + y )&& checkSurroundingGrid(unexploredGridX + x, unexploredGridY + y)) {
                                        System.out.println("robot current position: " + robot.getXCoord() + " " + robot.getYCoord() + " grid to go to: " + (unexploredGridX + x) + " " + (unexploredGridY + y));
                                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX + x, unexploredGridY + y);
                                    }

                                    if (path != null)
                                        break;
                                }
                                if (path!=null)
                                    break;
                            }
                        }

                    }
                }
                if (terminate)
                    break;
            }

            clearParent();
           //System.out.println("path:");

            //System.out.println("************");
            if (path!=null){
                pathFinder = new PathFinder(map);
                moveInstructions = pathFinder.getRobotInstructions(path, robot.getDirection(),robot.getXCoord(), robot.getYCoord() );
                //System.out.println("^^^^^movement length"+moveInstructions.size());

                for (Movement movement: moveInstructions){
                    if (movement.equals(Movement.MOVE_FORWARD)){
                        if (hasObstacle(robot.getDirection())){
                            //System.out.println("obstacle in front");
                            //terminate=true;
                            break;
                        }
                        //System.out.println("^^^^move forward, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.moveForward();
                        senseMap();
                        if (hasObstacleOnRight()){
                            System.out.println("obstacle on right...calibrate");
                            robot.calibrate();
                        }
                    }
                    else if (movement.equals(Movement.TURN_RIGHT)){
                        //System.out.println("^^^^turn right, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.turn(robot.robotRightDir());
                        senseMap();
                        if (hasObstacleOnRight()){
                            System.out.println("obstacle on right...calibrate");
                            robot.calibrate();
                        }

                    }
                    else{
                        //System.out.println("^^^^move left, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                        robot.turn(robot.robotLeftDir());
                        senseMap();
                        if (hasObstacleOnRight()){
                            System.out.println("obstacle on right...calibrate");
                            robot.calibrate();
                        }
                    }

                if (terminate)
                    break;
                //System.out.println(movement);
                //System.out.println("robot where u at: "+robot.getXCoord()+ " "+ robot.getYCoord());

                if ( map.getGridCell(unexploredGridY,unexploredGridX).getExplored()) //constantly seeing if the unexplored grid is explored alr in case is a obstacle then the last move cmi
                    break;
                /*
                if ((robot.getYCoord()+1==unexploredGridY||robot.getYCoord()-1==unexploredGridY)&&(robot.getXCoord()+1==unexploredGridX||robot.getXCoord()-1==unexploredGridX)) //if any part of the robot is in the grid
                    break;*/

                }
                if (!map.getGridCell(unexploredGridY,unexploredGridX).getExplored()){
                    //turn to face the coordinate
                    Direction directionToFace = getDirection(unexploredGridX, unexploredGridY);
                    robot.turn(directionToFace);
                    System.out.println("direction to face ..." + directionToFace);
                    senseMap();
                    if (hasObstacleOnRight()){
                        System.out.println("obstacle on right...calibrate");
                        robot.calibrate();
                    }
                    map.updateMap(robot.getXCoord(),robot.getYCoord());
                    map.displayDirection(robot.getYCoord(),robot.getXCoord(),directionToFace);
                    map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
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
        }

        //send final mdf string
        robot.setMap(map);

        if(!isSimulated){
            ActualRobot actualRobot = (ActualRobot) robot;
            //send mdf shit
            System.out.println("sending mdf string...");
            actualRobot.sendMdfString();
        }

           System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord());
        //if robot not at start point get shortest path to start point & move to start point
        if (robot.getXCoord()!=1||robot.getYCoord()!=1){
            //PathFinder pathFinder;
            pathFinder = new PathFinder(robot.getMap());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(),1,1);
            if (path==null) //shouldnt happen at all
                return null;
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
                map.updateMap(robot.getXCoord(),robot.getYCoord());
                map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);

            }
            clearParent();
        }

        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord() + "direction: "+ robot.getDirection());
        //update robot map
        resetRobotDirection();
        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord() + "direction: "+ robot.getDirection());
        robot.setMap(map);
        map.updateMap(robot.getXCoord(),robot.getYCoord());
        map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
        //map.displayDirection(robot.getYCoord(),robot.getXCoord(),robot.getDirection());
        //return robot
        return robot;



    }

    private void resetRobotDirection(){
        switch (robot.getDirection()){
            case SOUTH: robot.turn(robot.robotRightDir());
                robot.turn(robot.robotRightDir());
                break;
            case WEST:
                robot.turn(robot.robotRightDir());
                break;
            case EAST: robot.turn(robot.robotLeftDir());
                break;
        }
    }

    private void resetRobotDirectionToEast(){
        switch (robot.getDirection()){
            case SOUTH: robot.turn(robot.robotLeftDir());
                break;
            case WEST:
                robot.turn(robot.robotRightDir());
                robot.turn(robot.robotRightDir());
                break;
            case NORTH: robot.turn(robot.robotRightDir());
                break;
        }
    }

    private void rightWallHugging() {
        System.out.println("has obstacle: " + hasObstacle(robot.getDirection()));
        System.out.println("WTF IS GOIN ON HEER");
        // if no obstacle on the right, turn right and move forward
        if (!hasObstacle(robot.peekRobotRightDir())) {
            System.out.println("turn right.....");
            robot.turn(robot.robotRightDir());
            senseMap();

            System.out.println("MOVE FORWARD.....");
            movement.add(Constants.Movement.TURN_RIGHT);
            //System.out.println("move forward");
            robot.moveForward();
            movement.add(Constants.Movement.MOVE_FORWARD);


        }

        // if can move forward, move forward
        else if (!hasObstacle(robot.getDirection())) {
            //System.out.println("move forward");
            robot.moveForward();
            movement.add(Constants.Movement.MOVE_FORWARD);
        }
        else{
            System.out.println("turn left....");
            robot.turn(robot.robotLeftDir());
            movement.add(Constants.Movement.TURN_LEFT);
        }

        /*
        // else, turn left
        else if (!hasObstacle(robot.peekRobotLeftDir())){
            //check right for unexplored grid
            System.out.println("turn left....");
            robot.turn(robot.robotLeftDir());
            movement.add(Constants.Movement.TURN_LEFT);
        }
        else{
            //uturn
            System.out.println("uturn");
            robot.uTurn();
        }*/
    }

    /*
    logic
    while (rightWallHuggingNotCompleted) {
        1. senseMap()
        2. rightWallHugging()
        3. if obstacle on right -->take photo  (first 2 grids?)
    }

    island hugging

    while (a* search){
        1. senseMap()
        2. if obstacle on right --> take photo (first 2 grids?)
    }

    if there are still sides of obstacle which camera nvr take pic--> put them into pq --> then a* to the right
    (aka island hugging)
     */
    public Robot imageRecognitionExploration(){
        visibleSurface = new VisibleSurface();
        GridCell nearestUnexploredGrid;
        ArrayList<GridCell> path ;
        ArrayList<Movement> moveInstructions;
        boolean terminate=false;
        boolean start = false;
        boolean takePhoto = false;
        ArrayList<int[]> rightObstacleCoordinates = new ArrayList<>();

        System.out.println("image regconition");

        //init start grid to be explored alr
        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);
        for (int i = 0; i < 3; i++) {
            for (int r = 0; r < 3; r++) {
                map.setExploredForGridCell(i, r, true);
            }
        }
        //init

        robot.initSensor();
        System.out.println("EXPLORATION-FINISH INITIALISE SENSOR");

        while (actual_percentage < goal_percentage && System.currentTimeMillis() <endTime){
            senseMap();

            System.out.println("after sense map");
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++){
                    printGridCell(map.getGridCell(row, col));
                }
                System.out.println();
            }
            System.out.println("robot x:"+ robot.getXCoord() + " ,y:"+robot.getYCoord() + "direction" + robot.getDirection());
            if (robot.getXCoord()==1&&robot.getYCoord()==5&&start||robot.getXCoord()==1&&robot.getYCoord()==1&&start){
                System.out.println("break right wall hugging");
                break;
            }
            start=true;
            System.out.println("percentage covered:" + actual_percentage);
            //check back of robot for unexplored grid

            robot.setMap(map);
            rightObstacleCoordinates = getRightSurfaceCoordinates();
            if (!rightObstacleCoordinates.isEmpty()){
                for (int[] coordinate: rightObstacleCoordinates){
                    if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                        visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                        takePhoto=true;
                    }
                }
                //send command
                if (takePhoto)
                    robot.takePhoto(rightObstacleCoordinates);
            }
            actual_percentage = getActualPerc();
            rightWallHugging();

        }
        rightObstacleCoordinates = getRightSurfaceCoordinates(); //dont use grid cell, use surface instead
        if (!rightObstacleCoordinates.isEmpty()){
            for (int[] coordinate: rightObstacleCoordinates){
                if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                    visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                    takePhoto=true;
                }
            }
            //send command
            if (takePhoto)
                robot.takePhoto(rightObstacleCoordinates);
        }
        actual_percentage = getActualPerc();


        System.out.println("percentage covered:" + actual_percentage);

        PriorityQueue<GridCell> unexploredGrids = getUnexploredGrid();

        int unexploredGridX, unexploredGridY;
        PathFinder pathFinder;
        int robotX, robotY;
        int counter=0;


        //island hugging

        /*
        go to unexplored grid & continue to senseMap
        find out grid is obstacle or what
        then unexploredGrids = getUnexploredGrid();
         */
        while (!unexploredGrids.isEmpty()&&actual_percentage < goal_percentage&& System.currentTimeMillis() < endTime){
            for (GridCell gridCell:unexploredGrids){
                System.out.println(gridCell.getHorCoord() + " "+gridCell.getVerCoord());
            }

            System.out.println("POLLLLLLL");
            nearestUnexploredGrid = unexploredGrids.poll();
            unexploredGridX = nearestUnexploredGrid.getHorCoord();
            unexploredGridY = nearestUnexploredGrid.getVerCoord();
            System.out.println("unexplored grid:" + nearestUnexploredGrid.getHorCoord()+" " +nearestUnexploredGrid.getVerCoord());

            pathFinder = new PathFinder(map); //do singleton or smth
            System.out.println("what happen start:" + robot.getXCoord()+" "+ robot.getYCoord()+" end:"+  nearestUnexploredGrid.getHorCoord()+" "+  nearestUnexploredGrid.getVerCoord());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, unexploredGridY);

            //path = checkPath(path);

            while (path==null){
                System.out.println("wtf is goin on");
                //try all the 4 grid beside if there is path
                path = findPathForSurroundingGrid(unexploredGridX, unexploredGridY);
                if (path!=null)
                    break;

                //clearParent();
                if (unexploredGrids.isEmpty()){
                    //shouldnt happen at all
                    terminate=true;
                    break;
                }
                pathFinder = new PathFinder(map); //do singleton or smth
                System.out.println("get next unexplored grid in list: " + unexploredGrids.peek().getHorCoord() + " "+unexploredGrids.peek().getVerCoord());
                nearestUnexploredGrid = unexploredGrids.poll();
                unexploredGridX = nearestUnexploredGrid.getHorCoord();
                unexploredGridY = nearestUnexploredGrid.getVerCoord();
                System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +nearestUnexploredGrid.getHorCoord()+" "+ nearestUnexploredGrid.getVerCoord());
                path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), nearestUnexploredGrid.getHorCoord(), nearestUnexploredGrid.getVerCoord());
                if (path==null) {
                    path = findPathForSurroundingGrid(unexploredGridX, unexploredGridY);
                }
                if (terminate) //force break and go to start point
                    break;
            }

            clearParent();

            if (path!=null){
                sendRobotInstructionFromPathWithSensorWithImageReg(path, unexploredGridX, unexploredGridY);
                if (!map.getGridCell(unexploredGridY,unexploredGridX).getExplored()){
                    //turn to face the coordinate
                    Direction directionToFace = getDirection(unexploredGridX, unexploredGridY);
                    robot.turn(directionToFace);
                    System.out.println("direction to face ..." + directionToFace);
                    senseMap();
                    if (hasObstacleOnRight()){
                        System.out.println("obstacle on right...calibrate");
                        robot.calibrate();
                    }
                    map.updateMap(robot.getXCoord(),robot.getYCoord());
                    map.displayDirection(robot.getYCoord(),robot.getXCoord(),directionToFace);
                    map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
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
        }

        //print all the visble surface
        for (int y=0; y<20; y++){
            for (int x=0; x<15; x++){
                for (int i=0; i<4; i++){
                    System.out.println(x + " " + y + "direction: " + i+ " "+ visibleSurface.getSurface(x,y,i));
                }
            }
        }
        //a*star to capture image that haven capture
        getAllSurfaces();

        PriorityQueue<GridCell> goToGrids = getGridsImageReg();

        int irGridX, irGridY;
        GridCell irGrid;
        while (!goToGrids.isEmpty()){
            System.out.println("image reg pq..");
            for (GridCell gridCell:goToGrids){
                System.out.println("x:"  +gridCell.getHorCoord() + "y: " + gridCell.getVerCoord());
            }
            System.out.println("POLLLLLLL");
            irGrid = goToGrids.poll();
            irGridX = irGrid.getHorCoord();
            irGridY= irGrid.getVerCoord();
            pathFinder = new PathFinder(map);
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), irGridX, irGridY);
            sendRobotInstructionFromPathNoSensorWithImageReg(path, path.get(path.size()-1));
        }

        //send final mdf string
        robot.setMap(map);

        if(!isSimulated){
            ActualRobot actualRobot = (ActualRobot) robot;
            //send mdf shit
            System.out.println("sending mdf string...");
            actualRobot.sendMdfString();
        }

        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord());
        //if robot not at start point get shortest path to start point & move to start point
        if (robot.getXCoord()!=1||robot.getYCoord()!=1){
            //PathFinder pathFinder;
            pathFinder = new PathFinder(robot.getMap());
            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(),1,1);
            if (path==null) //shouldnt happen at all
                return null;
            System.out.println("path size"+path.size());
            for(GridCell gridCell:path){
                System.out.println(gridCell.getHorCoord()+" "+gridCell.getVerCoord());
            }
           sendRobotInstructionFromPathNoSensor(path);
            clearParent();
        }

        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord() + "direction: "+ robot.getDirection());
        //update robot map
        resetRobotDirection();
        System.out.println("WHERE IS THE ROBOT??? x:" + robot.getXCoord()+ " ,y:" + robot.getYCoord() + "direction: "+ robot.getDirection());
        robot.setMap(map);
        map.updateMap(robot.getXCoord(),robot.getYCoord());
        map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
        //map.displayDirection(robot.getYCoord(),robot.getXCoord(),robot.getDirection());
        //return robot
        return robot;
    }

    public void getAllSurfaces(){
        /*
        true--> taken
        false --> not taken
        null --> no surface
         */

        VisibleSurface allVisibleSurface = new VisibleSurface();
        //get all the surface --> false means there is surface to capture
        for (int y=0; y<Constants.HEIGHT;y++){
            for (int x=0; x<Constants.WIDTH; x++){
                if (map.getGridCell(y,x).getObstacle()){
                    //check the 4 grids ard it
                    if (map.getGridCell(y,x+1)!=null){
                        if (!map.getGridCell(y,x+1).getObstacle()){
                            allVisibleSurface.surfaceCaptureObstacleDirection(x, y, Direction.EAST);
                        }
                    }

                    if (map.getGridCell(y,x-1)!=null){
                        if (!map.getGridCell(y,x-1).getObstacle()){
                            allVisibleSurface.surfaceCaptureObstacleDirection(x, y, Direction.WEST);
                        }
                    }

                    if (map.getGridCell(y+1,x)!=null){
                        if (!map.getGridCell(y+1,x).getObstacle()){
                            allVisibleSurface.surfaceCaptureObstacleDirection(x, y, Direction.NORTH);
                        }
                    }

                    if (map.getGridCell(y+1,x)!=null){
                        if (!map.getGridCell(y+1,x).getObstacle()){
                            allVisibleSurface.surfaceCaptureObstacleDirection(x, y, Direction.SOUTH);
                        }
                    }
                }

            }
        }

        //get those surfaces that are still not capture
        Boolean result;
        for (int y=0; y<Constants.HEIGHT;y++){
            for (int x=0; x<Constants.WIDTH; x++){
                for (int i=0; i<4; i++){
                    result = allVisibleSurface.getSurface(x,y,i);
                    //double check it doesnt account for null value
                    if (allVisibleSurface.getSurface(x,y,i)!=null){
                        if (!allVisibleSurface.getSurface(x,y,i)){
                            if (visibleSurface.getSurface(x,y,i)==null)
                                visibleSurface.setSurfaceFalse(x,y,i);
                        }
                    }

                }
            }
        }

    }


    public PriorityQueue<GridCell> getGridsImageReg(){
        PriorityQueue<GridCell> goToGrids = new PriorityQueue<>(11, (o1, o2) -> Integer.compare(o1.getgCost(), o2.getgCost()));
        System.out.println("get grid image reg...");
        int curX = robot.getXCoord();
        int curY= robot.getYCoord();
        System.out.println("start from: "+curX+" "+curY);
        int cost;
        GridCell gridCell = null;
        Boolean result, gridPresent=false;
        //MapPanel map = map;
        for (int y =0; y<=19;y++){
            for (int x=0; x<=14; x++){
                for (int i=0; i<4; i++){
                    result = visibleSurface.getSurface(x,y,i);
                    if (result!=null && !result){
                        System.out.println("surface.." + x + " " + y +" direction: " + i);
                        //surface not taken
                        //TODO: a* take into consideration of the direction too?
                        switch (i){
                            case 0:
                                if (checkSurroundingGrid(x,y+2)){
                                    gridCell = map.getGridCell(y+2, x);
                                }else{
                                    visibleSurface.setSurfaceNull(x,y,i);
                                }
                                    break;
                            case 1:
                                if (checkSurroundingGrid(x+2,y)){
                                    gridCell = map.getGridCell(y, x+2);
                                }
                                else{
                                    visibleSurface.setSurfaceNull(x,y,i);
                                }
                                    break;
                            case 2:
                                if (checkSurroundingGrid(x,y-2)){
                                    gridCell = map.getGridCell(y-2, x);
                                }
                                else{
                                    visibleSurface.setSurfaceNull(x,y,i);
                                }
                                    break;
                            case 3:
                                if (checkSurroundingGrid(x-2, y)){
                                    gridCell = map.getGridCell(y, x-2);
                                }
                                else{
                                    visibleSurface.setSurfaceNull(x,y,i);
                                }
                                    break;
                        }
                        if (gridCell!=null){
                            for (GridCell gridCellpq: goToGrids){
                                if (gridCellpq.getHorCoord()==gridCell.getHorCoord() && gridCellpq.getVerCoord() == gridCell.getVerCoord()){
                                    gridPresent= true;
                                    break;
                                }
                            }
                            if (!gridPresent){
                                System.out.println("grid... "+ gridCell.getHorCoord() +" " +gridCell.getVerCoord());
                                cost = calculateHeuristicCost(curX,curY,gridCell.getHorCoord(),gridCell.getVerCoord());
                                //update g cost
                                gridCell.setgCost(cost);
                                goToGrids.add(gridCell);
                            }

                        }
                    }
                }
            }

            //remove duplicated coordinates
        }
        System.out.println("FINAL GRID LIST");
        for (int y=0; y<20; y++){
            for (int x=0; x<15; x++){
                for (int i=0; i<4; i++){
                    System.out.println(x + " " + y + "direction: " + i+ " "+ visibleSurface.getSurface(x,y,i));
                }
            }
        }
        return goToGrids;
    }

    public void sendRobotInstructionFromPathNoSensor(ArrayList<GridCell> path){
        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Movement> moveInstructions;
        moveInstructions = pathFinder.getRobotInstructions(path,robot.getDirection(),robot.getXCoord(), robot.getYCoord() );
        ArrayList<int[]> rightObstacleCoordinates = new ArrayList<>();
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


            map.updateMap(robot.getXCoord(),robot.getYCoord());
            map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
        }
    }

    public void sendRobotInstructionFromPathNoSensorWithImageReg(ArrayList<GridCell> path, GridCell lastGridCell){
        PathFinder pathFinder = new PathFinder(map);
        ArrayList<Movement> moveInstructions;
        moveInstructions = pathFinder.getRobotInstructions(path,robot.getDirection(),robot.getXCoord(), robot.getYCoord() );
        ArrayList<int[]> rightObstacleCoordinates = new ArrayList<>();
        boolean takePhoto = false;
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

            rightObstacleCoordinates = getRightSurfaceCoordinates();
            if (!rightObstacleCoordinates.isEmpty()){
                for (int[] coordinate: rightObstacleCoordinates){
                    if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                        visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                        takePhoto=true;
                    }
                }
                //send command
                if (takePhoto)
                    robot.takePhoto(rightObstacleCoordinates);
            }

            map.updateMap(robot.getXCoord(),robot.getYCoord());
            map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
        }
        //robot turn right until .... shld change this

        if (robot.getDirection()!=lastGridCell.getDirection()){
            while (robot.getDirection()!=lastGridCell.getDirection()){
                robot.turn(robot.robotRightDir());

                rightObstacleCoordinates = getRightSurfaceCoordinates();
                if (!rightObstacleCoordinates.isEmpty()){
                    for (int[] coordinate: rightObstacleCoordinates){
                        if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                            visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                            takePhoto=true;
                        }
                    }
                    //send command
                    if (takePhoto)
                        robot.takePhoto(rightObstacleCoordinates);
                }
            }
        }


        /*
        if (!hasObstacleOnRight()){
            //turn 4 times
            for (int i=0; i<4; i++){
                robot.turn(robot.robotRightDir());
                rightObstacleCoordinates = getRightSurfaceCoordinates();
                if (!rightObstacleCoordinates.isEmpty()){
                    for (int[] coordinate: rightObstacleCoordinates){
                        if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                            visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                            takePhoto=true;
                        }
                    }
                    //send command
                    if (takePhoto)
                        robot.takePhoto(rightObstacleCoordinates);
                }
            }

        }*/
    }

    public void sendRobotInstructionFromPathWithSensorWithImageReg(ArrayList<GridCell> path, int unexploredGridX, int unexploredGridY ){
        PathFinder pathFinder;
        ArrayList<int[]> rightObstacleCoordinates = new ArrayList<>();
        pathFinder = new PathFinder(map);
        ArrayList<Movement> moveInstructions;
        boolean takePhoto = false;
        moveInstructions = pathFinder.getRobotInstructions(path, robot.getDirection(),robot.getXCoord(), robot.getYCoord() );

        //System.out.println("^^^^^movement length"+moveInstructions.size());

        for (Movement movement: moveInstructions){
            if (movement.equals(Movement.MOVE_FORWARD)){
                if (hasObstacle(robot.getDirection())){
                    //System.out.println("obstacle in front");
                    //terminate=true;
                    break;
                }
                //System.out.println("^^^^move forward, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                robot.moveForward();
                senseMap();
                if (hasObstacleOnRight()){
                    System.out.println("obstacle on right...calibrate");
                    robot.calibrate();
                }
            }
            else if (movement.equals(Movement.TURN_RIGHT)){
                //System.out.println("^^^^turn right, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                robot.turn(robot.robotRightDir());
                senseMap();
                if (hasObstacleOnRight()){
                    System.out.println("obstacle on right...calibrate");
                    robot.calibrate();
                }

            }
            else{
                //System.out.println("^^^^move left, robot coordinate: " +  robot.getXCoord() +" "+ robot.getYCoord());
                robot.turn(robot.robotLeftDir());
                senseMap();
                if (hasObstacleOnRight()){
                    System.out.println("obstacle on right...calibrate");
                    robot.calibrate();
                }
            }

            //take photo if there is obstacle on right
            rightObstacleCoordinates = getRightSurfaceCoordinates();
            if (!rightObstacleCoordinates.isEmpty()){
                for (int[] coordinate: rightObstacleCoordinates){
                    if (coordinate!=null && coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                        visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                        takePhoto=true;
                    }
                }
                //send command
                if (takePhoto)
                    robot.takePhoto(rightObstacleCoordinates);
            }


            /*
            if (hasAnyObstacleOnRight()){
                //get right coordinates
                rightObstacleCoordinates = getRightObstacleCoordinates();


                for (int[] coordinate: rightObstacleCoordinates){
                    if (coordinate[0]>=0 && coordinate[0]<15 && coordinate[1]>=0&&coordinate[1]<20){
                        visibleSurface.surfaceCapture(coordinate[0], coordinate[1], robot.getDirection());
                        takePhoto=true;
                    }
                }
                //send command
                if (takePhoto)
                    robot.takePhoto(rightObstacleCoordinates);

            }*/

            //System.out.println(movement);
            //System.out.println("robot where u at: "+robot.getXCoord()+ " "+ robot.getYCoord());

            if ( map.getGridCell(unexploredGridY,unexploredGridX).getExplored()) //constantly seeing if the unexplored grid is explored alr in case is a obstacle then the last move cmi
                break;
                /*
                if ((robot.getYCoord()+1==unexploredGridY||robot.getYCoord()-1==unexploredGridY)&&(robot.getXCoord()+1==unexploredGridX||robot.getXCoord()-1==unexploredGridX)) //if any part of the robot is in the grid
                    break;*/

        }
    }

    public ArrayList<GridCell> findPathForSurroundingGrid(int unexploredGridX, int unexploredGridY){
        ArrayList<GridCell> path = null;
        PathFinder pathFinder;
        for (int i = 1; i <= 2; i++) {
            if (unexploredGridX==0){
                for (int index=-2;index<4;index+=2){
                    if (index!=0){
                        pathFinder = new PathFinder(map);
                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 1, unexploredGridY + index);
                    }
                    if (path!=null)
                        break;
                }
                if (path!=null)
                    break;

            }
            else if(unexploredGridX==14){
                System.out.println("CORNER GRID WTD");
                for (int index=-2;index<4;index+=2){
                    if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 12, unexploredGridY);*/
                    }else{
                        pathFinder = new PathFinder(map);
                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), 13, unexploredGridY + index);
                    }
                    if (path!=null)
                        break;
                }
                if (path!=null)
                    break;
            }
            else if(unexploredGridY==0){
                for (int index=-2;index<4;index+=2){
                    if (index==0){
                                   /* pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 2);*/
                    }else{
                        pathFinder = new PathFinder(map);
                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,1);
                    }
                    if (path!=null)
                        break;
                }
                if (path!=null)
                    break;
            }
            else if (unexploredGridY==19){
                for (int index=-2;index<4;index+=2){
                    if (index==0){
                                    /*pathFinder = new PathFinder(map);
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX, 17);*/
                    }else{
                        pathFinder = new PathFinder(map);
                        path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+index,18);
                    }
                    if (path!=null)
                        break;
                }
                if (path!=null)
                    break;
            }

            else {
                for (int y = -1; y < 2; y++) {
                    for (int x = -1; x < 2; x++) {
                        if (x == 0 && y == 0)
                            continue;
                        pathFinder = new PathFinder(map);
                        System.out.println("robot current position: " + robot.getXCoord() + " " + robot.getYCoord() + " grid to go to: " + (unexploredGridX + x) + " " + (unexploredGridY + y));
                        if ((robot.getXCoord() != unexploredGridX + x || robot.getYCoord() != unexploredGridY + y )&& checkSurroundingGrid(unexploredGridX + x, unexploredGridY + y)) {
                            System.out.println("robot current position: " + robot.getXCoord() + " " + robot.getYCoord() + " grid to go to: " + (unexploredGridX + x) + " " + (unexploredGridY + y));
                            path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX + x, unexploredGridY + y);
                        }

                        if (path != null)
                            break;
                    }
                    if (path!=null)
                        break;
                }
                if (path==null){
                    for (int y=-2;y<3; y++){
                        for (int x=-2; x<3;x++){
                            if ((x==2&&y==0)||(x==-2&&y==0)||(x==0&&y==-2)||(x==0&&y==2)){
                                pathFinder = new PathFinder(map);
                                System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                if (robot.getXCoord()!=unexploredGridX+x||robot.getYCoord()!=unexploredGridY+y&&checkSurroundingGrid(unexploredGridX+x, unexploredGridY+y )){
                                    System.out.println("robot current position: " + robot.getXCoord() +" "+robot.getYCoord() +" grid to go to: " +(unexploredGridX+x)+" "+ (unexploredGridY+y));
                                    path = pathFinder.getShortestPath(robot.getXCoord(), robot.getYCoord(), unexploredGridX+x, unexploredGridY+y);
                                }

                                if (path!=null)
                                    break;
                            }
                        }
                        if (path!=null)
                            break;
                    }
                }
            }

        }
        return path;
    }

    public ArrayList<int[]> getRightSurfaceCoordinates(){
        ArrayList<int[]> obstacles = new ArrayList<>();
        boolean noSurface = true;
        int x = robot.getXCoord();
        int y = robot.getYCoord();
        GridCell gridCell;
        switch (robot.getDirection()){
            case WEST:
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y+2+i,x-1);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x-1,y+2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.isEmpty())
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y+2+i,x);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x,y+2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==1)
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y+2+i,x+1);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x+1,y+2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==2)
                    obstacles.add(null);
                /*
                obstacles.add(new int[] {x-1,y+2});
                        obstacles.add(new int[] {x,y+2});
                        obstacles.add(new int[] {x+1,y+2});*/
                break;
            case EAST:
                for (int i=0; i>-IMAGE_RANGE; i--){
                    gridCell = map.getGridCell(y-2+i,x+1);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x+1,y-2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.isEmpty())
                    obstacles.add(null);
                for (int i=0; i>-IMAGE_RANGE; i--){
                    gridCell = map.getGridCell(y-2+i,x);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x,y-2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==1)
                    obstacles.add(null);
                for (int i=0; i>-IMAGE_RANGE; i--){
                    gridCell = map.getGridCell(y-2+i,x-1);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x-1,y-2+i});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==2)
                    obstacles.add(null);
                /*
                obstacles.add(new int[] {x+1,y-2});
                obstacles.add(new int[] {x,y-2});
                obstacles.add(new int[] {x-1,y-2});*/
                break;
            case NORTH:
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y+1,x+2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x+2+i,y+1});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.isEmpty())
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y,x+2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x+2+i,y});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==1)
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y-1,x+2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x+2+i,y-1});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==2)
                    obstacles.add(null);
                /*
                obstacles.add(new int[] {x+2,y+1});
                obstacles.add(new int[] {x+2,y});
                obstacles.add(new int[] {x+2,y-1});*/


                break;
            case SOUTH:
                for (int i=0; i>-IMAGE_RANGE; i--){
                    gridCell = map.getGridCell(y-1,x-2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x-2+i,y-1});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.isEmpty())
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y,x-2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x-2+i,y});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==1)
                    obstacles.add(null);
                for (int i=0; i<IMAGE_RANGE; i++){
                    gridCell = map.getGridCell(y+1,x-2+i);
                    if (gridCell!=null && gridCell.getObstacle()){
                        obstacles.add(new int[] {x-2+i,y+1});
                        noSurface= false;
                        break;
                    }
                }
                if (obstacles.size()==2)
                    obstacles.add(null);

                /*obstacles.add(new int[] {x-2,y-1});
                obstacles.add(new int[] {x-2,y});
                obstacles.add(new int[] {x-2,y+1});*/
                break;
        }

        //no surface then return empty array list
        if (noSurface)
            return new ArrayList<>();

        return obstacles;
    }

    public ArrayList<int[]> getRightObstacleCoordinates(){
        //order --> front, mid, back
        ArrayList<int[]> obstacles = new ArrayList<>();
        int x = robot.getXCoord();
        int y = robot.getYCoord();
        switch (robot.getDirection()){
            case WEST:
                for (int i=0; i<2; i++){
                    if (map.getGridCell(y+2+i,x-1).getObstacle()){
                        obstacles.add(new int[] {x-1,y+2+i});
                        break;
                    }
                }
                for (int i=0; i<2; i++){
                    if (map.getGridCell(y+2+i,x).getObstacle()){
                        obstacles.add(new int[] {x,y+2+i});
                        break;
                    }
                }
                for (int i=0; i<2; i++){
                    if (map.getGridCell(y+2+i,x+1).getObstacle()){
                        obstacles.add(new int[] {x+1,y+2+i});
                        break;
                    }
                }
                /*
                obstacles.add(new int[] {x-1,y+2});
                        obstacles.add(new int[] {x,y+2});
                        obstacles.add(new int[] {x+1,y+2});*/
                        break;
            case EAST:
                obstacles.add(new int[] {x+1,y-2});
                obstacles.add(new int[] {x,y-2});
                obstacles.add(new int[] {x-1,y-2});
                break;
            case NORTH:
                obstacles.add(new int[] {x+2,y+1});
                obstacles.add(new int[] {x+2,y});
                obstacles.add(new int[] {x+2,y-1});


                break;
            case SOUTH:
                obstacles.add(new int[] {x-2,y-1});
                obstacles.add(new int[] {x-2,y});
                obstacles.add(new int[] {x-2,y+1});
                break;
        }
        return obstacles;
    }

    public boolean hasAnyObstacleOnRight(){
        System.out.println("check if hav n obstacle on the right");
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkObstacleRow(true,y,x);
            case EAST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkObstacleRow(true,y,x);
            case SOUTH:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkObstacleRow(false,y,x);
            case NORTH:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkObstacleRow(false,y,x);
        }
        return true;
    }



    public boolean hasObstacleOnFront(){
        System.out.println("check if hav 3 obstacle on the right");
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkObstacleWholeRow(false,y,x);
            case EAST:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkObstacleWholeRow(false,y,x);
            case SOUTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkObstacleWholeRow(true,y,x);
            case NORTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkObstacleWholeRow(true,y,x);
        }
        return true;
    }


    public boolean hasObstacleOnRight(){
        System.out.println("check if hav 3 obstacle on the right");
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkObstacleWholeRow(true,y,x);
            case EAST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkObstacleWholeRow(true,y,x);
            case SOUTH:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkObstacleWholeRow(false,y,x);
            case NORTH:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkObstacleWholeRow(false,y,x);
        }
        return true;
    }


    private boolean hasObstacle(Direction dir){
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


    //return true if whole row is obstacle, false if there is no obstacle in any of the 3 grid
    private boolean checkObstacleWholeRow(boolean xIncrease, int y, int x){
        GridCell gridCell;
        if (xIncrease){
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y,x+i);
                if (gridCell != null && !gridCell.getObstacle()) {
                    System.out.println("check obstacle row: y=" + y +" "+true);
                    return false;
                }
            }
        }
        else{
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y+i,x);
                if (gridCell != null && !gridCell.getObstacle()) {
                    return false;
                }
            }
        }
        System.out.println("check obstacle row false");
        return true;
    }

    private boolean checkObstacleRow(boolean xIncrease, int y, int x){
        GridCell gridCell;
        if (xIncrease){
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y,x+i);
                if (gridCell == null || gridCell.getObstacle()) {
                    System.out.println("check obstacle row: y=" + y +" "+true);
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
        System.out.println("check obstacle row false");
        return false;
    }

    // sense map using sensors and update explored space on map
    private void senseMap() {
        ArrayList<Boolean> sensorResultArrayList;
        Boolean sensorResult;
        Direction direction = robot.getDirection();
        Sensor sensor;
        GridCell gridCell;
        int x, y;

        switch (direction){
            case WEST:
                // LEFT_MIDDLE(3)
                sensor = robot.getIndividualSensor(3);
                sensorResultArrayList = sensor.getSensorInformation();
                x = sensor.getXCoord();
                y = sensor.getYCoord();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    sensorResult =sensorResultArrayList.get(i);
                    System.out.println("sensor corrdinate: " + x + ", "+(y-i)+ "sensor result " + sensorResultArrayList.get(i));
                    if (sensorResult==null)
                        break;

                    //update information from sensor


                    try{
                        if (map.getGridCell(sensor.getYCoord()-i, sensor.getXCoord()).getExplored()){
                            //if explored alr dont change the grid
                            continue;
                        }
                    }catch (NullPointerException e){

                    }

                    map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                    //if no obstacle on the existing, then set ; if not dont update the update
                    try{
                        if (map.getGridCell(sensor.getYCoord()-i, sensor.getXCoord()).getObstacle()){
                            //safety check, ignore all the other sensor values aft obstacle is already detected
                            break;
                        }
                    }catch (NullPointerException e){
                        map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResultArrayList.get(i));
                    }



                    map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResultArrayList.get(i));


                    //update explore


                }
                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x-i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;


                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()-i).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){

                        }

                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);

                        try {
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()-i).getObstacle()){
                                break;
                            }
                        } catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResultArrayList.get(i));
                        }
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResultArrayList.get(i));


                    }
                }
                // RIGHT_Up(5),  right_DOWN(4)
                for (int loc = 4; loc < 6; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + x + ", "+(y+i)+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;


                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }

                        map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);

                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));


                    }
                }
                break;
            case NORTH:
                // LEFT_MIDDLE(3), LEFT_DOWN(4)

                    sensor = robot.getIndividualSensor(3);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x=sensor.getXCoord();
                    y=sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x-i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;


                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()-i).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }

                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, true);
                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()-i).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()-i, sensorResultArrayList.get(i));


                    }

                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + x + ", "+(y+i)+ "sensor result "+ sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;

                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }

                        map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);
                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));
                        }

                        map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));


                    }
                }
                // RIGHT_MIDDLE(5)
                for (int loc = 4; loc < 6; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x+i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i) == null)
                            break;

                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord() + i, true);
                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord() + i, sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord() + i, sensorResultArrayList.get(i));


                    }
                }
                break;
            case EAST:
                // LEFT_MIDDLE(3), LEFT_DOWN(4)
                    sensor = robot.getIndividualSensor(3);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x) + ", "+(y+i)+ "sensor result " + sensorResultArrayList.get(i));
                        //System.out.println("hmm" + i);
                        if (sensorResultArrayList.get(i)==null)
                            break;


                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }

                        map.setExploredForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), true);
                        try{
                            if (map.getGridCell(sensor.getYCoord()+i, sensor.getXCoord()).getObstacle()){
                                continue;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));
                        }

                        map.setObstacleForGridCell(sensor.getYCoord()+i, sensor.getXCoord(), sensorResultArrayList.get(i));


                    }

                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x+i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;


                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }

                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, true);
                        try{
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResultArrayList.get(i));


                    }
                }
                // RIGHT_MIDDLE(5)
                for (int loc = 4; loc < 6; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x) + ", "+(y-i)+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i) == null)
                            break;

                        try{
                            if (map.getGridCell(sensor.getYCoord() - i, sensor.getXCoord()).getExplored()){
                                continue;
                            }
                        } catch (NullPointerException e){
                        }
                        map.setExploredForGridCell(sensor.getYCoord() - i, sensor.getXCoord(), true);
                        try{
                            if (map.getGridCell(sensor.getYCoord() - i, sensor.getXCoord()).getObstacle()){
                                break;
                            }
                        } catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord() - i, sensor.getXCoord(), sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord() - i, sensor.getXCoord(), sensorResultArrayList.get(i));


                    }
                }
                break;
            case SOUTH:
                // LEFT_MIDDLE(3), LEFT_DOWN(4)

                sensor = robot.getIndividualSensor(3);
                sensorResultArrayList = sensor.getSensorInformation();
                x = sensor.getXCoord();
                y = sensor.getYCoord();
                for (int i = 0; i < sensor.getGridDistance(); i++) {
                    System.out.println("sensor corrdinate: " + (x+i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                    if (sensorResultArrayList.get(i)==null)
                        break;


                    try{
                        if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getExplored()){
                            continue;
                        }
                    }catch (NullPointerException e){
                    }
                    map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, true);
                    try{
                        if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord()+i).getObstacle()){
                            break;
                        }
                    }catch (NullPointerException e){
                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResultArrayList.get(i));
                    }


                    map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord()+i, sensorResultArrayList.get(i));


                }

                // UP_LEFT(0), UP_MIDDLE(1), UP_RIGHT(2)
                for (int loc = 0; loc < 3; loc++) {
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    x = sensor.getXCoord();
                    y = sensor.getYCoord();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x) + ", "+(y-i)+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i)==null)
                            break;

                        try{
                            if (map.getGridCell(sensor.getYCoord()-i, sensor.getXCoord()).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }
                        map.setExploredForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), true);
                        try{
                            if (map.getGridCell(sensor.getYCoord()-i, sensor.getXCoord()).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResultArrayList.get(i));
                        }


                        map.setObstacleForGridCell(sensor.getYCoord()-i, sensor.getXCoord(), sensorResultArrayList.get(i));

                    }
                }
                // RIGHT_MIDDLE(5)
                for (int loc = 4; loc < 6; loc++){
                    sensor = robot.getIndividualSensor(loc);
                    sensorResultArrayList = sensor.getSensorInformation();
                    for (int i = 0; i < sensor.getGridDistance(); i++) {
                        System.out.println("sensor corrdinate: " + (x-i) + ", "+y+ "sensor result " + sensorResultArrayList.get(i));
                        if (sensorResultArrayList.get(i) == null)
                            break;

                        try {
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord() - i).getExplored()){
                                continue;
                            }
                        }catch (NullPointerException e){
                        }
                        map.setExploredForGridCell(sensor.getYCoord(), sensor.getXCoord() - i, true);
                        try {
                            if (map.getGridCell(sensor.getYCoord(), sensor.getXCoord() - i).getObstacle()){
                                break;
                            }
                        }catch (NullPointerException e){
                            map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord() - i, sensorResultArrayList.get(i));
                        }

                        map.setObstacleForGridCell(sensor.getYCoord(), sensor.getXCoord() - i, sensorResultArrayList.get(i));


                    }
                }
                break;
        }
        System.out.println("update map-------");
        System.out.println("robot x and y?" + robot.getXCoord()+" "+robot.getYCoord());
        map.updateMap(robot.getXCoord(),robot.getYCoord());
        map.displayDirection(robot.getYCoord(),robot.getXCoord(),direction);
        map.setTravellededForGridCell(robot.getYCoord(),robot.getXCoord(), true);
        boolean isSimulated = robot.getClass().equals(SimulatorRobot.class);

        robot.setMap(map);
        if(!isSimulated){
            //send mdf shit
            if (robot!=null){
                System.out.println("sending mdf string...");
                ((ActualRobot) robot).sendMdfString();
                //delay
                try {
                    // ms timeout
                    Thread.sleep(100); // Customize your refresh time
                } catch (InterruptedException e) {
                }
            }

        }
    }

    //get direction to face the unexplored grid
    public Direction getDirection(int x, int y){
        int xCal = robot.getXCoord()-x;
        int yCal = robot.getYCoord()-y;

        //take into consideration the robot is 3x3
        if (xCal>=2){
            //face north
            return Direction.WEST;
        }
        else if(xCal<=-2){
            //face south
            return Direction.EAST;
        }
        else if (yCal>=2){
            //face east
            return Direction.SOUTH;
        }
        else if (yCal<=-2){
            //face west
            return Direction.NORTH;
        }
        return null;
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
        PriorityQueue<GridCell> unexploredGrid = new PriorityQueue<>(11, (o1, o2) -> Integer.compare(o2.getgCost(), o1.getgCost()));
        System.out.println("unexplored grid....pq");
        int curX = robot.getXCoord();
        int curY= robot.getYCoord();
        System.out.println("start from: "+curX+" "+curY);
        int cost;
        GridCell gridCell;
        //MapPanel map = map;
        for (int y =0; y<=19;y++){
            for (int x=0; x<=14; x++){
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

    //return true if all explored
    public boolean checkBackOfRobotAllExplored(){
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
            case EAST:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
            case SOUTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkExploredRow(true,y,x);
            case NORTH:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkExploredRow(true,y,x);
        }
        return true;
    }

    //return true if all explored
    private boolean checkExploredRow(boolean xIncrease, int y, int x){
        GridCell gridCell;
        if (xIncrease){
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y,x+i);
                if (gridCell==null)
                    continue;
                if (!gridCell.getExplored()) {
                    System.out.println("check explored row: y=" + y +" "+false);
                    return false;
                }
            }
        }
        else{
            for (int i=0;i<3;i++){
                gridCell = map.getGridCell(y+i,x);
                if (gridCell==null)
                    continue;
                if (!gridCell.getExplored()) {
                    return false;
                }
            }
        }
        System.out.println("check explored row true");
        return true;
    }

    //return true if all explored
    public boolean checkLeftOfRobotAllExplored(){
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkExploredRow(true,y,x);
            case EAST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkExploredRow(true,y,x);
            case SOUTH:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
            case NORTH:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
        }
        return true;
    }



    public boolean checkRightOfRobotAllExplored(){
        int x,y;

        switch (robot.getDirection()){
            case WEST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()+2;
                return checkExploredRow(true,y,x);
            case EAST:
                x=robot.getXCoord()-1;
                y=robot.getYCoord()-2;
                return checkExploredRow(true,y,x);
            case SOUTH:
                x=robot.getXCoord()-2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
            case NORTH:
                x=robot.getXCoord()+2;
                y=robot.getYCoord()-1;
                return checkExploredRow(false,y,x);
        }
        return true;
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

    private boolean checkSurroundingGrid(int x, int y){
        //System.out.println("for grid x:" +x +" y:"+y);

        //the boundary unless is waypoint
        if (x<=0||y<=0||y>=19||x>=14){
            return false;
        }

        return (!map.getGridCell(y+1,x+1).getObstacle()&&!map.getGridCell(y-1,x-1).getObstacle()&&!map.getGridCell(y-1,x+1).getObstacle()&&!map.getGridCell(y+1,x-1).getObstacle()
                &&!map.getGridCell(y,x+1).getObstacle()&&!map.getGridCell(y+1,x).getObstacle()&&!map.getGridCell(y-1,x).getObstacle()&&!map.getGridCell(y,x-1).getObstacle());
    }

    public static MapPanel getMap() {
        return map;
    }

    public static void setMap(MapPanel map) {
        Exploration.map = map;
    }



}
