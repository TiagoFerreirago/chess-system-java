package chess;

import boardgame.Board;
import boardgame.Pierce;
import boardgame.Position;

public abstract class ChessPierce extends Pierce {

	private Color color;

	public ChessPierce(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOponnentPiece(Position position) {
		ChessPierce p= (ChessPierce) getBoard().pierce(position);
		return p != null && p.getColor()!=color;
	}
	
	}
	
	

