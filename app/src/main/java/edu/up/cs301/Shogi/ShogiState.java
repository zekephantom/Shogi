package edu.up.cs301.Shogi;

import android.util.Log;

import java.util.ArrayList;
import edu.up.cs301.GameFramework.infoMessage.GameState;

/**
 * This contains the state for Shogi.
 *
 * @author Steven R. Vegdahl
 * @author Ezekiel Rafanan
 * @author Jona Bodirsky
 * @author Makengo Lokombo
 * @author Arnaj Sandhu
 * @author James Pham
 * @version October 2024
 */
public class ShogiState extends GameState {

	//Instance Variables for Shogi State
	// Information about all the pieces
	private ArrayList<ShogiPiece> pieces;

	// State of the board
	// private ShogiPiece[][] gameBoard; // Represents the board with pieces

	// Whose turn is it?
	private int currentPlayer; // 0 for Player 1, 1 for Player 2

	// Current score of each player
	private int player1Score;
	private int player2Score;

	// Current state of the game phase
	private String gamePhase; // Setup, Placement, Main Play, etc.

	// Default Constructor for ShogiState
	public ShogiState() {
		pieces = new ArrayList<>();
		this.initPieces();
		// gameBoard = new ShogiPiece[9][9]; // Shogi board is 9x9
		currentPlayer = 0; // Start with Player 1
		player1Score = 0;
		player2Score = 0;
		gamePhase = "Setup";
	}

	/**
	 * This deep copy constructor ensures that the new ShogiState object is different from the original,
	 * allowing changes to be made without affecting the original state.
	 * @param orig original ShogiState object to be copied
	 */
	public ShogiState(ShogiState orig) {
		// Deep copy player pieces
		this.pieces = new ArrayList<>();
		for (ShogiPiece piece : orig.pieces) {
			this.pieces.add(new ShogiPiece(piece));
		}

		// Deep copy game board
		//this.gameBoard = new ShogiPiece[9][9];
		//for (int i = 0; i < 9; i++) {
		//	for (int j = 0; j < 9; j++) {
		//		if (orig.gameBoard[i][j] != null) {
		//			this.gameBoard[i][j] = new ShogiPiece(orig.gameBoard[i][j]);
		//		} else {
		//			this.gameBoard[i][j] = null;
		//		}
		//	}
		//}

		// Copy current player
		this.currentPlayer = orig.currentPlayer;

		// Copy scores
		this.player1Score = orig.player1Score;
		this.player2Score = orig.player2Score;

		// Copy game phase
		this.gamePhase = orig.gamePhase;
	}

