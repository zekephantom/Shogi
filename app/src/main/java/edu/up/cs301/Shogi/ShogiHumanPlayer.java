package edu.up.cs301.Shogi;


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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

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
public class ShogiHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

	/* instance variables */
	// The EditText that displays test results or game state information
	private EditText testResultsEditText;

	// the surface view of the board
	private ShogiGUI shogiBoard;

	// the most recent game state, as given to us by the ShogiLocalGame
	private ShogiState state = new ShogiState();
	
	// the android activity that we are running
	private GameMainActivity myActivity;

	private Handler guiHandler = null;

	// the field the user touched on
	private ShogiSquare gridTouched;
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
	 * Updates the display based on the current game state.
	 */
	protected void updateDisplay(ShogiState updatedState) {

		// copied from computer player 2, idk if we need a new thread here
		if (guiHandler != null) {
			guiHandler.post(
					new Runnable() {
						public void run() {
							if (updatedState != null) {
								shogiBoard.setShogiState(updatedState);

								// when this is uncommented it doesnt work because the game
								// state passed in is not properly initialized
							}
						}});
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

		/*if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			// if the move was out of turn or otherwise illegal, flash the screen
			surfaceView.flash(Color.RED, 50);
		}
		else*/ if (!(info instanceof ShogiState))
			// ignore the message if it's not a ShogiState message
			return;
		else {
			this.state = (ShogiState)info;
			updateDisplay(state);
		}
		// update our state; then update the display
		if (this.state == null) Log.d("state", "State is null");
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


		// TODO: add switchListeners for englisch/japanese
		// TODO: quit button

		Button quit = myActivity.findViewById(R.id.butQuit);
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
			gridTouched = shogiBoard.gridSelection(x, y);

			if (gridTouched != null) {
                Log.d("Touch", "field touched: \n row: " + gridTouched.getRow() + " col: " + gridTouched.getCol());
            }

			// Logic to get the possible move if a piece is selected
			if (pieceIsSelected) { // drop action
				// Check if the drop move is a legal move
				for (ShogiSquare targetSquare : piecePossibleMoves) {
					if (gridTouched.getRow() == targetSquare.getRow() && gridTouched.getCol() == targetSquare.getCol()) {
						// send move action here
						ShogiMoveAction action = new ShogiMoveAction(this, selectedPiece, gridTouched);
						game.sendAction(action);
						Log.d("Touch", "Piece selected move valid");
						shogiBoard.setPriorMoveSquares(selectedPiece.getPosition(), gridTouched);

						// reset the selected piece
						selectedPiece = null;
						shogiBoard.setSelected(null);
						pieceIsSelected = false;
						return true;
					}
				}

				Log.d("Touch", "Piece selected move invalid");
				// else deselect the piece
				// TODO flash screen potentially
				selectedPiece = null;
				shogiBoard.setSelected(null);
				pieceIsSelected = false;

			} else { // no piece currently selected
				if (selectedPiece != null) {
					shogiBoard.setPriorMoveSquares(selectedPiece.getPosition(), gridTouched);
				}
				selectedPiece = state.getPiece(gridTouched);
				// TODO: to make sure that selection also works for captured
				// 	pieces we have to logically put them in the right location
				//  when being captured within the shogiState
				if (selectedPiece != null) {

					shogiBoard.setSelected(gridTouched);
					piecePossibleMoves = selectedPiece.getPossibleMoves(state);
					shogiBoard.setPossibleMoves(piecePossibleMoves);
					pieceIsSelected = true;
					Log.d("Touch", "Piece getting selected");
				} else {
					Log.d("Touch", "Piece not selected");
					// pieceIsSelected = false;
					// TODO flash screen potentially
				}
			}
		}

		return true;
	}//onTouch

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

