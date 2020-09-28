package exploration;

import main.Constants;
import map.MapPanel;
import robot.Robot;
import robot.SimulatorRobot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExplorationTest {
    public static void main(String[] args) {
        String[][] sampleMap = getSampleMap(6);
        MapPanel map = new MapPanel(sampleMap);

        Robot robot = new SimulatorRobot(map, 2);
        Exploration exploration = new Exploration(robot, 360000,100, 2,false);
        exploration.explore();
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
