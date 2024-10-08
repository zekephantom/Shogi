package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A ShogiMoveAction is an action that is a "move" the game: moving a piece to a given location
 *
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author Arnaj Sandhu
 * @author James Pham
 * @version October 2024
 */
public class ShogiMoveAction extends GameAction {

	ShogiPiece piece;
	int moveRow;
	int moveCol;
	
	/**
	 * Constructor for the CounterMoveAction class.
	 *
	 */
	public ShogiMoveAction(GamePlayer player, ShogiPiece piece, int moveRow, int moveCol) {
		super(player);
		this.piece = piece;
		this.moveRow = moveRow;
		this.moveCol = moveCol;
	}
}//class ShogiMoveAction
