// Author: Kevin L
// https://github.com/kle510
// October 2016

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import java.io.File;

/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 * java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start
 * location, and ending with the exit location. Each maze location is either a
 * wall (1) or free (0). Here is an example of contents of a file for a 3x4
 * maze, with start location as the top left, and exit location as the bottom
 * right (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 0111 0000 1110 0 0 2 3
 * 
 */

public class MazeViewer {

	// define input char from input file
	private static final char WALL_CHAR = '1';
	private static final char FREE_CHAR = '0';
	
	//define boolean setting for input maze
	private static final boolean FREE = false;
	private static final boolean WALL = true;

	public static void main(String[] args) {

		String fileName = "";

		try {

			if (args.length < 1) {
				System.out.println("ERROR: missing file name command line argument");
			} else {
				fileName = args[0];

				JFrame frame = readMazeFile(fileName);

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setVisible(true);
			}

		} catch (FileNotFoundException exc) {
			System.out.println("File not found: " + fileName);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * readMazeFile reads in maze from the file whose name is given and returns
	 * a MazeFrame created from it.
	 * 
	 * @param fileName
	 *            the name of a file to read from (file format shown in class
	 *            comments, above)
	 * @returns a MazeFrame containing the data from the file.
	 * 
	 * @throws FileNotFoundException
	 *             if there's no such file (subclass of IOException)
	 * @throws IOException
	 *             (hook given in case you want to do more error-checking. that
	 *             would also involve changing main to catch other exceptions)
	 */
	private static MazeFrame readMazeFile(String fileName) throws IOException {

		//reads in file to Scanner
		File inFile = new File(fileName);
		Scanner in = new Scanner(inFile);
		
		//reads in rows and cols of maze
		int row = in.nextInt();
		int col = in.nextInt();

		
		//take input char of maze and converts it to a boolean 2D array
		boolean[][] mazeData = new boolean[row][col];

		in.nextLine();
		in.useDelimiter("");

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < col; j++) {

				char nextChar = in.next().charAt(0);

				if (nextChar == WALL_CHAR) {
					mazeData[i][j] = WALL;

				} else if (nextChar == FREE_CHAR) {
					mazeData[i][j] = FREE;

				}

			}
			in.nextLine();

		}

		in.reset(); // reset delimiter

		//reads in MazeCoord values for entryLoc and exitLoc
		MazeCoord entryLoc = new MazeCoord(in.nextInt(), in.nextInt());
		MazeCoord exitLoc = new MazeCoord(in.nextInt(), in.nextInt());

		return new MazeFrame(mazeData, entryLoc, exitLoc);

	}

}