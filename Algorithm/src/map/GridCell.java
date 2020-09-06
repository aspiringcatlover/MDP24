package map;

import java.awt.*;
import static main.Constants.*;
import main.Constants.*;

public class GridCell {

	private int ver_coord;// ver_coord: along length
	private int hor_coord;// hor_coord: along width
	private boolean explored;
	private boolean obstacle;

	// constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
		explored = false;
		obstacle = false;
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

}
