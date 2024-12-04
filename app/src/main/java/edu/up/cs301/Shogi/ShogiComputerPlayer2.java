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
						// Sends the move to human player so that he can print the move
						ShogiHumanPlayer.setPriorMove(king.getPosition(),targetSquare);
						game.sendAction(checkMove);
					}
				}
			}
		}

		// if a piece can capture an opponents piece, do that
		for (ShogiPiece selectedPiece : playerPieces) {
			if (selectedPiece.isOnBoard()) {
				ArrayList<ShogiSquare> possibleMoves = selectedPiece.getPossibleMoves();

				for (ShogiSquare targetSquare : possibleMoves) {
					if (gameState.getPiece(targetSquare) != null && gameState.getPiece(targetSquare).getOwner() != playerNum) {
						ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
						// Sends the move to human player so that he can print the move
						ShogiHumanPlayer.setPriorMove(selectedPiece.getPosition(),targetSquare);
						game.sendAction(moveAction);
						return;
					}
				}
			}
		}

		// drop a piece if there are any to drop
		for (ShogiPiece selectedPiece : playerPieces) {
			if (!selectedPiece.isOnBoard()) {
				ShogiSquare targetSquare = new ShogiSquare(rand.nextInt(7), rand.nextInt(7));
				// Sends the move to human player so that he can print the move
				ShogiHumanPlayer.setPriorMove(selectedPiece.getPosition(),targetSquare);
				ShogiDropAction dropAction = new ShogiDropAction(this, selectedPiece, targetSquare);
				if (gameState.dropPiece(dropAction, false)) {
					game.sendAction(dropAction);
				}
			}
		}

		// do a random move
		ArrayList<ShogiSquare> possibleMoves = new ArrayList<>();
		ShogiPiece selectedPiece = null;

		while (possibleMoves.isEmpty()) {
			// Choose a random piece from the player's pieces
			selectedPiece = playerPieces.get(rand.nextInt(playerPieces.size()));
			// Get all possible moves for the selected piece
			possibleMoves = selectedPiece.getPossibleMoves();
		}

		ShogiSquare targetSquare = selectedPiece.getPossibleMoves().get(rand.nextInt(selectedPiece.getPossibleMoves().size()));

		// Sends the move to human player so that he can print the move
		ShogiHumanPlayer.setPriorMove(selectedPiece.getPosition(),targetSquare);

		ShogiMoveAction moveAction = new ShogiMoveAction(this, selectedPiece, targetSquare);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        game.sendAction(moveAction);

	}
}