package test;

import static test.Constants.HEIGHT;
import static test.Constants.WIDTH;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulatorMap {

	private String[][] sample_map;
	private MapPanel map;

	public SimulatorMap() {

		String[][] sample_map = new String[Constants.HEIGHT][Constants.WIDTH];
		try {
			String path_name = new File("").getAbsolutePath();
			path_name = System.getProperty("user.dir") + "/src/sample_map/map3" + ".txt";
			// path_name = "src/sample_map/map" + Integer.toString(mapChoice) + ".txt";
			File myObj = new File(path_name);
			Scanner myReader = new Scanner(myObj);
			int row = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] arrOfStr = data.split("");
				for (int col = 0; col < arrOfStr.length; col++) {
					sample_map[row][col] = arrOfStr[col];
				}
				row++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		map = new MapPanel(sample_map);
	}

	// getters and setters
	public MapPanel getMap() {
		return map;
	}
}
