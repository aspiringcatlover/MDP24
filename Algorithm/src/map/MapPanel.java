package map;

import map.GridCell.*;
import robot.SimulatorRobot;
import main.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;

import static main.Constants.HEIGHT;
import static main.Constants.WIDTH;

public class MapPanel extends JPanel {
	private GridCell[][] gridcells;
	//Timer timer = new Timer(500, this);
	String[] mdfString;
	boolean changed;
	private int[] waypoint = new int[] {-1, -1};
	private int steps_per_sec;


	// constructor
	public MapPanel(String[][] sample_map) {

		gridcells = new GridCell[Constants.HEIGHT][Constants.WIDTH];
		for (int row = 0; row < Constants.HEIGHT; row++) {
			for (int col = 0; col < Constants.WIDTH; col++) {
				GridCell gridCell = new GridCell(row, col, sample_map[row][col]);
				gridcells[row][col] = gridCell;
			}
		}
		parseToSimulatorGrid(gridcells);
		changed = true; 
		/*
		gridcells[0][0].setBackground(Color.RED);
		gridcells[19][0].setBackground(Color.ORANGE);
		gridcells[0][14].setBackground(Color.BLUE);*/

	}

	public GridCell[][] parseToSimulatorGrid(GridCell[][] gridcells){
		GridCell[][] parseGridcells;
		setLayout(new GridLayout(Constants.WIDTH, Constants.HEIGHT)); //simulator will be 20x15 (the opposite)
		parseGridcells = new GridCell[Constants.WIDTH][Constants.HEIGHT];
		for (int row = 0; row < Constants.WIDTH; row++) {
			for (int col = 0; col < Constants.HEIGHT; col++) {
				parseGridcells[row][col]=gridcells[col][row];
				MapPanel.this.add(parseGridcells[row][col]);
				//timer.start();
			}
		}
		return parseGridcells;

	}



	// getter and setter

	public float getActualPerc() {
		int totalGridCells = Constants.HEIGHT * Constants.WIDTH;
		int gridCellsCovered = 0;
		GridCell gridCell;
		for (int row = 0; row < Constants.HEIGHT; row++) {
			for (int col = 0; col < Constants.WIDTH; col++) {
				gridCell = gridcells[row][col];
				if (gridCell.getExplored() || gridCell.getObstacle()) {
					gridCellsCovered += 1;
				}
			}
		}
		// System.out.println((float) gridCellsCovered / totalGridCells * 100);
		return (((float) gridCellsCovered / totalGridCells) * 100);
	}


