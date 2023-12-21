import java.util.Random;

public class AIPlayer extends Player {

    private int AIMoves;
    private Random random;

    public AIPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
        AIMoves = 0;
        random = new Random();
    }

    @Override
    public void makeMove(Board board) {
        // During the first two moves, it's not possible for anyone to win
        if (AIMoves < 2) {
            makeRandomMove(board);
            AIMoves++;  //add to moves count
            return;
        }

        // Check if AI can win in the next move
        int winColInd = board.AIWin(symbol);
        if (winColInd != -1) {
            board.placeToken(winColInd, symbol);
            return;
        }

        // Check if the opponent can win in the next move, and block it
        winColInd = board.AIBlock(symbol);
        if (winColInd != -1) {
            board.placeToken(winColInd, symbol);
            return;
        }

        // If nobody can win in the next move, make a random move
        makeRandomMove(board);
    }

    private void makeRandomMove(Board board) {
        int randomColumnIndex = random.nextInt(board.getNumCol());
        
        // Makes sure the AI always picks a non-full column
        while (board.checkColFull(randomColumnIndex + 1)) {
            randomColumnIndex = random.nextInt(board.getNumCol());
        }
        board.placeToken(randomColumnIndex + 1, symbol);
    }
}

