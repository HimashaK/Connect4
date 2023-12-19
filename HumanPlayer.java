import java.util.Scanner;

public class HumanPlayer extends Player {

    /*constructor for HumanPlayer class*/
    public HumanPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
        
    }

    //Implementation of makeMove method inherited from abstract class Player
    @Override
    public void makeMove(Board board) {
        
        //create Scanner object input
        Scanner user_input = new Scanner(System.in);
        System.out.print(name + ", please input your next move: ");

        //get user input
        int column_num;
        column_num = Integer.parseInt(user_input.nextLine());

        // check if user input is between 1 and 7 (inclusive)
        while ((column_num < 1 || column_num > 7 )){
            System.out.print(name + ", sorry that is not a valid move, please enter a position from 1-7: ");
            column_num = Integer.parseInt(user_input.nextLine());
        }

        // prompt user again when column that user chooses is full
        while(board.checkColFull(column_num)){
            user_input = new Scanner(System.in);
            System.out.print(name + ", sorry this column is full, please enter a valid position");
            column_num = Integer.parseInt(user_input.nextLine());
        }

        board.placeToken(column_num,symbol);

    }
    
}
