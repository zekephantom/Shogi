package edu.up.cs301.Shogi;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShogiStateTest {

    @Before
    public void setUp() throws Exception {
        ShogiState firstInstance = new ShogiState(); // Create new instance of game state class

        ShogiMoveAction moveGoldG1= new ShogiMoveAction(this, firstInstance.getPiece(0, 3), 1, 2);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPieces() {
    }

    @Test
    public void moveAction() {
        firstInstance.moveAction(moveGoldG1);

    }

    @Test
    public void getPiece() {
    }
}