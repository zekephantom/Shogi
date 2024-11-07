package edu.up.cs301.Shogi;

import java.util.ArrayList;
import java.util.List;

/**
 * ShogiBoard handles the logical representation of a Shogi game board,
 * maintaining the position of pieces and tracking captured pieces.
 */
public class ShogiBoard {
    // List of all active pieces on the board
    private List<ShogiPiece> shogiPieces;

    // List of captured pieces
    private List<ShogiPiece> capturedPieces;

    /**
     * Constructor initializes the game board and pieces.
     */
    public ShogiBoard() {
        shogiPieces = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        initializeBoard();
    }

    /**
     * Initializes the board to the starting positions of each piece.
     */
    private void initializeBoard() {
        // Initialize pieces in their starting positions.
        // Example:
        // shogiPieces.add(new ShogiPiece(PieceType.PAWN, 6, 0, PlayerType.PLAYER1));
        // shogiPieces.add(new ShogiPiece(PieceType.KING, 8, 4, PlayerType.PLAYER1));
        // Similar initialization for other pieces
    }

    /**
     * Moves a piece from its current position to a new position on the board.
     * @param piece the piece to be moved
     * @param row the row to move to
     * @param col the column to move to
     * @return true if the move is successful, false otherwise
     */
    public boolean movePiece(ShogiPiece piece, int row, int col) {
        if (isValidMove(piece, row, col)) {
            // Update piece's position
            piece.setPosition(row, col);
            return true;
        }
        return false;
    }

    /**
     * Captures a piece and moves it to the captured pieces list.
     * @param piece the piece to capture
     */
    public void capturePiece(ShogiPiece piece) {
        shogiPieces.remove(piece);
        capturedPieces.add(piece);
    }

    /**
     * Determines if a move is valid.
     * @param piece the piece to move
     * @param row the target row
     * @param col the target column
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(ShogiPiece piece, int row, int col) {
        // Add logic to determine if a move is valid based on game rules
        return true; // Placeholder
    }

    /**
     * Retrieves all pieces on the board.
     * @return list of all pieces
     */
    public List<ShogiPiece> getShogiPieces() {
        return shogiPieces;
    }

    /**
     * Retrieves all captured pieces.
     * @return list of captured pieces
     */
    public List<ShogiPiece> getCapturedPieces() {
        return capturedPieces;
    }
}
