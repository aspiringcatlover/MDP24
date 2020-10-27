package connection.test;

import connection.ConnectionManager;
import connection.SocketConnection;
import main.Constants;
import map.MapPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MdfStringTest {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        boolean connected = false;
        String[][] sampleMap = getSampleMap(8);
        MapPanel map = new MapPanel(sampleMap);


        for (int i=0; i<=19;i++){
            for (int r=0;r<=14;r++){
                if ((i==9||i==10)&&(r>=4&&r<=10))
                    continue;
                map.setExploredForGridCell(i,r,true);
            }
        }
        while (!connected) {
            connected = connectionManager.connectToRPi();
        }
        try {
            //connectionManager.start();
            while (connected) {
                SocketConnection socketConnection = SocketConnection.getInstance();
                System.out.println("CONNECTED TO SOCKET");
                String[] arr = map.getMdfString();
                /*
                socketConnection.sendMessage("M{\"map\":[{\"explored\": \"" + arr[0] + "\",\"length\":" + arr[1] + ",\"obstacle\":\"" + arr[2] +
                        "\"}]}");*/
                socketConnection.sendMessage("M{\"map\":[{\"explored\": \"" + "FFC07F80FF01FE03FFFFFFF3FFE7FFCFFF9C7F38FE71FCE3F87FF0FFE1FFC3FF87FF0E0E1C1F" + "\",\"length\":" + "208" + ",\"obstacle\":\"" + "100001C80000000001C0000080000060001C00000080000" +
                        "\"}]}");
                break;

            }
        } catch (Exception e) {
            connectionManager.stopCM();
            System.out.println("ConnectionManager is stopped");
        }

    }


    public static String[][] getSampleMap(int mapChoice){
        String[][] temp_sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
        try {
            String path_name = new File("").getAbsolutePath();
            path_name = System.getProperty("user.dir")+"/Algorithm/src/sample_map/map" +Integer.toString(mapChoice) + ".txt";
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
