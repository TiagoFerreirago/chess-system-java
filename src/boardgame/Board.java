package boardgame;

public class Board {

	private int rows;
	private int column;
	private Pierce[][] pierce;
	
	public Board(int rows, int column) {
	
		this.rows = rows;
		this.column = column;
		pierce = new Pierce[rows][column];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public Pierce pierce(int row, int column) {
		return pierce [row][column];
	}
	
	public Pierce pierce(Position position) {
		return pierce [position.getRow()][position.getColumn()];
	}
	public void placePiece(Pierce piece,  Position position) {
		pierce[position.getRow()][position.getColumn()] = piece;
		piece.position= position;
	}
	
	
}
