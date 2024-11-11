package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

import java.util.ArrayList;

/**
 * A computer version of a player in the game. This AI will randomly select valid moves.
 *
 * @author Your Name
 * @version October 2024
 */
public class ShogiComputerPlayer1 extends GameComputerPlayer implements Tickable {

	/**
	 * Constructor for objects of class ShogiComputerPlayer1
	 *
	 * @param name The player's name
	 */
	public ShogiComputerPlayer1(String name) {
		// Invoke superclass constructor
		super(name);

		// Start the timer, ticking every second
		getTimer().setInterval(1000);
		getTimer().start();
	}

	/**
	 * Callback method -- the game's state has changed
	 *
	 * @param info The information received from the game state
	 */
	@Override
	protected void receiveInfo(GameInfo info) {
		// Currently not processing game state changes
	}

	/**
	 * Callback method: the timer ticked
	 */
	protected void timerTicked() {
		// Check if it's the AI's turn
		ShogiState gameState = (ShogiState) game.getGameState();
		if (gameState.getCurrentPlayer() != 1) return; // AI is Player 1

		// Get all pieces that belong to the AI and are on the board
		ArrayList<ShogiPiece> playerPieces = new ArrayList<>();
		for (ShogiPiece piece : gameState.getPieces()) {
			if (piece.getOwner() == 1 && piece.isOnBoard()) {
				playerPieces.add(piece);
			}
		}

		// If there are no pieces to move, return
		if (playerPieces.isEmpty()) return;

		// Choose a random piece from the player's pieces
		ShogiPiece selectedPiece = playerPieces.get((int)(Math.random() * playerPieces.size()));

		// Get all possible moves for the selected piece
		ArrayList<ShogiSquare[]> possibleMoves = selectedPiece.getPossibleMoves(gameState);

		// If there are no possible moves, return
		if (possibleMoves.isEmpty()) return;

		// Choose a random move from the available moves
	/*
		int[] selectedMove = possibleMoves.get((int)(Math.random() * possibleMoves.size()));
		int targetRow = selectedMove[0];
		int targetCol = selectedMove[1];

		// Create a move action and send it to the game
		ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetRow, targetCol);
		game.sendAction(moveAction);
		*/
	}
}