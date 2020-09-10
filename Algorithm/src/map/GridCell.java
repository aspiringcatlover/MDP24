package map;
import java.awt.*;
import robot.SimulatorRobot;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import robot.*;
import robot.Robot;

public class GridCell extends JPanel {

	private boolean obstacle;
	private boolean explored;
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width
	private SimulatorRobot simRobot = new SimulatorRobot(1);
	
	//constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
		
		//mark start area
		if((this.hor_coord == 0 && this.ver_coord == 0) || (this.hor_coord == 0 && this.ver_coord == 1) || (this.hor_coord == 0 && this.ver_coord == 2) || (this.hor_coord == 1 && this.ver_coord == 0) || (this.hor_coord == 1 && this.ver_coord == 1) || (this.hor_coord == 1 && this.ver_coord == 2) || (this.hor_coord == 2 && this.ver_coord == 0) || (this.hor_coord == 2 && this.ver_coord == 1) || (this.hor_coord == 2 && this.ver_coord == 2)) {
			setBackground(Color.YELLOW);
		} 
		//mark goal area
		else if ((this.hor_coord == 12 && this.ver_coord == 17) || (this.hor_coord == 12 && this.ver_coord == 18) || (this.hor_coord == 12 && this.ver_coord == 19) || (this.hor_coord == 13 && this.ver_coord == 17) || (this.hor_coord == 13 && this.ver_coord == 18) || (this.hor_coord == 13 && this.ver_coord == 19) || (this.hor_coord == 14 && this.ver_coord == 17) || (this.hor_coord == 14 && this.ver_coord == 18) || (this.hor_coord == 14 && this.ver_coord == 19)) {
			setBackground(Color.GREEN);
		}
		//mark unexplored area
		else {
			setBackground(Color.BLUE);
		}
		
		//mark area occupied by robot 
		if ((this.isRobotCenter(ver_coord, hor_coord)) || (this.isRobot(ver_coord, hor_coord))) {
			setBackground(Color.ORANGE);
		}
		setOpaque(true); 
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension (20, 20));
	}
	
	//getters and setters
	public void setVerCoord(int ver_coord) {
		this.ver_coord = ver_coord;
	}
	
	public int getVerCoord() {
		return ver_coord;
	}

	public void setHorCoord(int hor_coord) {
		this.hor_coord = hor_coord;
	}
	
	public int getHorCoord() {
		return hor_coord;
	}

	public boolean getObstacle() {
		return obstacle;
	}
	
	public boolean getExplored() {
		return explored;
	}
	
	public void setExplored(boolean explored) {
		this.explored = explored;
	}
	
	//set color
	public void setColor() {
		if (explored) {
			setBackground(Color.WHITE);
		}
		if (obstacle){
			setBackground(Color.RED);
		}
	}
	
	//check if gridcell is occcupied by robot center
	public boolean isRobotCenter(int ver_coord, int hor_coord) {
		if (simRobot.getXCoord() == hor_coord && simRobot.getYCoord() == ver_coord)
			return true;
		else
			return false;
	}
	
	//check if gridcell is occupied by robot
	public boolean isRobot(int ver_coord, int hor_coord) {
		if (simRobot.getXCoord() == hor_coord-1 && simRobot.getYCoord() == ver_coord-1)
			return true;
		else if (simRobot.getXCoord() == hor_coord && simRobot.getYCoord() == ver_coord-1)
			return true;
		else if (simRobot.getXCoord() == hor_coord+1 && simRobot.getYCoord() == ver_coord-1)
			return true;
		else if (simRobot.getXCoord() == hor_coord+1 && simRobot.getYCoord() == ver_coord)
			return true;
		else if (simRobot.getXCoord() == hor_coord+1 && simRobot.getYCoord() == ver_coord+1)
			return true;
		else if (simRobot.getXCoord() == hor_coord && simRobot.getYCoord() == ver_coord+1)
			return true;
		else if (simRobot.getXCoord() == hor_coord-1 && simRobot.getYCoord() == ver_coord+1)
			return true;
		else if (simRobot.getXCoord() == hor_coord-1 && simRobot.getYCoord() == ver_coord)
			return true;
		else
			return false;
	}
	
}