package chess;

import boardgame.Board;
import boardgame.Pierce;

public class ChessPierce extends Pierce {

	private Color color;

	public ChessPierce(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	
	}
	
	

