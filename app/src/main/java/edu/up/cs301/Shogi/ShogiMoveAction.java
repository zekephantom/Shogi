package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.players.GamePlayer;
import edu.up.cs301.GameFramework.actionMessage.GameAction;

/**
 * A ShogiMoveAction is an action that is a "move" in the game: moving a piece to a given location.
 *
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author Arnaj Sandhu
 * @author James Pham
 * @author Ezekiel Rafanan
 * @version October 2024
 */
public class ShogiMoveAction extends GameAction {
	private static final long serialVersionUID = 1L;

	private ShogiPiece piece;
	private int moveRow;
	private int moveCol;

	public ShogiMoveAction(GamePlayer player, ShogiPiece piece, int moveRow, int moveCol) {
		super(player);
		this.piece = piece;
		this.moveRow = moveRow;
		this.moveCol = moveCol;
	}

	public ShogiPiece getPiece() {
		return piece;
	}

	public int getMoveRow() {
		return moveRow;
	}

	public int getMoveCol() {
		return moveCol;
	}
}