package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A ShogiMoveAction represents an action taken by a player during the game.
 * It includes details such as whether it is a "plus" move and the ID of the player who made the move.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2012
 */
public class ShogiMoveAction extends GameAction {

	// to satisfy the serializable interface
	private static final long serialVersionUID = 28062013L;

	// Whether this move is a plus (true) or minus (false)
	private boolean isPlus;

	// The player ID who performed the action
	private int playerId;

	/**
	 * Constructor for the ShogiMoveAction class.
	 *
	 * @param player the player making the move
	 * @param isPlus value to initialize this.isPlus
	 */
	public ShogiMoveAction(GamePlayer player, boolean isPlus, int playerId) {
		super(player);
		this.isPlus = isPlus;
		this.playerId = playerId; // Store the player ID in a member variable
	}

	/**
	 * Getter method to tell whether the move is a "plus"
	 *
	 * @return
	 *         a boolean that tells whether this move is a "plus"
	 */
	public boolean isPlus() {
		return isPlus;
	}

	/**
	 * Getter method for playerId
	 *
	 * @return
	 *         the ID of the player making the move
	 */
	public int getPlayerId() {
		return playerId;
	}
}
