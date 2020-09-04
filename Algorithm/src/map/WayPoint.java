package map;

//waypoint represents the end point for the robot to reach
public class WayPoint {

	private int ver_coord;
	private int hor_coord;
	
	//constructor
	public WayPoint(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
	}
	
	//getters and setters
	public int getVerCoord() {
		return ver_coord;
	}
	
	public int getHorCoord() {
		return hor_coord;
	}
	
	public void setVerCoord(int ver_coord) {
		this.ver_coord = ver_coord;
	}
	
	public void setHorCoord(int hor_coord) {
		this.hor_coord = hor_coord;
	}
}


