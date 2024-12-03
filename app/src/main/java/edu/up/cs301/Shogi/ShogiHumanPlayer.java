package edu.up.cs301.Shogi;


import static androidx.core.app.ActivityCompat.finishAffinity;

import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.utilities.Logger;
import edu.up.cs301.shogi.R;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * A GUI for the human player in the Shogi game. It displays the current game state
 * and allows the player to interact with the game by making moves.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @author Ezekiel Rafanan
 * @author Jona Bodirsky
 * @author James Pham
 * @author Arnaj Sandhu
 * @author Makengo Lokombo
 * @version July 2013 (original), 28 October 2024
 */
public class ShogiHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, GestureDetector.OnGestureListener {

	/* instance variables */
	// The EditText that displays test results or game state information
	private EditText testResultsEditText;

	// the surface view of the board
	public ShogiGUI shogiBoard;

	// the most recent game state, as given to us by the ShogiLocalGame
	private ShogiState state = new ShogiState();
	
	// the android activity that we are running
	private GameMainActivity myActivity;

	// private GestureDetector myDetector = new GestureDetector(this, this);

	private Handler guiHandler = null;

	// the field the user touched on
	private static ShogiSquare gridTouched;
	private static ShogiSquare priorGridTouched;
	private ShogiPiece selectedPiece;
	private Boolean pieceIsSelected = false;
	private ArrayList<ShogiSquare> piecePossibleMoves = new ArrayList<>();


	/**
	 * constructor
	 * @param name
	 * 		the player's name
	 */
	public ShogiHumanPlayer(String name) {
		super(name);
	}

	/**
	 * Returns the GUI's top view object
	 * 
	 * @return
	 * 		the top object in the GUI's view hierarchy
	 */
	@Override
	public View getTopView() {
		/**
		 * External Citation
		 * Date: 21 October 2024
		 * Problem: Unable to figure out what to return for getTopView()
		 * Resource: Dr. Andrew Nuxoll
		 * Solution: He gave us an idea on which id to return
		 */
		return myActivity.findViewById(R.id.topLevel);
	}

	/**
	 * Gets the player number for this player
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * Setter method to print the prior move of the computer player
	 * @param priorG
	 * @param gridT
	 */
	public static void setPriorMove(ShogiSquare priorG, ShogiSquare gridT){
		priorGridTouched = priorG;
		gridTouched = gridT;
	}

	/**
	 * Updates the display based on the current game state.
	 */
	protected void updateDisplay(ShogiState updatedState) {

		if (updatedState != null) {
			shogiBoard.setShogiState(updatedState);
		}

	/* Code used for game state test
		// set the text in the appropriate widget
	if(state == null) return;
	Log.d("toString", state.toString());

	 */
	}

	/**
	 * Callback method when we receive information from the game.
	 *
	 * @param info
	 *      the message received from the game
	 */
	@Override
	public void receiveInfo(GameInfo info) {

		if (shogiBoard == null) return;

		if (!(info instanceof ShogiState))
			return;
		else {

			this.state = (ShogiState)info;
			updateDisplay(state);

		}
	}
	
