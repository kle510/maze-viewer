import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {
	
	private Maze maze;
	private static final int START_X = 10;
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20;
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;
	private static final int REDUCEBOX = 2 * INSET;

	public MazeComponent(Maze mazeDisplay) {
		maze = mazeDisplay;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// draw maze border
		g2.setColor(Color.BLACK);
		Rectangle mazeExterior = new Rectangle(START_X, START_Y, BOX_HEIGHT * maze.numCols(),
				BOX_WIDTH * maze.numRows());
		g2.draw(mazeExterior);

		// draw maze
		for (int i = 0; i < maze.numRows(); i++) {
			for (int j = 0; j < maze.numCols(); j++) {
				if (maze.hasWallAt(new MazeCoord(i, j)) == true) {
					g2.fillRect(START_X + j * BOX_HEIGHT, START_Y + i * BOX_WIDTH, BOX_HEIGHT, BOX_WIDTH);
				}
			}
		}

		// draw yellow entry box
		g2.setColor(Color.YELLOW);
		g2.fillRect(START_X + maze.getEntryLoc().getCol() * BOX_HEIGHT + INSET,
				START_Y + maze.getEntryLoc().getRow() * BOX_WIDTH + INSET, BOX_HEIGHT - 2 * INSET,
				BOX_WIDTH - 2 * INSET);

		// draw green exit box
		g2.setColor(Color.GREEN);
		g2.fillRect(START_X + maze.getExitLoc().getCol() * BOX_HEIGHT + INSET,
				START_Y + maze.getExitLoc().getRow() * BOX_WIDTH + INSET, BOX_HEIGHT - REDUCEBOX,
				BOX_WIDTH - REDUCEBOX);

		// if search called and path found, draw the blue path
		LinkedList<MazeCoord> list = maze.getPath();
		if (maze.pathDisplayCalled) {
			g2.setColor(Color.BLUE);

			ListIterator<MazeCoord> iterator = list.listIterator();

			// initialize first MazeCoord in linked list
			MazeCoord path1 = iterator.next();

			// Draw line between first and second MazeCoord and update values
			// until there are no more values in linked list
			while (iterator.hasNext()) {
				MazeCoord path2 = iterator.next();

				g2.drawLine(START_X + path1.getCol() * BOX_HEIGHT + BOX_HEIGHT / 2,
						START_Y + path1.getRow() * BOX_WIDTH + BOX_WIDTH / 2,
						START_X + path2.getCol() * BOX_HEIGHT + BOX_HEIGHT / 2,
						START_Y + path2.getRow() * BOX_WIDTH + BOX_WIDTH / 2);

				path1 = path2;
			}
			
			list.clear();
			maze.pathDisplayCalled = false;
		}
	}
}
