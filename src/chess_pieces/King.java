package chess_pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPierce;
import chess.Color;

public class King extends ChessPierce {

	public King(Board board, Color color) {
		super(board, color);

	}
	public boolean canMove( Position position) {
		ChessPierce p= (ChessPierce)getBoard().pierce(position);
		return p != null && p.getColor() != getColor();
	}
	@Override
	public String toString() {
		return"K";
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat= new boolean[getBoard().getRows()][getBoard().getColumn()];
		Position position= new Position(0,0);
		//above
		position.setValue(position.getRow()-1, position.getColumn());
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//bellow
		position.setValue(position.getRow()+1, position.getColumn());
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//left
		position.setValue(position.getRow(), position.getColumn()-1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//right
		position.setValue(position.getRow(), position.getColumn()+1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//nw
		position.setValue(position.getRow()-1, position.getColumn()-1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//ne
		position.setValue(position.getRow()-1, position.getColumn()+1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//se
		position.setValue(position.getRow()+1, position.getColumn()+1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		//sw
		position.setValue(position.getRow()+1, position.getColumn()-1);
		if(getBoard().positionExists(position)&&canMove(position)) {
			mat[position.getRow()][position.getColumn()] = true;
		}
		return mat;
	}

}
