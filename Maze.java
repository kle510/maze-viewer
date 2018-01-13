// Author: Kevin L
// https://github.com/kle510
// October 2016

import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 */

public class Maze {

	// define boolean setting for input maze
	private static final boolean FREE = false;
	private static final boolean WALL = true;

	// define boolean setting for visited maze
	private static final boolean UNVISITED = false;
	private static final boolean VISITED = true;

	// boolean test if search passes, then display path in MazeComponent
	// (public, since it's called in MazeComponent)
	public static boolean pathDisplayCalled = false;

	public LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
	private MazeCoord startDimensions;
	private MazeCoord endDimensions;
	private boolean[][] maze;
	private boolean[][] visited;

	/**
	 * Constructs a maze.
	 * 
	 * @param mazeData
	 *            the maze to search. See general Maze comments for what goes in
	 *            this array.
	 * @param startLoc
	 *            the location in maze to start the search (not necessarily on
	 *            an edge)
	 * @param endLoc
	 *            the "exit" location of the maze (not necessarily on an edge)
	 *            PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <=
	 *            startLoc.getCol() < mazeData[0].length and 0 <=
	 *            endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() <
	 *            mazeData[0].length
	 * 
	 */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc) {

		maze = mazeData;
		startDimensions = new MazeCoord(startLoc.getRow(), startLoc.getCol());
		endDimensions = new MazeCoord(endLoc.getRow(), endLoc.getCol());

	}

	/**
	 * Returns the number of rows in the maze
	 * 
	 * @return number of rows
	 */
	public int numRows() {
		Maze a = this;

		return a.maze.length;
	}

	/**
	 * Returns the number of columns in the maze
	 * 
	 * @return number of columns
	 */
	public int numCols() {
		Maze a = this;
		return a.maze[0].length;
	}

	/**
	 * Returns true iff there is a wall at this location
	 * 
	 * @param loc
	 *            the location in maze coordinates
	 * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows()
	 *         and 0 <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) {
		Maze a = this;
		if (a.maze[loc.getRow()][loc.getCol()] == WALL) {
			return WALL;
		}

		return FREE;
	}

	/**
	 * Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc() {
		return new MazeCoord(startDimensions.getRow(), startDimensions.getCol());
	}

	/**
	 * Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc() {
		return new MazeCoord(endDimensions.getRow(), endDimensions.getCol());
	}

	/**
	 * Returns the path through the maze. First element is starting location,
	 * and last element is exit location. If there was not path, or if this is
	 * called before search, returns empty list.
	 * 
	 * @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() {

		// create an empty linked list. getPath will get called whenever the
		// search passes the base cases to add that current MazeCoord to the
		// path

		Maze a = this;
		LinkedList<MazeCoord> list = a.path;

		return list;

	}

	/**
	 * Find a path through the maze if there is one. Client can access the path
	 * found via getPath method. Relies on searchHelper method for recursive
	 * search.
	 * 
	 * @return whether path was found.
	 */
	public boolean search() {
		Maze a = this;
		LinkedList<MazeCoord> list = a.path;

		// initialize visited array
		int visitedRows = a.numRows();
		int visitedCols = a.numCols();
		visited = new boolean[visitedRows][visitedCols];

		for (int i = 0; i < visitedRows; i++) {
			for (int j = 0; j < visitedCols; j++) {
				visited[i][j] = UNVISITED;
			}
		}

		// if path is found, return true. display path in MazeComponent
		if (searchHelper(a.getEntryLoc(), list) == true) {
			pathDisplayCalled = true;

			return true;
		}
		// if path is not found, return false
		else {
			return false;
		}

	}

	/**
	 * helper method for search method. performs recursive search from start
	 * location. relies on isValidMove helper method, which determines if the
	 * move to the next square is possible.
	 * 
	 * @param current
	 *            location of the maze, LinkedList of maze coordinates
	 * @return whether path was found.
	 */
	
	private boolean searchHelper(MazeCoord currentLoc, LinkedList<MazeCoord> list) {
		Maze a = this;

		// if there is wall at entry or exit, return false immediately
		if (hasWallAt(a.getEntryLoc()) == true || hasWallAt(a.getExitLoc()) == true) {
			return false;
		}

		// if (r,c) made it to the end, then true
		if (currentLoc.getRow() == (a.getExitLoc().getRow()) && currentLoc.getCol() == (a.getExitLoc().getCol())) {
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

	/**
	 * helper function for searchHelper. determines if the current location can
	 * proceed with the next move.
	 * 
	 * @param current location of the maze
	 * @return true if all conditions are passed.
	 */


	private boolean isValidMove(MazeCoord currentLoc) {
		Maze a = this;

		// return true if current position is in boundaries of maze,
		// if there is no wall,
		// and if the position has not been visited before
		
		if (currentLoc.getRow() >= 0 && currentLoc.getCol() >= 0 && currentLoc.getRow() < a.numRows()
				&& currentLoc.getCol() < a.numCols() && hasWallAt(currentLoc) == FREE
				&& visited[currentLoc.getRow()][currentLoc.getCol()] == UNVISITED) {
			return true;
		}

		return false;
	}


}