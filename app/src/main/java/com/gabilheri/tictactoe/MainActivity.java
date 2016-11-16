package com.gabilheri.tictactoe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Constants
    public static final int BOARD_SIZE = 3;
    public static final String P1_SCORE = "p1Score";
    public static final String P2_SCORE = "p2Score";

    // Views from the XML layout
    @BindViews({
            R.id.btn_00, R.id.btn_01, R.id.btn_02,
            R.id.btn_10, R.id.btn_11, R.id.btn_12,
            R.id.btn_20, R.id.btn_21, R.id.btn_22
    })
    List<ImageButton> buttonList;

    @BindView(R.id.tv_winner)
    TextView winnerTv;

    @BindView(R.id.p1_score)
    TextView p1ScoreTv;

    @BindView(R.id.p2_score)
    TextView p2ScoreTv;

    // Instance variables
    int[][] gameGrid = new int[BOARD_SIZE][BOARD_SIZE];
    int playerTurn = 1;
    boolean isGamePlaying = true;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // define the XML layout to be used
        ButterKnife.bind(this); // Bind the views
        // Grab a instance of the preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Update the scores (at 1st launch will be 0)
        updateScores();
        // Assign a click listener to each one of the buttons
        for (ImageButton btn : buttonList) {
            btn.setOnClickListener(this);
        }
    }

    /**
     * Updates the scores of the players from SharedPreferences
     */
    public void updateScores() {
        int p1Score = preferences.getInt(P1_SCORE, 0);
        int p2Score = preferences.getInt(P2_SCORE, 0);
        p1ScoreTv.setText(String.format(Locale.getDefault(), "%d", p1Score));
        p2ScoreTv.setText(String.format(Locale.getDefault(), "%d", p2Score));
    }

    /**
     * Increments the score of the winning player and saves it on the preferences
     * for future launches of the app
     */
    private void incrementPlayerScore() {
        String key = playerTurn == 1 ? P1_SCORE : P2_SCORE;
        int score = preferences.getInt(key, 0);
        score++;
        preferences.edit().putInt(key, score).apply();
        updateScores();
    }

    @Override
    public void onClick(View view) {
        if (!isGamePlaying) {
            return; // if we are not playing a game... we do nothing on button press
        }
        if (view instanceof ImageButton) { // check just to be safe..
            ImageButton btn = (ImageButton) view; // casts the ImageButton from the view
            // Check that this is a button from the Grid
            if (handleMove(btn)) {
                if (playerTurn == 1) {
                    playerTurn = 2;
                    btn.setImageResource(R.drawable.e2);
                } else {
                    playerTurn = 1;
                    btn.setImageResource(R.drawable.e3);
                }
            }
        }
    }

    @OnClick(R.id.restart_game)
    public void restartGame(View v) {
        // Clear the images of the buttons
        for (ImageButton btn : buttonList) {
            btn.setImageDrawable(null);
        }
        // Restart the board
        gameGrid = new int[BOARD_SIZE][BOARD_SIZE];
        // Initialize a game again
        isGamePlaying = true;
        // Clear the winning message
        winnerTv.setText("");
    }

    /**
     * Performs a move for the pressed button and the current player
     * @param btn
     *      The pressed button
     * @return
     *      Whether the player can perform this move or not
     */
    public boolean handleMove(ImageButton btn) {
        // gets the x y element from the button tag
        String[] pos = ((String) btn.getTag()).split(" ");
        if (pos.length > 1) {
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            // checks that this position has not been played yet
            if (gameGrid[x][y] != 0) {
                return false;
            }
            // Performs the move
            gameGrid[x][y] = playerTurn;
            if (checkWin(x, y)) { // checks if the player has won the game
                isGamePlaying = false;
                winnerTv.setText(String.format(Locale.getDefault(), "Player %d wins", playerTurn));
                incrementPlayerScore();
            }
        }
        return true;
    }

    /**
     * Checks if the current player has won the game or not
     * @param x
     *      The x position of the move
     * @param y
     *      The y position of the move
     * @return
     *      true if the player has won the game, false otherwise
     */
    public boolean checkWin(int x, int y) {
        // check cols
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameGrid[x][i] != playerTurn)
                break;
            if (i == BOARD_SIZE - 1) {
                return true;
            }
        }

        //check row
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (gameGrid[i][y] != playerTurn)
                break;
            if (i == BOARD_SIZE - 1) {
                return true;
            }
        }

        //check diagonal
        if (x == y) {
            //we're on a diagonal
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (gameGrid[i][i] != playerTurn)
                    break;
                if (i == BOARD_SIZE - 1) {
                    return true;
                }
            }
        }

        //check anti diagonal
        if (x + y == BOARD_SIZE - 1) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (gameGrid[i][(BOARD_SIZE - 1) - i] != playerTurn)
                    break;
                if (i == BOARD_SIZE - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_scores) {
            preferences.edit().putInt(P1_SCORE, 0).apply();
            preferences.edit().putInt(P2_SCORE, 0).apply();
            updateScores();
        }
        return super.onOptionsItemSelected(item);
    }
}
