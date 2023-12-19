package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int rows;
	
	public ChessPosition(char column, int rows) {
		if(column < 'a' || column >'h' || rows < 1 || rows > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid value are from a1 to h8");
		}
		this.column = column;
		this.rows = rows;
	}

	public char getColumn() {
		return column;
	}

	public int getRows() {
		return rows;
	}

	protected Position toPosition() {
		return new Position(8 - rows, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)( 'a'+ position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
	
		return "" + column + rows;
    }
}
