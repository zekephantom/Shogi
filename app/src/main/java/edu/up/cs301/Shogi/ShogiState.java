package edu.up.cs301.Shogi;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GamePlayer;

/**
 * This class represents the state of the Shogi game.
 *
 * @author Ezekiel Rafanan
 * @author Jona Bodirsky
 * @author Arnaj Sandhu
 * @author Makengo Lokombo
 * @author James Pham
 *
 * @version November 2024
 */
public class ShogiState extends GameState implements Serializable {


	// Instance variables
	// List of all active pieces on the board
	private ArrayList<ShogiPiece> pieces;
	private static final int Player0Lance1  = 0;

	//// Captured pieces for each player
	//private ArrayList<ShogiPiece> capturedPiecesPlayer0; // Player 0's captured pieces
	//private ArrayList<ShogiPiece> capturedPiecesPlayer1; // Player 1's captured pieces

	// Current player (0 for Player 0, 1 for Player 1)
	private int currentPlayer;

	// Game phase (e.g., "Main Play")
	private String gamePhase;

	// Constructor
	public ShogiState() {
		pieces = new ArrayList<>();
		//capturedPiecesPlayer0 = new ArrayList<>();
		//capturedPiecesPlayer1 = new ArrayList<>();
		initPieces();
		currentPlayer = 0; // Player 0 starts
		gamePhase = "Main Play";
		for (ShogiPiece piece : pieces) {
			updatePossibleMoves(piece);
		}
	}

