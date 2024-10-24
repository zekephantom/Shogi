package edu.up.cs301.Shogi;

import edu.up.cs301.GameFramework.players.GameHumanPlayer;
import edu.up.cs301.GameFramework.GameMainActivity;
import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.shogi.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

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
 * @version July 2013
 */

public class ShogiHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */
	// The EditText that displays test results or game state information
	private EditText testResultsEditText;

	// the most recent game state, as given to us by the ShogiLocalGame
	private ShogiState state;
	
	// the android activity that we are running
	private GameMainActivity myActivity;
	
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
	protected void updateDisplay() {
		// set the text in the appropriate widget
	if(state == null) return;
	testResultsEditText.setText(state.toString());
	}


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
	@Override
	public void onClick(View button) {
		// if we are not yet connected to a game, ignore
		if (game == null) return;

		//Clears the text in the multi-line EditText
		testResultsEditText.setText("");

		// Create new instance of game state class
		ShogiState firstInstance = new ShogiState();
		//Create deep copy from player 1's perspective
		ShogiState firstCopy = new ShogiState(firstInstance);

	}// onClick

	/**
	 * Callback method when we receive information from the game.
	 *
	 * @param info
	 *      the message received from the game
	 */
	@Override
	public void receiveInfo(GameInfo info) {
		// ignore the message if it's not a ShogiState message
		if (!(info instanceof ShogiState)) return;
		
		// update our state; then update the display
		this.state = (ShogiState)info;
		updateDisplay();
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
		activity.setContentView(R.layout.game_state_test);
		this.testResultsEditText = (EditText) activity.findViewById(R.id.tv_test_results);


		Button runTestButton = (Button) activity.findViewById(R.id.button_run_test);
		runTestButton.setOnClickListener(this);
	}

}// class ShogiHumanPlayer

