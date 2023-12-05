package chess;

import boardgame.Board;
import boardgame.Position;
import chess_pieces.King;
import chess_pieces.Rook;

public class ChessMatch {

	private Board board;

	public ChessMatch() {
		        
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPierce[][] getPieces(){
		ChessPierce [][] mat= new ChessPierce[board.getRows()][board.getColumn()];
		for( int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumn(); j++) {
				mat[i][j] = (ChessPierce)board.pierce(i, j);
			}
		}
		return mat;
	}
	
	public void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE),new Position(2,1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7,4));
	}
	 
}
