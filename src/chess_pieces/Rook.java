package chess_pieces;

import boardgame.Board;
import chess.ChessPierce;
import chess.Color;

public class Rook extends ChessPierce {

	public Rook(Board board, Color color) {
		super(board, color);
		
	}
	@Override
	public String toString() {
		
		return "R";
	
}
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumn()];
		return mat;
	}
}