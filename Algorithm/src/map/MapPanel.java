package map;

import map.GridCell.*;
import robot.SimulatorRobot;
import main.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

        //StringBuilder P1 = new StringBuilder(new String());
        String P1 = new String();
        String P2 = new String();

        P1 += "11";		// Padding sequence

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 15; x++) {
                if (gridcells[y][x].getExplored()){
                    //explored = 1
                    P1 += "1";

                    if (gridcells[y][x].getObstacle()){
                        //obstacle=1
                        P2 += "1";
                    }
                    else{
                        P2 += "0";
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

		int length = 0;
		for (int j = 0; j < Constants.HEIGHT; j++) {
			for (int i = 0; i < Constants.WIDTH; i++) {
				if (gridcells[j][i].getExplored()) {
					length++;
				}
			}
		}

		//round up
		//Math. ceil()
		double roundUp =Math.ceil(length/4.0);
		length = (int) roundUp*4;

        String[] mdf = new String[3];
        mdf[0]= hexString;
        mdf[1]= Integer.toString(length);
        mdf[2] = hexStringP2;
		//this.mdfString[0] = hexString;
        //this.mdfString[2] = hexStringP2;
		System.out.println("1..."+hexString+"length: " + length+"2...."+hexStringP2);
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
					gridcells[i][j].setBackground(new Color(255, 211, 29)); // start
				} else if ((i == 12 && j == 17) || (i == 12 && j == 18) || (i == 12 && j == 19) || (i == 13 && j == 17)
						|| (i == 13 && j == 18) || (i == 13 && j == 19) || (i == 14 && j == 17) || (i == 14 && j == 18)
						|| (i == 14 && j == 19)) { 
					gridcells[i][j].setBackground(new Color(47, 196, 178)); // goal
				} else {
					if (gridcells[i][j].getExplored() == true) {
						if (gridcells[i][j].getTravelled() == true)
							gridcells[i][j].setBackground(new Color(60, 223, 255)); // travelled
						else if (gridcells[i][j].getObstacle() == true)
							gridcells[i][j].setBackground(new Color(255,105,97)); // blocked
						else
							gridcells[i][j].setBackground(Color.WHITE); // explored
					}
					else
					gridcells[i][j].setBackground(new Color(105,105,105)); // unexplored
				}
			}
		}

		//robot itself
		gridcells[y - 1][x - 1].setBackground(new Color(206, 157, 217));
		gridcells[y - 1][x].setBackground(new Color(206, 157, 217));
		gridcells[y - 1][x + 1].setBackground(new Color(206, 157, 217));
		gridcells[y][x + 1].setBackground(new Color(206, 157, 217));
		gridcells[y + 1][x + 1].setBackground(new Color(206, 157, 217));
		gridcells[y + 1][x].setBackground(new Color(206, 157, 217));
		gridcells[y + 1][x - 1].setBackground(new Color(206, 157, 217));
		gridcells[y][x- 1].setBackground(new Color(206, 157, 217));
		gridcells[y][x].setBackground(new Color(206, 157, 217));

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
					gridcells[i][j].setBackground(new Color(255, 211, 29));
				}
				// mark goal area
				else if ((j == 12 && i == 17) || (j == 12 && i == 18) || (j == 12 && i == 19) || (j == 13 && i == 17)
						|| (j == 13 && i == 18) || (j == 13 && i == 19) || (j == 14 && i == 17) || (j == 14 && i == 18)
						|| (j == 14 && i == 19)) {
					gridcells[i][j].setBackground(new Color(47, 196, 178));
				}
				// mark unexplored area
				else {
					gridcells[i][j].setBackground(new Color(105,105,105));
				}
			}
		}
	}



	public void setWayPoint(int x, int y) {
		this.waypoint[0] = x;
		this.waypoint[1] = y;
	}

	public int[] getWayPoint() {
		return waypoint;
	}

	public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
		//displayDirection(dir, hor_coord, ver_coord);
		gridcells[ver_coord][hor_coord].displayDirection(dir, steps_per_sec);
	}

}