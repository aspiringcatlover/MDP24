package fastest_path;

import main.Constants;
import map.GridCell;
import map.MapPanel;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {


    private MapPanel map;
    private ArrayList<GridCell> closedList = new ArrayList<>();
    private ArrayList<GridCell> pathList = new ArrayList<>();


    public PathFinder(MapPanel map) {
        this.map = map;
    }

    public ArrayList<GridCell> getShortestPath(int xStart, int yStart, int xEnd, int yEnd){
        calculateHeuristicCostOfMap(xEnd, yEnd);
        //System.out.println("check hcost: " + map.getGridCell(10,10).gethCost());
        search(xStart, yStart,xEnd,yEnd);
        return pathList;
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

    /**
     * after heuristic cost is calculated, then a* search will be performed
     */
    private void search(int xStart, int yStart, int xEnd, int yEnd) {

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



        //Adds the Starting grid inside the openList
        GridCell startGrid = map.getGridCell(yStart, xStart);
        startGrid.setgCost(0);
        openList.add(startGrid);

        GridCell lowestCostGridCell;
        Constants.Direction currentDirection;
        lowestCostGridCell = openList.poll();

        int debugCounter=1;
        while (true){


            if (lowestCostGridCell==null)
                break;

            if (lowestCostGridCell.getHorCoord()==xEnd && lowestCostGridCell.getVerCoord()==yEnd){
                closedList.add(lowestCostGridCell);
                break;
            }

            closedList.add(lowestCostGridCell);

            currentDirection = getCurrentDirection(lowestCostGridCell);
            for (Constants.Direction direction: Constants.Direction.values()){
                //find current direction
                openList = exploreNeighbourGrid(lowestCostGridCell,direction,currentDirection, openList, xEnd, yEnd);
            }

            /*
            System.out.println("COUNT " + debugCounter+"------------------------------------------------------------");
            for (GridCell gridCell: openList){
                System.out.println("x:"+gridCell.getHorCoord()+" y:" +gridCell.getVerCoord()+", fcost"+gridCell.getfCost()+", gcost"+gridCell.getgCost()+", hcost"+gridCell.gethCost());
            }
            debugCounter++;*/

            //System.out.println("openlist" + Arrays.toString(openList.toArray()));
            lowestCostGridCell = openList.poll();
        }

        //last grid cell in closed list is end point
        GridCell endPoint=closedList.get(closedList.size() - 1);

        retracePath(endPoint);

        /*
        while (endNode.parent != null) {
            Node currentNode = endNode;
            pathList.add(currentNode);
            endNode = endNode.parent;
        }
         */
    }

    /**
     * add neighbouring grid to open list
     * @param parentGridCell
     * @param cardinalDirection
     * @param currentDirection
     * @param openList
     * @return
     */
    private PriorityQueue<GridCell> exploreNeighbourGrid(GridCell parentGridCell, Constants.Direction cardinalDirection, Constants.Direction currentDirection,
                                                         PriorityQueue<GridCell> openList, int xEnd, int yEnd){
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
        if (childGridCell==null || childGridCell.getObstacle() || !checkSurroundingGrid(xChild,yChild, xEnd, yEnd))
            return openList; //no changes to openlist

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

    Constants.Direction getCurrentDirection(GridCell gridCell){
        //get parent of current grid cell
        GridCell parentGridCell=gridCell.getParentGrid();
        if (parentGridCell==null){
            return Constants.Direction.NORTH;
        }

        //calculate

        //move x
        if (parentGridCell.getVerCoord()==gridCell.getVerCoord()){

            if (gridCell.getHorCoord()-parentGridCell.getHorCoord()>0){
                return Constants.Direction.EAST;
            }
            else
                return Constants.Direction.WEST;
        }
        //move y
        else {
            if (gridCell.getVerCoord()-parentGridCell.getVerCoord()>0){
                return Constants.Direction.NORTH;
            }
            else
                return Constants.Direction.SOUTH;
        }
    }


    private void retracePath(GridCell current){
        while (current.getParentGrid()!=null){
            this.pathList.add(0,current);
            current = current.getParentGrid();
        }
        //need to add start? no right
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
        if (x==0||y==0||y==19||x==14){
            return xEnd == x || yEnd == y; //if coordinate is waypoint then its fine --> assuming that it just need to pass through, so means got extra space
        }

        /*
        System.out.println(!map.getGridCell(y+1,x+1).getObstacle());
        System.out.println(!map.getGridCell(y-1,x-1).getObstacle());
        System.out.println(!map.getGridCell(y-1,x+1).getObstacle());
        System.out.println(!map.getGridCell(y+1,x-1).getObstacle());
        System.out.println(!map.getGridCell(y,x+1).getObstacle());
        System.out.println(!map.getGridCell(y+1,x).getObstacle());
        System.out.println(!map.getGridCell(y-1,x).getObstacle());
        System.out.println(!map.getGridCell(y,x-1).getObstacle());*/

        return (!map.getGridCell(y+1,x+1).getObstacle()&&!map.getGridCell(y-1,x-1).getObstacle()&&!map.getGridCell(y-1,x+1).getObstacle()&&!map.getGridCell(y+1,x-1).getObstacle()
                &&!map.getGridCell(y,x+1).getObstacle()&&!map.getGridCell(y+1,x).getObstacle()&&!map.getGridCell(y-1,x).getObstacle()&&!map.getGridCell(y,x-1).getObstacle());
    }

}
