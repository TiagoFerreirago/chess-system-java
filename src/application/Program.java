package application;

import java.util.Scanner;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPierce;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
	
		ChessMatch chessMatch= new ChessMatch();
		Scanner sc= new Scanner(System.in);
		
		
		while(true) {
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.println("Source: ");
			ChessPosition source = UI.readChessPosition(sc);
			
			System.out.println();
			System.out.println("Target: ");
			ChessPosition target = UI.readChessPosition(sc);
			
			ChessPierce capturedPiece = chessMatch.performChessMove(source, target);
		}
	}

}
