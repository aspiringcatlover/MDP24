package exploration.test;

import fastestPath.PathFinder;
import main.Constants;
import map.GridCell;
import map.MapPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RightWallHuggingIncompleteTest {
    public static void main(String[] args) {
        String[][] sampleMap = getSampleMap(6666);
        MapPanel map = new MapPanel(sampleMap);

        PathFinder pathFinder = new PathFinder(map);
        for (int i=0; i<19;i++){
            for (int r=0;r<14;r++){
                if ((i==9||i==10)&&(r>=4&&r<=10))
                    continue;
                map.setExploredForGridCell(i,r,true);
            }
        }

        for (int i=0; i<19;i++){
            for (int r=0;r<14;r++){
                System.out.println("x:" + map.getGridCell(i,r).getHorCoord() + " y: " +map.getGridCell(i,r).getVerCoord() +
                        " unexplored " + map.getGridCell(i,r).getExplored() +"obstacle?"+map.getGridCell(i,r).getObstacle());
            }
        }
        System.out.println("map complete?"+map.getActualPerc());
        ArrayList<GridCell> result = null;
                result = pathFinder.getShortestPath(1,1,12,0, Constants.Direction.NORTH, null);
        if (result!=null){
            System.out.println("num grid in result: "+result.size());
            for (GridCell gridCell: result){
                System.out.println("x:" + gridCell.getHorCoord() + " y: " +gridCell.getVerCoord() +
                        " fcost: " + gridCell.getfCost() + " gcost:"+gridCell.getgCost()+" hcost:"+gridCell.gethCost());
            }
        }
        else
            System.out.println("result empty");

    }

    public static String[][] getSampleMap(int mapChoice){
        String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
        try {
            String path_name = new File("").getAbsolutePath();
            path_name = System.getProperty("user.dir")+"/src/sample_map/map" +Integer.toString(mapChoice) + ".txt";
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
}
