package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.infoMessage.NotYourTurnInfo;
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
public class ShogiComputerPlayer1 extends GameComputerPlayer{

	/**
	 * Constructor for objects of class ShogiComputerPlayer1
	 *
	 * @param name The player's name
	 */
	public ShogiComputerPlayer1(String name) {
		// Invoke superclass constructor
		super(name);
	}

	/**
	 * Callback method -- the game's state has changed
	 *
	 * @param info The information received from the game state
	 */
	@Override
	protected void receiveInfo(GameInfo info) {
		ShogiState gameState = (ShogiState) game.getGameState();

		// if not our turn, return
		if (gameState.getCurrentPlayer() != playerNum) return;


		ArrayList<ShogiPiece> playerPieces = new ArrayList<>();
		for (ShogiPiece piece : gameState.getPieces()) {
			if (piece.getOwner() == playerNum && piece.isOnBoard()) {
				playerPieces.add(piece);
			}
		}

		ArrayList<ShogiSquare> possibleMoves = new ArrayList<>();
		ShogiPiece selectedPiece = null;

		while (possibleMoves.isEmpty()) {
			// Choose a random piece from the player's pieces
			selectedPiece = playerPieces.get((int)(Math.random() * playerPieces.size()));
			// Get all possible moves for the selected piece
			possibleMoves = selectedPiece.getPossibleMoves(gameState);
		}
		
		ShogiSquare targetSquare = selectedPiece.getPossibleMoves(gameState).get((int)(Math.random() * selectedPiece.getPossibleMoves(gameState).size()));

		ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
		game.sendAction(moveAction);
	}
}