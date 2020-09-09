package map;

import static Main.Constants.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class MapPanel extends JPanel {
	
	public MapPanel() {
		
		setLayout(new GridLayout(20, 15));
		setBounds(10, 50, 150, 100);
		for (int row = 1; row < 21; row++)
        {
            for (int col = 1; col < 16; col++)
            {
                add(new GridCell(row,col));
            }
        }
	}
	
	    //Generate map descriptor part 1
		public String generateMapDes1() {
			Component[] components = this.getComponents();
			String bitStream1 = "11";
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) 
					bitStream1 = bitStream1 + "0";
				else 
					bitStream1 = bitStream1 + "1";
				}
			bitStream1 = bitStream1 + "11";
			return String.format("%016x", Integer.parseInt(bitStream1));
		}
		
		
		//Generate map descriptor part 2
		public String generateMapDes2() {
			Component[] components = this.getComponents();
			String bitStream2 = "";
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) {
						if (components[i] instanceof JPanel && components[i].getState() == State.BLOCKED)
							bitStream2 = bitStream2 + "1";
						else
							bitStream2 = bitStream2 + "1";
				}
			}
			return String.format("%016x", Integer.parseInt(bitStream2));
		}
		
		
		public void move(movement m, boolean sendMoveToAndroid) {
			//get robot coordinates from sensor
	        if (!realBot) {
	            try {
	                long speed;
					TimeUnit.MILLISECONDS.sleep(speed);
	            } catch (InterruptedException e) {
	                System.out.println("Something went wrong in move()!");
	            }
	        }

	        switch (m) {
	            case FORWARD:
	                switch (robotDir) {
	                    case NORTH:
	                        robot.getYCoord()++;
	                        break;
	                    case EAST:
	                    	robot.getXCoord()++;
	                        break;
	                    case SOUTH:
	                    	robot.getYCoord()--;
	                        break;
	                    case WEST:
	                    	robot.getYCoord()--;
	                        break;
	                }
	                break;
	            case BACKWARD:
	                switch (robotDir) {
	                case NORTH:
                        robot.getYCoord()--;
                        break;
                    case EAST:
                    	robot.getXCoord()--;
                        break;
                    case SOUTH:
                    	robot.getYCoord()++;
                        break;
                    case WEST:
                    	robot.getYCoord()++;
                        break;
	                }
	                break;
	            case CALIBRATE:
	                break;
	            default:
	                System.out.println("Error!");
	                break;
	        }
	        if (realBot) sendMovement(m, sendMoveToAndroid);
	    }

		public void clearMap() {
			Component[] components = this.getComponents();
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JPanel && components[i].getState() == State.EXPLORED) {
					components.setState(State.UNEXPLORED);
				}
			}
		}

}
