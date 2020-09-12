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
	
	//constructor
	public GridCell(int ver_coord, int hor_coord, String state) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
		if (state.equals("O"))
			obstacle = true;
		
		//mark start area
		if (state.equals("S")) {
			setBackground(Color.YELLOW);
		} 
		//mark goal area
		else if (state.equals("E")){
			setBackground(Color.GREEN);
		}
		//mark unexplored area
		else {
			setBackground(Color.BLUE);
		}
		
		//mark area covered by obstacle
		if (obstacle) {
			setBackground(Color.RED);
		}
		
//		//mark area occupied by robot 
//		if ((this.isRobotCenter(ver_coord, hor_coord)) || (this.isRobot(ver_coord, hor_coord))) {
//			setBackground(Color.ORANGE);
//		}
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
	
	//set color for map
	public void setColor() {
		if (explored) {
			setBackground(Color.WHITE);
		}
		if (obstacle){
			setBackground(Color.RED);
		}
	}
	
	//set color for robot
	public void setRobotColor() {
		setBackground(Color.ORANGE);
	}
	
	/*
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
	*/
	
}