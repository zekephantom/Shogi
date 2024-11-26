package edu.up.cs301.Shogi;

import java.io.Serializable;

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
public class ShogiMoveAction extends GameAction implements Serializable {
	private static final long serialVersionUID = 1L;

	private ShogiPiece piece;
	private ShogiSquare targetPosition;

	public ShogiMoveAction(GamePlayer player, ShogiPiece piece, ShogiSquare targetPosition) {
		super(player);
		this.piece = piece;
		this.targetPosition = targetPosition;
	}

	public ShogiMoveAction(ShogiComputerPlayer1 player, ShogiPiece selectedPiece, int targetRow, int targetCol) {
		super();
	}

	public ShogiMoveAction(ShogiComputerPlayer1 player, ShogiPiece selectedPiece, ShogiSquare targetRow, ShogiSquare targetCol) {
	}

	public ShogiPiece getPiece() {
		return piece;
	}

	public ShogiSquare getTargetPosition() { return targetPosition; }
}