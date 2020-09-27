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

public class MapPanel extends JPanel implements ActionListener {
	private GridCell[][] gridcells;
	Timer timer = new Timer(1000, this);
	String[] mdfString;
	boolean changed;

	// constructor
	public MapPanel(String[][] sample_map) {
		setLayout(new GridLayout(Constants.WIDTH, Constants.HEIGHT));
		gridcells = new GridCell[Constants.WIDTH][Constants.HEIGHT];
		for (int row = 0; row < Constants.WIDTH; row++) {
			for (int col = 0; col < Constants.HEIGHT; col++) {
				GridCell gridCell = new GridCell(row, col, sample_map[row][col]);
				gridcells[row][col] = gridCell;
				MapPanel.this.add(gridCell);
//				System.out.println(row + " " + col);
//				System.out.println(Constants.WIDTH + " " + Constants.HEIGHT);
				timer.start();
			}
		}
	}

	// getter and setter

	public String[] getMdfString() {
		if (!changed) {
			return this.mdfString;
		}

		changed = false;

		StringBuilder MDFBitStringPart1 = new StringBuilder();
		StringBuilder MDFBitStringPart2 = new StringBuilder();

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
				if (!gridcells[x][y].getExplored()) {
					//TODO:: CHECK FOR THIS ONE
					length++;
				}
			}
		}

		MDFHexString[1] = Integer.toString(length);

		this.mdfString = MDFHexString;
		return MDFHexString;

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

	public void setGridCell(int y, int x, GridCell gridCell) {
		changed=true;
		this.gridcells[y][x] = gridCell;
	}

	// assigns a color depending on whether gridCell is obstacle and
	// explored/explored
	public void colorMap(GridCell gridCell) {
		//gridCell.setColor();
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			repaint();// this will call at every 1 second
		}
	}

	// update simulation map
	public void updateMap(int x_coord, int y_coord) {
		for (int i = 0; i < gridcells.length; i++) {
			for (int j = 0; j < gridcells[i].length; j++) {
				if ((j == 0 && i == 0) || (j == 0 && i == 1) || (j == 0 && i == 2) || (j == 1 && i == 0)
						|| (j == 1 && i == 1) || (j == 1 && i == 2) || (j == 2 && i == 0) || (j == 2 && i == 1)
						|| (j == 2 && i == 2)) {
					gridcells[j][i].setBackground(Color.YELLOW); // start
				} else if ((j == 12 && i == 17) || (j == 12 && i == 18) || (j == 12 && i == 19) || (j == 13 && i == 17)
						|| (j == 13 && i == 18) || (j == 13 && i == 19) || (j == 14 && i == 17) || (j == 14 && i == 18)
						|| (j == 14 && i == 19)) {
					gridcells[j][i].setBackground(Color.GREEN); // goal
				} else {
					if (gridcells[j][i].getExplored() == true)
						gridcells[j][i].setBackground(Color.BLUE); // explored
					else if (gridcells[j][i].getObstacle() == true)
						gridcells[j][i].setBackground(Color.RED); // blocked
					else
						gridcells[j][i].setBackground(Color.WHITE); // unexplored
				}
			}
			// robot
			gridcells[y_coord - 1][x_coord - 1].setBackground(Color.ORANGE);
			gridcells[y_coord - 1][x_coord].setBackground(Color.ORANGE);
			gridcells[y_coord - 1][x_coord + 1].setBackground(Color.ORANGE);
			gridcells[y_coord][x_coord + 1].setBackground(Color.ORANGE);
			gridcells[y_coord + 1][x_coord + 1].setBackground(Color.ORANGE);
			gridcells[y_coord + 1][x_coord].setBackground(Color.ORANGE);
			gridcells[y_coord + 1][x_coord - 1].setBackground(Color.ORANGE);
			gridcells[y_coord][x_coord - 1].setBackground(Color.ORANGE);
			gridcells[y_coord][x_coord].setBackground(Color.ORANGE);
		}
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
	
	 //set robot color
//	public void displayRobotSpace(int x_coord, int y_coord) {
//		System.out.println(x_coord);
//		System.out.println(y_coord);
//		boolean outOfMap = false;
//		if (y_coord-1 < 0 || y_coord-1 > HEIGHT || x_coord-1 < 0 || x_coord-1 > WIDTH) 
//			outOfMap = true;
//		else
//			outOfMap = false;
//
//		if(!outOfMap) {
		/*
		gridcells[y_coord - 1][x_coord - 1].setRobotColor();
		gridcells[y_coord - 1][x_coord].setRobotColor();
		gridcells[y_coord - 1][x_coord + 1].setRobotColor();
		gridcells[y_coord][x_coord + 1].setRobotColor();
		gridcells[y_coord + 1][x_coord + 1].setRobotColor();
		gridcells[y_coord + 1][x_coord].setRobotColor();
		gridcells[y_coord + 1][x_coord - 1].setRobotColor();
		gridcells[y_coord][x_coord - 1].setRobotColor();
		gridcells[y_coord][x_coord].setRobotColor();*/
//		}
//	}
	

	public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
//		gridcells[ver_coord][hor_coord].displayDirection(dir);
	}

}