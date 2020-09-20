package test;

public class GridCell {

	private boolean obstacle;
	private boolean explored;
	private int ver_coord;// ver_coord: along length
	private int hor_coord;// hor_coord: along width

	public GridCell(int ver_coord, int hor_coord, String state) {

		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
		if (state.equals("O"))
			obstacle = true;
		else
			obstacle = false;
		explored = false;
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
}