	/**
	 * callback method--our game has been chosen/rechosen to be the GUI,
	 * called from the GUI thread
	 * 
	 * @param activity
	 * 		the activity under which we are running
	 */
	@Override
	public void setAsGui(GameMainActivity activity) {

		// remember the activity
		this.myActivity = activity;


		// remember the handler for the GUI thread
		this.guiHandler = new Handler();

		//  set the right content view
		activity.setContentView(R.layout.game_interface);

		// uncomment when running game state test
		/*
		this.testResultsEditText = (EditText) activity.findViewById(R.id.tv_test_results);
		Button runTestButton = (Button) activity.findViewById(R.id.button_run_test);
		runTestButton.setOnClickListener(this);
		*/

		shogiBoard = (ShogiGUI)myActivity.findViewById(R.id.shogiBoard);
		shogiBoard.setShogiState(state);

// --------- testing of the GUI functionality ---------------
/*		// captured
		state.getPieces().get(15).setOnBoard(false);
		updateDisplay(state);

		// promoted
		state.getPieces().get(35).setPromoted(true);
		updateDisplay(state);

		// owner
		state.getPieces().get(35).setOwner(1-state.getPieces().get(35).getOwner());
		updateDisplay(state);
*/

		Logger.log("set listener","OnTouch");
		shogiBoard.setOnTouchListener(this);


		// TODO: add switchListeners for english/japanese

		Button quit = myActivity.findViewById(R.id.butQuit);
		quit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				confirmExit();
			}
		});

		/*
		quit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				confirmExit();
			}
		});*/
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
	if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
		return false;
	}
		float x = motionEvent.getX();
		float y = motionEvent.getY();

		// Only checks the state if a new field is touched
		// -> multiple onTouch calls during a single touch
		if (gridTouched != shogiBoard.gridSelection(x,y)) {

			// will return captured pieces being col 9, 10 for player 1, 0
			if (gridTouched != null) priorGridTouched = new ShogiSquare(gridTouched);
			gridTouched = shogiBoard.gridSelection(x, y);

			if (gridTouched != null) {
                Log.d("Touch", "field touched: \n row: " + gridTouched.getRow() + " col: " + gridTouched.getCol());
            }

			// -----------  move a selected piece ----------------
			if (pieceIsSelected) {

				// DROP action
				if(!selectedPiece.isOnBoard()){

					ShogiDropAction dropAction = new ShogiDropAction(this, selectedPiece, gridTouched);
					if (state.dropPiece(dropAction, false)) {
						game.sendAction(dropAction);
						shogiBoard.setPriorMoveSquares(priorGridTouched, gridTouched);

						// reset the selected piece
						selectedPiece = null;
						shogiBoard.setSelected(null);
						pieceIsSelected = false;
						return true;
					}

					// Invalid drop
					// TODO draw red fields if it is a illegal drop
					// shogiBoard.setPriorMoveSquares(priorGridTouched, gridTouched);

					// reset the selected piece
					selectedPiece = null;
					shogiBoard.setSelected(null);
					pieceIsSelected = false;
					// return true;
				}

				// MOVE action
				for (ShogiSquare possibleTargetSquare : piecePossibleMoves) {
					if (gridTouched.getRow() == possibleTargetSquare.getRow() && gridTouched.getCol() == possibleTargetSquare.getCol()) {
						// send move action here
						ShogiMoveAction action = new ShogiMoveAction(this, selectedPiece, gridTouched);
						game.sendAction(action);
						Log.d("Touch", "Piece selected move valid");
						shogiBoard.setPriorMoveSquares(priorGridTouched, gridTouched);

						// reset the selected piece
						selectedPiece = null;
						shogiBoard.setSelected(null);
						pieceIsSelected = false;
						return true;
					}
				}

				Log.d("Touch", "Piece selected move invalid");

				// else deselect the piece and try to select another piece
				selectedPiece = null;
				shogiBoard.setSelected(null);
				pieceIsSelected = false;
			}

			if(!pieceIsSelected) { // --------- no piece currently selected ---------------

				// Draws the prior moves once the move was successful and there was a piece selected
				if (selectedPiece != null && selectedPiece.getOwner() != state.getCurrentPlayer()) {
					shogiBoard.setPriorMoveSquares(selectedPiece.getPosition(), gridTouched);
				}

				// get piece if there is a piece on the touched grid
				if(gridTouched != null) selectedPiece = state.getPiece(gridTouched);

				if (selectedPiece != null) {
					// checks that only pieces from current player can be selected
					Log.d("Touch", ""+playerNum);
					if (selectedPiece.getOwner() != playerNum){
						selectedPiece = null;
						return true;
					}

					shogiBoard.setSelected(gridTouched);
					piecePossibleMoves = selectedPiece.getPossibleMoves();
					shogiBoard.setPossibleMoves(piecePossibleMoves);
					pieceIsSelected = true;
					Log.d("Touch", "Piece getting selected");
				} else {
					Log.d("Touch", "Piece not selected");
					// pieceIsSelected = false;
				}

			}// piece is selected
		}

		return true;
	}//onTouch

	@Override
	public boolean onDown(@NonNull MotionEvent motionEvent) {
		return false;
	}

	@Override
	public void onShowPress(@NonNull MotionEvent motionEvent) {

	}

	@Override
	public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
		return false;
	}

	/**
	 * implements drag and drop for moving a piece
	 * @param motionEvent
	 * @param motionEvent1
	 * @param v
	 * @param v1
	 * @return
	 */
	@Override
	public boolean onScroll(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {

		return false;
	}

	@Override
	public void onLongPress(@NonNull MotionEvent motionEvent) {

	}

	@Override
	public boolean onFling(@Nullable MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
		return false;
	}

	private void confirmExit() {
		// Show a confirmation dialog before exiting
		new AlertDialog.Builder(myActivity)
				.setTitle("Quit App")
				.setMessage("Are you sure you want to quit?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {
						// Close the app
						finishAffinity(myActivity); // Close all activities and exit
					}})
				.setNegativeButton(android.R.string.no, null).show();
	}

	/**
	 * code from shogi test that gets called in onClick
	 */
	public void shogiStateTest(){

		//Clears the text in the multi-line EditText
		testResultsEditText.setText("");

		ShogiState firstInstance = new ShogiState(); // Create new instance of game state class

		ShogiState firstCopy = new ShogiState(firstInstance); 	//Create deep copy from player 1's perspective

		// Simulate gameplay by calling methods on firstInstance and append action descriptions to the EditText
		/**
		 * External Citation
		 * Date: 28 October 2024
		 * Problem: Required modifications to the onClick() method to simulate gameplay
		 *          and ensure thorough testing of the ShogiState class.
		 * Resource: ChatGPT
		 * Solution: Consulted ChatGPT for guidance on how to implement the logic moves,
		 *    ensuring that each method in ShogiState is called at least once
		 *    during the simulation.
		 */
		// Player 1 moves Pawn from (6, 2) to (5, 2) (MovePawn)
		ShogiMoveAction movePawn1 = new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(6, 2)), new ShogiSquare(5, 2));
		testResultsEditText.append("Player 1 moves Pawn from (6, 2) to (5, 2): " + (firstInstance.moveAction(movePawn1) ? "Success" : "Failed") + "\n");

