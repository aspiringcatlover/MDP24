package map;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import main.Constants.Direction;

import static main.Constants.Direction.WEST;

public class GridCell extends JPanel {

	private boolean obstacle;
	private boolean explored;
	private boolean travelled;
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width
	private BasicArrowButton arrow;

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

	public void setObstacle(boolean obstacle) {
		this.obstacle = obstacle;
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

	public void setTravelled(boolean travelled) {
		this.travelled = travelled;
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


	public void displayDirection(Direction direction, int steps_per_sec) {
		switch (direction) {
			case NORTH:
				BasicArrowButton arrowNorth = new BasicArrowButton(BasicArrowButton.EAST);
				add(arrowNorth, BorderLayout.EAST);
				revalidate();
				repaint();
				try {
					Thread.sleep(1000/steps_per_sec);}
				catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				remove(arrowNorth);
				break;
			case SOUTH:
				BasicArrowButton arrowSouth = new BasicArrowButton(BasicArrowButton.WEST);
				add(arrowSouth, BorderLayout.WEST);
				revalidate();
				repaint();
				try {
					Thread.sleep(1000/steps_per_sec);}
				catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				remove(arrowSouth);
				break;
			case WEST:
				BasicArrowButton arrowWest = new BasicArrowButton(BasicArrowButton.NORTH);
				add(arrowWest, BorderLayout.NORTH);
				revalidate();
				repaint();
				try {
					Thread.sleep(1000/steps_per_sec);}
				catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				remove(arrowWest);
				break;
			case EAST:
				BasicArrowButton arrowEast = new BasicArrowButton(BasicArrowButton.SOUTH);
				add(arrowEast, BorderLayout.SOUTH);
				revalidate();
				repaint();
				try {
					Thread.sleep(1000/steps_per_sec);}
				catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				remove(arrowEast);
		}
	}
}