	public String[] getMdfString() {
		StringBuilder MDFBitStringPart1 = new StringBuilder();
		StringBuilder MDFBitStringPart2 = new StringBuilder();

		int numExploredGrid=0;


        //StringBuilder P1 = new StringBuilder(new String());
        String P1 = new String();
        String P2 = new String();

        P1 += "11";		// Padding sequence

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 15; x++) {
                if (gridcells[y][x].getExplored()){
                    //explored = 1
                    P1 += "1";
                    numExploredGrid++;
                    if (gridcells[y][x].getObstacle()){
                        //obstacle=1
                        P2 += "1";
                    }
                    else{
                        P2 += "0";
                        System.out.println("obstacle"+x+" "+y);
                    }
                }
                else{
                    P1 += "0";
                }

            }
        }

        P1 += "11";		// Padding sequence
        System.out.println("p1..." + P1);

        //p1 convert to hex string
        String hexString = new String();
        for (int i = 0; i < 304; i += 4) {
            String binOf4Bits = P1.substring(i, i + 4);
            int intOf4Bits = Integer.parseInt(binOf4Bits, 2);	// Binary String to Decimal Number
            hexString += Integer.toString(intOf4Bits, 16).toUpperCase();	// Decimal Number to Hex String
        }
        System.out.println("hex string p1: " + hexString);

        //p2
        // Normalise P2 Binary
        int remainder = P2.length() % 4;
        String lastBit = new String();
        String padding = new String();

        switch (remainder) {
            case 1:
                lastBit = P2.substring(P2.length() - 1);
                padding = "000";
                P2 = P2.substring(0, P2.length() - 1).concat(padding).concat(lastBit);
                break;
            case 2:
                lastBit = P2.substring(P2.length() - 2);
                padding = "00";
                P2 = P2.substring(0, P2.length() - 2).concat(padding).concat(lastBit);
                break;
            case 3:
                lastBit = P2.substring(P2.length() - 3);
                padding = "0";
                P2 = P2.substring(0, P2.length() - 3).concat(padding).concat(lastBit);
                break;
            default: // Do nothing
        }

        // Convert to Hexadecimal
        String hexStringP2 = new String();
        for (int i = 0; i < P2.length(); i += 4) {
            String binOf4Bits = P2.substring(i, i + 4);
            int intOf4Bits = Integer.parseInt(binOf4Bits, 2);	// Binary String to Decimal Number
            hexStringP2 += Integer.toString(intOf4Bits, 16).toUpperCase();	// Decimal Number to Hex String
        }
        System.out.println("hex string p2: " + hexStringP2);

        /*
        MDFBitStringPart1.append("11");
		String[] MDFHexString = new String[] {"","",""};

		for (int y = 0; y < Constants.HEIGHT; y++) {
			for (int x = 0; x < Constants.WIDTH; x++) {

				if (gridcells[y][x].getObstacle()) { // Obstacle
					MDFBitStringPart1.append("1");
					MDFBitStringPart2.append("1");

				}
				else if (gridcells[y][x].getExplored()) { // Unexplored
					MDFBitStringPart1.append("0");
				}
				else {
					MDFBitStringPart1.append("1");
					MDFBitStringPart2.append("0");
				}

			}
		}
		MDFBitStringPart1.append("11");
		System.out.println("MDF bit string part 1"+MDFBitStringPart1);
        System.out.println("MDF bit string part 2"+MDFBitStringPart2);

		for (int i = 0; i < MDFBitStringPart1.length(); i += 4) {
			MDFHexString[0] += Integer.toString(Integer.parseInt(MDFBitStringPart1.substring(i, i + 4), 2), 16);
		}

		if ((MDFBitStringPart2.length() % 4) != 0){ // Only pad if the MDF Bit string is not a multiple of 4
			MDFBitStringPart2.insert(0, "0".repeat(4 - (MDFBitStringPart2.length() % 4)));
		}

		for (int i = 0; i < MDFBitStringPart2.length(); i += 4) {
			MDFHexString[2] += Integer.toString(Integer.parseInt(MDFBitStringPart2.substring(i, i + 4), 2), 16);
		}

		int length = 0;
		for (int y = 0; y < Constants.HEIGHT; y++) {
			for (int x = 0; x < Constants.WIDTH; x++) {
				if (!gridcells[y][x].getExplored()) {
					//TODO:: CHECK FOR THIS ONE
					length++;
				}
			}
		}

		MDFHexString[1] = Integer.toString(length);*/
        String[] mdf = new String[3];
        mdf[0]= hexString;
        mdf[1]= Integer.toString(numExploredGrid);
        mdf[2] = hexStringP2;
		//this.mdfString[0] = hexString;
        //this.mdfString[2] = hexStringP2;
		System.out.println("1..."+hexString+"length: " + numExploredGrid+"2...."+hexStringP2);
		return mdf;

	}

	public GridCell getGridCell(int y, int x) {
		// System.out.println("y: "+y+" x: "+x);
		if ((y < 0) || (x < 0) || (y >= gridcells.length) || (x >= gridcells[y].length))
			return null;

		return gridcells[y][x];
	}

	public void setObstacleForGridCell(int y,int x, Boolean obstacle){
		changed=true;
		if (y<0||y>19||x<0||x>14||obstacle==null)
			return;
		this.gridcells[y][x].setObstacle(obstacle);
	}

	public void setExploredForGridCell(int y, int x, Boolean explored){
		changed=true;
		if (y<0||y>19||x<0||x>14||explored==null)
			return;
		this.gridcells[y][x].setExplored(explored);
	}
	
	public void setTravellededForGridCell(int y, int x, Boolean travelled){
		changed=true;
		if (y<0||y>19||x<0||x>14||travelled==null)
			return;
		this.gridcells[y][x].setTravelled(travelled);
	}

	public void setGridCell(int y, int x, GridCell gridCell) {
		changed=true;
		this.gridcells[y][x] = gridCell;
	}

	public int getSteps_per_sec() {
		return steps_per_sec;
	}

	public void setSteps_per_sec(int steps_per_sec) {
		this.steps_per_sec = steps_per_sec;
	}

	// update simulation map
	public void updateMap(int x, int y ) {
		System.out.println("update map");
		for (int i = 0; i < gridcells.length; i++) {
			for (int j = 0; j < gridcells[i].length; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 2) || (i == 1 && j == 0)
						|| (i == 1 && j == 1) || (i == 1 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 1)
						|| (i == 2 && j == 2)) {
					gridcells[i][j].setBackground(Color.YELLOW); // start
				} else if ((i == 12 && j == 17) || (i == 12 && j == 18) || (i == 12 && j == 19) || (i == 13 && j == 17)
						|| (i == 13 && j == 18) || (i == 13 && j == 19) || (i == 14 && j == 17) || (i == 14 && j == 18)
						|| (i == 14 && j == 19)) { 
					gridcells[i][j].setBackground(Color.GREEN); // goal
				} else {
					if (gridcells[i][j].getExplored() == true) {
						if (gridcells[i][j].getTravelled() == true)
							gridcells[i][j].setBackground(Color.CYAN); // travelled
						else if (gridcells[i][j].getObstacle() == true)
							gridcells[i][j].setBackground(Color.RED); // blocked
						else
							gridcells[i][j].setBackground(Color.BLUE); // explored
					}
					else
					gridcells[i][j].setBackground(Color.WHITE); // unexplored
				}
			}
		}

		//robot itself
		gridcells[y - 1][x - 1].setBackground(Color.MAGENTA);
		gridcells[y - 1][x].setBackground(Color.MAGENTA);
		gridcells[y - 1][x + 1].setBackground(Color.MAGENTA);
		gridcells[y][x + 1].setBackground(Color.MAGENTA);
		gridcells[y + 1][x + 1].setBackground(Color.MAGENTA);
		gridcells[y + 1][x].setBackground(Color.MAGENTA);
		gridcells[y + 1][x - 1].setBackground(Color.MAGENTA);
		gridcells[y][x- 1].setBackground(Color.MAGENTA);
		gridcells[y][x].setBackground(Color.MAGENTA);

		revalidate();
		repaint();
	}

	// clear simulation map
	public void clearMap() {
		for (int i = 0; i < gridcells.length; i++) {
			for (int j = 0; j < gridcells[i].length; j++) {
				// mark start area
				if ((j == 0 && i == 0) || (j == 0 && i == 1) || (j == 0 && i == 2) || (j == 1 && i == 0)
						|| (j == 1 && i == 1) || (j == 1 && i == 2) || (j == 2 && i == 0) || (j == 2 && i == 1)
						|| (j == 2 && i == 2)) {
					gridcells[i][j].setBackground(Color.YELLOW);
				}
				// mark goal area
				else if ((j == 12 && i == 17) || (j == 12 && i == 18) || (j == 12 && i == 19) || (j == 13 && i == 17)
						|| (j == 13 && i == 18) || (j == 13 && i == 19) || (j == 14 && i == 17) || (j == 14 && i == 18)
						|| (j == 14 && i == 19)) {
					gridcells[i][j].setBackground(Color.GREEN);
				}
				// mark unexplored area
				else {
					gridcells[i][j].setBackground(Color.BLUE);
				}
			}
		}
	}



	public void setWayPoint(int x, int y) {
		//boolean verbose = new Exception().getStackTrace()[1].getClassName().equals("robot.Robot");

		/*
		if (x >= Constants.WIDTH - 1 || x <= 0 || y >= Constants.HEIGHT - 1 || y <= 0)
				 {
			if (!(waypoint[0] == -1 && waypoint[1] == -1)) {
				this.waypoint[0] = -1;
				this.waypoint[1] = -1;
				/*
				if (verbose) {
					System.out.println("The current waypoint is set as: " + "-1" + "," + "-1");
				}*/

			//return;
		//}
		this.waypoint[0] = x;
		this.waypoint[1] = y;
		/*if (verbose) {
			System.out.println("Successfully set the waypoint: " + x + "," + y);
		}*/
	}

	public int[] getWayPoint() {
		return waypoint;
	}

	public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
		//displayDirection(dir, hor_coord, ver_coord);
		gridcells[ver_coord][hor_coord].displayDirection(dir, steps_per_sec);
	}
		//


//	    //Generate map descriptor part 1
//		public String generateMapDes1() {
//			Component[] components = this.getComponents();
//			String bitStream1 = "11";
//			for (int i = 0; i < components.length; i++) {
//				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) 
//					bitStream1 = bitStream1 + "0";
//				else 
//					bitStream1 = bitStream1 + "1";
//				}
//			bitStream1 = bitStream1 + "11";
//			return String.format("%016x", Integer.parseInt(bitStream1));
//		}
//		
//		
//		//Generate map descriptor part 2
//		public String generateMapDes2() {
//			Component[] components = this.getComponents();
//			String bitStream2 = "";
//			for (int i = 0; i < components.length; i++) {
//				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) {
//						if (components[i] instanceof JPanel && components[i].getState() == State.BLOCKED)
//							bitStream2 = bitStream2 + "1";
//						else
//							bitStream2 = bitStream2 + "1";
//				}
//			}
//			return String.format("%016x", Integer.parseInt(bitStream2));
//		}

}