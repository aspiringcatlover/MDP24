package map;
import static main.Constants.*;
import java.awt.*;
import javax.swing.*;

public class Map extends JFrame{
	
	private int goal_coverage_perc;
	private int actual_coverage_perc;
	private GridCell[][] grid;
	public static final Color START = Color.yellow;
	public static final Color GOAL = Color.green;
	public static final Color ROBOT_CENTER = Color.orange;
	
	//simulation frame
	JFrame mapFrame = new JFrame("Map Simulator");
	mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mapFrame.setSize(2000,1500);
	mapFrame.setLocationRelativeTo(null);
	
	//constructor for simulation
	public Map(int goal_coverage_perc) {
		this.goal_coverage_perc = goal_coverage_perc;
		actual_coverage_perc = 0;
		grid = new GridCell[HEIGHT][WIDTH];
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				grid[row][col] = new GridCell(row,col);
				JLabel label = makeColorLabel(grid[row][col].getState());
				mapFrame.add(label);
				JPanel panel = new JPanel();
				if (grid[row][col].getHorCoord() == 15 && grid[row][col].getVerCoord() == 20)
					panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
				
				else if (grid[row][col].getHorCoord() == 1 && grid[row][col].getVerCoord() == 20)
					panel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK));
				
				else if (grid[row][col].getHorCoord() == 15 && grid[row][col].getVerCoord() == 1)
					panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
				
				else if (grid[row][col].getHorCoord() == 1 && grid[row][col].getVerCoord() == 1)
					panel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
				
				else if (grid[row][col].getHorCoord() == 1)
					panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
				
				else if (grid[row][col].getHorCoord() == 15)
					panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
				
				else if (grid[row][col].getVerCoord() == 1)
					panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				
				else if (grid[row][col].getVerCoord() == 20)
					panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
			}
		}
		mapFrame.pack();
		mapFrame.setVisible(true);
	}
	
	//constructor for actual
	public Map() {
		goal_coverage_perc = GOAL_COVERAGE_PERC;
		actual_coverage_perc = 0;
		grid = new GridCell[HEIGHT][WIDTH];
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				grid[row][col] = new GridCell(row,col);
				JLabel label = makeColorLabel(grid[row][col].getState());
				mapFrame.add(label);
			}
		}
		mapFrame.pack();
		mapFrame.setVisible(true);
	}
	
	//label color of grid cell
	public JLabel makeColorLabel(State s) {
        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(s) {
        case UNEXPLORED:
            label.setBackground(Color.WHITE);
            break;
        case EXPLORED:
            label.setBackground(Color.BLUE);
            break;
        case BLOCKED:
            label.setBackground(Color.RED);
            break;
        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return label;
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
	
	//Generate map descriptor part 1
	public String generateMapDes1() {
		String bitStream1 = "11";
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				if (grid[row][col].getState() == State.UNEXPLORED) 
					bitStream1 = bitStream1 + "0";
				else 
					bitStream1 = bitStream1 + "1";
			}
		}
		bitStream1 = bitStream1 + "11";
		return String.format("%016x", Integer.parseInt(bitStream1));
	}
	
	//Generate map descriptor part 2
	public String generateMapDes2() {
		String bitStream2 = "";
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				if (grid[row][col].getState() == State.EXPLORED) {
						if (grid[row][col].getState() == State.BLOCKED)
							bitStream2 = bitStream2 + "1";
						else
							bitStream2 = bitStream2 + "1";
				}
			}
		}
		return String.format("%016x", Integer.parseInt(bitStream2));
	}
	
	public void clearMap() {
		for (int row=0; row<HEIGHT; row ++) {
			for (int col=0; col<WIDTH; col++) {
				if (grid[row][col].getState() == State.EXPLORED) {
					grid[row][col].setState(State.UNEXPLORED);
				}
			}
		}
	}
}