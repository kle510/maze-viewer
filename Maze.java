import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about this maze and can find this path through the maze (if
 * there is one).
 * 
 */

public class Maze {

	private static final boolean FREE = false;
	private static final boolean WALL = true;
	private static final boolean UNVISITED = false;
	private static final boolean VISITED = true;
	public static boolean pathDisplayCalled = false;
	public LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
	private MazeCoord startDimensions;
	private MazeCoord endDimensions;
	private boolean[][] maze;
	private boolean[][] visited;

	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc) {
		maze = mazeData;
		startDimensions = new MazeCoord(startLoc.getRow(), startLoc.getCol());
		endDimensions = new MazeCoord(endLoc.getRow(), endLoc.getCol());
	}

	public int numRows() {
		return this.maze.length;
	}

	public int numCols() {
		return this.maze[0].length;
	}

	public boolean hasWallAt(MazeCoord loc) {
		if (this.maze[loc.getRow()][loc.getCol()] == WALL)
			return WALL;
		return FREE;
	}

	public MazeCoord getEntryLoc() {
		return new MazeCoord(startDimensions.getRow(), startDimensions.getCol());
	}

	public MazeCoord getExitLoc() {
		return new MazeCoord(endDimensions.getRow(), endDimensions.getCol());
	}

	public LinkedList<MazeCoord> getPath() {
		return this.path;
	}

	public boolean search() {
		visited = new boolean[this.numRows()][this.numCols()];

		for (int i = 0; i < this.numRows(); i++) {
			for (int j = 0; j < this.numCols(); j++) {
				visited[i][j] = UNVISITED;
			}
		}

		// if path is found, return true. display path in MazeComponent
		if (searchHelper(this.getEntryLoc(), this.path) == true) {
			pathDisplayCalled = true;
			return true;
		}

		return false;
	}

	private boolean searchHelper(MazeCoord currentLoc, LinkedList<MazeCoord> list) {

		// if there is wall at entry or exit, return false immediately
		if (hasWallAt(this.getEntryLoc()) == true || hasWallAt(this.getExitLoc()) == true) {
			return false;
		}

		// if (r,c) made it to the end, then true
		if (currentLoc.getRow() == (this.getExitLoc().getRow()) && currentLoc.getCol() == (this.getExitLoc().getCol())) {
			list.addFirst(currentLoc);
			return true;
		}

		if (isValidMove(currentLoc) == true) {
			// mark the current location as VISITED in 2D array
			visited[currentLoc.getRow()][currentLoc.getCol()] = VISITED;

			// go down and add position to LinkedList
			if (searchHelper(new MazeCoord(currentLoc.getRow() + 1, currentLoc.getCol()), list)) {
				list.addFirst(currentLoc);
				return true;
			}
			// go right
			if (searchHelper(new MazeCoord(currentLoc.getRow(), currentLoc.getCol() + 1), list)) {
				list.addFirst(currentLoc);
				return true;
			}
			// go up
			if (searchHelper(new MazeCoord(currentLoc.getRow() - 1, currentLoc.getCol()), list)) {
				list.addFirst(currentLoc);
				return true;
			}
			// go left
			if (searchHelper(new MazeCoord(currentLoc.getRow(), currentLoc.getCol() - 1), list)) {
				list.addFirst(currentLoc);
				return true;
			}

			// if dead end reached, mark current location as UNVISITED in 
			// 2D Array. Back track one square and explore search
			// options for previous square
			visited[currentLoc.getRow()][currentLoc.getCol()] = UNVISITED;
			return false;

		}

		return false;
	}

	private boolean isValidMove(MazeCoord currentLoc) {
		if (currentLoc.getRow() >= 0 
				&& currentLoc.getCol() >= 0 
				&& currentLoc.getRow() < this.numRows()
				&& currentLoc.getCol() < this.numCols() 
				&& hasWallAt(currentLoc) == FREE
				&& visited[currentLoc.getRow()][currentLoc.getCol()] == UNVISITED) {
			return true;
		}

		return false;
	}
}