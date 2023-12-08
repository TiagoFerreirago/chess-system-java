package boardgame;

public class Board {

	private int rows;
	private int column;
	private Pierce[][] pierce;
	
	public Board(int rows, int column) {
	
		if(rows < 1 || column < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column ");
		}
		this.rows = rows;
		this.column = column;
		pierce = new Pierce[rows][column];
	}

	public int getRows() {
		return rows;
	}

	public int getColumn() {
		return column;
	}

	public Pierce pierce(int row, int column) {
		if(!positionExists(row, column)){
			throw new BoardException("Position not on the board");
		}
		return pierce [row][column];
	}
	
	public Pierce pierce(Position position) {
		if(!positionExists(position)){
			throw new BoardException("Position not on the board");
		}
		return pierce [position.getRow()][position.getColumn()];
	}
	public void placePiece(Pierce piece,  Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already i piece on position " + position);
		}
		pierce[position.getRow()][position.getColumn()] = piece;
		piece.position= position;
	}
	public Pierce removePiece(Position position) {
		if(!positionExists(position)){
			throw new BoardException("Position not on the board");
		}
		if(pierce(position) == null) {
			return null;
		}
		Pierce aux = pierce(position);
		aux.position = null;
		pierce [position.getRow()][position.getColumn()]= null;
		return aux;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	

	public boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < this.column; 
				
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)){
			throw new BoardException("Position not on the board");
		}
		return pierce(position) != null;
	}
}
