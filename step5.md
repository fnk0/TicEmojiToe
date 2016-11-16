# Step 5: Finishing up the details!

To store the scores of the games we will make usage of a Android API named SharedPreferences.
This will allow the scores to be persisted across launches of the app. 

Without more delays let's jump into code!

#### 1. Get a instance of SharedPreferences

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Define the constants to be used
    public static final String P1_SCORE = "p1Score";
    public static final String P2_SCORE = "p2Score";

    // ... code here
    SharedPreferences preferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ... other code here
        
        // Grab a instance of the preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        // Update the scores (at 1st launch will be 0)
        // This method is not defined yet, don't worry we will add in the next step.
        updateScores();
    }
}
```

#### 2. Define the updateScores method

```java
// inside MainActivity
 /**
 * Updates the scores of the players from SharedPreferences
 */
public void updateScores() {
    int p1Score = preferences.getInt(P1_SCORE, 0);
    int p2Score = preferences.getInt(P2_SCORE, 0);
    p1ScoreTv.setText(String.format(Locale.getDefault(), "%d", p1Score));
    p2ScoreTv.setText(String.format(Locale.getDefault(), "%d", p2Score));
}
```

#### 3. Define Increment Player Scores:
```java
// Inside MainActivity
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
```

#### 4. Increment and Update scores during the game!
 
```java
// Inside MainActivity

// Att the following line inside handleMove
public boolean handleMove(ImageButton btn) {
        // .. other code here...
        if (checkWin(x, y)) { 
            // other code here...
            incrementPlayerScore(); // We add this line!
        }
    }
```

Awesome!! Try play the game now and you will see that the scores change when a player wins! Try closing the app
and starting it again! You will see that the scores are saved in the preferences.
 
That's great! But you noticed that we can't clear the scores right? Let's fix this!

#### 5. Clearing the scores!

Inside the folder _src/main/res_ create a folder named _menu_ and a file named _menu.xml_ inside.

Open the file and add the following:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/clear_scores"
        android:title="CLEAR SCORES"
        app:showAsAction="always"
        />
</menu>
```

The next step is to add this menu to our activity. Open MainActivity.java and add the following:

```java
// Inside MainActivity
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
```


That's it! We are done!! Run the app and enjoy your work!! Try experimenting with different emoji's and colors!

Congratulations!! You have your own game now!! Play with your friends and brag about how you made it!

### [Where to go from now?](https://github.com/fnk0/TicEmojiToe#where-to-go-from-now)