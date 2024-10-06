package edu.up.cs301.Shogi;

import android.graphics.Bitmap;

public class ShogiPiece {

    // Type of the piece (e.g., Pawn, Rook, King)
    private String type;

    // Owner of the piece (0 for Player 1, 1 for Player 2)
    private int owner;

    // Promotion status
    private boolean promoted;

    // Bitmap representing the visual appearance of the piece
    private Bitmap bitmap;

    // Position on the board
    private int row;
    private int col;

    // Constructor
    public ShogiPiece(String type, int owner, int row, int col, Bitmap bitmap) {
        this.type = type;
        this.owner = owner;
        this.row = row;
        this.col = col;
        this.bitmap = bitmap;
        this.promoted = false; // Default to not promoted
    }

    // Constructor to match ShogiBoard expectations
    public ShogiPiece(Bitmap bitmap, int row, int col) {
        this.bitmap = bitmap;
        this.row = row;
        this.col = col;
        this.promoted = false; // Default to not promoted
    }

    // Copy Constructor (for deep copy)
    public ShogiPiece(ShogiPiece original) {
        this.type = original.type;
        this.owner = original.owner;
        this.promoted = original.promoted;
        this.bitmap = original.bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

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
