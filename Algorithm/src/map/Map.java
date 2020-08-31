package map;
import static Main.Constants.*;

public class Map {
	
	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private GridCell[][] grid;
	
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
}
