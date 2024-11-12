package edu.up.cs301.Shogi;

import java.util.ArrayList;

import edu.up.cs301.GameFramework.infoMessage.GameState;

/**
 * Class ShogiPiece represents a piece in the game of Shogi.
 * It stores information about the piece's type, owner, promotion status, whether it's on the board,
 * and its position on the board.
 *
 * @author
 * @version November 2023
 */
public class ShogiPiece {
    // Enum for the type of the piece
    /**
     * External Citation:
     * Date: 8 November 2024
     * Problem: Needed to implement and understand Java enums for piece types in Shogi.
     * Resource: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html, https://stackoverflow.com/questions/9246934/working-with-enums-in-android
     * Solution: Referenced Java's official documentation to understand the syntax, also looked into StackOverflow about enums
     *
     */
    // Watch this for later: https://www.youtube.com/watch?v=Hzs6OBcvNQE, about enums and performance issues
    public enum PieceType {
        King,
        Rook,
        Bishop,
        GoldGeneral,
        SilverGeneral,
        Knight,
        Lance,
        Pawn
    }

    // Type of the piece (e.g., Pawn, Rook, King)
    private PieceType type;

    // Owner of the piece (0 for Player 0, 1 for Player 1)
    private int owner;

    // Promotion status
    private boolean promoted;

    // Whether the piece is on the board
    private boolean onBoard;

    // Position on the board
    private ShogiSquare position;

    // ArrayList of all possible moves
    private ArrayList<ShogiSquare> possibleMoves;

    /**
     * Constructor for ShogiPiece
     *
     * @param type  The type of the Shogi Piece
     * @param owner The owner of the piece
     * @param position  Intended position
     */
    public ShogiPiece(PieceType type, int owner, ShogiSquare position) {
        this.type = type;
        this.owner = owner;
        this.position = position;
        this.promoted = false; // Default to not promoted
        this.onBoard = true; // Default to on the board
    }

    /**
     * Copy Constructor for ShogiPiece
     *
     * @param original The original ShogiPiece to copy
     */
    public ShogiPiece(ShogiPiece original) {
        this.type = original.type;
        this.owner = original.owner;
        this.promoted = original.promoted;
        this.onBoard = original.onBoard;
        for(ShogiSquare sq : original.possibleMoves) {
            this.possibleMoves.add(sq.clone());
        }
        //this.position.setRow(original.position.getRow());
        //this.position.setCol(original.position.getCol());
    }

    // Getters and Setters
    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
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

    // added to temporarily fix the error in dumb AI - Jona

    public ArrayList<ShogiSquare> getPossibleMoves(GameState gameState){
        return possibleMoves;
    }

    // Method for promoting a piece
    public void bePromoted(boolean promoted) {
        // Only certain pieces can be promoted
        if (type == PieceType.Pawn || type == PieceType.Lance || type == PieceType.Knight ||
                type == PieceType.SilverGeneral || type == PieceType.Rook || type == PieceType.Bishop) {
            this.promoted = promoted;
        }
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public ShogiSquare getPosition() { return position; }

    // Method to update the position of the piece
    public void setPosition(ShogiSquare position) {
        this.position = position;
    }
}
