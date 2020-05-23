import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
   MazeFrame class
   
   Shows the status of the maze search and the maze itself after a successful search. 
   A key listener is used to start the maze search.   
 */

public class MazeFrame extends JFrame {
   private JLabel searchStatusLabel;  
   private MazeComponent mazeComponent;
   private Maze maze;
   private static final int FRAME_WIDTH = 500;
   private static final int FRAME_HEIGHT = 500;
   private static final String PROMPT_STRING = "Type any key to start maze search . . . ";
   private static final String SUCCESS_STRING = "Found the way out!";
   private static final String FAIL_STRING = "No path out of maze.";
   
   /*
      Sets up the GUI components with the given maze data. 
   */
   public MazeFrame(boolean[][] mazeData, MazeCoord entryLoc, MazeCoord exitLoc) {

      setSize(FRAME_WIDTH, FRAME_HEIGHT);

      searchStatusLabel = new JLabel(PROMPT_STRING);
      add(searchStatusLabel, BorderLayout.NORTH); 
      maze = new Maze(mazeData, entryLoc, exitLoc);

      mazeComponent = new MazeComponent(maze);
      add(mazeComponent, BorderLayout.CENTER); 

      KeyAdapter listener = new MazeKeyListener(); 
      addKeyListener(listener);
      setFocusable(true);

   }
   
   /*
      getSearchMessage 
      returns the message to display for a successful or failed search.
   */
   public String getSearchMessage(boolean success) {
      if (success) {
         return SUCCESS_STRING;
      } else {
         return FAIL_STRING;
      }
   }

   class MazeKeyListener extends KeyAdapter {
      public void keyPressed(KeyEvent event) {
         System.out.println("DEBUG: key pressed");
         System.out.println("DEBUG: doing maze search. . . ");

         boolean success = maze.search();
         mazeComponent.repaint();
         
         System.out.println("DEBUG: " + getSearchMessage(success));
         searchStatusLabel.setText(getSearchMessage(success));
      }
   }
}