package edu.up.cs301.Shogi;

import java.util.ArrayList;
import edu.up.cs301.GameFramework.infoMessage.GameState;

/**
 * This class represents the state of the Shogi game.
 *
 * @author
 * @version November 2023
 */
public class ShogiState extends GameState {

	// Instance variables

	// List of all active pieces on the board
	private ArrayList<ShogiPiece> pieces;

	// Captured pieces for each player
	private ArrayList<ShogiPiece> capturedPiecesPlayer0; // Player 0's captured pieces
	private ArrayList<ShogiPiece> capturedPiecesPlayer1; // Player 1's captured pieces

	// Current player (0 for Player 0, 1 for Player 1)
	private int currentPlayer;

	// Game phase (e.g., "Main Play")
	private String gamePhase;

	// Constructor
	public ShogiState() {
		pieces = new ArrayList<>();
		capturedPiecesPlayer0 = new ArrayList<>();
		capturedPiecesPlayer1 = new ArrayList<>();
		initPieces();
		currentPlayer = 0; // Player 0 starts
		gamePhase = "Main Play";
	}

	// Copy constructor for deep copying
	public ShogiState(ShogiState original) {
		this.pieces = new ArrayList<>();
		for (ShogiPiece piece : original.pieces) {
			this.pieces.add(new ShogiPiece(piece));
		}

		// TODO: Need to complete external citation here
		/**
		 * External Citation
		 * 	Date:
		 * 	Problem:
		 * 	Resource:
		 * 	Solution:
		 */

		this.capturedPiecesPlayer0 = new ArrayList<>();
		for (ShogiPiece piece : original.capturedPiecesPlayer0) {
			this.capturedPiecesPlayer0.add(new ShogiPiece(piece));
		}
		this.capturedPiecesPlayer1 = new ArrayList<>();
		for (ShogiPiece piece : original.capturedPiecesPlayer1) {
			this.capturedPiecesPlayer1.add(new ShogiPiece(piece));
		}
		this.currentPlayer = original.currentPlayer;
		this.gamePhase = original.gamePhase;
	}

