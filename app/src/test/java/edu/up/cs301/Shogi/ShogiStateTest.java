package edu.up.cs301.Shogi;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShogiStateTest {

    ShogiState firstInstance = new ShogiState(); // Create new instance of game state class
    ShogiHumanPlayer player = new ShogiHumanPlayer("player1");
    ShogiMoveAction movePawn1;
    ShogiMoveAction moveGoldG1;
    ShogiMoveAction moveBishop1;
    ShogiMoveAction moveGoldG2;
    ShogiMoveAction moveBishop2;
    ShogiMoveAction moveKing;
    ShogiMoveAction dropGoldG;

    @Before
    public void setUp() throws Exception {
        movePawn1 = new ShogiMoveAction(player, firstInstance.getPiece(6, 2), 5, 2);
        moveGoldG1 = new ShogiMoveAction(player, firstInstance.getPiece(0, 3), 1, 2);
        moveBishop1 = new ShogiMoveAction(player, firstInstance.getPiece(7, 1), 2, 6);
        firstInstance.getPiece(2,6).bePromoted(true); // bishop promotion
        moveGoldG2 = new ShogiMoveAction(player, firstInstance.getPiece(0, 5), 1, 5);
        moveBishop2= new ShogiMoveAction(player, firstInstance.getPiece(2, 6), 1, 5);
        moveKing = new ShogiMoveAction(player, firstInstance.getPiece(0, 4), 0, 3);
        firstInstance.getPieces().get(35).setOnBoard(true);
        dropGoldG = new ShogiMoveAction(player, firstInstance.getPieces().get(35), 1, 4);
    }

    @After
    public void tearDown() throws Exception {


    }


    @Test
    public void moveAction() {
        assertTrue(firstInstance.moveAction(movePawn1));
    }

    @Test
    public void dropAction() {
        assertTrue(firstInstance.moveAction(dropGoldG));
    }

    @Test
    public void promoteAction() {
        assertTrue(firstInstance.getPiece(1, 5).isPromoted());
    }

}