package edu.up.cs301.Shogi;

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
public class ShogiDropAction extends GameAction {
    //private static final long serialVersionUID = 1L;

    private ShogiPiece pieceToDrop;
    private int targetRow;
    private int targetCol;

    public ShogiDropAction(GamePlayer player, ShogiPiece pieceToDrop, int targetRow, int targetCol) {
        super(player);
        this.pieceToDrop = pieceToDrop;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
    }

    public ShogiPiece getPieceToDrop() {
        return pieceToDrop;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }
}
