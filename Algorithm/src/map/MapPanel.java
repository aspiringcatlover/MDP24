package map;
import map.GridCell.*;

//import static Main.Constants.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class MapPanel extends JPanel {
	private GridCell[][] gridcells;
	
	public MapPanel() {
		setLayout(new GridLayout(20, 15));
		gridcells = new GridCell[20][15];
		for (int row = 0; row < 20; row++)
        {
            for (int col = 0; col < 15; col++)
            {
				GridCell gridcell = new GridCell(row, col);
				gridcells[row][col] = gridcell;
				MapPanel.this.add(gridcell);
                
            }
        }
	}
	
	//getter and setter
	public GridCell getGridCell(int ver_coord, int hor_coord) {
		for(int i = 0; i < gridcells.length; i++){
			if (i == ver_coord) {
				for (int j = 0; j < gridcells[i].length; j++){
	                if (j == hor_coord)
	                    return gridcells[i][j];
	            }
			}
        }
		return gridcells[0][0];
	}
	
	//assigns a color depending on whether gridCell is obstacle and explored/explored
	public void colorMap(GridCell gridCell) {
		gridCell.setColor();
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
//
//		public void clearMap() {
//			Component[] components = this.getComponents();
//			for (int i = 0; i < components.length; i++) {
//				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) {
//					components.setState(State.UNEXPLORED);
//				}
//			}
//		}

}