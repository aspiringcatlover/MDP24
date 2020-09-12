package map;

import map.GridCell.*;
import robot.SimulatorRobot;

//import static Main.Constants.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;

public class MapPanel extends JPanel {
	private GridCell[][] gridcells;
	private SimulatorRobot simRobot;

	// constructor
	public MapPanel(String[][] sample_map) {
		setLayout(new GridLayout(20, 15));
		gridcells = new GridCell[20][15];
		for (int row = 0; row < 20; row++) {
			for (int col = 0; col < 15; col++) {
				GridCell gridCell = new GridCell(row, col, sample_map[row][col]);
				gridcells[row][col] = gridCell;
				MapPanel.this.add(gridCell);

			}
		}
	}

	// getter and setter
	public GridCell getGridCell(int y, int x) {
		if ((y >gridcells.length) || (x>gridcells[y].length))
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
	public void displayMove(int x_coord, int y_coord) {
		gridcells[x_coord - 1][y_coord - 1].setRobotColor();
		gridcells[x_coord][y_coord - 1].setRobotColor();
		gridcells[x_coord + 1][y_coord - 1].setRobotColor();
		gridcells[x_coord + 1][y_coord].setRobotColor();
		gridcells[x_coord + 1][y_coord + 1].setRobotColor();
		gridcells[x_coord][y_coord + 1].setRobotColor();
		gridcells[x_coord - 1][y_coord + 1].setRobotColor();
		gridcells[x_coord - 1][y_coord].setRobotColor();
		gridcells[x_coord][y_coord].setRobotColor();
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
//		
//		
//		public void move(movement m, boolean sendMoveToAndroid) {
//			//get robot coordinates from sensor
//	        if (!realBot) {
//	            try {
//	                long speed;
//					TimeUnit.MILLISECONDS.sleep(speed);
//	            } catch (InterruptedException e) {
//	                System.out.println("Something went wrong in move()!");
//	            }
//	        }
//
//	        switch (m) {
//	            case FORWARD:
//	                switch (robotDir) {
//	                    case NORTH:
//	                        robot.getYCoord()++;
//	                        break;
//	                    case EAST:
//	                    	robot.getXCoord()++;
//	                        break;
//	                    case SOUTH:
//	                    	robot.getYCoord()--;
//	                        break;
//	                    case WEST:
//	                    	robot.getYCoord()--;
//	                        break;
//	                }
//	                break;
//	            case BACKWARD:
//	                switch (robotDir) {
//	                case NORTH:
//                        robot.getYCoord()--;
//                        break;
//                    case EAST:
//                    	robot.getXCoord()--;
//                        break;
//                    case SOUTH:
//                    	robot.getYCoord()++;
//                        break;
//                    case WEST:
//                    	robot.getYCoord()++;
//                        break;
//	                }
//	                break;
//	            case CALIBRATE:
//	                break;
//	            default:
//	                System.out.println("Error!");
//	                break;
//	        }
//	        if (realBot) sendMovement(m, sendMoveToAndroid);
//	    }

}