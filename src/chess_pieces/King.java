package chess_pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPierce;
import chess.Color;

public class King extends ChessPierce {

	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;

	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPierce p = (ChessPierce)getBoard().pierce(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		
		ChessPierce p = (ChessPierce)getBoard().pierce(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && getMoveCount() == 0;
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumn()];

		Position p = new Position(0, 0);

		// above
		p.setValue(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValue(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValue(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValue(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValue(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValue(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValue(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// se
		p.setValue(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//#especialmove castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			//#wspecialmove castling kingside rook
			Position posT1= new Position(position.getRow(), position.getColumn()+3);
			if(testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 2);
				Position p2  = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().pierce(p1) == null && getBoard().pierce(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
		}
		
		//#wspecialmove castling queenside rook
		Position posT2= new Position(position.getRow(), position.getColumn()-4);
		if(testRookCastling(posT2)) {
			Position p3 = new Position(position.getRow(), position.getColumn() - 3);
			Position p1 = new Position(position.getRow(), position.getColumn() - 2);
			Position p2  = new Position(position.getRow(), position.getColumn() - 1);
			if(getBoard().pierce(p1) == null && getBoard().pierce(p2) == null && getBoard().pierce(p3) == null){
				mat[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		return mat;
	}
}
	
