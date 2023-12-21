public class Human_Runner {

	public static void main(String[] args) {
		
		
		Board board = new Board();
		ConnectFour game = new ConnectFour(board);
		game.setPlayer1(new HumanPlayer('R', board, "Alice"));
		game.setPlayer2(new HumanPlayer('B', board, "Bob"));
		game.playGame();
		
	}
}
