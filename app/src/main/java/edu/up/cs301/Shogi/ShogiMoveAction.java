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
 * @version October 2024
 */
public class ShogiMoveAction extends GameAction {

	private ShogiPiece piece;   // The piece being moved
	private int moveRow;        // The row to which the piece is moved
	private int moveCol;        // The column to which the piece is moved

	/**
	 * Constructor for the ShogiMoveAction class.
	 *
	 * @param player The player making the move.
	 * @param piece The piece to be moved.
	 * @param moveRow The row to which the piece will be moved.
	 * @param moveCol The column to which the piece will be moved.
	 */
	public ShogiMoveAction(GamePlayer player, ShogiPiece piece, int moveRow, int moveCol) {
		super(player);
		this.piece = piece;
		this.moveRow = moveRow;
		this.moveCol = moveCol;
	}

	// Getter methods
	public ShogiPiece getPiece() {
		return piece;
	}

	public int getMoveRow() {
		return moveRow;
	}

	public int getMoveCol() {
		return moveCol;
	}

	// Setter methods
	public void setPiece(ShogiPiece piece) {
		this.piece = piece;
	}

	public void setMoveRow(int moveRow) {
		this.moveRow = moveRow;
	}

	public void setMoveCol(int moveCol) {
		this.moveCol = moveCol;
	}
}