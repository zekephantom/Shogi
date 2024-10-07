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
	// Information about each player's resources
	private ArrayList<ShogiPiece> player1Pieces; // Pieces for Player 1
	private ArrayList<ShogiPiece> player2Pieces; // Pieces for Player 2

	// State of the board
	private ShogiPiece[][] gameBoard; // Represents the board with pieces

	// Whose turn is it?
	private int currentPlayer; // 0 for Player 1, 1 for Player 2

	// Visibility information for each player
	private boolean[][] player1Visibility; // Visibility of pieces for Player 1
	private boolean[][] player2Visibility; // Visibility of pieces for Player 2

	// Current score of each player
	private int player1Score;
	private int player2Score;

	// Current state of the game phase
	private String gamePhase; // Setup, Placement, Main Play, etc.

	// Constructor for ShogiState
	public ShogiState() {
		player1Pieces = new ArrayList<>();
		player2Pieces = new ArrayList<>();
		gameBoard = new ShogiPiece[9][9]; // Shogi board is 9x9
		currentPlayer = 0; // Start with Player 1
		player1Visibility = new boolean[9][9];
		player2Visibility = new boolean[9][9];
		player1Score = 0;
		player2Score = 0;
		gamePhase = "Setup";
	}

	/*
	* Deep copy constructor
	* */
	public ShogiState(ShogiState orig) {
		// Deep copy player pieces
		this.player1Pieces = new ArrayList<>();
		for (ShogiPiece piece : orig.player1Pieces) {
			this.player1Pieces.add(new ShogiPiece(piece));
		}

		this.player2Pieces = new ArrayList<>();
		for (ShogiPiece piece : orig.player2Pieces) {
			this.player2Pieces.add(new ShogiPiece(piece));
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

		// Deep copy visibility arrays
		this.player1Visibility = new boolean[9][9];
		this.player2Visibility = new boolean[9][9];
		for (int i = 0; i < 9; i++) {
			System.arraycopy(orig.player1Visibility[i], 0, this.player1Visibility[i], 0, 9);
			System.arraycopy(orig.player2Visibility[i], 0, this.player2Visibility[i], 0, 9);
		}

		// Copy scores
		this.player1Score = orig.player1Score;
		this.player2Score = orig.player2Score;

		// Copy game phase
		this.gamePhase = orig.gamePhase;
	}

	// Getters and Setters for various instance variables
	public ArrayList<ShogiPiece> getPlayer1Pieces() {return player1Pieces;}

	public ArrayList<ShogiPiece> getPlayer2Pieces() {return player2Pieces;}

	public ShogiPiece[][] getGameBoard() {return gameBoard;}

	public int getCurrentPlayer() {return currentPlayer;}

	public void setCurrentPlayer(int currentPlayer) {this.currentPlayer = currentPlayer;}

	public boolean[][] getPlayer1Visibility() {return player1Visibility;}

	public boolean[][] getPlayer2Visibility() {return player2Visibility;}

	public int getPlayer1Score() {return player1Score;}

	public void setPlayer1Score(int player1Score) {	this.player1Score = player1Score;}

	public int getPlayer2Score() {return player2Score;}

	public void setPlayer2Score(int player2Score) {	this.player2Score = player2Score;}

	public String getGamePhase() {return gamePhase;	}

	public void setGamePhase(String gamePhase) {this.gamePhase = gamePhase;	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Shogi State:\n");
		sb.append("Current Player: ").append(currentPlayer == 0 ? "Player 1" : "Player 2").append("\n");
		sb.append("Game Phase: ").append(gamePhase).append("\n");
		sb.append("Player 1 Score: ").append(player1Score).append("\n");
		sb.append("Player 2 Score: ").append(player2Score).append("\n");
		sb.append("Player 1 Pieces: ").append(player1Pieces).append("\n");
		sb.append("Player 2 Pieces: ").append(player2Pieces).append("\n");
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