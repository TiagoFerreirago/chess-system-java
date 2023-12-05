package application;

import chess.ChessPierce;

public class UI {

	public static void printBoard(ChessPierce [][] piece) {
		for(int i=0; i<piece.length; i++) {
			System.out.print((8-i)+(" "));
			for(int j=0; j<piece.length; j++) {
				printPierce(piece[i][j]);
			}
			System.out.println();
		}
		System.out.print("a b c d e f g h");
	}
	
	public static void printPierce(ChessPierce piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
	}
}

