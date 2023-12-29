package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Pierce;
import boardgame.Position;
import chess_pieces.Bishop;
import chess_pieces.Horse;
import chess_pieces.King;
import chess_pieces.Pawn;
import chess_pieces.Queen;
import chess_pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private  List <Pierce> piecesOnTheBoard = new ArrayList<>();
	private List<Pierce> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	private ChessPierce enPassantVulnerable;
	private ChessPierce promoted;
	
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
	
	public ChessPierce getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPierce getPromoted() {
		return promoted;
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
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturePiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		ChessPierce movedPiece = (ChessPierce)board.pierce(target);
		
		//#specialmove promotion
		promoted  = null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor()== Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPierce)board.pierce(target);
				promoted = replacePromotedPiece("Q");
				
				}
			}
		
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		//# Special move en passant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() -2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		return (ChessPierce)capturePiece;
	}
	
	public boolean getCheck() {
		return check;
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
	public ChessPierce replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			throw new InvalidParameterException("invalid type for promotion");
		}
		Position pos = promoted.getChessPosition().toPosition();
		Pierce p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPierce newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPierce newPiece(String type, Color color) {
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("N")) return new Horse(board, color);
		if(type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	private Pierce makeMove(Position source, Position target) {
		ChessPierce p = (ChessPierce)board.removePiece(source);
		p.increaseMoveCount();
		Pierce capturePiece= board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturePiece != null) {
			piecesOnTheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
			
		}
		//specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPierce rook = (ChessPierce)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//specialmove castling queenside rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPierce rook = (ChessPierce)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		//specialmove en passant
		if(p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturePiece == null) {
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturePiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturePiece);
				piecesOnTheBoard.remove(capturePiece);
			}
		}
		return capturePiece;
	}
	
	private void undoMove(Position source, Position target, Pierce captured) {
		ChessPierce p = (ChessPierce)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(captured != null) {
			board.placePiece(captured, target);
			capturedPieces.remove(captured);
			piecesOnTheBoard.add(captured);
		}
		
		//specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
				Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
				Position targetT = new Position(source.getRow(), source.getColumn() + 1);
				ChessPierce rook = (ChessPierce)board.removePiece(targetT);
				board.placePiece(rook, sourceT);
				rook.decreaseMoveCount();
		}
				
				//specialmove castling queenside rook
			if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
				Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
				Position targetT = new Position(source.getRow(), source.getColumn() - 1);
				ChessPierce rook = (ChessPierce)board.removePiece(targetT);
				board.placePiece(rook, sourceT);
				rook.decreaseMoveCount();
						}
			//specialmove en passant
			if(p instanceof Pawn) {
				if(source.getColumn() != target.getColumn() && captured == enPassantVulnerable) {
					
					ChessPierce pawn = (ChessPierce)board.removePiece(target);
					Position pawnPosition;
					if(p.getColor() == Color.WHITE) {
						pawnPosition = new Position(3, target.getColumn());
					}
				
					else {
						pawnPosition = new Position(4, target.getColumn());
					}
					board.placePiece(pawn, pawnPosition);
				}
			}
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
		
	private ChessPierce king(Color color) {
		
		List<Pierce> list = piecesOnTheBoard.stream().filter(x -> ((ChessPierce)x).getColor() == color).collect(Collectors.toList()); 
		
		for(Pierce p : list) {
			if(p instanceof King) {
				return(ChessPierce)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
		
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Pierce> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPierce)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Pierce p : opponentPieces) {
			boolean [][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		
		List<Pierce> list = piecesOnTheBoard.stream().filter(x -> ((ChessPierce)x).getColor() == color).collect(Collectors.toList());
		
		for(Pierce p : list) {
			boolean mat [][] = p.possibleMoves();
			for(int i =0; i < board.getRows();i ++) {
				for(int j =0; j <board.getColumn(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPierce)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Pierce capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	public void initialSetup() {
		 placeNewPiece('a', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('e', 1, new King(board, Color.WHITE,this));
	        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('b', 1, new Horse(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('g', 1, new Horse(board, Color.WHITE));
	        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('b', 8, new Horse(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('g', 8, new Horse(board, Color.BLACK));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
		}
	 
}
