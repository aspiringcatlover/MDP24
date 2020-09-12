package fastest_path;

import main.Constants;
import map.GridCell;
import map.MapPanel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathFinder {


    private MapPanel map;
    private ArrayList<GridCell> closedList = new ArrayList<>();
    private ArrayList<GridCell> pathList = new ArrayList<>();


    public PathFinder(MapPanel map) {
        this.map = map;
    }

    public ArrayList<GridCell> getShortestPath(int xStart, int yStart, int xEnd, int yEnd){
        calculateHeuristicCost(xEnd, yEnd);
        search(xStart, yStart,xEnd,yEnd);
        return pathList;
    }

    /*
	map --> completed explored map, got obstacles

	from the map, calculate the cost of grid n to waypoint
	calculation of the cost of the grid thru heuristic function (problem now is how to find a good heuristic function)

	after calculation, then can perform a*star search grid by grid to get the fastest algorithm
	 */

    private void calculateHeuristicCost(int xEnd, int yEnd){
        //heuristic

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
        PriorityQueue<GridCell> openList = new PriorityQueue<>(11, new Comparator<GridCell>() {

            //Compares 2 Node objects stored in the PriorityQueue and Reorders the Queue according to the object which has the lowest fValue
            @Override
            public int compare(GridCell o1, GridCell o2) {
                return Integer.compare(o1.getfCost(), o2.getfCost());
            }
        });

        //Adds the Starting grid inside the openList
        openList.add(map.getGridCell(yStart, xStart));

        GridCell lowestCostGridCell;
        Constants.Direction currentDirection;

        while (true){
            lowestCostGridCell = openList.poll();

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
                openList = exploreNeighbourGrid(lowestCostGridCell,direction,currentDirection, openList);
            }
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
                                      PriorityQueue<GridCell> openList){
        GridCell childGridCell;
        int xChild, yChild, parentToChildCost, xParent, yParent;

        xParent = parentGridCell.getHorCoord();
        yParent = parentGridCell.getVerCoord();
        //get coordinates of grid to explore
        //note: cardinal direction is the direction based on the x & y axis
        switch (cardinalDirection){
            case DOWN: xChild =xParent;
                        yChild = yParent-1;
                        break;
            case UP: xChild = xParent;
                    yChild = yParent+1;
                    break;
            case LEFT: xChild = xParent-1;
                        yChild = yParent;
                        break;
            case RIGHT: xChild = xParent+1;
                        yChild = yParent;
                        break;
            default:
                throw new IllegalStateException("Unexpected value: " + cardinalDirection);
        }

        //cost from parent to child
        if (cardinalDirection==currentDirection)
            parentToChildCost = 1;
        else
            parentToChildCost = 3; //2 for turning, 1 for moving in front --> here is with the assumption of there is no uturn


        //if grid exist
        if (map.getGridCell(yChild, xChild)!=null)
            childGridCell =  map.getGridCell(yParent,xParent);
        else
            return openList; //?????

        /*
        1. childGrid is not obstacle
        2. childGrid hCost is not negative (negative heuristic --> means it go behind the end point already) BUT KIV, is there -number for heuristic
        3. childGrid is not in openList (not explored before)
        4. childGrid is not in closedList (not expanded before) --> so by right shld alr have the shortest path to it
         */
        if (!childGridCell.getObstacle() && childGridCell.gethCost()>=0 && !openList.contains(childGridCell) && !closedList.contains(childGridCell)){
            //double check
            childGridCell.setgCost(parentGridCell.getgCost()+parentToChildCost);  //g = parent g cost + cost from parent to child
            childGridCell.setfCost(childGridCell.getgCost()+childGridCell.gethCost()); //f = g + h

            childGridCell.setParentGrid(parentGridCell);  //update parent
            openList.add(childGridCell);
            map.setGridCell(yChild,xChild,childGridCell);
        }
        /*
        1. childGrid is not obstacle
        2. childGrid hCost is not negative (negative heuristic --> means it go behind the end point already) BUT KIV, is there -number for heuristic
        3. childGrid is IN openList (EXPLORED BEFORE thru other grid) --> nid need if this parent will have lower cost or wat
        4. childGrid is not in closedList (not expanded before) --> so by right shld alr have the shortest path to it
         */
        else if (!childGridCell.getObstacle() && childGridCell.gethCost()>=0 && openList.contains(childGridCell)  && !closedList.contains(childGridCell)){
            int fCost = parentGridCell.gethCost() + parentToChildCost + childGridCell.gethCost(); //new fCost

            //if cost calculated before is > new cost
            if (childGridCell.getfCost() > fCost){
                openList.remove(childGridCell);

                childGridCell.setfCost(fCost);  //set the lower f cost
                childGridCell.setParentGrid(parentGridCell);  //update new parent

                openList.add(childGridCell);
                map.setGridCell(yChild,xChild,childGridCell);
            }
        }

        return openList;


    }

    private Constants.Direction getCurrentDirection(GridCell gridCell){
        //get parent of current grid cell
        GridCell parentGridCell=gridCell.getParentGrid();
        //calculate

        //move x
        if (parentGridCell.getVerCoord()==gridCell.getVerCoord()){

            if (gridCell.getHorCoord()-parentGridCell.getHorCoord()>0){
                return Constants.Direction.RIGHT;
            }
            else
                return Constants.Direction.LEFT;
        }
        //move y
        else {
            if (gridCell.getVerCoord()-parentGridCell.getVerCoord()>0){
                return Constants.Direction.UP;
            }
            else
                return Constants.Direction.DOWN;
        }
    }


    private void retracePath(GridCell current){
        while (current.getParentGrid()!=null){
            this.pathList.add(0,current);
            current = current.getParentGrid();
        }
        //need to add start? no right
    }


}
