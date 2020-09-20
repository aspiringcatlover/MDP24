package map;
import java.awt.*;
import robot.SimulatorRobot;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;
import robot.*;

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
		else
			obstacle = false;
		explored = false;
		
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
			setBackground(Color.WHITE);
		}
		
		//mark area covered by obstacle
		if (obstacle) {
			setBackground(Color.RED);
		}
		
		setOpaque(true); 
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension (25, 25));
        
        
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

	public boolean getWall(int ver_coord, int hor_coord) {
		if (ver_coord < 0 || ver_coord > 14)
			return true;
		else if (hor_coord < 0 || hor_coord > 19)
			return true;
		else
			return false;
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
			setBackground(Color.BLUE);
		}
		else if (obstacle){
			setBackground(Color.RED);
		}
		revalidate();
		repaint();
	}
	
	//set color for robot
	public void setRobotColor() {
		setBackground(Color.ORANGE);
		revalidate();
		repaint();
	}

	public void displayDirection(Direction direction) {
		switch (direction) {
		case UP:
			BasicArrowButton arrowSouth = new BasicArrowButton(BasicArrowButton.EAST);
			add(arrowSouth, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case DOWN:
			BasicArrowButton arrowNorth = new BasicArrowButton(BasicArrowButton.WEST);
			add(arrowNorth, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case LEFT:
			BasicArrowButton arrowEast = new BasicArrowButton(BasicArrowButton.NORTH);
			add(arrowEast, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		case RIGHT:
			BasicArrowButton arrowWest = new BasicArrowButton(BasicArrowButton.SOUTH);
			add(arrowWest, BorderLayout.NORTH);
			revalidate();
			repaint();
			break;
		}
	}
	
}