package edu.up.cs301.Shogi;

public class ShogiPiece {

    // Type of the piece (e.g., Pawn, Rook, King)
    private String type;

    // Owner of the piece (0 for Player 1, 1 for Player 2)
    private int owner;

    // Promotion status
    private boolean promoted;

    // Constructor
    public ShogiPiece(String type, int owner) {
        this.type = type;
        this.owner = owner;
        this.promoted = false; // Default to not promoted
    }

    // Copy Constructor (for deep copy)
    public ShogiPiece(ShogiPiece original) {
        this.type = original.type;
        this.owner = original.owner;
        this.promoted = original.promoted;
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
}