	// Copy constructor for deep copying
	public ShogiState(ShogiState original) {
		this.pieces = new ArrayList<>();
		for (ShogiPiece piece : original.pieces) {
			this.pieces.add(new ShogiPiece(piece));
		}

		/**
		 * External Citation//
		 * 	Date: 8 November 2024
		 * 	Problem: Needed a way to perform deep copies of captured pieces lists to avoid references to the original objects.
		 * 	Resource: https://www.geeksforgeeks.org/deep-shallow-lazy-copy-java-examples/, ChatGPT
		 * 	Solution:
		 * 		Implemented a loop to create new ShogiPiece objects for each item in the original lists,
		 *		ensuring each piece is independently copied without linking to the original.
		 */

		//this.capturedPiecesPlayer0 = new ArrayList<>();
		//for (ShogiPiece piece : original.capturedPiecesPlayer0) {
		//	this.capturedPiecesPlayer0.add(new ShogiPiece(piece));
		//}
		//this.capturedPiecesPlayer1 = new ArrayList<>();
		//for (ShogiPiece piece : original.capturedPiecesPlayer1) {
		//	this.capturedPiecesPlayer1.add(new ShogiPiece(piece));
		//}

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
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 0, new ShogiSquare(8, 0)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 0, new ShogiSquare(8, 1)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 0, new ShogiSquare(8, 2)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 0, new ShogiSquare(8, 3)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.King, 0, new ShogiSquare(8, 4)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 0, new ShogiSquare(8, 5)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 0, new ShogiSquare(8, 6)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 0, new ShogiSquare(8, 7)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 0, new ShogiSquare(8, 8)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Bishop, 0, new ShogiSquare(7, 1)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Rook, 0, new ShogiSquare(7, 7)));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece(ShogiPiece.PieceType.Pawn, 0, new ShogiSquare(6, i)));
		}

		// Initialize pieces for Player 1 (top side)
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 1, new ShogiSquare(0, 0)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 1, new ShogiSquare(0, 1)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 1, new ShogiSquare(0, 2)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 1, new ShogiSquare(0, 3)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.King, 1, new ShogiSquare(0, 4)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.GoldGeneral, 1, new ShogiSquare(0, 5)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.SilverGeneral, 1, new ShogiSquare(0, 6)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Knight, 1, new ShogiSquare(0, 7)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Lance, 1, new ShogiSquare(0, 8)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Bishop, 1, new ShogiSquare(1, 7)));
		pieces.add(new ShogiPiece(ShogiPiece.PieceType.Rook, 1, new ShogiSquare(1, 1)));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece(ShogiPiece.PieceType.Pawn, 1, new ShogiSquare(2, i)));
		}
	}


	/**
	 * Switches the current player.
	 */
	private void switchTurn() {
		/**
		 * External Citation
		 * Date: 8 November 2024
		 * Problem: Needed a simple way to alternate between players after each turn.
		 * Resource: ChatGPT 4o
		 * Solution: Used a common toggle pattern (currentPlayer = 1 - currentPlayer) to switch between player 0 and player 1 each turn.
		 */
		currentPlayer = 1 - currentPlayer;
	}

	/**
	 * Captures a piece and adds it to the capturing player's captured pieces list.
	 *
	 * @param piece The piece to capture.
	 */
	private void capturePiece(ShogiPiece piece) {
		piece.setOwner(currentPlayer);
		piece.setOnBoard(false);
		piece.setPromoted(false);
		piece.setPossibleMoves(null);
	}

	/**
	 * Retrieves the piece at the specified position.
	 *
	 * @param position the position.
	 * @return The piece at the position or null if none.
	 */
	public ShogiPiece getPiece(ShogiSquare position) {
		for (ShogiPiece piece : pieces) {
			// isOnboard is not needed and breaks GUI selection for a captured piece
			try{
				if (/*piece.isOnBoard() && */piece.getPosition().equals(position)) {
					return piece;
				}
			}catch(NullPointerException npe){
				Log.d("NULL", "null obj");
			}
		}
		return null;
	}

	/**
	 * Checks if the position is within the board boundaries.
	 *
	 * @param position Which position to check
	 * @return True if within bounds, false otherwise.
	 */
	private boolean isOutOfBounds(ShogiSquare position) {
		return position.getRow() < 0 || position.getRow() >= 9 || position.getCol() < 0 || position.getCol() >= 9;
	}

	/**
	 * Finalizes a move by updating the piece's position and handling captures and promotions.
	 * Capture piece logic
	 *
	 * @param piece     The piece to move.
	 * @param targetPosition The target position.
	 * @return True if the move is successful, false otherwise.
	 */
	private boolean finalizeMove(ShogiPiece piece, ShogiSquare targetPosition) {
		/**
		 * External Citation
		 * Date: 8 November 2024
		 * Problem: Needed a method to streamline finalizing each piece move in Shogi, handling tasks like
		 *          position updates, capture checks, and turn switching in one location.
		 * Resource: ChatGPT 4o
		 * Solution: Developed `finalizeMove()` to perform consistent end-of-move operations, consolidating
		 *           these processes to simplify individual piece movement methods.
		 */
		// Check bounds
		if (isOutOfBounds(targetPosition)) {
			return false;
		}

		// Check for own piece at target
		ShogiPiece targetPiece = getPiece(targetPosition);
		if (targetPiece != null && targetPiece.getOwner() == piece.getOwner()) {
			return false;
		}

		// Handle capture
		if (targetPiece != null && targetPiece.getOwner() != piece.getOwner()) {
			capturePiece(targetPiece);
		}

		// Update position
		piece.setPosition(targetPosition);

		// Handle promotion if eligible
		if (isEligibleForPromotion(piece, targetPosition.getRow())) {
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
	 * @param targetPosition The target position.
	 * @return True if path is blocked, false otherwise.
	 */
	private boolean isPathBlocked(ShogiPiece piece, ShogiSquare targetPosition) {
		ShogiSquare currentPosition = new ShogiSquare(piece.getPosition());
		if (piece == null) return false;

		int rowDirection = Integer.compare(targetPosition.getRow(), currentPosition.getRow());
		int colDirection = Integer.compare(targetPosition.getCol(), currentPosition.getCol());

		int row = currentPosition.getRow() + rowDirection;
		int col = currentPosition.getCol() + colDirection;
		ShogiSquare nextPos = new ShogiSquare(row, col);

		while (row - rowDirection != targetPosition.getRow() || col - colDirection != targetPosition.getCol()) {
			nextPos = new ShogiSquare(row, col);
			if (getPiece(nextPos) != null && getPiece(nextPos).isOnBoard()) {
				if (getPiece(nextPos).getOwner() == piece.getOwner()) {
					return true;
				}
			}
			int nextRow = row - rowDirection;
			int nextCol = col - colDirection;
			ShogiSquare stepPos = new ShogiSquare(nextRow, nextCol);
			if (nextRow >= 0 && nextCol >= 0) {
				if (getPiece(stepPos) != null && getPiece(stepPos).isOnBoard()) {
					if (getPiece(stepPos).getOwner() != piece.getOwner()) {
						return true;
					}
				}
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
	 * @param finalizeMove Whether the move should be finalized.
	 * @return True if the move is valid, false otherwise.
	 */
	public boolean moveKing(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.King || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.King || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = new ShogiSquare(targetRow, targetCol);

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		// go through each piece
		for (int i = 0; i < pieces.size(); i++) {
			ShogiPiece curPiece = pieces.get(i);
			if (curPiece.getOwner() != piece.getOwner() && curPiece.getType() != ShogiPiece.PieceType.King && curPiece.isOnBoard()) {
				updatePossibleMoves(curPiece);
				// go through each possible move for the current piece
				for (ShogiSquare possibleMove : curPiece.getPossibleMoves()) {
					// check if we are trying to move where an opponents piece can move to
					if (targetPosition.equals(possibleMove)) {
						return false;
					}
				}
			}
		}

		int rowDiff = Math.abs(currentRow - targetRow);
		int colDiff = Math.abs(currentCol - targetCol);

		if ((rowDiff > 1 || rowDiff < -1 || colDiff > 1 || colDiff < -1) && isOutOfBounds(targetPosition)) {
			return false;
		}

		// if the king is in check after this move
		if (!finalizeMove) {
			if (!checkIfMoveProtectsKing(action)) {
				return false;
			}
		}

		if (finalizeMove) {
			return finalizeMove(piece, targetPosition);
		}
		else {
			return true;
		}
	}

	/**
	 * Moves the Rook according to its movement rules.
	 *
	 * @param action The move action.
	 * @param finalizeMove Whether the move should be finalized.
	 * @return True if the move is valid, false otherwise.
	 */
	public boolean moveRook(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.Rook || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.Rook || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = new ShogiSquare(targetRow, targetCol);

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		if (piece.isPromoted()) {
			int rowDiff = Math.abs(currentRow - targetRow);
			int colDiff = Math.abs(currentCol - targetCol);
			if (rowDiff == 1 && colDiff == 1) {
				if (finalizeMove) {
					return finalizeMove(piece, targetPosition);
				}
				else {
					return true;
				}
			}
		}

		// Check path for obstructions
		if (isPathBlocked(piece, targetPosition)) {
			return false;
		}

		if (finalizeMove) {
			return finalizeMove(piece, targetPosition);
		}
		else {
			return true;
		}
	}

	/**
	 * Moves the Bishop according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveBishop(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.Bishop || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.Bishop || !piece.isOnBoard()) {
				return false;
			}
		}
		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();

		int rowDiff = Math.abs(targetRow - currentRow);
		int colDiff = Math.abs(targetCol - currentCol);

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		// If promoted (Dragon Horse), allow one-step orthogonal moves in addition to diagonal
		if (piece.isPromoted()) {
			// Check for one-square orthogonal movement
			if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
				if (finalizeMove) {
					return finalizeMove(piece, targetPosition);
				}
				else {
					return true;
				}
			}
		}

		// Check if it's a valid diagonal move for an unpromoted Bishop
		if (rowDiff == colDiff) {
			// Verify that the path is clear for diagonal movement
			if (!isPathBlocked(piece, targetPosition)) {
				if (finalizeMove) {
					return finalizeMove(piece, targetPosition);
				}
				else {
					return true;
				}
			}
			return false;
		}

		// If none of the move conditions are met, the move is invalid
		return false;
	}

	/**
	 * Moves the Gold General according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveGoldGeneral(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.GoldGeneral || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.GoldGeneral || !piece.isOnBoard()) {
				return false;
			}
		}
		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();

		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

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

		if (isOutOfBounds(targetPosition)) {
			return false;
		}

		// Check if the move matches any of the allowed moves
		for (int[] move : validMoves) {
			if (rowDiff == move[0] && colDiff == move[1]) {
				if (finalizeMove) {
					return finalizeMove(piece, targetPosition);
				}
				else {
					return true;
				}
			}
		}

		// Return false if the move is not valid for a Gold General
		return false;
	}

	/**
	 * Moves the Silver General according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveSilverGeneral(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.SilverGeneral || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.SilverGeneral || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();
		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetPosition, finalizeMove);
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
					if (finalizeMove) {
						return finalizeMove(piece, targetPosition);
					}
					else {
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * Moves the Knight according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveKnight(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.Knight || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.Knight || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();

		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetPosition, finalizeMove);
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
					if (isOutOfBounds(targetPosition)) {
						return false;
					}
					// Knights can jump over pieces, so no need to check for path obstructions
					// Check if target square has own piece
					ShogiPiece targetPiece = getPiece(targetPosition);
					if (targetPiece != null && targetPiece.isOnBoard() && targetPiece.getOwner() == piece.getOwner()) {
						return false;
					}
					if (finalizeMove) {
						return finalizeMove(piece, targetPosition);
					}
					else {
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * Moves the Lance according to its movement rules.
	 *
	 * @param action The move action containing the piece, target row, and target column.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean moveLance(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.Lance || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.Lance || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();

		int rowDiff = targetRow - currentRow;
		int colDiff = targetCol - currentCol;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		if (piece.isPromoted()) {
			// If promoted, move like a Gold General
			return moveAsGoldGeneral(piece, targetPosition, finalizeMove);
		}
		else {
			// Unpromoted Lance: Only moves forward in the same column
			if (colDiff == 0) {
				if ((piece.getOwner() == 0 && rowDiff < 0) || (piece.getOwner() == 1 && rowDiff > 0)) {
					// Ensure the path is clear
					if (!isPathBlocked(piece, targetPosition)) {
						if (finalizeMove) {
							return finalizeMove(piece, targetPosition);
						}
						else {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Moves the Pawn according to its movement rules.
	 *
	 * @param action The move action.
	 * @param finalizeMove Whether the move should be finalized
	 * @return True if the move is successful, false otherwise.
	 */
	public boolean movePawn(ShogiMoveAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());
		/**
		 * External Citation
		 * Date: 15 November 2024
		 * Problem: A copy of the game state is being changed, but not the original game state
		 * Resource: Professor Andrew Nuxoll
		 * Solution: Added 'ShogiPiece piece = getPiece(action.GetPiece().getPosition());
		 */
		if (piece == null) return false;
		// check if the piece we are trying to move is owned by the currentPlayer if finalizeMove is true
		if (finalizeMove) {
			if (piece.getType() != ShogiPiece.PieceType.Pawn || piece.getOwner() != currentPlayer || !piece.isOnBoard()) {
				return false;
			}
		}
		else {
			if (piece.getType() != ShogiPiece.PieceType.Pawn || !piece.isOnBoard()) {
				return false;
			}
		}

		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int targetRow = action.getTargetPosition().getRow();
		int targetCol = action.getTargetPosition().getCol();
		ShogiSquare targetPosition = action.getTargetPosition();

		int rowDirection = (piece.getOwner() == 0) ? -1 : 1;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetRow == currentRow && targetCol == currentCol || isOutOfBounds(targetPosition)) {
			return false;
		}
		if (getPiece(targetPosition) != null) {
			if (getPiece(targetPosition).getOwner() == piece.getOwner()){
				return false;
			}
		}

		if (!piece.isPromoted()) {
			if (targetRow != currentRow + rowDirection || targetCol != currentCol) {
				return false;
			}
		}
		else {
			// Promoted Pawn moves like a Gold General
			return moveAsGoldGeneral(piece, targetPosition, finalizeMove);
		}

		// Check for capturing own piece
		ShogiPiece targetPiece = getPiece(targetPosition);
		/**
		 * External Citation
		 * Date: 15 November 2024
		 * Problem: A copy of the game state is being changed, but not the original game state
		 * Resource: Professor Andrew Nuxoll
		 * Solution: Added 'ShogiPiece targetPiece = getPiece(targetPosition);'
		 */
		if (targetPiece != null && targetPiece.getOwner() == piece.getOwner()) {
			return false;
		}

		if (finalizeMove) {
			return finalizeMove(piece, targetPosition);
		}
		else {
			return true;
		}
	}

	/**
	 * Checks if the move is valid for a Gold General.
	 *
	 * @param piece     The piece attempting to move.
	 * @param targetPosition The target position.
	 * @return True if the move is valid, false otherwise.
	private boolean isValidGoldGeneralMove(ShogiPiece piece, ShogiSquare targetPosition) {
		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();

		int rowDiff = targetPosition.getRow() - currentRow;
		int colDiff = targetPosition.getCol() - currentCol;

		int[][] validMoves;

		if (piece.getOwner() == 0) {
			// Player 0 (moving upwards)
			validMoves = new int[][]{
					{-1, 0}, {-1, -1}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}};
		} else {
			// Player 1 (moving downwards)
			validMoves = new int[][]{
					{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}};
		}

		for (int[] move : validMoves) {
			if (rowDiff == move[0] && colDiff == move[1]) {return true;}
		}
		return false;
	}*/

	/**
	 * Handles a move or drop action by delegating to the appropriate method.
	 *
	 * @param action The action to handle (either a move or a drop).
	 * @return True if the action is successful, false otherwise.
	 */
	public boolean moveAction(GameAction action) {


		if (action instanceof ShogiMoveAction) {
			// Handle move actions
			ShogiMoveAction moveAction = (ShogiMoveAction) action;
			ShogiPiece piece = getPiece(moveAction.getPiece().getPosition());


			// Ensure the piece belongs to the current player
			if (piece.getOwner() != currentPlayer) {
				return false;
			}

			if (getPiece(((ShogiMoveAction) action).getTargetPosition()) != null &&
					getPiece(((ShogiMoveAction) action).getTargetPosition()).getType() == ShogiPiece.PieceType.King) {
				return false;
			}
			// Delegate to the specific move method based on piece type
			switch (piece.getType()) {
				case King:
					if (moveKing(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case Rook:
					if (moveRook(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case Bishop:
					if (moveBishop(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case GoldGeneral:
					if (moveGoldGeneral(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case SilverGeneral:
					if (moveSilverGeneral(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case Knight:
					if (moveKnight(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case Lance:
					if (moveLance(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				case Pawn:
					if (movePawn(moveAction, true)) {
						for (ShogiPiece updatePiece : pieces) {
							updatePossibleMoves(updatePiece);
						}
						return true;
					}
					return false;
				default:
					return false;
			}
		} else if (action instanceof ShogiDropAction) {
			// Handle drop actions
			ShogiDropAction dropAction = (ShogiDropAction) action;

			// Delegate to the dropPiece method
			return dropPiece(dropAction, true);
		}


		// If action is neither a move nor a drop, return false
		return false;
	}

	public boolean checkIfMoveProtectsKing(GameAction action){
		if (action instanceof ShogiMoveAction) {
			ShogiState copy = new ShogiState(this);
			copy.moveAction(action);
			if (!copy.isKingInCheck(((ShogiMoveAction) action).getPiece().getOwner())) {
				return true;
			}
		}
		if (action instanceof ShogiDropAction) {
			ShogiState copy = new ShogiState(this);
			copy.moveAction(action);
			if (!copy.isKingInCheck(((ShogiDropAction) action).getPiece().getOwner())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to handle movement as a Gold General.
	 * This method is private because it is a helper method used internally within this class
	 * 	to support the movement rules of pieces like a Gold General (e.g., promoted Knight, Lance,
	 * 	or Silver General).
	 * Also, it is only meant to be called within other movement methods - like moveKnight() or moveSilverGeneral() -
	 * 	that determine whether or not a piece should move as a Gold General based on its type and promotion status.
	 *
	 * @param piece The Silver General piece.
	 * @param targetPosition The position for the move.
	 * @param finalizeMove Whether to finalize the move
	 * @return True if the move is successful, false otherwise.
	 */

	private boolean moveAsGoldGeneral(ShogiPiece piece, ShogiSquare targetPosition, boolean finalizeMove) {
		int currentRow = piece.getPosition().getRow();
		int currentCol = piece.getPosition().getCol();
		int rowDiff = targetPosition.getRow() - currentRow;
		int colDiff = targetPosition.getCol() - currentCol;

		// if the piece is trying to move to where is the piece currently is, or not within bounds, or one of the current players pieces, return false
		if (targetPosition.getRow() == currentRow && targetPosition.getCol() == currentCol || isOutOfBounds(targetPosition)) {
			if (getPiece(targetPosition) != null) {
				if (getPiece(targetPosition).getOwner() == getCurrentPlayer()){
					return false;
				}
			}
		}

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
				if (finalizeMove) {
					return finalizeMove(piece, targetPosition);
				}
				else {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Handles a drop action by placing a captured piece onto the board.
	 *
	 * @param action The drop action to handle.
	 * @return True if the drop is successful, false otherwise.
	 */
	public boolean dropPiece(ShogiDropAction action, boolean finalizeMove) {
		ShogiPiece piece = getPiece(action.getPiece().getPosition());

		// Verify that the current player owns the piece to drop
		//ArrayList<ShogiPiece> capturedPieces = currentPlayer == 0 ? capturedPiecesPlayer0 : capturedPiecesPlayer1;
		//if (!capturedPieces.contains(piece)) {
		//	return false;
		//}

		if (action.getPieceToDrop().isOnBoard() || action.getPieceToDrop().getOwner() != currentPlayer) {
			return false;
		}

		ShogiSquare targetPosition = new ShogiSquare(action.getTargetPosition());

		// Check if the target position is within bounds and empty
		if (isOutOfBounds(targetPosition) || getPiece(targetPosition) != null) {
			return false;
		}

		// Additional Shogi drop rules
		if (!isValidDrop(piece, targetPosition)) {
			return false;
		}


		// Place the piece on the board
		if (finalizeMove) {
			piece.setOnBoard(true);
			piece.setPosition(targetPosition);

			// Switch turn
			switchTurn();
			return true;
		}
		else{
			return true;
		}



	}

	/**
	 * Checks if the drop is valid according to Shogi rules.
	 *
	 * @param piece The piece to drop.
	 * @param targetPosition The target position.
	 * @return True if the drop is valid, false otherwise.
	 */
	private boolean isValidDrop(ShogiPiece piece, ShogiSquare targetPosition) {
		// Pawns cannot be dropped on the last row for the player
		if (piece.getType() == ShogiPiece.PieceType.Pawn) {
			if ((currentPlayer == 0 && targetPosition.getRow() == 0) || (currentPlayer == 1 && targetPosition.getRow() == 8)) {
				return false;
			}
			// Additional rule: No dropping pawn in a column that already has an unpromoted pawn (Nifu)
			if (isPawnInColumn(currentPlayer, targetPosition.getCol())) {
				return false;
			}

			// Additional rule: Pawn cannot be dropped to give immediate checkmate (Uchifuzume)
			// This requires checking for immediate checkmate after the drop
			// Implementing this requires more complex game state analysis
		}

		// Knights cannot be dropped on the last two rows
		if (piece.getType() == ShogiPiece.PieceType.Knight) {
			if ((currentPlayer == 0 && targetPosition.getRow() <= 1) || (currentPlayer == 1 && targetPosition.getRow() >= 7)) {
				return false;
			}
		}

		// Lances cannot be dropped on the last row
		if (piece.getType() == ShogiPiece.PieceType.Lance) {
			if ((currentPlayer == 0 && targetPosition.getRow() == 0) || (currentPlayer == 1 && targetPosition.getRow() == 8)) {
				return false;
			}
		}

		// Cannot drop a piece on a square that would result in no legal moves
		// Implement additional rules as needed

		return true;
	}


	/**
	 * Checks if the player already has an unpromoted pawn in the specified column.
	 *
	 * @param player The player number (0 or 1).
	 * @param col The column to check.
	 * @return True if there is an unpromoted pawn, false otherwise.
	 */
	private boolean isPawnInColumn(int player, int col) {
		for (ShogiPiece piece : pieces) {
			if (piece.getType() == ShogiPiece.PieceType.Pawn &&
					piece.getOwner() == player &&
					piece.getPosition().getCol() == col &&
					piece.isOnBoard() &&
					!piece.isPromoted()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Updates the possibleMoves ArrayList for the piece
	 *
	 * @param piece The piece to update the possibleMoves ArrayList
	 */
	public void updatePossibleMoves(ShogiPiece piece) {
		ArrayList<ShogiSquare> possibleMoves = new ArrayList<>();
		if (!piece.isOnBoard()) {
			piece.setPossibleMoves(possibleMoves);
            return;
        }
		switch (piece.getType()) {
			case King:
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
						ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

						if (moveKing(checkMove, false)) {
							possibleMoves.add(targetPosition);
						}
					}
				}
				break;
			case Rook:
				if (piece.isPromoted()) {
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveRook(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				for (int x = -8; x <= 8; x++) {
					ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol());
					ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

					if (moveRook(checkMove, false)) {
						possibleMoves.add(targetPosition);
					}

					if (x == 0) {
						for (int y = -8; y <= 8; y++) {
							targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveRook(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				break;
			case Bishop:
				if (piece.isPromoted()) {
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveBishop(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				for (int x = -8; x <= 8; x++) {
					int y = x;
					ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
					ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

					if (moveBishop(checkMove, false)) {
						possibleMoves.add(targetPosition);
					}
				}
				for (int x = -8; x <= 8; x++) {
					int y = (x * -1);
					ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
					ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

					if (moveBishop(checkMove, false)) {
						possibleMoves.add(targetPosition);
					}
				}
				break;
			case GoldGeneral:
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
						ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

						if (moveGoldGeneral(checkMove, false)) {
							possibleMoves.add(targetPosition);
						}
					}
				}
				break;
			case SilverGeneral:
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
						ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

						if (moveSilverGeneral(checkMove, false)) {
							possibleMoves.add(targetPosition);
						}
					}
				}
				break;
			case Knight:
				if (piece.isPromoted()) {
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveKnight(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				else {
					for (int x = -2; x <= 2; x += 4) {
						for (int y = -1; y <= 1; y += 2) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveKnight(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				break;
			case Lance:
				if (piece.isPromoted()) {
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (moveLance(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				else {
					for (int x = -8; x <= 8; x++) {
						ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol());
						ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

						if (moveLance(checkMove, false)) {
							possibleMoves.add(targetPosition);
						}
					}
				}
				break;
			case Pawn:
				if (piece.isPromoted()) {
					for (int x = -1; x <= 1; x++) {
						for (int y = -1; y <= 1; y++) {
							ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol() + y);
							ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

							if (movePawn(checkMove, false)) {
								possibleMoves.add(targetPosition);
							}
						}
					}
				}
				else {
					for (int x = -1; x <= 1; x++) {
						ShogiSquare targetPosition = new ShogiSquare(piece.getPosition().getRow() + x, piece.getPosition().getCol());
						ShogiMoveAction checkMove = new ShogiMoveAction(null, piece, targetPosition);

						if (movePawn(checkMove, false)) {
							possibleMoves.add(targetPosition);
						}
					}
				}
				break;
			default:

				break;
		}
		piece.setPossibleMoves(possibleMoves);
	}

	public boolean isKingInCheck(int player) {
		ShogiPiece king;
		if (player == 0) {
			king = pieces.get(4);
		}
		else {
			king = pieces.get(24);
		}

		if (king == null) {
			throw new IllegalStateException("King not found for player: " + player);
		}

		ShogiSquare kingPosition = king.getPosition();


		// Check if any opponent's piece can attack the king
		for (ShogiPiece piece : pieces) {
			if (piece.getOwner() != player && piece.isOnBoard()) {
				ArrayList<ShogiSquare> possibleMoves = piece.getPossibleMoves();

				for (ShogiSquare move : possibleMoves) {
					if (move.equals(kingPosition)) {
						return true; // The king is in check
					}
				}
			}
		}

		return false; // The king is not in check
	}


	public boolean isCheckmate(int playerNum, GamePlayer player) {
		if (!isKingInCheck(playerNum)) {
			return false; // Not in check, so not checkmate
		}

		// set king's possible moves based on playerNum to check for
		ArrayList <ShogiSquare> kingsPossibleMoves = new ArrayList<>();
		if (playerNum == 0) {
			kingsPossibleMoves = pieces.get(4).getPossibleMoves();
		}
		else {
			kingsPossibleMoves = pieces.get(24).getPossibleMoves();
		}
		boolean[] kingLegalMovesChecked = new boolean[kingsPossibleMoves.size()];
		// go through all of the kings possible moves
		for (int i = 0; i < kingsPossibleMoves.size(); i++) {
			ShogiSquare kingMove = kingsPossibleMoves.get(i);
			// go through all of the pieces
			for (ShogiPiece piece : pieces) {
				if (piece.getOwner() != playerNum && piece.isOnBoard()) {
					// store opponent piece's possible moves
					ArrayList<ShogiSquare> opponentsPossibleMoves = piece.getPossibleMoves();

					// check if opponent piece's can move to the same square as the king can
					for (ShogiSquare opponentMove : opponentsPossibleMoves) {
						if (opponentMove.equals(kingMove)) {
							kingLegalMovesChecked[i] = true; // The move at index i is checked by an opponent piece
							if (i == kingsPossibleMoves.size()) {
								break;
							}
						}
					}
				}
			}
		}
		boolean checkmate = true;
		// if any of the possible moves are not checked, its not checkmate
        for (boolean b : kingLegalMovesChecked) {
			if (!b) {
				checkmate = false;
				break;
			}
		}
		if (checkmate) {
			for (ShogiPiece piece : pieces) {
				if (piece.getOwner() == playerNum) {
					if (piece.isOnBoard()) {
						// store our piece's possible moves
						ArrayList<ShogiSquare> possibleMoves = piece.getPossibleMoves();

						// check if the piece can move in a way that makes the king not be in check
						for (int i = 0; i < possibleMoves.size(); i++) {
							ShogiSquare move = possibleMoves.get(i);
							ShogiMoveAction moveAction = new ShogiMoveAction(player, piece, move);
							if (checkIfMoveProtectsKing(moveAction)) {
								return false;
							}
							else {
								ArrayList<ShogiSquare> newPossibleMoves = piece.getPossibleMoves();
								newPossibleMoves.remove(move);
								piece.setPossibleMoves(newPossibleMoves);
							}
						}
					}
					//else {
					//	for (int x = 0; x <= 8; x++) {
					//		for (int y = 0; y <= 8; y++) {
					//			ShogiSquare move = new ShogiSquare(x,y);
					//			ShogiMoveAction moveAction = new ShogiMoveAction(player, piece, move);
					//			if (isValidDrop(piece, move)) {
					//				if (checkIfMoveProtectsKing(moveAction)) {
					//					return false;
					//				}
					//			}
					//		}
					//	}
					//}
				}
			}
		}

		return checkmate;
	}


	@Override
	public String toString() {
		/**
		 * External Citation
		 * Date: 8 November 2024
		 * Problem: Needed an efficient way to concatenate multiple strings in the toString() method.
		 * Resource: https://developer.android.com/reference/java/lang/StringBuilder
		 * Solution: Used StringBuilder to build the game state as a single string, which improves performance over string concatenation in loops.
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("Current Player: ").append(currentPlayer == 0 ? "Player 1" : "Player 2").append("\n");
		sb.append("Game Phase: ").append(gamePhase).append("\n");

		// Display captured pieces for each player
		sb.append("Captured Pieces Player 1: ");
		for (ShogiPiece piece : pieces) {
			if (!piece.isOnBoard() && piece.getOwner() == 1) {
				sb.append(getPieceSymbol(piece)).append(" ");
			}
		}
		sb.append("\n");

		sb.append("Captured Pieces Player 2: ");
		for (ShogiPiece piece : pieces) {
			if (!piece.isOnBoard() && piece.getOwner() == 0) {
				sb.append(getPieceSymbol(piece)).append(" ");
			}
		}
		sb.append("\n");

		sb.append("Board:\n");

		// Initialize a blank 9x9 board representation
		String[][] board = new String[9][9];
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				board[row][col] = "[ ]"; // empty cell
			}
		}

		// Place each piece on the board based on its row and column
		/*for (ShogiPiece piece : pieces) {
			if (piece.isOnBoard()) {
				int row = piece.getPosition().getRow();
				int col = piece.getPosition().getCol();
				String symbol = getPieceSymbol(piece);
				board[row][col] = "[" + symbol + "]";
			}
		}

		// Append board rows to the StringBuilder, top to bottom (Shogi orientation)
		for (int row = 8; row >= 0; row--) {
			for (int col = 0; col < 9; col++) {
				sb.append(board[row][col]).append(" ");
			}
			sb.append("\n");
		}*/
		return sb.toString();
	}

	/**
	 * Helper method to get the symbol representation of a piece.
	 * External Citation:
	 * 	Date: 8 November 2024
	 * 	Problem: Needed a standardized way to represent each piece type as a unique symbol in the `toString()` method.
	 * 	Resource: ChatGPT 4o
	 * 	Solution: Recommended creating a `getPieceSymbol()` method to map each piece type to a distinct symbol, which improved readability and modularity in `toString()`.
	 *
	 * @param piece The ShogiPiece object.
	 * @return A string representing the piece.
	 */
	private String getPieceSymbol(ShogiPiece piece) {
		String symbol;
		switch (piece.getType()) {
			case King:
				symbol = "K";
				break;
			case Rook:
				symbol = "R";
				break;
			case Bishop:
				symbol = "B";
				break;
			case GoldGeneral:
				symbol = "G";
				break;
			case SilverGeneral:
				symbol = "S";
				break;
			case Knight:
				symbol = "N";
				break;
			case Lance:
				symbol = "L";
				break;
			case Pawn:
				symbol = "P";
				break;
			default:
				symbol = " ";
				break;
		}
		if (piece.isPromoted()) {
			symbol = "+" + symbol;
		}
		symbol += piece.getOwner(); // e.g., "K0" for Player 1's King
		return symbol;
	}


}
