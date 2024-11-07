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

	public static final int TARGET_MAGNITUDE = 10;

	// the game's state
	private ShogiState gameState;
	
	/**
	 * can this player move
	 * 
	 * @return
	 * 		true, because all player are always allowed to move at all times,
	 * 		as this is a fully asynchronous game
	 */
	@Override
	protected boolean canMove(int playerIdx) {
		return true;
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
		Log.i("action", action.getClass().toString());
		
		if (action instanceof ShogiMoveAction) {

			ShogiMoveAction sma = (ShogiMoveAction)action;

			// denote that this was a legal/successful move
			return true;
		}
		else {
			// denote that this was an illegal move
			return false;
		}
	}//makeMove
	
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
		return null;
	}

}
