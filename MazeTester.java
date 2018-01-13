// Author: Kevin L
// https://github.com/kle510
// October 2016


//Tester program for the maze
public class MazeTester {
	
	private static final boolean FREE = false;
	private static final boolean WALL = true;
	
	public static void main(String [ ] args) { 
		
		
		//hard coded maze
		
		boolean [][] mazeData = 
				{
						{WALL,FREE,WALL},
						{WALL,FREE,WALL},
						{WALL,FREE,WALL},
						{FREE,FREE,WALL},
				};
		
		
		
		Maze m = new Maze(mazeData, new MazeCoord(1,2), new MazeCoord(4,1));
		
	    System.out.println(m.toString());

		
	}

}
