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

	// constructor
	public MapPanel(String[][] sample_map) {
		setLayout(new GridLayout(Constants.HEIGHT, Constants.WIDTH));
		gridcells = new GridCell[Constants.HEIGHT][Constants.WIDTH];
		for (int row = 0; row < Constants.HEIGHT; row++) {
			for (int col = 0; col < Constants.WIDTH; col++) {
				GridCell gridCell = new GridCell(row, col, sample_map[row][col]);
				gridcells[row][col] = gridCell;
				MapPanel.this.add(gridCell);
//				System.out.println(row + " " + col);
//				System.out.println(Constants.HEIGHT + " " + Constants.WIDTH);
				timer.start();
			}
		}
	}

	// getter and setter
	public GridCell getGridCell(int y, int x) {
		// System.out.println("y: "+y+" x: "+x);
		if ((y < 0) || (x < 0) || (y >= gridcells.length) || (x >= gridcells[y].length))
			return null;

		return gridcells[y][x];
	}

	public void setGridCell(int y, int x, GridCell gridCell) {
		this.gridcells[y][x] = gridCell;
	}

	// assigns a color depending on whether gridCell is obstacle and
	// explored/explored
	public void colorMap(GridCell gridCell) {
		gridCell.setColor();
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			repaint();// this will call at every 1 second
		}
	}

	// update simulation map
	public void updateMap() {
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

	// set robot color
	public void displayRobotSpace(int x_coord, int y_coord) {
		System.out.println(x_coord);
		System.out.println(y_coord);
//		boolean outOfMap = false;
//		if (y_coord-1 < 0 || y_coord-1 > HEIGHT || x_coord-1 < 0 || x_coord-1 > WIDTH) 
//			outOfMap = true;
//		else
//			outOfMap = false;
//
//		if(!outOfMap) {
		gridcells[y_coord - 1][x_coord - 1].setRobotColor();
		gridcells[y_coord - 1][x_coord].setRobotColor();
		gridcells[y_coord - 1][x_coord + 1].setRobotColor();
		gridcells[y_coord][x_coord + 1].setRobotColor();
		gridcells[y_coord + 1][x_coord + 1].setRobotColor();
		gridcells[y_coord + 1][x_coord].setRobotColor();
		gridcells[y_coord + 1][x_coord - 1].setRobotColor();
		gridcells[y_coord][x_coord - 1].setRobotColor();
		gridcells[y_coord][x_coord].setRobotColor();
//		}
	}

	public void displayDirection(int ver_coord, int hor_coord, Direction dir) {
		gridcells[ver_coord][hor_coord].displayDirection(dir);
	}

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