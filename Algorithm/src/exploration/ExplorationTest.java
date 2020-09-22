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
        String[][] sampleMap = getSampleMap(2);
        MapPanel map = new MapPanel(sampleMap);

        Robot robot = new SimulatorRobot(map);
        Exploration exploration = new Exploration(robot, 360000,100);
        exploration.explore();
    }

    private static String[][] getSampleMap(int mapChoice){
        String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
        try {
            String path_name = new File("").getAbsolutePath();
            path_name = "src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
            //path_name =  System.getProperty("user.dir")+"/algorithm/src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
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
