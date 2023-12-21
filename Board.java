import java.util.ArrayList;

public class Board {

	//Set connect4 board dimensions
	private final int NUM_COL = 7;
	private final int NUM_ROW = 6;

	// I have decided to represent my board using a private 2D arraylist structure (of type String)
	// making the data structure private ensures that implentation details are not exposed to other classes
	private ArrayList<ArrayList<String>> board;
	
	/*Board constructor to create Board instance */
	public Board() {
		this.board = new ArrayList<ArrayList<String>>();

		//construct board with empty cells
        for (int i = 0; i < NUM_COL; i++) {
            ArrayList<String> column = new ArrayList<>(); //store contents of a single column in an arraylist
            for (int j = 0; j < NUM_ROW; j++) {
                column.add("");
            }
            board.add(column);
        }
	}

	/*Method to print connect 4 board */
	public void printBoard() {
        for (int i = 0; i < NUM_ROW; i++) {
            System.out.println("---------------");
            System.out.print("|");

            for (int j = 0; j < NUM_COL; j++) {
                String cell = board.get(j).get(i);
                System.out.print(cell.isEmpty() ? " " : cell);	//if cell is empty then " ", if not then cell content is printed
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("---------------");
	}

	/*Method to place token into selected board column */
	public void placeToken(int col_num, char symbol){
        if (checkColFull(col_num)) {
            return;
        }
        ArrayList<String> column = board.get(col_num - 1);
        for (int i = NUM_ROW - 1; i >= 0; i--) {
            if (column.get(i).isEmpty()) {
                column.set(i, String.valueOf(symbol));	//convert char to string representation
                break;
            }
        }		
	}	
	
	/*Method to check if there is a win (4 in a row) */
	public boolean containsWin() {
        for (int col = 0; col < NUM_COL; col++) {
            for (int row = 0; row < NUM_ROW; row++) {
                String cell = board.get(col).get(row);
                if (!cell.isEmpty() && this.checkFourinRow(row, col)) {
                    return true;
                }
            }
        }
        return false;
	}
	
	/*Method to check if there is a tie */
	public boolean isTie() {
        return this.checkBoardFull() && !this.containsWin();
	}
	
	/* Method to reset/clear the board - */
	public void reset() {
        for (ArrayList<String> column : board) {
            for (int j = 0; j < NUM_ROW; j++) {
                column.set(j, "");
            }
        }
	}

	/*Helper Methods*/

	/*Method to check if the selected column is full  */
	public boolean checkColFull(int colNum) {
        return !board.get(colNum - 1).contains(""); //checks if column contains empty cell
    }

	/*Method to check if the board is full*/
    private boolean checkBoardFull() {
		//check each column in the board for empty cells
        for (ArrayList<String> column : board) {
            if (column.contains("")) {
                return false;
            }
        }
        return true;
    }

    /*Method to check that there are four consectutive cells in a row/column/diagonal with the same symbol */
    private boolean checkFourinRow(int row, int col) {
        String checker = board.get(col).get(row);
        return checkDirection(row, col, 1, 0, checker) ||       //  check horizontal
               checkDirection(row, col, 0, 1, checker) ||       //  check vertical
               checkDirection(row, col, 1, 1, checker) ||       //  check diagonal descending
               checkDirection(row, col, 1, -1, checker);            //   check diagonal ascending
    }

    /*Method to check if there are 4 consectutive cells that are in the same direction with the same value */
    private boolean checkDirection(int row, int col, int dRow, int dCol, String token) {
        //iterate from 1-3 (inclusive) to check  the next 3 cells in the given direction from the starting cell
        for (int i = 1; i < 4; i++) {
            // calculate the position of the next cell to check based on the direction
            int newRow = row + dRow * i;    
            int newCol = col + dCol * i;
            //check bounds of new row and column and check if value at new position matched token symbol
            if (newRow < 0 || newRow >= NUM_ROW || newCol < 0 || newCol >= NUM_COL || !board.get(newCol).get(newRow).equals(token)) {
                return false;
            }
        }
        return true;
    }

    /* AI player methods and helpers */
    
    /* Getter methods */
	public int getNumRows(){
		return NUM_ROW;
	}

	public int getNumCol(){
		return NUM_COL;
	}

    /*Method to check if there is a potential winning move for the AI player
     * returns col num where placing the symbol would lead to a win or -1 if there is no winning move found
     */
    public int AIWin(char symbol) {
        ArrayList<ArrayList<String>> bCopy = this.copyBoard();
        int aicol = -1;
    
        for (int colNum = 1; colNum <= NUM_COL; colNum++) {
            this.placeToken(colNum, symbol);
            if (this.containsWin()) {
                aicol = colNum;
                break;
            }
            this.restoreBoard(bCopy);
        }
    
        restoreBoard(bCopy);
        return aicol;
    }

    /*Method to identify if there is a move that can be made to block the oponent from winning 
     * if AIWin returns col num then opponent could win by playing that col so the AI blocks it
     * if it returns -1, there is no immediate winning move for the opponent so blocking isnt necessary
    */
    public int AIBlock(char symbol) {
        String self = String.valueOf(symbol);
        String other = findOppSymbol(self);
    
        return AIWin(other.charAt(0));
    }
    
    /*Method to  find the symbol of the opponent */
    private String findOppSymbol(String selfSymbol) {
        /*  iterates through each cell of the board, if cell is not empty and does not 
        contain AI's symbol then returns cell (opponents symbol) */
        for (ArrayList<String> column : board) {
            for (String cell : column) {
                if (!cell.isEmpty() && !cell.equals(selfSymbol)) {
                    return cell;
                }
            }
        }
        return ""; // return empty string if no opponent symbol found
    }

    /*Method to copy the board so that the original board remains unchanged when simulating moves by the AI*/
    private ArrayList<ArrayList<String>> copyBoard() {
        ArrayList<ArrayList<String>> newBoard = new ArrayList<>();
        for (ArrayList<String> column : board) {
            newBoard.add(new ArrayList<>(column));
        }
        return newBoard;
    }

    /*Method to revert changes made to the board*/
    private void restoreBoard(ArrayList<ArrayList<String>> boardCopy) {
        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_ROW; j++) {
                board.get(i).set(j, boardCopy.get(i).get(j));
            }
        }
    }
    
    
    
    
	
}
