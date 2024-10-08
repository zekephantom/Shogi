package edu.up.cs301.Shogi;

import android.graphics.Bitmap;

public class ShogiPiece {

    // Type of the piece (e.g., Pawn, Rook, King)
    private String type;

    // Owner of the piece (0 for Player 1, 1 for Player 2)
    private int owner;

    // Promotion status
    private boolean promoted;

    // Whether the piece is on the board
    private boolean onBoard;

    // Position on the board
    private int row;
    private int col;

    // Constructor
    public ShogiPiece(String type, int owner, int row, int col) {
        this.type = type;
        this.owner = owner;
        this.row = row;
        this.col = col;
        this.promoted = false; // Default to not promoted
        this.onBoard = false; // Default to on the board
    }

    // Constructor to match ShogiBoard expectations
    public ShogiPiece(int row, int col) {
        this.row = row;
        this.col = col;
        this.promoted = false; // Default to not promoted
        this.onBoard = false; // Default to on the board
    }

    // Copy Constructor (for deep copy)
    public ShogiPiece(ShogiPiece original) {
        this.type = original.type;
        this.owner = original.owner;
        this.promoted = original.promoted;
        this.onBoard = original.onBoard;
        this.row = original.row;
        this.col = original.col;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public boolean isOnBoard() { return onBoard; }

    public void setOnBoard(boolean onBoard) { this.onBoard = onBoard; }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
