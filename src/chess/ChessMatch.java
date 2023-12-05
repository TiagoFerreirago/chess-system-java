package chess;

import boardgame.Board;

public class ChessMatch {

	private Board board;

	public ChessMatch() {
		        
		board = new Board(8, 8);
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
	 
}