// Player 2 moves Pawn from (0, 3) to (1, 2)
		ShogiMoveAction moveGoldG1= new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(0, 3)), new ShogiSquare(1, 2));
		testResultsEditText.append("Player 2 moves GoldG from (0, 3) to (1, 2): " + (firstInstance.moveAction(moveGoldG1) ? "Success" : "Failed") + "\n");

// Player 1 moves moveBishop1 from (7, 1) to (2, 6)
		ShogiMoveAction moveBishop1 = new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(7, 1)), new ShogiSquare(2, 6));
		testResultsEditText.append("Player 1 moves Bishop from (7, 1) to (2, 6): " + (firstInstance.moveAction(moveBishop1) ? "Success" : "Failed") + "\n");
		firstInstance.getPiece(new ShogiSquare(2, 6)).bePromoted(true); // bishop promotion
// Player 2 moves moveGoldG2 from (0, 5) to (1, 5)
		ShogiMoveAction moveGoldG2 = new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(0, 5)), new ShogiSquare(1, 5));
		testResultsEditText.append("Player 2 moves GoldG from (0, 5) to (1, 5): " + (firstInstance.moveAction(moveGoldG2) ? "Success" : "Failed") + "\n");

// Player 1 moves moveBishop2 from (2, 6) to (1, 5)
		ShogiMoveAction moveBishop2= new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(2, 6)), new ShogiSquare(1, 5));
		testResultsEditText.append("Player 1 moves Bishop from (2, 6) to (1, 5): " + (firstInstance.moveAction( moveBishop2) ? "Success" : "Failed") + "\n");

// Player 2 moves King from (0, 4) to (0, 3)
		ShogiMoveAction moveKing = new ShogiMoveAction(this, firstInstance.getPiece(new ShogiSquare(0, 4)), new ShogiSquare(0, 3));
		testResultsEditText.append("Player 2 moves King from (0, 4) to (0, 3): " + (firstInstance.moveAction(moveKing) ? "Success" : "Failed") + "\n");
// player1 drops Gold General (1,4)
		firstInstance.getPieces().get(35).setOnBoard(true);
		ShogiMoveAction dropGoldG = new ShogiMoveAction(this, firstInstance.getPieces().get(35), new ShogiSquare(1, 4));
		testResultsEditText.append("Player 2 drops Gold General to (1, 4): " + (firstInstance.moveAction(dropGoldG) ? "Success" : "Failed") + "\n");
		// Final message about the game's end or winner
		testResultsEditText.append("Player 1 has won the game.\n");


		// Create another instance of the game state and deep copy
		ShogiState secondInstance = new ShogiState();
		ShogiState secondCopy = new ShogiState(secondInstance);


		// Compare firstCopy and secondCopy, print if identical
		if (firstCopy.toString().equals(secondCopy.toString())) {
			testResultsEditText.append("The two instances are identical.\n");
		} else {
			testResultsEditText.append("The two instances are not identical.\n");
		}

		//Print string representations for both copies
		testResultsEditText.append("First Copy: \n" + firstCopy.toString() + "\n");
		testResultsEditText.append("Second Copy:\n" + secondCopy.toString() + "\n");
	}// shogiGameStateTest()

	/**
	 * This method gets called when the user clicks the 'Run Test' button.
	 * It performs the following actions:
	 * 1. Clears any text currently displayed in the EditText.
	 * 2. Creates a new instance of the game state class.
	 * 3. Creates a deep copy of the first instance.
	 *
	 * @param button
	 *      the button that was clicked
	 */
/*
	@Override
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		if (game == null) return;

		// ShogiStateTest
		// shogiStateTest();


	}// onClick*/

}// class ShogiHumanPlayer

