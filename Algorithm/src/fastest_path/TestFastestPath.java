package fastest_path;

import main.Constants;
import map.GridCell;
import map.MapPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestFastestPath {
    public static void main(String[] args) {
        String[][] sampleMap = getSampleMap(2);
        MapPanel map = new MapPanel(sampleMap);
        PathFinder pathFinder = new PathFinder(map);

        /*
        print map layout
        System.out.println(map.getGridCell(0,7).getVerCoord() + "x :" +map.getGridCell(0,7).getHorCoord() +map.getGridCell(0,7).getObstacle() );

        for (int i=0;i<20;i++){
            for (int r=0;r<15;r++){
                System.out.println("y:" + map.getGridCell(i,r).getVerCoord() + "x :" +map.getGridCell(i,r).getHorCoord() +map.getGridCell(i,r).getObstacle() );
            }
        }
        System.out.println("----------------------------");*/

        ArrayList<GridCell> result =  pathFinder.getShortestPath(1, 1, 13, 18);
        System.out.println("num grid in result: "+result.size());
        for (GridCell gridCell: result){
            System.out.println("x:" + gridCell.getHorCoord() + " y: " +gridCell.getVerCoord() +
                    " fcost: " + gridCell.getfCost() + " gcost:"+gridCell.getgCost()+" hcost:"+gridCell.gethCost());
        }
    }

    private static String[][] getSampleMap(int mapChoice){
        String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
        try {
            String path_name = new File("").getAbsolutePath();
            path_name =  System.getProperty("user.dir")+"/algorithm/src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
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
