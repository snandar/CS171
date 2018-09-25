import java.util.Stack;

public class NQueens {
 
  //Purpose: finds and prints out all solutions to the n-queens problem
  //Input: integer representing the board size
  //Output: int number of solution and displays of solution
  public static int solve(int n) {

    //Declaration of Variables
    Stack<Integer> answer = new Stack<Integer>();
    int solutionCount = 0;
    int column = 0;
    int rowOne = 0;
    
    //Continue running until the queen on first row is at nth row
    while(rowOne < n){
    
    	//when stack size is equal to n, it is a solution
    	if(answer.size() == n){
    		
    		printSolution(answer);	//print solution
    		solutionCount++;		//increase solution Count by one
    		column = answer.pop()+1;//check if placing Queen at a different column in the same row is another solution
    		continue;
    	}
    	
    	//If there is no queen on diagonal and horizontal, and it's still on the board
    	if(queenCheck(answer, column) == false && column < n){
    		answer.push(column);	//push column value into answer stack
    		column = 0;				//go to the next row, starting at column 0
    		continue;
    	}
    	
    	//If there is a queen on diagonal and horizontal, and it's still on the board
    	if(queenCheck(answer, column) == true && column != n){
    		column++;               //check next column;
    		continue;
    	}
    	
    	//If it's off the board
    	if(column >= n){	
    		//If the stack is not empty
    		if(!answer.isEmpty()){
    			column = answer.pop()+1;	//check next column of the last element in stakc
    		}
    		
    		//If stack is empty
    		if(answer.size() == 0){	
    			rowOne++;					//increase the queen on the first row by 1
    		}
    		continue;
    	}
    }
    
    //update the following statement to return the number of solutions found
    return solutionCount;
    
  }//solve()

  //Purpose: Checks if there is a Queen to the left or right diagonal or horizonal of the current Position
  //Input: the current stack of answers, the int representing the column to check
  //Output: boolean; true = queen is present (invalid); false = queen is not present (valid)
  private static boolean queenCheck(Stack<Integer> tempAnswer, int checkPosition){
	
	//Declaration of Variables
    boolean queenCheck = false; 	//store whether current queen is in valid position, true = invalid; false = valid
    int size = tempAnswer.size(); 	//store tempAnswer stack size
    Stack<Integer> answerStorage = new Stack<Integer>();	//stores popped data
    
    //If stack is empty
    if(tempAnswer.isEmpty()){
    	return false;
    }
    
    int columnPop = checkPosition;		//stores current queen position
    int count = 1;						//keeps track of the invalid positions on diagonals	
    
    //Checks while stack is not empty
    while(!tempAnswer.isEmpty()){
    	columnPop = tempAnswer.pop();
    	answerStorage.push(columnPop);
    	int difference = checkPosition - columnPop;
    	
    	//Current position has queen on the left diagonal 
    	if(difference == count){
    		queenCheck = true;
    	}
    	//Current position has queen on the right diagonal
    	else if(difference == 0-count){
    		queenCheck = true;
    	}
    	//Current position has queen on top horizontally
    	else if(difference == 0){
    		queenCheck = true;
    	}
    	count++;
    }
    
    //Restores original stack
    for(int i=0; i<size ; i++) {
    	tempAnswer.push(answerStorage.pop());
    }

    return queenCheck;

  }//queenCheck()

  //this method prints out a solution from the current stack
  //(you should not need to modify this method)

  private static void printSolution(Stack<Integer> s) {
    for (int i = 0; i < s.size(); i ++) {
      for (int j = 0; j < s.size(); j ++) {
        if (j == s.get(i))
          System.out.print("Q ");
        else
          System.out.print("* ");
      }//for
      System.out.println();
    }//for
    System.out.println();  
  }//printSolution()
  
  // ----- the main method -----
  // (you shouldn't need to change this method)
  public static void main(String[] args) {
  
    int n = 8;
  
    // pass in parameter n from command line
    if (args.length == 1) {
      n = Integer.parseInt(args[0].trim());
      if (n < 1) {
        System.out.println("Incorrect parameter");
        System.exit(-1);
      }//if   
    }//if
    
    int number = solve(n);
    System.out.println("There are " + number + " solutions to the " + n + "-queens problem.");
    
 }//main()
  
}
