package edu.up.cs301.Shogi;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShogiStateTest {

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void moveAction() {
        ShogiState firstInstance = new ShogiState(); // Create new instance of game state class
        //ShogiState firstCopy = new ShogiState(firstInstance); 	//Create deep copy from player 1's perspective
        ShogiHumanPlayer player = new ShogiHumanPlayer("player1");

        ShogiMoveAction movePawn1 = new ShogiMoveAction(player, firstInstance.getPiece(6, 2), 5, 2);
        assertTrue(firstInstance.moveAction(movePawn1));

        ShogiMoveAction movePawn2 = new ShogiMoveAction(player, firstInstance.getPiece(2, 2), 5, 2);
        assertFalse(firstInstance.moveAction(movePawn2));


    }

}