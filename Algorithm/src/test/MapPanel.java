package test;

import static test.Constants.*;

public class MapPanel {

	private GridCell[][] gridcells;

	// constructor
	public MapPanel(String[][] sample_map) {
		gridcells = new GridCell[HEIGHT][WIDTH];
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				GridCell gridCell = new GridCell(row, col, sample_map[row][col]);
				gridcells[row][col] = gridCell;
			}
		}
	}

	// getter and setter
	public GridCell getGridCell(int y, int x) {
		// System.out.println("y: "+y+" x: "+x);
		if ((y < 0) || (x < 0) || (y >= gridcells.length) || (x >= gridcells[y].length))
			return null;

		return gridcells[y][x];
	}

	public void setGridCell(int y, int x, GridCell gridCell) {
		this.gridcells[y][x] = gridCell;
	}

}
