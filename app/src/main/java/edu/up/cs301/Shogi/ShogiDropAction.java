package edu.up.cs301.Shogi;

import java.io.Serializable;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Represents a drop action in the Shogi game.
 *
 * @author Ezekiel Rafanan
 * @author Arnaj Sandhu
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author James Pham
 * @version 8 November 2024
 */
public class ShogiDropAction extends GameAction implements Serializable {
    //private static final long serialVersionUID = 1L;

    private ShogiPiece pieceToDrop;
    private ShogiSquare targetPosition;

    public ShogiDropAction(GamePlayer player, ShogiPiece pieceToDrop, ShogiSquare targetPosition) {
        super(player);
        this.pieceToDrop = pieceToDrop;
        this.targetPosition = targetPosition;
    }

    public ShogiPiece getPieceToDrop() {
        return pieceToDrop;
    }

    public ShogiSquare getTargetPosition() {
        return targetPosition;
    }

    public ShogiPiece getPiece() {
        return pieceToDrop;
    }
}
