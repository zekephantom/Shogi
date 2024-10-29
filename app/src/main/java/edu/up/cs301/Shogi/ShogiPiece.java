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

    // Constructor

    public ShogiPiece(String type, int owner, int row, int col) {
        /**
         * External Citation
         * Date: 23 October 2024
         * Problem: Professor Nux commented about a potential problem. Quote:
         * "row/col in Shogi piece may actually create problems for you.  What if it
         *      gets out of sync with the board?  Is is it really necessary?"
         * Resource: ChatGPT
         * Solution: I asked ChatGPT to look into the code along with the comment, and
         *              advised to remove/comment out row and col from default and copy constructors
         */
        this.type = type;
        this.owner = owner;
        //this.row = row;
        //this.col = col;
        this.promoted = false; // Default to not promoted
        this.onBoard = false; // Default to on the board
    }

    // Copy Constructor (for deep copy)
    public ShogiPiece(ShogiPiece original) {
        this.type = original.type;
        this.owner = original.owner;
        this.promoted = original.promoted;
        this.onBoard = original.onBoard;
        //this.row = original.row;
        //this.col = original.col;
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

    // Method to update the position of the piece
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
