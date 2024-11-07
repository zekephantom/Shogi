package edu.up.cs301.Shogi;

/**
 * class ShogiPiece details the piece representation in the game.
 * This class stores info about the piece's type, its owner, its promotion status, whether it exists on the board or not,
 *  and its position relative to the board
 *
 * @author Ezekiel Rafanan
 * @author Arnaj Sandhu
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author James Pham
 * @version 28 October 2024
 */
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

    /**
     *
     * @param type The type of the Shogi Piece
     * @param owner The owner of the piece
     * @param row intended row position
     * @param col intended column position
     */
    public ShogiPiece(String type, int owner, int row, int col) {
        this.type = type;
        this.owner = owner;
        this.row = row;
        this.col = col;
        this.promoted = false; // Default to not promoted
        this.onBoard = true; // Default to on the board
        // TODO Implement ArrayList for which squares this piece can move to
    }

    /**
     * Shogi Piece Deep Copy Constructor
     * @param original
     */
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

    //method for promoting a piece
    public void bePromoted(boolean promoted) {
        if (type.equals("Pawn") || type.equals("Lance") || type.equals("Knight") ||
                type.equals("SilverGeneral") || type.equals("Rook") || type.equals("Bishop")) {
            this.promoted = promoted;
        }
        // TODO add switch case for setting promoted bitmap
    }

    public boolean isOnBoard() { return onBoard; }
    public void setOnBoard(boolean onBoard) { this.onBoard = onBoard;}
    public int getRow() {return row;}
    public void setRow(int row) {this.row = row;}
    public int getCol() {return col;}
    public void setCol(int col) {this.col = col;}

    // Method to update the position of the piece
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
