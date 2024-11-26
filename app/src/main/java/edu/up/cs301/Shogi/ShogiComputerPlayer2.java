package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;

import java.util.ArrayList;
import java.util.Random;


/**
* @author Steven R. Vegdahl
* @author Andrew M. Nuxoll
* @version September 2013
*/
public class ShogiComputerPlayer2 extends GameComputerPlayer {

	private Random rand = null;
	/**
	 * Constructor for objects of class ShogiComputerPlayer1
	 *
	 * @param name The player's name
	 */
	public ShogiComputerPlayer2(String name) {
		// Invoke superclass constructor
		super(name);

		rand = new Random();
	}

	/**
	 * Gets the player number for this player
	 */
	public int getPlayerNum() {
		return playerNum;
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
			if (piece.getOwner() == playerNum) {
				playerPieces.add(piece);
			}
		}

		for (ShogiPiece selectedPiece : playerPieces) {
			if (selectedPiece.isOnBoard()) {
				ArrayList<ShogiSquare> possibleMoves = selectedPiece.getPossibleMoves();

				for (ShogiSquare targetSquare : possibleMoves) {
					if (gameState.getPiece(targetSquare).getOwner() != playerNum) {
						ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
						game.sendAction(moveAction);
						return;
					}
				}
			}
		}

		for (ShogiPiece selectedPiece : playerPieces) {
			if (!selectedPiece.isOnBoard()) {
				ShogiSquare targetSquare = new ShogiSquare(rand.nextInt(7), rand.nextInt(7));
				ShogiDropAction dropAction = new ShogiDropAction(this, selectedPiece, targetSquare);
				if (gameState.dropPiece(dropAction, false)) {
					game.sendAction(dropAction);
				}
			}
		}
		ArrayList<ShogiSquare> possibleMoves = new ArrayList<>();
		ShogiPiece selectedPiece = null;

		while (possibleMoves.isEmpty()) {
			// Choose a random piece from the player's pieces
			selectedPiece = playerPieces.get((int)(Math.random() * playerPieces.size()));
			// Get all possible moves for the selected piece
			possibleMoves = selectedPiece.getPossibleMoves();
		}

		ShogiSquare targetSquare = selectedPiece.getPossibleMoves().get((int)(Math.random() * selectedPiece.getPossibleMoves().size()));

		ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
		game.sendAction(moveAction);

	}
}