	// Getters and setters
	public ArrayList<ShogiPiece> getPieces() {
		return pieces;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public String getGamePhase() {
		return gamePhase;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setGamePhase(String gamePhase) {
		this.gamePhase = gamePhase;
	}

	/**
	 * Initializes the pieces on the board at their starting positions.
	 */
	private void initPieces() {
		// Initialize pieces for Player 0 (bottom side)
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 0, 8, 0));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 0, 8, 1));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 0, 8, 2));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 0, 8, 3));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.King, 0, 8, 4));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 0, 8, 5));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 0, 8, 6));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 0, 8, 7));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 0, 8, 8));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Bishop, 0, 7, 1));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Rook, 0, 7, 7));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece(ShogiPiece.PieceType.Pawn, 0, 6, i));
		}

		// Initialize pieces for Player 1 (top side)
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 1, 0, 0));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 1, 0, 1));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 1, 0, 2));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 1, 0, 3));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.King, 1, 0, 4));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 1, 0, 5));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 1, 0, 6));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 1, 0, 7));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 1, 0, 8));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Bishop, 1, 1, 7));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Rook, 1, 1, 1));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece(ShogiPiece.PieceType.Pawn, 1, 2, i));
		}
	}


	/**
	 * Switches the current player.
	 */
	private void switchTurn() {
		currentPlayer = 1 - currentPlayer;
	}

	/**
	 * Captures a piece and adds it to the capturing player's captured pieces list.
	 *
	 * @param piece The piece to capture.
	 */
	private void capturePiece(ShogiPiece piece) {
		piece.setOnBoard(false);
		piece.setPromoted(false);
		pieces.remove(piece);
		if (currentPlayer == 0) {
			capturedPiecesPlayer0.add(piece);
		} else {
			capturedPiecesPlayer1.add(piece);
		}
	}

	/**
	 * Retrieves the piece at the specified position.
	 *
	 * @param row The row position.
	 * @param col The column position.
	 * @return The piece at the position or null if none.
	 */
	public ShogiPiece getPiece(int row, int col) {
		for (ShogiPiece piece : pieces) {
			if (piece.isOnBoard() && piece.getRow() == row && piece.getCol() == col) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * Checks if the position is within the board boundaries.
	 *
	 * @param row The row index.
	 * @param col The column index.
	 * @return True if within bounds, false otherwise.
	 */
	private boolean isWithinBounds(int row, int col) {
		return row >= 0 && row < 9 && col >= 0 && col < 9;
	}

	/**
	 * Finalizes a move by updating the piece's position and handling captures and promotions.
	 *
	 * @param piece     The piece to move.
	 * @param targetRow The target row.
	 * @param targetCol The target column.
	 * @return True if the move is successful, false otherwise.
	 */
	private boolean finalizeMove(ShogiPiece piece, int targetRow, int targetCol) {
		// Check bounds
		if (!isWithinBounds(targetRow, targetCol)) {
			return false;
		}

		// Check for own piece at target
		ShogiPiece targetPiece = getPiece(targetRow, targetCol);
		if (targetPiece != null && targetPiece.getOwner() == currentPlayer) {
			return false;
		}

		// Handle capture
		if (targetPiece != null && targetPiece.getOwner() != currentPlayer) {
			capturePiece(targetPiece);
		}

		// Update position
		piece.setPosition(targetRow, targetCol);

		// Handle promotion if eligible
		if (isEligibleForPromotion(piece, targetRow)) {
			// For simplicity, auto-promote if eligible (can be modified to ask the player)
			piece.bePromoted(true);
		}

		// Switch turn
		switchTurn();

		return true;
	}

	/**
	 * Checks if a piece is eligible for promotion.
	 *
	 * @param piece The piece to check.
	 * @param row   The row the piece is moving to.
	 * @return True if eligible for promotion, false otherwise.
	 */
	private boolean isEligibleForPromotion(ShogiPiece piece, int row) {
		if (piece.getOwner() == 0) {
			return row <= 2;
		} else {
			return row >= 6;
		}
	}

	/**
	 * Checks if the path is blocked for sliding pieces.
	 *
	 * @param piece     The piece attempting to move.
	 * @param targetRow The target row.
	 * @param targetCol The target column.
	 * @return True if path is blocked, false otherwise.
	 */
	private boolean isPathBlocked(ShogiPiece piece, int targetRow, int targetCol) {
		int currentRow = piece.getRow();
		int currentCol = piece.getCol();

		int rowDirection = Integer.compare(targetRow, currentRow);
		int colDirection = Integer.compare(targetCol, currentCol);

		int row = currentRow + rowDirection;
		int col = currentCol + colDirection;

		while (row != targetRow || col != targetCol) {
			if (getPiece(row, col) != null) {
				return true;
			}
			row += rowDirection;
			col += colDirection;
		}
		return false;
	}

	/**
	 * Moves the King according to its movement rules.
	 *
	 * @param action The move action.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveKing(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.King || piece.getOwner() != currentPlayer) {
			return false;
		}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();

		int rowDiff = Math.abs(currentRow - targetRow);
		int colDiff = Math.abs(currentCol - targetCol);

		if (rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)) {
			return finalizeMove(piece, targetRow, targetCol);
		}
		return false;
	}

	/**
	 * Moves the Rook according to its movement rules.
	 *
	 * @param action The move action.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveRook(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.Rook || piece.getOwner() != currentPlayer) {return false;}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();

		if (currentRow != targetRow && currentCol != targetCol) {
			// Not moving in a straight line
			// Check for promoted Rook's diagonal move
			if (piece.isPromoted()) {
				int rowDiff = Math.abs(currentRow - targetRow);
				int colDiff = Math.abs(currentCol - targetCol);
				if (rowDiff == 1 && colDiff == 1) {
					return finalizeMove(piece, targetRow, targetCol);
				}
			}
			return false;
		}

		// Check path for obstructions
		if (isPathBlocked(piece, targetRow, targetCol)) {
			return false;
		}

		return finalizeMove(piece, targetRow, targetCol);
	}

	// Implement similar methods for other piece types
	// For brevity, only the movePawn method is added here

	/**
	 * Moves the Pawn according to its movement rules.
	 *
	 * @param action The move action.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean movePawn(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.Pawn || piece.getOwner() != currentPlayer) {
			return false;
		}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();

		int rowDirection = (currentPlayer == 0) ? -1 : 1;

		if (!piece.isPromoted()) {
			if (targetRow != currentRow + rowDirection || targetCol != currentCol) {
				return false;
			}
		} else {
			// Promoted Pawn moves like a Gold General
			if (!isValidGoldGeneralMove(piece, targetRow, targetCol)) {
				return false;
			}
		}

		// Check for capturing own piece
		ShogiPiece targetPiece = getPiece(targetRow, targetCol);
		if (targetPiece != null && targetPiece.getOwner() == currentPlayer) {
			return false;
		}

		// Check for illegal Pawn drop (if implementing drops)

		return finalizeMove(piece, targetRow, targetCol);
	}

	/**
	 * Checks if the move is valid for a Gold General.
	 *
	 * @param piece     The piece attempting to move.
	 * @param targetRow The target row.
	 * @param targetCol The target column.
	 * @return True if the move is valid, false otherwise.
	 */
	private boolean isValidGoldGeneralMove(ShogiPiece piece, int targetRow, int targetCol) {
		int currentRow = piece.getRow();
		int currentCol = piece.getCol();

		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		int[][] validMoves;

		if (piece.getOwner() == 0) {
			// Player 0 (moving upwards)
			validMoves = new int[][]{
					{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}
			};
		} else {
			// Player 1 (moving downwards)
			validMoves = new int[][]{
					{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}
			};
		}

		for (int[] move : validMoves) {
			if (rowDiff == move[0] && colDiff == move[1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles a move action by delegating to the appropriate piece movement method.
	 *
	 * @param action The move action to handle.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveAction(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		switch (piece.getType()) {
			case King:
				return moveKing(action);
			case Rook:
				return moveRook(action);
			case Bishop:
				return moveBishop(action);
			case GoldGeneral:
				return moveGoldGeneral(action);
			case SilverGeneral:
				return moveSilverGeneral(action);
			case Knight:
				return moveKnight(action);
			case Lance:
				return moveLance(action);
			case Pawn:
				return movePawn(action);
			default:
				return false;
		}
	}


	/**
	 * Moves the Bishop according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveBishop(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.Bishop || piece.getOwner() != currentPlayer) {return false;}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();

		int rowDiff = Math.abs(targetRow - currentRow);
		int colDiff = Math.abs(targetCol - currentCol);

		// Check if it's a valid diagonal move for an unpromoted Bishop
		if (rowDiff == colDiff) {
			// Verify that the path is clear for diagonal movement
			if (!isPathBlocked(piece, targetRow, targetCol)) {
				return finalizeMove(piece, targetRow, targetCol);
			}
			return false;
		}

		// If promoted (Dragon Horse), allow one-step orthogonal moves in addition to diagonal
		if (piece.isPromoted()) {
			// Check for one-square orthogonal movement
			if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
				return finalizeMove(piece, targetRow, targetCol);
			}
		}

		// If none of the move conditions are met, the move is invalid
		return false;
	}

	/**
	 * Moves the Gold General according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveGoldGeneral(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.GoldGeneral || piece.getOwner() != currentPlayer) {return false;}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();

		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		// Define allowed moves for the Gold General based on player direction
		int[][] validMoves;

		if (piece.getOwner() == 0) {
			// Player 0: moving upward
			validMoves = new int[][]{
					{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}
			};
		} else {
			// Player 1: moving downward
			validMoves = new int[][]{
					{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}
			};
		}

		// Check if the move matches any of the allowed moves
		for (int[] move : validMoves) {
			if (rowDiff == move[0] && colDiff == move[1]) {
				return finalizeMove(piece, targetRow, targetCol);
			}
		}

		// Return false if the move is not valid for a Gold General
		return false;
	}

	// Similar methods for moveGoldGeneral, moveSilverGeneral, moveKnight, moveLance

	/**
	 * Moves the Silver General according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveSilverGeneral(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.SilverGeneral || piece.getOwner() != currentPlayer) {
			return false;
		}
		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();
		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetRow, targetCol);
		} else {
			// Unpromoted Silver General move directions
			int[][] validMoves;
			if (piece.getOwner() == 0) {
				validMoves = new int[][]{
						{-1, -1}, {-1, 0}, {-1, 1}, {1, -1}, {1, 1}
				};
			} else {
				validMoves = new int[][]{
						{1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 1}
				};
			}
			for (int[] move : validMoves) {
				if (rowDiff == move[0] && colDiff == move[1]) {
					return finalizeMove(piece, targetRow, targetCol);
				}
			}
		}
		return false;
	}


	/**
	 * Moves the Knight according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveKnight(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.Knight || piece.getOwner() != currentPlayer) {
			return false;
		}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();
		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetRow, targetCol);
		} else {
			// Unpromoted Knight move directions
			int[][] validMoves;
			if (piece.getOwner() == 0) {
				validMoves = new int[][]{
						{-2, -1}, {-2, 1}
				};
			} else {
				validMoves = new int[][]{
						{2, -1}, {2, 1}
				};
			}
			for (int[] move : validMoves) {
				if (rowDiff == move[0] && colDiff == move[1]) {
					// Check if target position is within bounds
					if (!isWithinBounds(targetRow, targetCol)) {
						return false;
					}
					// Knights can jump over pieces, so no need to check for path obstructions
					// Check if target square has own piece
					ShogiPiece targetPiece = getPiece(targetRow, targetCol);
					if (targetPiece != null && targetPiece.getOwner() == currentPlayer) {
						return false;
					}
					return finalizeMove(piece, targetRow, targetCol);
				}
			}
		}
		return false;
	}


	/**
	 * Moves the Lance according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveLance(ShogiMoveAction action) {
		ShogiPiece piece = action.getPiece();
		if (piece.getType() != ShogiPiece.PieceType.Lance || piece.getOwner() != currentPlayer) {
			return false;
		}

		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int targetRow = action.getMoveRow();
		int targetCol = action.getMoveCol();
		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetRow, targetCol);
		} else {
			// Unpromoted Lance: Only moves forward in the same column
			if (colDiff == 0) {
				if ((piece.getOwner() == 0 && rowDiff < 0) || (piece.getOwner() == 1 && rowDiff > 0)) {
					// Ensure the path is clear
					if (!isPathBlocked(piece, targetRow, targetCol)) {
						return finalizeMove(piece, targetRow, targetCol);
					}
				}
			}
		}
		return false;
	}


	/**
	 * Helper method to handle movement as a Gold General.
	 *
	 * @param piece The Silver General piece.
	 * @param targetRow Target row for the move.
	 * @param targetCol Target column for the move.
	 * @return True if the move is successful, false otherwise.
	 */
	private boolean moveAsGoldGeneral(ShogiPiece piece, int targetRow, int targetCol) {
		int currentRow = piece.getRow();
		int currentCol = piece.getCol();
		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		int[][] validMoves;

		if (piece.getOwner() == 0) {
			validMoves = new int[][]{
					{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}
			};
		} else {
			validMoves = new int[][]{
					{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}
			};
		}

		for (int[] move : validMoves) {
			if (rowDiff == move[0] && colDiff == move[1]) {
				return finalizeMove(piece, targetRow, targetCol);
			}
		}
		return false;
	}



	// Additional helper methods as needed


}
