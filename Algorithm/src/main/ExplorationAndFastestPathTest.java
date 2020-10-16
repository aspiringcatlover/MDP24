package main;

import exploration.Exploration;
import fastest_path.PathFinder;
import map.GridCell;
import map.MapPanel;
import map.SimulatorMap;
import robot.Robot;
import robot.SimulatorRobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExplorationAndFastestPathTest {
    public static void main(String[] args) {
        String[][] sampleMap = getSampleMap(2);
        MapPanel map = new MapPanel(sampleMap);
        MapPanel emptyMap = new MapPanel(SimulatorMap.getSampleMap(1));

        for (int i = 0; i < 3; i++) {
            for (int r = 0; r < 3; r++) {
                emptyMap.setExploredForGridCell(i, r, true);
            }
        }
        Robot robot = new SimulatorRobot(emptyMap, 2, map);
        Exploration exploration = new Exploration(robot, 360000,100, 2,false);
        robot = exploration.explore();

        PathFinder  pathFinder = new PathFinder(robot.getMap());
        ArrayList<GridCell> result =  pathFinder.getShortestPath(1, 1, 14, 19, Constants.Direction.NORTH);
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
            path_name = System.getProperty("user.dir")+"/Algorithm/src/sample_map/map" +Integer.toString(mapChoice) + ".txt";
            System.out.println(path_name);
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
