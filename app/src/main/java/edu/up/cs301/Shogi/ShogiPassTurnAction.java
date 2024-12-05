package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * Action to pass the turn when a player's timer runs out.
 */
public class ShogiPassTurnAction extends GameAction {
    public ShogiPassTurnAction(GamePlayer player) {
        /**
         * External Citation
         *  Date: 4 December 2024
         *  Problem: Need to add logic for dealing with running out of time
         *  Resource: ChatGPT
         *  Solution: Recommended me to create this ShogiPassTurnAction class to allow
         *              passing of turns when time runs out
         */
        super(player);
    }
}
