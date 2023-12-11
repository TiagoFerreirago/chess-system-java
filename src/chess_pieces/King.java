package chess_pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPierce;
import chess.Color;

public class King extends ChessPierce {

	public King(Board board, Color color) {
		super(board, color);

	}
	@Override
	public String toString() {
		return"K";
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat= new boolean[getBoard().getRows()][getBoard().getColumn()];
		return mat;
	}

}
