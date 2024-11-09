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
        movePawn1 = new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(6, 2)), new ShogiSquare(5, 2));
        moveGoldG1 = new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(0, 3)), new ShogiSquare(1, 2));
        moveBishop1 = new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(7, 1)), new ShogiSquare(2, 6));
        firstInstance.getPiece(new ShogiSquare(2, 6)).bePromoted(true); // bishop promotion
        moveGoldG2 = new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(0, 5)), new ShogiSquare(1, 5));
        moveBishop2= new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(2, 6)), new ShogiSquare(1, 5));
        moveKing = new ShogiMoveAction(player, firstInstance.getPiece(new ShogiSquare(0, 4)), new ShogiSquare(0, 3));
        firstInstance.getPieces().get(35).setOnBoard(true);
        dropGoldG = new ShogiMoveAction(player, firstInstance.getPieces().get(35), new ShogiSquare(1, 4));
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
        assertTrue(firstInstance.getPiece(new ShogiSquare(1, 5)).isPromoted());
    }

}