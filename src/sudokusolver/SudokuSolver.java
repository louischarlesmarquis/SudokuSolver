
package sudokusolver;

/*
    By: Louis-Charles Marquis
    Date: January 13th, 2022

    The following program can solve 9x9 sudokus of any difficulty. 
    Simply change the grid in the main method to your own grid.
    It will also display if there exist a solution or not for the given grid
    
    Possible future implementations: I might make a GUI, add the time it took for the algorithm 
        to solve the sudoku and, eventually, when I will be skilled enough, an AI reader that will be able 
        to scan a sudoku grid and solve it!!!
*/

public class SudokuSolver {
    
    private static final int SIZE_OF_GRID = 9; //we will solve 9x9 sudoku grid
    
    public static void main(String[] args) {
        
        System.out.println("Grid:");
        
        //sudoku grid to be solved, where 0 represents an empty cell
        //change grid to your own convenience
        int[][] grid = {
            {7, 0, 2, 0, 5, 0, 6, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 0, 0},
            {1, 0, 0, 0, 0, 9, 5, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 9, 0},
            {0, 4, 3, 0, 0, 0, 7, 5, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 8},
            {0, 0, 9, 7, 0, 0, 0, 0, 5},
            {0, 0, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 7, 0, 4, 0, 2, 0, 3} 
          };

        show(grid);
        
        System.out.println("------------------------------------------");
        
        if (solution(grid)) {
            System.out.println("A solution has been found by the algorithm!");
        }
        else {
            System.out.println("No solution found:(");
        }
        
        show(grid);
    }
    
    //method that prints the 9x9 grid
    private static void show(int[][] grid) {
        for (int row = 0; row < SIZE_OF_GRID; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("___________");
            }
            for (int column = 0; column < SIZE_OF_GRID; column++) {
                if (column % 3 == 0 && column != 0) {
                  System.out.print("|");
                }
                System.out.print(grid[row][column]);
            }
            System.out.println();
        }
    }

    //this method will be used to check if the number is in the row
    private static boolean inRow(int[][] grid, int num, int row) {
        for (int i = 0; i < SIZE_OF_GRID; i++) { //goes through every element of the row
            if (grid[row][i] == num) {
                return true;
            }
        }
        return false; //no match found
    }

    //this method will be used to check if the number is in the column
    private static boolean inColumn(int[][] grid, int num, int column) {
        for (int i = 0; i < SIZE_OF_GRID; i++) { //goes through every element of the column
            if (grid[i][column] == num) { //check for match
                return true;
            }
        }
        return false; //no match found
    }

    //this method will be used to check if the num is in the 3x3 subgrid
    private static boolean inSubGrid(int[][] grid, int num, int row, int column) {
        //get the coordinates of the top-left element of the subgrid
        //the top left element is always going to be 0 or a multiple of 3; (0, 3, 6)
        //thus, we use row%3 to remove the "excess" space and get 0 or a multiple of 3
        int subGridRow = row - row % 3; //0, 3 or 6
        int subGridColumn = column - column % 3; //0, 3 or 6
        
        for (int i = subGridRow; i < subGridRow + 3; i++) { 
            for (int j = subGridColumn; j < subGridColumn + 3; j++) { //start from top left element of subGrid
                if (grid[i][j] == num) { //check for match
                    return true;
                }
            }
        }
        return false; //no match found
    }

    //method checking if the cell is valid for a given num
    private static boolean check(int[][] grid, int num, int row, int column) {
        //for the cell to be valid for the num, the num shouldn't be found in the same row, column of subGrid
        //therefore, we use the ! operator to negate the method's output
        //for the num to be in a valid cell, all 3 conditions should be met, thus we should use &&
        return !inRow(grid, num, row) &&
            !inColumn(grid, num, column) &&
            !inSubGrid(grid, num, row, column);
    }

    //the algorithm that sovles the sudoku puzzle
    private static boolean solution(int[][] grid) {
        /*
            logic used to solve this problem:
            1. go through each cell of the grid (nested for loops)
            2. for each empty cell, try all possibilities of numbers from 1 to 9 (check method)
            3. if a try/guess is not correct, backtrack to the number and check the next valid number (recursion)
        */
        //Step 1: go through every cell in the grid
        for (int row = 0; row < SIZE_OF_GRID; row++) {
            for (int column = 0; column < SIZE_OF_GRID; column++) {
                // Step 2: if cell is empty, proceed with code below, otherwise goes through next cell
                if (grid[row][column] == 0) { //recall that 0 represents an empty cell
                    for (int tryNumber = 1; tryNumber <= SIZE_OF_GRID; tryNumber++) { //try numbers from 1 to 9
                        if (check(grid, tryNumber, row, column)) { //check if number is valid
                            grid[row][column] = tryNumber;
                            //Step 3: recursion method
                            if (solution(grid)) {
                                return true; //if all of our tries/guesses are correct, it will eventually return true!
                            }
                            //backtracking; sets the cell to 0 (empty)
                            //then, it will try again with the next number
                            else { 
                                grid[row][column] = 0;
                            }
                        }
                    }
                    return false; //we tried every number for a cell and it didn't work, so no possible solution
                }
            }
        }
        return true;
    }
}


