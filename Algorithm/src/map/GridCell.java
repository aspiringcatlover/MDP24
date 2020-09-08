package map;

import java.awt.*;
<<<<<<< Updated upstream
import static main.Constants.*;
import main.Constants.*;
=======
import javax.swing.*;
import Main.Constants.*;
import robot.*;
import robot.Robot;
>>>>>>> Stashed changes

public class GridCell extends JPanel {

	private int ver_coord;// ver_coord: along length
	private int hor_coord;// hor_coord: along width
	private boolean explored;
	private boolean obstacle;

	// constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
<<<<<<< Updated upstream
		explored = false;
		obstacle = false;
=======
		Robot robot = new Robot();
		if((this.hor_coord == 1 && this.ver_coord == 1) || (this.hor_coord == 1 && this.ver_coord == 2) || (this.hor_coord == 1 && this.ver_coord == 3) || (this.hor_coord == 2 && this.ver_coord == 1) || (this.hor_coord == 2 && this.ver_coord == 2) || (this.hor_coord == 2 && this.ver_coord == 3) || (this.hor_coord == 3 && this.ver_coord == 1) || (this.hor_coord == 3 && this.ver_coord == 2) || (this.hor_coord == 3 && this.ver_coord == 3)) {
			state = State.START;
			setBackground(Color.YELLOW);
		} 
		else if ((this.hor_coord == 13 && this.ver_coord == 18) || (this.hor_coord == 13 && this.ver_coord == 19) || (this.hor_coord == 13 && this.ver_coord == 20) || (this.hor_coord == 14 && this.ver_coord == 18) || (this.hor_coord == 14 && this.ver_coord == 19) || (this.hor_coord == 14 && this.ver_coord == 20) || (this.hor_coord == 15 && this.ver_coord == 18) || (this.hor_coord == 15 && this.ver_coord == 19) || (this.hor_coord == 15 && this.ver_coord == 20)) {
			state = State.GOAL;
			setBackground(Color.GREEN);
		}
		else if (this.hor_coord == robot.getXCoord() && this.ver_coord == robot.getYCoord())
		else {
			state = State.EXPLORED;
			setBackground(Color.ORANGE);
		}
		setOpaque(true); 
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension (20, 20));
>>>>>>> Stashed changes
	}

	// getters and setters
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
	
	public void setExplored(boolean isExplored) {
		explored = isExplored;
	}
	
	public boolean getObstacle() {
		return obstacle;
	}
	
	public void setObstacle(boolean isObstacle) {
		obstacle = isObstacle;
	}

}
