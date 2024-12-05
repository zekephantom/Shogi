package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Tickable;

import java.util.ArrayList;
import java.util.Random;

/**
 * A computer version of a player in the game. This AI will randomly select valid moves.
 *
 * @author Your Name
 * @version October 2024
 */
public class ShogiComputerPlayer1 extends GameComputerPlayer{
	private Random rand = null;
	/**
	 * Constructor for objects of class ShogiComputerPlayer1
	 *
	 * @param name The player's name
	 */
	public ShogiComputerPlayer1(String name) {
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
			if (piece.getOwner() == playerNum && piece.isOnBoard()) {
				playerPieces.add(piece);
			}
		}

		// move king if in check
		ShogiPiece king = null;
		if (gameState.isKingInCheck(playerNum)) {
			if (playerNum == 0) {
				king = gameState.getPieces().get(4);
			}
			else {
				king = gameState.getPieces().get(24);
			}
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					ShogiSquare targetSquare = new ShogiSquare(king.getPosition().getRow() + x, king.getPosition().getCol() + y);
					ShogiMoveAction checkMove = new ShogiMoveAction(this, king, targetSquare);

					if (gameState.moveKing(checkMove, false)) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						// Sends the move to human player so that he can print the move
						ShogiHumanPlayer.setPriorMove(king.getPosition(),targetSquare);
						game.sendAction(checkMove);
					}
				}
			}
		}

		ArrayList<ShogiSquare> possibleMoves = new ArrayList<>();
		ShogiPiece selectedPiece = null;

		while (possibleMoves.isEmpty()) {
			// Choose a random piece from the player's pieces
			selectedPiece = playerPieces.get(rand.nextInt(playerPieces.size()));
			// Get all possible moves for the selected piece
			possibleMoves = selectedPiece.getPossibleMoves();
		}

		ShogiSquare targetSquare = selectedPiece.getPossibleMoves().get(rand.nextInt(selectedPiece.getPossibleMoves().size()));


		ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		// Sends the move to human player so that he can print the move
		ShogiHumanPlayer.setPriorMove(selectedPiece.getPosition(),targetSquare);
		game.sendAction(moveAction);

	}
}