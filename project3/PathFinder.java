import java.io.*;
import java.util.Scanner;

import javax.xml.soap.Node;

import java.util.ArrayDeque;

/*
 * Recursive class to represent a position in a path
 */
class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	
	// reference to the previous position (parent) that leads to this position on a path
	Position parent;
	
	Position(int x, int y, char v){
		i=x; j = y; val=v;
	}
	
	Position(int x, int y, char v, Position p){
		i=x; j = y; val=v;
		parent=p;
	}
	
}


public class PathFinder {
	
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		char [][] maze;
		maze = readMaze(args[0]);
		printMaze(maze);
		System.out.println("stackSearch Solution:");
		Position [] path = stackSearch(maze);
		printMaze(maze);
		
		char [][] maze2 = readMaze(args[0]);
		System.out.println("queueSearch Solution:");
		Position[] path2 = queueSearch(maze2);
		printMaze(maze2);
	}
	
	//Purpose: Algorithum to find a path through the maze using stacks to manage search list
	//Input: maze to find the path for in the form as char[][]
	//Outut: mark the path in the maze, and return array of Position corresponding to path, or null if no path found
	public static Position[] stackSearch(char[][] maze){

		//If maze is closed from the entrance
		if(maze[0][0] == '1'){
			return null;
		}
		
		//Declaration of Variables
		char[][] mazeCopy = copyMaze(maze);		//copies the maze and creates scratch maze to work with
		char visited = 'X';						//char variable to mark places that were visited
		Position[] answer;						//Array of position that represents the path
		Position next;							//Position to represent the next potential path to go to 
		ArrayDeque<Position> remainingStack = new ArrayDeque<Position>();	//Stack with position that needs to be checked
		Position current = new Position(0, 0, '0');	//Current Position that is to checked (initialized to entrance)
		
		current.parent = null;					//The previous position prior to entrance is null
		remainingStack.push(current);			//Push entrance into stack of position to be checked
		mazeCopy[0][0] = visited;				//Mark entrance as visited.
		
		//While stack of position to be checked is not empty
		while(!remainingStack.isEmpty()){
			
			current = remainingStack.pop();		//Pop the last thing added to the the stack 
			int row = current.i;				//Gets the current row
			int column = current.j;				//Gets the current column
			
			//If current position is at exit point
			//Constructs a path and returns a path
			if(row == mazeCopy.length-1 && column == mazeCopy.length-1){
				
				//Counts the number of position in the linked list of position representing path
				int countParent = 0;			
				Position x = current;
				while(x != null){		//Traverse through the linked list starting from current
					countParent++;		//+1 for each position
					x = x.parent;		//Move x pointer to point to current x's parent
				}
				
				//Creates an array of position with the length of countParent
				answer = new Position[countParent--];

				//Put the position representing path into the array answer
				//Marks the original maze
				Position y = current;			//Traverse through the linked list starting from current
				for(int i=0; y != null; i++){	
					answer[countParent--] = y;	//Put the position at y into answer array at i
					maze[y.i][y.j]=visited;		//Marks the original maze char[][] with the path
					y=y.parent;					//Move y pointer to point at current y's parent
				}
				
				printPath(answer);		//Prints the path
				
				//Return the array of position representing the path
				return answer;
			}
			
			//If the position above the current position is a potential path
			if(inBounds(mazeCopy, row+1, column) && mazeCopy[row+1][column] == '0'){
				remainingStack.push(next = new Position(row+1, column, '0'));	//Push the position above the current position into the stack with the list  position
				mazeCopy[row][column] = visited;								//Mark the current position as visited
				next.parent = current;											//Set the current position as next's parent

			}
			
			//If the position below the current position is a potential path
			if(inBounds(mazeCopy, row-1, column) && mazeCopy[row-1][column] == '0'){
				remainingStack.push(next = new Position(row-1, column, '0'));	//Push the potential position into the stack
				mazeCopy[row][column] = visited;								//Mark the current position as visited
				next.parent = current;											//Set the current position as next's parent

			}
			
			//If the position on the right of current position is a potential path
			if(inBounds(mazeCopy, row, column+1) && mazeCopy[row][column+1] == '0'){
				remainingStack.push(next = new Position(row, column+1, '0')); 	//Push the potential position into the stack
				mazeCopy[row][column] = visited;								//Mark the current position as visited
				next.parent = current;											//Set the current position as next's parent

			}
			
			//If the position on the left of current position is a potential path
			if(inBounds(mazeCopy, row, column-1) && mazeCopy[row][column-1] == '0'){
				remainingStack.push(next = new Position(row, column-1, '0'));	//Push the potential position into the stack
				mazeCopy[row][column] = visited;								//Mark the current position as visited
				next.parent = current;											//Set the current position as next's parent

			}
			
			
			//dead-end and the list of position to check on stack is not empty
			else{
				mazeCopy[row][column] = visited;	//Mark current position as visited
			}
			
		}
		
		//If dead end and there is no more position to check on stack
		System.out.println("No path exists");
		return null;

	}

	//Purpose: Find the path through the maze using queue to manage search list
	//Input: the maze in the form char[][]
	//Output: Array of Position corresponding to path, or null if no path found
	public static Position [] queueSearch(char [] [] maze){ 
		
		char[][] mazeCopy = copyMaze(maze);	//creates a copy of the maze
		char visited = 'X';					//variable to show it's visited
		Position[] answer;					//Array of Position corresponding to path, or null if no path found
		Position next;						//Position object that keeps in track of the next Position of current Position
		ArrayDeque<Position> remainingQueue = new ArrayDeque<Position>();	//Queue to keep track of remaining Positions to check in maze
		
		//If maze is blocked from the entrance
		if(mazeCopy[0][0] == '1'){
			return null;	//exist (no path)
		}
		
		Position current = new Position(0, 0, '0');	//Position object to reference the current Position in maze add the entrance 
		current.parent = null;						//Set parent of entrance to null			
		remainingQueue.add(current);				//Add entrance into Positions to check
		mazeCopy[0][0] = visited;					//Mark entrance as visited
		
		//Runs loop till Queue is empty or until path is found
		while(!remainingQueue.isEmpty()){
			
			current = remainingQueue.remove();		//Remove the first position added to the Queue
			int row = current.i;					//Get the row number on maze
			int column = current.j;					//Get the column number on maze
			
			//If exit is found where row and column is at n-1
			if(row == mazeCopy.length-1 && column == mazeCopy.length-1){
				
				//Count how many Positions are connected in the linked list representing the path
				int countParent = 0;
				Position x = current;
				while(x != null){		//Traverse through the list till position x is null
					countParent++;		//increase the count of positions
					x = x.parent;		//move x pointer to point to x's parent
				}
				
				//create an array of position with the size of countParent
				answer = new Position[countParent--];

				//Put the path into the array "answer"
				//Marks the original maze according to the path
				Position y = current;
				for(int i=0; y != null; i++){	//Traverse through the current currently at exit
					answer[countParent--] = y;	//Put path position into array
					maze[y.i][y.j]=visited;		//Marks original maze
					y=y.parent;					//move y pointer to point to y's parent
				}
				
				//Prints the path in ([]) form
				printPath(answer);
				
				return answer;
			}
			
			//If row position above current is a potential path(next)
			if(inBounds(mazeCopy, row+1, column) && mazeCopy[row+1][column] == '0'){
				
				remainingQueue.add(next = new Position(row+1, column, '0'));	//add the position into queue of position to check
				mazeCopy[row][column] = visited;								//marks current position as visited
				next.parent = current;											//set the parent of the potential path(next) to current
			
			}
			
			//If row position below current is a potential path(next)
			if(inBounds(mazeCopy, row-1, column) && mazeCopy[row-1][column] == '0'){
				
				remainingQueue.add(next = new Position(row-1, column, '0'));
				mazeCopy[row][column] = visited;
				next.parent = current;

			}
			
			//If column on the right of current is a potential path(next)
			if(inBounds(mazeCopy, row, column+1) && mazeCopy[row][column+1] == '0'){
				
				remainingQueue.add(next = new Position(row, column+1, '0'));
				mazeCopy[row][column] = visited;
				next.parent = current;

					
			}
			
			//If column on the left of current is a potential path(next)
			if(inBounds(mazeCopy, row, column-1) && mazeCopy[row][column-1] == '0'){
				
				remainingQueue.add(next = new Position(row, column-1, '0'));
				mazeCopy[row][column] = visited;
				next.parent = current;

			}

			//dead-end and queue of position to check and queue is not empty
			else{
				mazeCopy[row][column] = visited;	//marks current position as visited
			}
			
		}
		
		//If queue of position to check is empty and there is no path
		System.out.println("No path exists");
		return null;

	}

	//Purpose: to print out the path in ([]) this form
	//Input: Array of Position 
	//Output: print out the path in correct order is path is not empty, if path is empty prints ([])
	public static void printPath(Position [] path){
		
		//Try to print the array of path
		try{
			//Create a object position and set to null
			Position position = null;
			System.out.print("(");	//Prints beginning (
			
			//For all the Position in the path array
			for(int i=0; i<path.length; i++){
			
				position = path[i];		//set position to Position object at i
				
				//If it is the last position in the array
				if(i == path.length-1){
					System.out.print("["+position.i+"] ["+position.j+"]");
				}
				//If it is not the last position in the array
				else {
					System.out.print("["+position.i+"] ["+position.j+"], ");
				}
			}
			System.out.println(")"); //Prints ending )
		}
		
		//If path is blocked and hence path array is null
		catch(Exception e) {
			System.out.println("([])");
		}
		
	}
	
	//Purpose: to copy the maze
	//Input: char[][] 2D char array to be copied
	//Output: char[][] 2D char array that is a copy
	public static char[][] copyMaze(char[][] maze){

		char[][] newMaze = new char[maze.length][maze.length];
		
		//Copy the character on each row 
		for(int i=0; i<maze.length ;i++){
			//Copy the character on each column
			for(int j=0; j<maze[0].length; j++) {
				//put character into the copy
				newMaze[i][j] = maze [i][j];
			}
		}

		return newMaze;

	}

	//Purpose: Helper Method to check if the row and column being checked is in bounds 
	//Input: maze, and int representing row and column
	//Output: true: if row and column is in bounds. false: if row and column is out of bounds.
	private static boolean inBounds(char[][] maze, int row, int column){

		//try to find the row and column in the maze 
		try{
			boolean inBounds = maze[row][column] == '0' || maze[row][column] == '1' || maze[row][column] =='X';
			return inBounds;
		}
		//Catch array out of bounds error
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
		
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}
		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}

}
