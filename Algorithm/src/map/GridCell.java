package map;
import Main.Constants.*;

public class GridCell {

	enum State{UNEXPLORED, EXPLORED, BLOCKED}
	private int ver_coord;
	private int hor_coord;
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
}
