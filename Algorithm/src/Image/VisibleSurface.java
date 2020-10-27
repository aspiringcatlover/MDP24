package Image;

import main.Constants;

public class VisibleSurface {
    /*
    direction
    0 --> north
    1 --> east
    2 --> south
    3 --> west
     */

    //use 3d array [y][x][direction]
    Boolean[][][] surfaces = new Boolean[Constants.HEIGHT][Constants.WIDTH][4];

    public VisibleSurface(){

        //init all to null
        for (int y=0; y<Constants.HEIGHT; y++){
            for (int x=0; x<Constants.WIDTH; x++){
                for (int i=0; i<4;i++){
                    surfaces[y][x][i] = null;
                }
            }
        }
    }

    public void surfaceCapture(int x, int y, Constants.Direction robotDirection){
        Constants.Direction surfaceDirection;
        int intSurfaceDirection=0;
        switch (robotDirection){
            case WEST: surfaceDirection = Constants.Direction.SOUTH;
                        intSurfaceDirection = 2;
                        break;
            case EAST: surfaceDirection = Constants.Direction.NORTH;
                        intSurfaceDirection = 0;
                        break;
            case NORTH: surfaceDirection = Constants.Direction.WEST;
                        intSurfaceDirection = 3;
                        break;
            case SOUTH: surfaceDirection = Constants.Direction.EAST;
                        intSurfaceDirection = 1;
                        break;
        }
        surfaces[y][x][intSurfaceDirection]=true;
    }


    public void surfaceCaptureObstacleDirection(int x, int y, Constants.Direction surfaceDirection){
        int intSurfaceDirection=0;
        switch (surfaceDirection){
            case WEST:
                intSurfaceDirection = 3;
                break;
            case EAST:
                intSurfaceDirection = 1;
                break;
            case NORTH:
                intSurfaceDirection = 0;
                break;
            case SOUTH:
                intSurfaceDirection = 2;
                break;
        }
        surfaces[y][x][intSurfaceDirection]=false;
    }

    public Boolean getSurface(int x, int y, int intSurfaceDirection){
        return surfaces[y][x][intSurfaceDirection];
    }

    public void setSurfaceFalse(int x, int y, int i){
        surfaces[y][x][i]=false;
    }

    public void setSurfaceNull(int x, int y, int i){
        surfaces[y][x][i]= null;
    }
}
