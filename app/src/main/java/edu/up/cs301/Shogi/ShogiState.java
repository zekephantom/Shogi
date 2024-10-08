package edu.up.cs301.Shogi;


import android.graphics.BitmapFactory;
import android.graphics.Bitmap;


import java.util.ArrayList;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.shogi.R;

/**
 * This contains the state for Shogi.
 *
 * @author Steven R. Vegdahl
 * @author Ezekiel Rafanan
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author Arnaj Sandhu
 * @author James Pham
 * @version October 2024
 */
public class ShogiState extends GameState {

	// to satisfy Serializable interface
	private static final long serialVersionUID = 7737393762469851826L;

	private static final String TAG = "ShogiState";

	/*
	* Instance Variables for Shogi State
	* */
	// Information about all the pieces
	private ArrayList<ShogiPiece> pieces;

	// State of the board
	private ShogiPiece[][] gameBoard; // Represents the board with pieces

	// Whose turn is it?
	private int currentPlayer; // 0 for Player 1, 1 for Player 2

	// Current score of each player
	private int player1Score;
	private int player2Score;

	// Current state of the game phase
	private String gamePhase; // Setup, Placement, Main Play, etc.

	// Constructor for ShogiState
	public ShogiState() {
		pieces = new ArrayList<>();
		this.initPieces();
		gameBoard = new ShogiPiece[9][9]; // Shogi board is 9x9
		currentPlayer = 0; // Start with Player 1
		player1Score = 0;
		player2Score = 0;
		gamePhase = "Setup";

	}


	/*
	* Deep copy constructor
	* */
	public ShogiState(ShogiState orig) {
		// Deep copy player pieces
		this.pieces = new ArrayList<>();
		for (ShogiPiece piece : orig.pieces) {
			this.pieces.add(new ShogiPiece(piece));
		}

		// Deep copy game board
		this.gameBoard = new ShogiPiece[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (orig.gameBoard[i][j] != null) {
					this.gameBoard[i][j] = new ShogiPiece(orig.gameBoard[i][j]);
				} else {
					this.gameBoard[i][j] = null;
				}
			}
		}

		// Copy current player
		this.currentPlayer = orig.currentPlayer;

		// Copy scores
		this.player1Score = orig.player1Score;
		this.player2Score = orig.player2Score;

		// Copy game phase
		this.gamePhase = orig.gamePhase;
	}

	// Getters and Setters for various instance variables
	public ArrayList<ShogiPiece> getPieces() {return pieces;}

	public ShogiPiece[][] getGameBoard() {return gameBoard;}

	public int getCurrentPlayer() {return currentPlayer;}

	public void setCurrentPlayer(int currentPlayer) {this.currentPlayer = currentPlayer;}

	public int getPlayer1Score() {return player1Score;}

	public void setPlayer1Score(int player1Score) {	this.player1Score = player1Score;}

	public int getPlayer2Score() {return player2Score;}

	public void setPlayer2Score(int player2Score) {	this.player2Score = player2Score;}

	public String getGamePhase() {return gamePhase;	}

	public void setGamePhase(String gamePhase) {this.gamePhase = gamePhase;	}



	public boolean moveAction(ShogiMoveAction action) {

		ShogiPiece selectedPiece = action.getPiece();

		int currentRow = selectedPiece.getRow();
		int currentCol = selectedPiece.getCol();
		int moveRow = action.getMoveRow();
		int moveCol = action.getMoveCol();

		// Check if target is occupied

		// check if path is blocked


		// checking for each piece
		switch (action.getPiece().getType()){
			case("King"):
				if ((Math.abs(currentRow-moveRow) == 1) &&  (Math.abs(currentCol-moveCol) == 1))
					return true;
				break;
			case("GoldGeneral"):

				break;
		}

			return false;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Shogi State:\n");
		sb.append("Current Player: ").append(currentPlayer == 0 ? "Player 1" : "Player 2").append("\n");
		sb.append("Game Phase: ").append(gamePhase).append("\n");
		sb.append("Player 1 Score: ").append(player1Score).append("\n");
		sb.append("Player 2 Score: ").append(player2Score).append("\n");
		sb.append("Pieces: ").append(pieces).append("\n");
		sb.append("Game Board:\n");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sb.append(gameBoard[i][j] == null ? "[ ]" : "[P]").append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void initPieces(){

		// Player 1 pieces
		pieces.add(new ShogiPiece("King", 1, 8, 4));
		pieces.add(new ShogiPiece("GoldGeneral", 1, 8,3));
		pieces.add(new ShogiPiece("GoldGeneral", 1, 8,5));
		pieces.add(new ShogiPiece("SilverGeneral", 1, 8,2));
		pieces.add(new ShogiPiece("SilverGeneral", 1, 8,6));
		pieces.add(new ShogiPiece("Knight", 1, 8,1));
		pieces.add(new ShogiPiece("Knight", 1, 8,7));
		pieces.add(new ShogiPiece("Lance", 1, 8,0));
		pieces.add(new ShogiPiece("Lance", 1, 8,8));
		pieces.add(new ShogiPiece("Bishop", 1, 7,1));
		pieces.add(new ShogiPiece("Rook", 1, 7,7));
		for (int i = 0; i < 9; i++){
			pieces.add(new ShogiPiece("Pawn", 1, 6,i));
		}
		// Player 2 pieces
		pieces.add(new ShogiPiece("King", 2, 0, 4));
		pieces.add(new ShogiPiece("GoldGeneral", 2, 0,3));
		pieces.add(new ShogiPiece("GoldGeneral", 2, 0,5));
		pieces.add(new ShogiPiece("SilverGeneral", 2, 0,2));
		pieces.add(new ShogiPiece("SilverGeneral", 2, 0,6));
		pieces.add(new ShogiPiece("Knight", 2, 0,1));
		pieces.add(new ShogiPiece("Knight", 2, 0,7));
		pieces.add(new ShogiPiece("Lance", 2, 0,0));
		pieces.add(new ShogiPiece("Lance", 2, 0,8));
		pieces.add(new ShogiPiece("Bishop", 2, 1,1));
		pieces.add(new ShogiPiece("Rook", 2, 1,7));
		for (int i = 0; i < 9; i++){
			pieces.add(new ShogiPiece("Pawn", 2, 2,i));
		}

	}//initPieces

}