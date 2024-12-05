package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import android.util.Log;

/**
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class ShogiLocalGame extends LocalGame {
	// the game's state
	private ShogiState gameState;
	
	/**
	 * can this player move
	 *
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return playerIdx == gameState.getCurrentPlayer();
	}


	/**
	 *
	 */
	public ShogiLocalGame(GameState state) {
		// initialize the game state
		if (! (state instanceof ShogiState)) {
			state = new ShogiState();
		}
		this.gameState = (ShogiState)state;
		super.state = state;
	}

	/**
	 *
	 */
	@Override
	protected boolean makeMove(GameAction action) {
		// Get the player who initiated the action
		GamePlayer actionPlayer = action.getPlayer();

		// Get the index of the player (0 or 1)
		int playerIdx = getPlayerIdx(actionPlayer);

		// Check if it's the player's turn
		if (!canMove(playerIdx)) {
			return false; // Not the player's turn
		}

		// Process the action based on its type
		if (action instanceof ShogiMoveAction || action instanceof ShogiDropAction) {
			// Delegate to gameState to handle the move or drop
			boolean success = gameState.moveAction(action);
			return success;
		}

		// Action type not recognized
		return false;
	}

	
	/**
	 * send the updated state to a given player
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// this is a perfect-information game, so we'll make a
		// complete copy of the state to send to the player
		p.sendInfo(new ShogiState(this.gameState));
		
	}//sendUpdatedSate
	
	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 * 
	 * @return
	 * 		a message that tells who has won the game, or null if the
	 * 		game is not over
	 */
	@Override
	protected String checkIfGameOver() {

		// if king can't move
		if (gameState.getPieces().get(4).getPossibleMoves().isEmpty()){
			boolean isKingOnlyPieceLeft = true;
			// go through piece and check if any other of player 1's pieces are on board
			for (ShogiPiece piece : gameState.getPieces()) {
				if (piece.getOwner() == 0 && piece.isOnBoard()) {
					isKingOnlyPieceLeft = false;
					break;
				}
			}
			if (isKingOnlyPieceLeft) {
				return "Player 2 has won.\n";
			}
		}

		if (gameState.getPieces().get(24).getPossibleMoves().isEmpty()){
			boolean isKingOnlyPieceLeft = true;
			// go through piece and check if any other of player 1's pieces are on board
			for (ShogiPiece piece : gameState.getPieces()) {
				if (piece.getOwner() == 1 && piece.isOnBoard()) {
					isKingOnlyPieceLeft = false;
					break;
				}
			}
			if (isKingOnlyPieceLeft) {
				return "Player 1 has won.\n";
			}
		}

		if (gameState.isCheckmate(1 - gameState.getCurrentPlayer(), players[1 - gameState.getCurrentPlayer()])) {
			return "Player " +  (gameState.getCurrentPlayer()) + " has won.";
		}
		if (gameState.isCheckmate(gameState.getCurrentPlayer(), players[gameState.getCurrentPlayer()])) {
			return "Player " +  (1 - gameState.getCurrentPlayer()) + " has won.";
		}
		return null;
	}

}
