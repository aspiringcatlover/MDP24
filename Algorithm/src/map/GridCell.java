package map;
import java.awt.*;
import Main.Constants.*;

public class GridCell {

	enum State{UNEXPLORED, EXPLORED, BLOCKED, START, GOAL}
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width
	private State state;
	
	//constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
		state = State.UNEXPLORED;
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