	// Getters and Setters for various instance variables
	/**
	 * External Citation:
	 * Date: 28 October 2024
	 * Problem: Needed to implement standard getters and setters
	 * Resource: Counter Game Source Code
	 * Solution: Check the code to see how the getters and setters were implemented
	 * 	and made new methods that are relevant to Shogi.
	 */
	public ArrayList<ShogiPiece> getPieces() {
		return pieces;
	}
	//public ShogiPiece[][] getGameBoard() {
	//	return gameBoard;
	//}
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public int getPlayer1Score() {
		return player1Score;
	}
	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}
	public int getPlayer2Score() {
		return player2Score;
	}
	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}
	public String getGamePhase() {
		return gamePhase;
    }
	public void setGamePhase(String gamePhase) {
		this.gamePhase = gamePhase;
	}

	/**
	 * Handles the drop action for a piece in Shogi.
	 * This action is used when a player attempts to drop a captured piece onto the board.
	 * Method checks is such drop is valid based on game rules.
	 * @param action Details about the piece and the target position for the drop
	 * @return true if drop action is valid and completed, otherwise false
	 */
	public boolean dropAction(ShogiMoveAction action) {
		ShogiPiece selectedPiece = action.getPiece();
		if (selectedPiece.isOnBoard()){
			return false;
		}
		int moveRow = action.getMoveRow();
		int moveCol = action.getMoveCol();

		if (checkForPiece(moveRow, moveCol)) return false;

		switch (selectedPiece.getType()) {
			case "Knight":
				if (moveRow == 0 || moveRow == 1) {
					return false;
				}
				break;
			case "Lance":
				if (moveRow == 0) {
					return false;
				}
				break;
			case "Pawn":
				if (moveRow == 0) {
					return false;
				}
				for (int col = 0; col <= 8; col++) {
					if (getPiece(moveRow, col).getType().equals("Pawn") && (getPiece(moveRow, col).getOwner() != currentPlayer)) {
						return false;
					}
				}
				// to:do check if dropped pawn results in checkmate and return false if so
				break;
		}

		selectedPiece.setOnBoard(true);
		selectedPiece.setOwner(currentPlayer);
		selectedPiece.setRow(moveRow);
		selectedPiece.setCol(moveCol);
		return true;

	}

	public boolean moveAction(ShogiMoveAction action) {
		ShogiPiece selectedPiece = action.getPiece();

		int currentRow = selectedPiece.getRow();
		int currentCol = selectedPiece.getCol();

		int moveRow = action.getMoveRow();
		int moveCol = action.getMoveCol();

		int rowDiff = Math.abs(currentRow - moveRow);
		int colDiff = Math.abs(currentCol - moveCol);

		//Log.d("moveAction", "begin moveAcvtion");


		// Check if target is occupied by opponents king
		if (pieces.get(4).getRow() == moveRow && pieces.get(4).getCol() == moveCol) {
			Log.d("moveAction", "targetOccupiedByKing");
			return false;
		}
		// Check if target is occupied by one of our pieces
		for (int i = 0; i <= 20; i++) {
			if (pieces.get(i).getRow() == moveRow && pieces.get(i).getCol() == moveCol && pieces.get(i).isOnBoard()) {
				Log.d("moveAction", "targetOccupiedByOurPiece");
				return false;
			}
		}


		// checking for each piece if the move is allowed
		switch (selectedPiece.getType()) {
			case "King":
				if ((rowDiff == 1 && colDiff == 1) || (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
					selectedPiece.setPosition(moveRow, moveCol); // Update piece position
					return true;
				}
				break;
			case "GoldGeneral":
				if ((rowDiff <= 1 && colDiff <= 1) && /*|| (rowDiff == 0 && colDiff == 1) ||*/
						!(rowDiff == 1 && colDiff == 1 && moveRow < currentRow)) {
					selectedPiece.setPosition(moveRow, moveCol);
					return true;
				}

				break;
			case "SilverGeneral":
				if (selectedPiece.isPromoted()) {
					// Moves like Gold General when promoted
					if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) ||
							(rowDiff == 1 && colDiff == 1 && moveRow < currentRow)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Silver General movement
					if ((rowDiff == 1 && colDiff == 1) || (rowDiff == 1 && colDiff == 0 && moveRow < currentRow)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
			case "Knight":
				if (selectedPiece.isPromoted()) {
					// Moves like Gold General when promoted
					if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) ||
							(rowDiff == 1 && colDiff == 1 && moveRow < currentRow)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Knight movement (2 forward, 1 sideways)
					if (rowDiff == 2 && colDiff == 1 && moveRow < currentRow) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
			case "Lance":
				if (selectedPiece.isPromoted()) {
					// Moves like Gold General when promoted
					if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) ||
							(rowDiff == 1 && colDiff == 1 && moveRow < currentRow)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Lance movement (forward any number of squares)
					if (colDiff == 0 && moveRow < currentRow && !isPathBlocked(selectedPiece, moveRow, moveCol)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
			case "Bishop":
				if (selectedPiece.isPromoted()) {
					// Moves like Bishop + King when promoted
					if ((rowDiff == colDiff && !isPathBlocked(selectedPiece, moveRow, moveCol)) ||
							(rowDiff <= 1 && colDiff <= 1)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Bishop movement (diagonally any distance)
					if (rowDiff == colDiff && !isPathBlocked(selectedPiece, moveRow, moveCol)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
			case "Rook":
				if (selectedPiece.isPromoted()) {
					// Moves like Rook + King when promoted
					if ((rowDiff == 0 || colDiff == 0) && !isPathBlocked(selectedPiece, moveRow, moveCol)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					} else if (rowDiff <= 1 && colDiff <= 1) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Rook movement (horizontally or vertically any distance)
					if ((rowDiff == 0 || colDiff == 0) && !isPathBlocked(selectedPiece, moveRow, moveCol)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
			case "Pawn":
				if (selectedPiece.isPromoted()) {
					// Moves like Gold General when promoted
					if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) ||
							(rowDiff == 1 && colDiff == 1 && moveRow < currentRow)) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				} else {
					// Normal Pawn movement (1 square forward)
					if (rowDiff == 1 && colDiff == 0 && moveRow < currentRow) {
						selectedPiece.setPosition(moveRow, moveCol);
						return true;
					}
				}
				break;
		}

		// Check for mandatory promotion
		if (isEligibleForPromotion(selectedPiece, moveRow)) {
			if (selectedPiece.getType().equals("Pawn") || selectedPiece.getType().equals("Lance") ||
					selectedPiece.getType().equals("Knight")) {
				// Force promotion if the piece reaches the last row and has no further legal moves
				if ((selectedPiece.getOwner() == 0 && moveRow == 0) ||
						(selectedPiece.getOwner() == 1 && moveRow == 8)) {
					selectedPiece.bePromoted(true);
				}
			}
		}

		return false; // Return false if the move is invalid or blocked
	}

	// Helper method to see if a piece can promote
	/**
	 * Determines if a given Shogi piece is eligible for promotion based on its
	 * current row position and ownership.

	 * @param piece The ShogiPiece being evaluated for promotion eligibility.
	 * @param row The row position to which the piece has moved.
	 * @return true if the piece is in the promotion zone, otherwise false
	 */
	private boolean isEligibleForPromotion(ShogiPiece piece, int row) {
		return (piece.getOwner() == 0 && row <= 2) || (piece.getOwner() == 1 && row >= 6);
	}


	@Override
	public String toString() { //TODO: add 2d array for gameboard by iterating through pieces
		/**
		 *External Citation
		 *	Date: 7 October 2024
		 * 	Problem: I do not know where to start with toString() method.
		 * 	Resource: https://developer.android.com/reference/java/lang/StringBuilder, ChatGPT
		 *  Solution: ChatGPT recommended me to start with StringBuilder, so I read the documentation. Followed GPT's guidelines.
		 */
		String[][] board = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = "[ ]"; // empty cell
			}
		}

		for (ShogiPiece piece : pieces) {
			if (piece.isOnBoard()) {
				int row = piece.getRow();
				int col = piece.getCol();
				String symbol;

				// Map piece types to symbols
				switch (piece.getType()) {
					case "King":
						symbol = "K";
						break;
					case "GoldGeneral":
						symbol = "G";
						break;
					case "SilverGeneral":
						symbol = "S";
						break;
					case "Knight":
						symbol = "N";
						break;
					case "Lance":
						symbol = "L";
						break;
					case "Bishop":
						symbol = "B";
						break;
					case "Rook":
						symbol = "R";
						break;
					case "Pawn":
						symbol = "P";
						break;
					default:
						symbol = " ";
						break;
				}

				// Indicate promotion
				if (piece.isPromoted()) {
					symbol = "+" + symbol;
				}

				// Indicate owner (optional)
				symbol += piece.getOwner(); // e.g., "K0" for Player 1's King

				board[row][col] = "[" + symbol + "]";
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Shogi State:\n");
		sb.append("Current Player: ").append(currentPlayer == 0 ? "Player 1" : "Player 2").append("\n");
		sb.append("Game Phase: ").append(gamePhase).append("\n");
		sb.append("Game Board:\n");

		// Print the board from top to bottom
		for (int i = 8; i >= 0; i--) {
			for (int j = 0; j < 9; j++) {
				sb.append(board[i][j]).append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();

		// commented out as they are not currently used
		//sb.append("Player 1 Score: ").append(player1Score).append("\n");
		//sb.append("Player 2 Score: ").append(player2Score).append("\n");
		//sb.append("Pieces: ").append(pieces).append("\n");
		//sb.append("Game Board:\n");
		//for (int i = 0; i < 9; i++) {
		//	for (int j = 0; j < 9; j++) {
		//		sb.append(gameBoard[i][j] == null ? "[ ]" : "[P]").append(" ");
		//	}
		//	sb.append("\n");
	}

	/**
	 * isPathBlocked() method checks if there is a piece in the path of where the piece needs to move to
	 * especially for Rook, Bishop, Lance
	 *
	 * @param piece The piece that is attempting to move
	 * @param moveRow Target row for piece's move
	 * @param moveCol The target column for the piece's move
	 * @return true if path is blocked, otherwise false
	 */
	private boolean isPathBlocked(ShogiPiece piece, int moveRow, int moveCol) {
		int currentRow = piece.getRow();
		int currentCol = piece.getCol();

		if (piece.getType().equals("Rook")) {
			// Check horizontal and vertical paths
			if (moveRow == currentRow) {
				// Moving horizontally (left or right)
				int step = (moveCol > currentCol) ? 1 : -1;
				for (int col = currentCol + step; col != moveCol; col += step) {
					if (checkForPiece(currentRow, col)) {
						return true; // Path is blocked
					}
				}
			} else if (moveCol == currentCol) {
				// Moving vertically (up or down)
				int step = (moveRow > currentRow) ? 1 : -1;
				for (int row = currentRow + step; row != moveRow; row += step) {
					if (checkForPiece(row, currentCol)) {
						return true; // Path is blocked
					}
				}
			}
		} else if (piece.getType().equals("Bishop")) {
			// Check diagonal paths
			int rowDiff = Math.abs(moveRow - currentRow);
			int colDiff = Math.abs(moveCol - currentCol);

			if (rowDiff == colDiff) {
				// Moving diagonally
				int rowStep = (moveRow > currentRow) ? 1 : -1;
				int colStep = (moveCol > currentCol) ? 1 : -1;

				int row = currentRow + rowStep;
				int col = currentCol + colStep;
				while (row != moveRow && col != moveCol) {
					if (checkForPiece(row, col)) {
						return true; // Path is blocked
					}
					row += rowStep;
					col += colStep;
				}
			}
		} else if (piece.getType().equals("Lance")) {
			// Lance moves forward any number of squares
			if (moveCol == currentCol) {
				int step = (moveRow < currentRow) ? -1 : 1;
				for (int row = currentRow + step; row != moveRow; row += step) {
					if (checkForPiece(row, currentCol)) {
						return true; // Path is blocked
					}
				}
			}
		} else if (piece.getType().equals("GoldGeneral")) {
			return false;
		} else if (piece.getType().equals("SilverGeneral")) {
			return false;
		}
		return false;
	}

	/**
	 * Returns the piece that is within a certain position
	 * @param row row position of the desired piece on the board.
	 * @param col column position of the desired piece on the board.
	 * @return The piece at the specified position, or null if no piece is found.
	 */
    public ShogiPiece getPiece(int row, int col) {
		for (ShogiPiece piece : pieces) {
			// Check if the piece is on the board and if it matches the given row and column
			if (piece.getRow() == row && piece.getCol() == col) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * Simple helper method that checks if currently there is any piece on a specific field
	 * @param row
	 * @param col
	 * @return false (default)
	 */
	private boolean checkForPiece(int row, int col) {
		for (ShogiPiece piece : pieces) {
			// Check if the piece is on the board and if it matches the given row and column
			if (piece.getRow() == row && piece.getCol() == col) {
				return true;
			}
		}
		return false;
	}

	/**
	  Method to initially create and place all the pieces
	  at the correct locations on the board
	 */
	private void initPieces() {
		// Player 1 pieces
		pieces.add(new ShogiPiece("Lance", 1, 8, 0));
		pieces.add(new ShogiPiece("Knight", 1, 8, 1));
		pieces.add(new ShogiPiece("SilverGeneral", 1, 8, 2));
		pieces.add(new ShogiPiece("GoldGeneral", 1, 8, 3));
		pieces.add(new ShogiPiece("King", 1, 8, 4));
		pieces.add(new ShogiPiece("GoldGeneral", 1, 8, 5));
		pieces.add(new ShogiPiece("SilverGeneral", 1, 8, 6));
		pieces.add(new ShogiPiece("Knight", 1, 8, 7));
		pieces.add(new ShogiPiece("Lance", 1, 8, 8));
		pieces.add(new ShogiPiece("Bishop", 1, 7, 1));
		pieces.add(new ShogiPiece("Rook", 1, 7, 7));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece("Pawn", 1, 6, i));
		}
		// Player 2 pieces
		pieces.add(new ShogiPiece("Lance", 2, 0, 0));
		pieces.add(new ShogiPiece("Knight", 2, 0, 1));
		pieces.add(new ShogiPiece("SilverGeneral", 2, 0, 2));
		pieces.add(new ShogiPiece("GoldGeneral", 2, 0, 3));
		pieces.add(new ShogiPiece("King", 2, 0, 4));
		pieces.add(new ShogiPiece("GoldGeneral", 2, 0, 5));
		pieces.add(new ShogiPiece("SilverGeneral", 2, 0, 6));
		pieces.add(new ShogiPiece("Knight", 2, 0, 7));
		pieces.add(new ShogiPiece("Lance", 2, 0, 8));
		pieces.add(new ShogiPiece("Bishop", 2, 1, 1));
		pieces.add(new ShogiPiece("Rook", 2, 1, 7));
		for (int i = 0; i < 9; i++) {
			pieces.add(new ShogiPiece("Pawn", 2, 2, i));
		}
	}
}