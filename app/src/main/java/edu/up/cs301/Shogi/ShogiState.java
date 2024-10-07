package edu.up.cs301.Shogi;

import java.util.ArrayList;
import edu.up.cs301.GameFramework.infoMessage.GameState;

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
		pieces = new ArrayList<>(40);
		this.initPieces;
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

}