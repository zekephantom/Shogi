package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Action to pass the turn when a player's timer runs out.
 */
public class ShogiPassTurnAction extends GameAction {
    public ShogiPassTurnAction(GamePlayer player) {
        super(player);
    }
}
