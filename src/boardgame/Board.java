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
	
	
	
	
}
