package boardgame;

public class Pierce {

	protected Position position;
	private Board board;
	
	public Pierce(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}


	
	
	
}
