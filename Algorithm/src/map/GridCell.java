package map;
import java.awt.*;
import simulator.SimulatorRobot;
import javax.swing.*;
import robot.*;
import robot.Robot;

public class GridCell extends JPanel {

	private boolean obstacle;
	private boolean explored;
	private int ver_coord;//ver_coord: along length
	private int hor_coord;//hor_coord: along width
	private SimulatorRobot simRobot = new SimulatorRobot(1);
	
	//constructor
	public GridCell(int ver_coord, int hor_coord) {
		this.ver_coord = ver_coord;
		this.hor_coord = hor_coord;
//		Robot robot = new Robot();
		//start point
		if((this.hor_coord == 0 && this.ver_coord == 0) || (this.hor_coord == 0 && this.ver_coord == 1) || (this.hor_coord == 0 && this.ver_coord == 2) || (this.hor_coord == 1 && this.ver_coord == 0) || (this.hor_coord == 1 && this.ver_coord == 1) || (this.hor_coord == 1 && this.ver_coord == 2) || (this.hor_coord == 2 && this.ver_coord == 0) || (this.hor_coord == 2 && this.ver_coord == 1) || (this.hor_coord == 2 && this.ver_coord == 2)) {
			setBackground(Color.YELLOW);
		} 
		//end point
		else if ((this.hor_coord == 12 && this.ver_coord == 17) || (this.hor_coord == 12 && this.ver_coord == 18) || (this.hor_coord == 12 && this.ver_coord == 19) || (this.hor_coord == 13 && this.ver_coord == 17) || (this.hor_coord == 13 && this.ver_coord == 18) || (this.hor_coord == 13 && this.ver_coord == 19) || (this.hor_coord == 14 && this.ver_coord == 17) || (this.hor_coord == 14 && this.ver_coord == 18) || (this.hor_coord == 14 && this.ver_coord == 19)) {
			setBackground(Color.GREEN);
		}
		if (this.isRobot(ver_coord, hor_coord)) {
			setBackground(Color.ORANGE);
		}
		else {
			setBackground(Color.BLUE);
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

	public boolean getObstacle() {
		return obstacle;
	}
	
	public boolean getExplored() {
		return explored;
	}
	
	public void setExplored(boolean explored) {
		this.explored = explored;
	}
	
	//set color
	public void setColor() {
		if (explored) {
			setBackground(Color.WHITE);
		}
		if (obstacle){
			setBackground(Color.RED);
		}
	}
	
	public boolean isRobot(int ver_coord, int hor_coord) {
		if (simRobot.getXCoord() == hor_coord && simRobot.getYCoord() == ver_coord)
			return true;
		else
			return false;
	}
	
	
	
}