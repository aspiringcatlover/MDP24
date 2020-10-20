package fastestPath;

import main.Constants.*;
import map.GridCell;
import map.MapPanel;


import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {


    private MapPanel map;
    private ArrayList<GridCell> closedList = new ArrayList<>();
    private ArrayList<GridCell> pathList = new ArrayList<>();
    private int fastestPathCost;


    public PathFinder(MapPanel map) {
        this.map = map;
    }

    public ArrayList<GridCell> getShortestPath(int xStart, int yStart, int xEnd, int yEnd, Direction startDirection, Direction endDirection){
        calculateHeuristicCostOfMap(xEnd, yEnd);
        //System.out.println("check hcost: " + map.getGridCell(10,10).gethCost());
        search(xStart, yStart,xEnd,yEnd, startDirection, endDirection);
        //checkPath(xEnd,yEnd);

        return pathList;
    }

    public ArrayList<GridCell> getShortestPathWithWaypoint(int xStart, int yStart, int xEnd, int yEnd, Direction startDirection){

        getShortestPath(xStart,yStart,xEnd,yEnd, startDirection, null);
        if (xEnd!=14||yEnd!=19){
            int size = pathList.size();
            GridCell gridCell = pathList.get(size-1);
            int xWaypoint, yWaypoint;
            xWaypoint = gridCell.getHorCoord();
            yWaypoint = gridCell.getVerCoord();
            ArrayList pathFirstPart = (ArrayList) pathList.clone();
            pathList = new ArrayList<>();

            System.out.println("what is happening here");
            closedList = new ArrayList<>();
            getShortestPath(xWaypoint,yWaypoint,14,19, getCurrentDirection(gridCell), null);
            //pathList.remove(0);
            pathFirstPart.addAll(pathList);
            pathList = pathFirstPart;
        }
        return pathList;
    }

    public ArrayList<Movement> getRobotInstructionWithCalibration(ArrayList<GridCell> path, Direction curDirection, int xStart, int yStart){
        Direction sampleRobot = curDirection; //this robot is just to provide reference of robot current direction, no link to simulator robot or real robot
        int xCoord, yCoord, xCoordNext, yCoordNext;
        xCoord = xStart;
        yCoord = yStart;


        //Robot sampleRobot = new SimulatorRobot(map);
        ArrayList<Movement> robotInstructions = new ArrayList<>();
        //sampleRobot.setDirection(curDirection);
        boolean init=false;
        //System.out.println("path coordinates:" + gridCell.getHorCoord() + gridCell.getVerCoord());
        System.out.println("robot direction" + curDirection);
        for (GridCell gridCell: path){
            System.out.println("getting robot instruction current grid: from " + xCoord +" " + yCoord +" to: " + gridCell.getHorCoord()+" " +gridCell.getVerCoord());
            xCoordNext = gridCell.getHorCoord();
            yCoordNext = gridCell.getVerCoord();
            switch (sampleRobot){
                case NORTH:
                    if (gridCell.getVerCoord()-yCoord==0){
                        if (gridCell.getHorCoord()-xCoord==1){
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                                robotInstructions.add(Movement.RIGHT_CALIBRATION);
                            }
                            sampleRobot = Direction.EAST;
                            System.out.println("right");
                        }
                        else {
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.WEST;
                            System.out.println("left");
                        }
                    }
                    else if (gridCell.getVerCoord()-yCoord==-1){
                        //turn right x 2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        sampleRobot = Direction.SOUTH;
                        System.out.println("rightx2");
                    }

                    robotInstructions.add(Movement.MOVE_FORWARD);
                    if (hasObstacleOnFront(xCoordNext,yCoordNext,curDirection)){
                        robotInstructions.add(Movement.FRONT_CALIBRATION);
                    }
                    System.out.println("forward");
                    break;
                case SOUTH:
                    if (gridCell.getVerCoord()-yCoord==0){
                        if (gridCell.getHorCoord()-xCoord==1){
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.EAST;
                            System.out.println("left");
                        }
                        else{
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                                robotInstructions.add(Movement.RIGHT_CALIBRATION);
                            }
                            sampleRobot=Direction.WEST;
                            System.out.println("right");
                        }
                    }
                    else if (gridCell.getVerCoord()-yCoord==1){
                        //move right*2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        sampleRobot = Direction.NORTH;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    if (hasObstacleOnFront(xCoordNext,yCoordNext,curDirection)){
                        robotInstructions.add(Movement.FRONT_CALIBRATION);
                    }
                    System.out.println("forward");
                    break;
                case EAST:
                    if (gridCell.getVerCoord()-yCoord!=0){
                        if (gridCell.getVerCoord()-yCoord==1){
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.NORTH;
                            System.out.println("left");
                        }
                        else{
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                                robotInstructions.add(Movement.RIGHT_CALIBRATION);
                            }
                            sampleRobot = Direction.SOUTH;
                            System.out.println("right");
                        }
                    }
                    else if (gridCell.getHorCoord()-xCoord==-1){
                        //turn right *2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        sampleRobot=Direction.WEST;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    if (hasObstacleOnFront(xCoordNext,yCoordNext,curDirection)){
                        robotInstructions.add(Movement.FRONT_CALIBRATION);
                    }
                    System.out.println("forward");
                    break;
                case WEST:
                    if (gridCell.getVerCoord()-yCoord!=0){
                        if (gridCell.getVerCoord()-yCoord==1){
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                                robotInstructions.add(Movement.RIGHT_CALIBRATION);
                            }
                            sampleRobot = Direction.NORTH;
                            System.out.println("right");
                        }
                        else{
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.SOUTH;
                            System.out.println("left");
                        }
                    }
                    else if (gridCell.getHorCoord()-xCoord==1){
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        robotInstructions.add(Movement.TURN_RIGHT);
                        if (hasObstacleOnRight(xCoordNext,yCoordNext,curDirection)){
                            robotInstructions.add(Movement.RIGHT_CALIBRATION);
                        }
                        sampleRobot=Direction.EAST;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    if (hasObstacleOnFront(xCoordNext,yCoordNext,curDirection)){
                        robotInstructions.add(Movement.FRONT_CALIBRATION);
                    }
                    System.out.println("forward");
                    break;
            }
            xCoord = gridCell.getHorCoord();
            yCoord = gridCell.getVerCoord();
        }

        return robotInstructions;
    }


    public ArrayList<Movement> getRobotInstructions(ArrayList<GridCell> path, Direction curDirection, int xStart, int yStart){
        Direction sampleRobot = curDirection; //this robot is just to provide reference of robot current direction, no link to simulator robot or real robot
        int xCoord, yCoord;
        xCoord = xStart;
        yCoord = yStart;
        //Robot sampleRobot = new SimulatorRobot(map);
        ArrayList<Movement> robotInstructions = new ArrayList<>();
        //sampleRobot.setDirection(curDirection);
        boolean init=false;
        //System.out.println("path coordinates:" + gridCell.getHorCoord() + gridCell.getVerCoord());
        System.out.println("robot direction" + curDirection);
        for (GridCell gridCell: path){
            System.out.println("getting robot instruction current grid: from " + xCoord +" " + yCoord +" to: " + gridCell.getHorCoord()+" " +gridCell.getVerCoord());
            switch (sampleRobot){
                case NORTH:
                    if (gridCell.getVerCoord()-yCoord==0){
                        if (gridCell.getHorCoord()-xCoord==1){
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            sampleRobot = Direction.EAST;
                            System.out.println("right");
                        }
                        else {
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.WEST;
                            System.out.println("left");
                        }
                    }
                    else if (gridCell.getVerCoord()-yCoord==-1){
                        //turn right x 2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        robotInstructions.add(Movement.TURN_RIGHT);
                        sampleRobot = Direction.SOUTH;
                        System.out.println("rightx2");
                    }

                    robotInstructions.add(Movement.MOVE_FORWARD);
                    System.out.println("forward");
                    break;
                case SOUTH:
                    if (gridCell.getVerCoord()-yCoord==0){
                        if (gridCell.getHorCoord()-xCoord==1){
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.EAST;
                            System.out.println("left");
                        }
                        else{
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            sampleRobot=Direction.WEST;
                            System.out.println("right");
                        }
                    }
                    else if (gridCell.getVerCoord()-yCoord==1){
                        //move right*2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        robotInstructions.add(Movement.TURN_RIGHT);
                        sampleRobot = Direction.NORTH;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    System.out.println("forward");
                    break;
                case EAST:
                    if (gridCell.getVerCoord()-yCoord!=0){
                        if (gridCell.getVerCoord()-yCoord==1){
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.NORTH;
                            System.out.println("left");
                        }
                        else{
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            sampleRobot = Direction.SOUTH;
                            System.out.println("right");
                        }
                    }
                    else if (gridCell.getHorCoord()-xCoord==-1){
                        //turn right *2
                        robotInstructions.add(Movement.TURN_RIGHT);
                        robotInstructions.add(Movement.TURN_RIGHT);
                        sampleRobot=Direction.WEST;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    System.out.println("forward");
                    break;
                case WEST:
                    if (gridCell.getVerCoord()-yCoord!=0){
                        if (gridCell.getVerCoord()-yCoord==1){
                            //turn right
                            robotInstructions.add(Movement.TURN_RIGHT);
                            sampleRobot = Direction.NORTH;
                            System.out.println("right");
                        }
                        else{
                            //turn left
                            robotInstructions.add(Movement.TURN_LEFT);
                            sampleRobot = Direction.SOUTH;
                            System.out.println("left");
                        }
                    }
                    else if (gridCell.getHorCoord()-xCoord==1){
                        robotInstructions.add(Movement.TURN_RIGHT);
                        robotInstructions.add(Movement.TURN_RIGHT);
                        sampleRobot=Direction.EAST;
                        System.out.println("rightx2");
                    }
                    robotInstructions.add(Movement.MOVE_FORWARD);
                    System.out.println("forward");
                    break;
            }
            xCoord = gridCell.getHorCoord();
            yCoord = gridCell.getVerCoord();
        }

        return robotInstructions;
    }


    /*
	map --> completed explored map, got obstacles

	from the map, calculate the cost of grid n to waypoint
	calculation of the cost of the grid thru heuristic function (problem now is how to find a good heuristic function)

	after calculation, then can perform a*star search grid by grid to get the fastest algorithm
	 */

    private int calculateHeuristicCost(int xNow, int yNow, int xEnd, int yEnd){
        //manhattan heuristic
        return (Math.abs(xNow-xEnd) + Math.abs(yNow-yEnd));
}

    private void calculateHeuristicCostOfMap(int xEnd, int yEnd){
        //System.out.println("height" + map.getX()+"width"+map.getWidth());
        //check the get height get width is it correct
        for (int y=0; y<20; y++){
            for (int x=0; x<15; x++){
                map.getGridCell(y,x).sethCost(calculateHeuristicCost(x,y,xEnd, yEnd));
            }
        }
    }

    /*
    private void calculateHeuristicCostOfMap(int xStart, int yStart, int xEnd, int yEnd){
        //System.out.println("height" + map.getX()+"width"+map.getWidth());
        //check the get height get width is it correct
        for (int y=0; y<20; y++){
            for (int x=0; x<15; x++){
                map.getGridCell(y,x).sethCost(calculateHeuristicCost(x,y,xEnd, yEnd));
            }
        }
    }*/

    /**
     * after heuristic cost is calculated, then a* search will be performed
     */
    private void search(int xStart, int yStart, int xEnd, int yEnd, Direction startDirection, Direction endDirection) {
        int xTemp, yTemp;
        boolean strict;
        strict = false; //this should be param, to be added to make sure tat the actual a* search can find path
        /*
        At each iteration, we will:
        Select the node from our open set that has the lowest estimated total score
        Remove this node from the open set
        Add to the open set all of the nodes that we can reach from it
         */

        /*
        cost
        turn left or right --> 2+1 (check again, how much time is needed)
        straight --> 1

        TODO: put this into constants (easy to make modification to the cost)
         */

        //Creation of a PriorityQueue and the declaration of the Comparator
        //Compares 2 Node objects stored in the PriorityQueue and Reorders the Queue according to the object which has the lowest fValue
        PriorityQueue<GridCell> openList = new PriorityQueue<>(11, (o1, o2) -> Integer.compare(o1.getfCost(), o2.getfCost()));

        boolean endpointCanAccess = checkEndpointCanAccess(xEnd, yEnd);

        //Adds the Starting grid inside the openList
        GridCell startGrid = map.getGridCell(yStart, xStart);
        startGrid.setDirection(startDirection);
        startGrid.setgCost(0);
        openList.add(startGrid);

        GridCell lowestCostGridCell;
        Direction currentDirection;
        lowestCostGridCell = openList.poll();
        System.out.println("lowest cost grid cell" + lowestCostGridCell.getHorCoord() + " "+lowestCostGridCell.getVerCoord());

        //check if map is fully explored.
        boolean mapExplored=false;
        if (map.getActualPerc()==100)
            mapExplored=true;


        int debugCounter=1;
        while (true){


            if (lowestCostGridCell==null)
                break;

            if (lowestCostGridCell.getHorCoord()==xEnd && lowestCostGridCell.getVerCoord()==yEnd){
                closedList.add(lowestCostGridCell);
                break;
            }

            if (!endpointCanAccess){


                if (xEnd==14&&yEnd==19){
                    if (map.getGridCell(18,13).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }

                    if (lowestCostGridCell.getHorCoord()==xEnd-1 && lowestCostGridCell.getVerCoord()==yEnd-1){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if(xEnd==0&&yEnd==19){
                    if (map.getGridCell(18,1).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd+1 && lowestCostGridCell.getVerCoord()==yEnd-1){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if(xEnd==14&&yEnd==0){
                    if (map.getGridCell(13,1).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd-1 && lowestCostGridCell.getVerCoord()==yEnd+1){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if (xEnd==14){
                    if (map.getGridCell(yEnd,13).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd-1 && lowestCostGridCell.getVerCoord()==yEnd){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if (yEnd==19){
                    if (map.getGridCell(18,xEnd).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd && lowestCostGridCell.getVerCoord()==yEnd-1){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if (xEnd==0){
                    if (map.getGridCell(yEnd,1).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd+1 && lowestCostGridCell.getVerCoord()==yEnd){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
                else if (yEnd==0){
                    if (map.getGridCell(1,xEnd).getObstacle()){
                        System.out.println("no path");
                        this.pathList=null;
                        return;
                    }
                    if (lowestCostGridCell.getHorCoord()==xEnd && lowestCostGridCell.getVerCoord()==yEnd+1){
                        closedList.add(lowestCostGridCell);
                        break;
                    }
                }
            }

            closedList.add(lowestCostGridCell);

            currentDirection = getCurrentDirection(lowestCostGridCell);
            for (Direction direction: Direction.values()){
                //find current direction
                openList = exploreNeighbourGrid(lowestCostGridCell,direction,currentDirection, openList, xEnd, yEnd, mapExplored);
            }


            /*
            System.out.println("check");

            System.out.println("COUNT " + debugCounter+"------------------------------------------------------------");
            for (GridCell gridCell: openList){
                System.out.println("x:"+gridCell.getHorCoord()+" y:" +gridCell.getVerCoord()+", fcost"+gridCell.getfCost()+", gcost"+gridCell.getgCost()+", hcost"+gridCell.gethCost());
            }
            debugCounter++;*/

            //System.out.println("openlist" + Arrays.toString(openList.toArray()));
            lowestCostGridCell = openList.poll();
        }

        System.out.println("yohz");
        //last grid cell in closed list is end point
        GridCell endPoint=closedList.get(closedList.size() - 1);

        if (endpointCanAccess){
            if (endPoint.getHorCoord()!=xEnd||endPoint.getVerCoord()!=yEnd){
                System.out.println("no path");
                this.pathList=null;
                fastestPathCost=0;
                return;
            }
        }
        else{

        }




        retracePath(endPoint);
        clearParent();
        System.out.println(endPoint.getHorCoord());

        ArrayList<Movement> movementsToFaceDirection;
        Direction curDirection =  getCurrentDirection(endPoint);
        int cost = endPoint.getfCost();
        //add in cost to face end direction
        if (endDirection!=null && curDirection!=endDirection){
            movementsToFaceDirection = movementForRobotToFaceDirection(xEnd, yEnd, curDirection, endDirection );
            for (Movement movement: movementsToFaceDirection){
                cost += 4; //4 for each turning
            }
        }
        fastestPathCost = cost;

        /*
        while (endNode.parent != null) {
            Node currentNode = endNode;
            pathList.add(currentNode);
            endNode = endNode.parent;
        }
         */
    }

    public ArrayList<Movement> movementForRobotToFaceDirection(int x, int y, Direction curDirection, Direction directionToFace){
        int bearing;
        ArrayList<Movement> movements = new ArrayList<>();
        if (curDirection==directionToFace)
            return null;

        bearing = curDirection.bearing - directionToFace.bearing;

        if (bearing>180){
            bearing = bearing-360;
        }
        else if (bearing<-180){
            bearing = 360+bearing;
        }

        while (bearing!=0){
            //positive turn left, negative turn right
            if (bearing<0){
                movements.add(Movement.TURN_RIGHT);
                bearing+= 90;
            }
            else {
                movements.add(Movement.TURN_LEFT);
                bearing-=90;
            }
        }
        return movements;
    }

    /**
     * add neighbouring grid to open list
     * @param parentGridCell
     * @param cardinalDirection
     * @param currentDirection
     * @param openList
     * @return
     */
    private PriorityQueue<GridCell> exploreNeighbourGrid(GridCell parentGridCell, Direction cardinalDirection, Direction currentDirection,
                                                         PriorityQueue<GridCell> openList, int xEnd, int yEnd, boolean mapComplete){
        GridCell childGridCell;
        int xChild, yChild, parentToChildCost, xParent, yParent;

        xParent = parentGridCell.getHorCoord();
        yParent = parentGridCell.getVerCoord();
        //get coordinates of grid to explore
        //note: cardinal direction is the direction based on the x & y axis
        switch (cardinalDirection){
            case SOUTH: xChild =xParent;
                        yChild = yParent-1;
                        break;
            case NORTH: xChild = xParent;
                    yChild = yParent+1;
                    break;
            case WEST: xChild = xParent-1;
                        yChild = yParent;
                        break;
            case EAST: xChild = xParent+1;
                        yChild = yParent;
                        break;
            default:
                throw new IllegalStateException("Unexpected value: " + cardinalDirection);
        }

        //System.out.println("ychild: " + yChild +"xchild: "+xChild);
        //if grid exist
        childGridCell =  map.getGridCell(yChild,xChild);
        if (mapComplete){
            if (childGridCell==null || childGridCell.getObstacle() || !checkSurroundingGrid(xChild,yChild, xEnd, yEnd))
                return openList; //no changes to openlist
        }
        else{
            //to find fastest path for exploration
            if (childGridCell==null )
                return openList;

            if ( childGridCell.getObstacle() || !checkSurroundingGrid(xChild,yChild, xEnd, yEnd)){
                    //childgridcell not explored and is not the end target
                    if ((!childGridCell.getExplored()&&childGridCell.getHorCoord()!=xEnd&&childGridCell.getVerCoord()!=yEnd))
                    {
                        //System.out.println("child grid cell that are unexplored and robot cannot go thru"+childGridCell.getHorCoord() +" "+childGridCell.getVerCoord());
                        //check if the surrounding cells explored or not
                        return openList; //no changes to openlist
                    }
                    //childgrid cell is the end target, check surroundgrid to see if robot can pass through
                    else if (!childGridCell.getExplored()&&childGridCell.getHorCoord()==xEnd&&childGridCell.getVerCoord()==yEnd){

                         if (!checkSurroundingGridUnexploredMap(xChild,yChild, xEnd, yEnd)){
                             return openList;
                         }
                    }
                    else{
                        return openList;
                    }
            }else if (!childGridCell.getExplored())
                return openList;
            else if  (!checkSurroundingGridUnexploredMap(xChild,yChild, xEnd, yEnd)){
                return openList;
            }
        }


        //cost from parent to child
        if (cardinalDirection==currentDirection)
            parentToChildCost = 1;
        else
            parentToChildCost = 4; //2 for turning, 1 for moving in front --> here is with the assumption of there is no uturn, tried 3 will have some issue


        /*
        1. childGrid is not obstacle (settle above)
        2. childGrid hCost is not negative (negative heuristic --> means it go behind the end point already) BUT KIV, is there -number for heuristic
        3. childGrid is not in openList (not explored before)
        4. childGrid is not in closedList (not expanded before) --> so by right shld alr have the shortest path to it
         */
        if (childGridCell.gethCost()>=0 && !openList.contains(childGridCell) && !closedList.contains(childGridCell)){
            //double check
            childGridCell.setgCost(parentGridCell.getgCost()+parentToChildCost);  //g = parent g cost + cost from parent to child
            childGridCell.setfCost(childGridCell.getgCost()+childGridCell.gethCost()); //f = g + h

            childGridCell.setParentGrid(parentGridCell);  //update parent
            openList.add(childGridCell);
            map.setGridCell(yChild,xChild,childGridCell);
        }
        /*
        1. childGrid is not obstacle (Settle above)
        2. childGrid hCost is not negative (negative heuristic --> means it go behind the end point already) BUT KIV, is there -number for heuristic
        3. childGrid is IN openList (EXPLORED BEFORE thru other grid) --> nid need if this parent will have lower cost or wat
        4. childGrid is not in closedList (not expanded before) --> so by right shld alr have the shortest path to it
         */
        else if ( childGridCell.gethCost()>=0 && openList.contains(childGridCell)  && !closedList.contains(childGridCell)){
            int fCost = parentGridCell.gethCost() + parentToChildCost + childGridCell.gethCost(); //new fCost

            //if cost calculated before is > new cost
            if (childGridCell.getgCost() > parentGridCell.getgCost()+parentToChildCost){
                openList.remove(childGridCell);

                childGridCell.setgCost(parentGridCell.getgCost()+parentToChildCost);
                childGridCell.setfCost(fCost);  //set the lower f cost
                childGridCell.setParentGrid(parentGridCell);  //update new parent

                openList.add(childGridCell);
                map.setGridCell(yChild,xChild,childGridCell);
            }
        }


        else if (openList.contains(childGridCell)  && closedList.contains(childGridCell)){
            int fCost = parentGridCell.gethCost() + parentToChildCost + childGridCell.gethCost();
            if (childGridCell.getfCost()>fCost){
                closedList.remove(childGridCell);

                childGridCell.setfCost(fCost);  //set the lower f cost
                childGridCell.setParentGrid(parentGridCell);  //update new parent

                openList.add(childGridCell);
                map.setGridCell(yChild,xChild,childGridCell);

            }
        }

        return openList;


    }

   public Direction getCurrentDirection(GridCell gridCell){
        //get parent of current grid cell
        GridCell parentGridCell=gridCell.getParentGrid();
        if (parentGridCell==null){
            //initial direction stored in grid cell>
            return gridCell.getDirection();
        }

        //calculate

        //move x
        if (parentGridCell.getVerCoord()==gridCell.getVerCoord()){

            if (gridCell.getHorCoord()-parentGridCell.getHorCoord()>0){
                return Direction.EAST;
            }
            else
                return Direction.WEST;
        }
        //move y
        else {
            if (gridCell.getVerCoord()-parentGridCell.getVerCoord()>0){
                return Direction.NORTH;
            }
            else
                return Direction.SOUTH;
        }
    }


    private void retracePath(GridCell current){
        while (current.getParentGrid()!=null){
            //System.out.println("retrace");
            //System.out.println(current.getHorCoord()+ " "+current.getVerCoord());
            this.pathList.add(0,current);
            current = current.getParentGrid();
        }
        //need to add start? no right
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

    /**
     *
     * @param x
     * @param y
     * @return return true if surrounding grid allows robot to pass through, false if surrounding grid is obstacle
     */
    private boolean checkSurroundingGrid(int x, int y, int xEnd, int yEnd){
        //System.out.println("for grid x:" +x +" y:"+y);

        //the boundary unless is waypoint
        if (x==xEnd&&y==yEnd){
            System.out.println("CHECK SURROUNDING GRID END POINT");

            return true;
        }



        if (x==0||y==0||y==19||x==14){
            return xEnd == x && yEnd == y; //if coordinate is waypoint then its fine --> assuming that it just need to pass through, so means got extra space
        }

        return (!map.getGridCell(y+1,x+1).getObstacle()&&!map.getGridCell(y-1,x-1).getObstacle()&&!map.getGridCell(y-1,x+1).getObstacle()&&!map.getGridCell(y+1,x-1).getObstacle()
                &&!map.getGridCell(y,x+1).getObstacle()&&!map.getGridCell(y+1,x).getObstacle()&&!map.getGridCell(y-1,x).getObstacle()&&!map.getGridCell(y,x-1).getObstacle());
    }


    private boolean checkSurroundingGridUnexploredMap(int x, int y, int xEnd, int yEnd){
        //System.out.println("for grid x:" +x +" y:"+y);

        //the boundary unless is waypoint
        if (x==xEnd&&y==yEnd){
            System.out.println("CHECK SURROUNDING GRID END POINT");

            return true;
        }



        if (x==0||y==0||y==19||x==14){
            return xEnd == x && yEnd == y; //if coordinate is waypoint then its fine --> assuming that it just need to pass through, so means got extra space
        }


        return (!map.getGridCell(y+1,x+1).getObstacle()&&!map.getGridCell(y-1,x-1).getObstacle()&&!map.getGridCell(y-1,x+1).getObstacle()&&!map.getGridCell(y+1,x-1).getObstacle()
                &&!map.getGridCell(y,x+1).getObstacle()&&!map.getGridCell(y+1,x).getObstacle()&&!map.getGridCell(y-1,x).getObstacle()&&!map.getGridCell(y,x-1).getObstacle()
        &&map.getGridCell(y+1,x+1).getExplored()&&map.getGridCell(y-1,x-1).getExplored()&&map.getGridCell(y-1,x+1).getExplored()&&map.getGridCell(y+1,x-1).getExplored()
                &&map.getGridCell(y,x+1).getExplored()&&map.getGridCell(y+1,x).getExplored()&&map.getGridCell(y-1,x).getExplored()&&map.getGridCell(y,x-1).getExplored());
    }

    private boolean checkPath(int xEnd, int yEnd){
        //if end is at the corners (x=0/14 or y=0/19) --> then remove last grid
        int length;
        if (xEnd==0 || xEnd==14||yEnd==0||yEnd==19){
            length=pathList.size();
            pathList.remove(length-1);
            return true;
        }
        return false;
    }

    /**
     * check if end point is accesible by mid of robot
     * @param xEnd
     * @param yEnd
     * @return true if can access, false is cannot
     */
    public boolean checkEndpointCanAccess(int xEnd, int yEnd){
        if (xEnd==0 || xEnd==14||yEnd==0||yEnd==19)
            return false;
        return true;
    }

    public void updateMap(MapPanel map){
        this.map = map;
    }


    public boolean hasObstacleOnFront(int xRobot, int yRobot, Direction direction){
        System.out.println("check if hav 3 obstacle on the right");
        int x,y;

        switch (direction){
            case WEST:
                x=xRobot-2;
                y=yRobot-1;
                return checkObstacleWholeRow(false,y,x);
            case EAST:
                x=xRobot+2;
                y=yRobot-1;
                return checkObstacleWholeRow(false,y,x);
            case SOUTH:
                x=xRobot-1;
                y=yRobot-2;
                return checkObstacleWholeRow(true,y,x);
            case NORTH:
                x=xRobot-1;
                y=yRobot+2;
                return checkObstacleWholeRow(true,y,x);
        }
        return true;
    }


    public boolean hasObstacleOnRight(int xRobot, int yRobot, Direction direction){
        System.out.println("check if hav 3 obstacle on the right");
        int x,y;

        switch (direction){
            case WEST:
                x=xRobot-1;
                y=yRobot+2;
                return checkObstacleWholeRow(true,y,x);
            case EAST:
                x=xRobot-1;
                y=yRobot-2;
                return checkObstacleWholeRow(true,y,x);
            case SOUTH:
                x=xRobot-2;
                y=yRobot-1;
                return checkObstacleWholeRow(false,y,x);
            case NORTH:
                x=xRobot+2;
                y=yRobot-1;
                return checkObstacleWholeRow(false,y,x);
        }
        return true;
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

    public int getFastestPathCost() {
        return fastestPathCost;
    }

    public void setFastestPathCost(int fastestPathCost) {
        this.fastestPathCost = fastestPathCost;
    }
}
