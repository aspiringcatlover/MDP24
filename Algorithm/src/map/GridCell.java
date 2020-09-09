package map;
import java.awt.*;
import javax.swing.*;
import Main.Constants.*;
import robot.*;
import robot.Robot;

public class GridCell extends JPanel {

	enum State{UNEXPLORED, EXPLORED, BLOCKED, START, GOAL}
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width
	private State state;
	
	//constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
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

	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean isBlocked(State state) {
		if (this.state == State.BLOCKED)
			return true;
		else
			return false;
	}
	
	public boolean hasExplored(State state) {
		if (this.state == State.EXPLORED)
			return true;
		else
			return false;
	}
	
}
