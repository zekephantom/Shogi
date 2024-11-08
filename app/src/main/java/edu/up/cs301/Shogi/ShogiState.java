package edu.up.cs301.Shogi;

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

		/**
		 * External Citation
		 * 	Date: 8 November 2024
		 * 	Problem: Needed a way to perform deep copies of captured pieces lists to avoid references to the original objects.
		 * 	Resource: https://www.geeksforgeeks.org/deep-shallow-lazy-copy-java-examples/, ChatGPT
		 * 	Solution:
		 * 		Implemented a loop to create new ShogiPiece objects for each item in the original lists,
		 *		ensuring each piece is independently copied without linking to the original.
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
	 * Capture piece logic
	 *
	 * @param piece     The piece to move.
	 * @param targetRow The target row.
	 * @param targetCol The target column.
	 * @return True if the move is successful, false otherwise.
	 */
	private boolean finalizeMove(ShogiPiece piece, int targetRow, int targetCol) {
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
	}

	/**
	 * Handles a move or drop action by delegating to the appropriate method.
	 *
	 * @param action The action to handle (either a move or a drop).
	 * @return True if the action is successful, false otherwise.
	 */
	public boolean moveAction(GameAction action) {
		// Verify that the action comes from the current player
		if (!isActionFromCurrentPlayer(action)) {
			return false;
		}

		if (action instanceof ShogiMoveAction) {
			// Handle move actions
			ShogiMoveAction moveAction = (ShogiMoveAction) action;
			ShogiPiece piece = moveAction.getPiece();

			// Ensure the piece belongs to the current player
			if (piece.getOwner() != currentPlayer) {
				return false;
			}

			// Delegate to the specific move method based on piece type
			switch (piece.getType()) {
				case King:
					return moveKing(moveAction);
				case Rook:
					return moveRook(moveAction);
				case Bishop:
					return moveBishop(moveAction);
				case GoldGeneral:
					return moveGoldGeneral(moveAction);
				case SilverGeneral:
					return moveSilverGeneral(moveAction);
				case Knight:
					return moveKnight(moveAction);
				case Lance:
					return moveLance(moveAction);
				case Pawn:
					return movePawn(moveAction);
				default:
					return false;
			}
		} else if (action instanceof ShogiDropAction) {
			// Handle drop actions
			ShogiDropAction dropAction = (ShogiDropAction) action;

			// Delegate to the dropPiece method
			return dropPiece(dropAction);
		}

		// If action is neither a move nor a drop, return false
		return false;
	}

	/**
	 * Checks if the given action was initiated by the current player.
	 *
	 * @param action The action to check, which contains information about the player who initiated it.
	 * @return true if the action was initiated by the player whose turn it currently is, false otherwise.
	 *
	 * External Citation
	 * Date: 8 November 2024
	 * Problem: Needed a way to verify that an action aligns with the current player's turn to ensure valid moves.
	 * Resource: ChatGPT 4o
	 * Solution: Structured the method to return true if the action matches the current player; added a TODO to refine the actual check.
	 */
	public boolean isActionFromCurrentPlayer(GameAction action) {
		return true; // TODO: finish implementing this block of code, should not return true
	}


	// TODO: Decide whether or not to use this block of code; commented for now
//	/**
//	 * Retrieves the player number associated with a GamePlayer instance.
//	 *
//	 * @param player The GamePlayer instance.
//	 * @return The player number (0 or 1).
//	 */
//	private int getPlayerNum(GamePlayer player) {
//		// Implement logic to determine player number from GamePlayer instance
//		// This could be based on a mapping or an attribute within GamePlayer
//		// For this example, we'll assume there's a method getPlayerNum()
//		return player.getPlayerNum();
//	}


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
	 * This method is private because it is a helper method used internally within this class
	 * 	to support the movement rules of pieces like a Gold General (e.g., promoted Knight, Lance,
	 * 	or Silver General).
	 * Also, it is only meant to be called within other movement methods - like moveKnight() or moveSilverGeneral() -
	 * 	that determine whether or not a piece should move as a Gold General based on its type and promotion status.
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


	/**
	 * Handles a drop action by placing a captured piece onto the board.
	 *
	 * @param action The drop action to handle.
	 * @return True if the drop is successful, false otherwise.
	 */
	public boolean dropPiece(ShogiDropAction action) {
		ShogiPiece piece = action.getPieceToDrop();

		// Verify that the current player owns the piece to drop
		ArrayList<ShogiPiece> capturedPieces = currentPlayer == 0 ? capturedPiecesPlayer0 : capturedPiecesPlayer1;
		if (!capturedPieces.contains(piece)) {
			return false;
		}

		int targetRow = action.getTargetRow();
		int targetCol = action.getTargetCol();

		// Check if the target position is within bounds and empty
		if (!isWithinBounds(targetRow, targetCol) || getPiece(targetRow, targetCol) != null) {
			return false;
		}

		// Additional Shogi drop rules
		if (!isValidDrop(piece, targetRow, targetCol)) {
			return false;
		}

		// Place the piece on the board
		piece.setOnBoard(true);
		piece.setPosition(targetRow, targetCol);
		pieces.add(piece); // Add back to active pieces

		// Remove the piece from the captured list
		capturedPieces.remove(piece);

		// Switch turn
		switchTurn();

		return true;
	}

	/**
	 * Checks if the drop is valid according to Shogi rules.
	 *
	 * @param piece The piece to drop.
	 * @param targetRow The target row.
	 * @param targetCol The target column.
	 * @return True if the drop is valid, false otherwise.
	 */
	private boolean isValidDrop(ShogiPiece piece, int targetRow, int targetCol) {
		// Pawns cannot be dropped on the last row for the player
		if (piece.getType() == ShogiPiece.PieceType.Pawn) {
			if ((currentPlayer == 0 && targetRow == 0) || (currentPlayer == 1 && targetRow == 8)) {
				return false;
			}
			// Additional rule: No dropping pawn in a column that already has an unpromoted pawn (Nifu)
			if (isPawnInColumn(currentPlayer, targetCol)) {
				return false;
			}

			// Additional rule: Pawn cannot be dropped to give immediate checkmate (Uchifuzume)
			// This requires checking for immediate checkmate after the drop
			// Implementing this requires more complex game state analysis
		}

		// Knights cannot be dropped on the last two rows
		if (piece.getType() == ShogiPiece.PieceType.Knight) {
			if ((currentPlayer == 0 && targetRow <= 1) || (currentPlayer == 1 && targetRow >= 7)) {
				return false;
			}
		}

		// Lances cannot be dropped on the last row
		if (piece.getType() == ShogiPiece.PieceType.Lance) {
			if ((currentPlayer == 0 && targetRow == 0) || (currentPlayer == 1 && targetRow == 8)) {
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
					piece.getCol() == col &&
					piece.isOnBoard() &&
					!piece.isPromoted()) {
				return true;
			}
		}
		return false;
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
		for (ShogiPiece piece : capturedPiecesPlayer0) {
			sb.append(getPieceSymbol(piece)).append(" ");
		}
		sb.append("\n");

		sb.append("Captured Pieces Player 2: ");
		for (ShogiPiece piece : capturedPiecesPlayer1) {
			sb.append(getPieceSymbol(piece)).append(" ");
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
		for (ShogiPiece piece : pieces) {
			if (piece.isOnBoard()) {
				int row = piece.getRow();
				int col = piece.getCol();
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
		}
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
