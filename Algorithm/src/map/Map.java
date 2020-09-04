package map;
import static Main.Constants.*;
import java.awt.*;
import javax.swing.*;

public class Map extends JPanel {
	
	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private GridCell[][] grid;
	public static final Color CELL_BORDER = Color.gray;
	public static final Color WALL = Color.black;
	public static final Color START = Color.yellow;
	public static final Color GOAL = Color.green;
	public static final Color OBSTACLE = Color.red;
	public static final Color UNEXPLORED = Color.white;
	public static final Color EXPLORED = Color.blue;
	public static final Color ROBOT_CENTER = Color.orange;
	
	
	//constructor for simulation
	public Map(int goal_coverage_perc) {
		this.goal_coverage_perc = goal_coverage_perc;
		actual_coverage_perc = 0;
		grid = new GridCell[HEIGHT][WIDTH];
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				grid[row][col] = new GridCell(row,col);
			}
		}
	}
	
	//constructor for actual
	public Map() {
		goal_coverage_perc = GOAL_COVERAGE_PERC;
		actual_coverage_perc = 0;
		grid = new GridCell[HEIGHT][WIDTH];
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				grid[row][col] = new GridCell(row,col);
			}
		}
	}
	
	//getters and setters
	public void setGoalCoveragePerc(int goal_coverage_perc) {
		this.goal_coverage_perc = goal_coverage_perc;
	}
	
	public int getGoalCoveragePerc() {
		return goal_coverage_perc;
	}
	public void setActualCoveragePerc(int actual_coverage_perc) {
		this.actual_coverage_perc = actual_coverage_perc;
	}
	
	public int getActualCoveragePerc() {
		return actual_coverage_perc;
	}
	
	//grid for testing
//	int[][] testgrid = {	
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//	};
	
	//update simulation map
	public void updateMap(GridCell[][] grid) {
	  for (int row=0; row<HEIGHT; row ++) {
		for (int col=0; col<WIDTH; col++) {
			grid[row][col].setBackground(UNEXPLORED);
		}
	  }
	}
	
	//clear simulation map
	public void clearMap() {
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				grid[row][col].setBorder(BorderFactory.createLineBorder(CELL_BORDER, 1));
			}
		}
	}
	
	public blocked() {
		//get robot coordinate
		//get sensor data from front, left and right
		
		boolean frontblocked = GridCell.isBlocked();
		boolean leftblocked = GridCell.isBlocked();
		boolean rightblocked = GridCell.isBlocked();
		
		if (frontblocked && leftblocked && rightblocked) {
			return true;
			//move back to previous gridcell
		}
		else
			return false;
	}
	
	public explored() {
		//get robot coordinate
		//get sensor data from front, left and right
		
		boolean frontexplored = GridCell.hasExplored();
		boolean leftexplored = GridCell.hasExplored();
		boolean rightexplored = GridCell.hasExplored();
			
	}
}
