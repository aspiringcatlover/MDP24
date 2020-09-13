package map;
import java.awt.*;
import robot.SimulatorRobot;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;
import robot.*;
import robot.Robot;

public class GridCell extends JPanel {

	private boolean obstacle;
	private boolean explored;
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width

	private int gCost;
	private int hCost;
	private int fCost; //f=g+h
	private GridCell parent;

	//constructor
	public GridCell(int ver_coord, int hor_coord, String state) {
		gCost = 0;
		hCost = 0;
		fCost = 0;
		parent= null;

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
		
		setOpaque(true); 
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension (30, 30));
        
        
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

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getfCost() {
		return fCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}

	public GridCell getParentGrid() {
		return parent;
	}

	public void setParentGrid(GridCell parent) {
		this.parent = parent;
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

	public void displayDirection(Direction direction) {
		switch (direction) {
		case UP:
			BasicArrowButton arrowSouth = new BasicArrowButton(BasicArrowButton.SOUTH);
			add(arrowSouth, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case DOWN:
			BasicArrowButton arrowNorth = new BasicArrowButton(BasicArrowButton.NORTH);
			add(arrowNorth, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case LEFT:
			BasicArrowButton arrowEast = new BasicArrowButton(BasicArrowButton.EAST);
			add(arrowEast, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case RIGHT:
			BasicArrowButton arrowWest = new BasicArrowButton(BasicArrowButton.WEST);
			add(arrowWest, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		}
	}
	
}