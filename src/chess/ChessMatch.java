package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Pierce;
import boardgame.Position;
import chess_pieces.King;
import chess_pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private  List <Pierce> piecesOnTheBoard = new ArrayList<>();
	private List<Pierce> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		        
		board = new Board(8, 8);
		turn = 1;
		currentPlayer= Color.WHITE;
		initialSetup();
	}
	
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public boolean [][] possibleMoves(ChessPosition sourcePosition) {
	Position position = sourcePosition.toPosition();
	validadedSourcePosition(position);
	return board.pierce(position).possibleMoves();
		
	}
	
	public ChessPierce performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source= sourcePosition.toPosition();
		Position target= targetPosition.toPosition();
		validadedSourcePosition(source);
		validadedTargetPosition(source,target);
		Pierce capturePiece= makeMove(source, target);
		nextTurn();
		return (ChessPierce)capturePiece;
	}
	private void validadedSourcePosition(Position position) {
		
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPierce)board.pierce(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		
		if(!board.pierce(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible move for the chosen piece");
		}
	}
	private void validadedTargetPosition(Position source, Position target) {
		if(!board.pierce(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move target position");
		}
	}
		
	private Pierce makeMove(Position source, Position target) {
		Pierce p = board.removePiece(source);
		Pierce capturePiece= board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturePiece != null) {
			piecesOnTheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
			
		}
		return capturePiece;
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
	private void placeNewPiece(char column, int row, ChessPierce piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void nextTurn() {
		turn ++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		
	}
		
	public void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	 
}
