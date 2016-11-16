# Step 4: Connecting XML to Java

By default Android provides a mechanism to reference a element inside XML by finding it by ID.
But we have quite a bit of elements and 9 buttons on a row. For each element we would do the following:

```java
ImageButton button00 = (ImageButton) findViewById(R.id.btn_00);
``

We would have to this 9 times. Then to assign a listener to that button we would have to do:
```java
button00.setOnClickListener(this);
```

So that's about 18 lines of repetitive code. That's why we will use a library named [ButterKnife](http://jakewharton.github.io/butterknife/) to make our lives easier.

Your app have 2 files named _build.gradle_. One for the whole project and another one for the _app_ module.

### 1. Add ButterKnife to our App

1st open the outer most one, the project one and add the following inside ```dependencies```

```groovy
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
```

The file should look somewhat like this now:

```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.2'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

If it doesn't look like this you are in the wrong file!

Now open the build.gradle file inside the app module and do the following:

Right after where it says ``` apply plugin: 'com.android.application' ```
Add ``` apply plugin: 'com.neenbedankt.android-apt' ```

Inside dependencies add:
```groovy
    compile 'com.jakewharton:butterknife:8.4.0'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'
```

The file should look like this now:
```groovy
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.0"
    // Note: the application ID will be different for your project.
    defaultConfig {
        applicationId "com.gabilheri.tictactoe"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.0.0'
    testCompile 'junit:junit:4.12'

    compile 'com.jakewharton:butterknife:8.4.0'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'
}
```

That's great! We now added our first third party library! 

### 2. Bind the XML elements to our Activity

Open the file MainActivity.java and the following:

```java
// Note the View.OnClickListener interface will allow us to define a onCLick method on this activity
to handle the click of all the ImageButtons
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // define the XML layout to be used
        ButterKnife.bind(this); // Bind the views
        // Assign a click listener to each one of the buttons
        for (ImageButton btn : buttonList) {
            btn.setOnClickListener(this);
        }
    }
    
    @Override
    public void onClick(View view) {
    }
}
```

You see how much better it was now? We had all our buttons inside an Array and where able to add a listener to
all the buttons with a few lines of code!

### 3. Handle the ImageButton click:
 
 Download any 2 emojis (or more) of your choice from https://github.com/fnk0/TicEmojiToe/tree/master/app/src/main/res/drawable-nodpi
 
 If you don't like those Emoji's you can use your own or get the [Emoji One pack free for anyone to use.](http://emojione.com/)
 
Next: Create a folder named _drawable-nodpi_ inside _src/main/res_ and paste the emoji's inside.

Note: ANY files inside the res folder should contain ONLY lowercase letters with underscores.

Now that we have our images let's handle the button click. Open MainActivity.java

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    // Define a player to keep track of who has the turn. 
    int playerTurn = 1;
    
    @Override
    public void onClick(View view) {
        if (view instanceof ImageButton) { // check just to be safe..
            ImageButton btn = (ImageButton) view; // casts the ImageButton from the view
            if (playerTurn == 1) {
                playerTurn = 2;
                // Note: e2 is the name of the emoji that I used! If you named it different
                // you should use that name. Same goes for e3.
                btn.setImageResource(R.drawable.e2);
            } else {
                playerTurn = 1;
                btn.setImageResource(R.drawable.e3);
            }
        }
    }
}
```

That's it! Now if you click the button you will see that the background of the button will change
to reflect a emoji for each player!

If you noticed is not very fun, you can play on top of a already played element, when the game is over you can still
play and there's no way to restart the game or define a winner. We will be adding that in the next steps!

### 4. Make it a game!

Now to make it a game add the following to your code. Read the comments for more details!

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Constants
    public static final int BOARD_SIZE = 3;
    
    // other code goes here...
    // Instance variables
    int[][] gameGrid = new int[BOARD_SIZE][BOARD_SIZE];
    int playerTurn = 1;
    boolean isGamePlaying = true;
    
    // Uses ButterKnife to bind the OnClick of the button
    // with R.id.restart_game to this method
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
}
```

Now we need to modify the onClick method of our activity to not allow someone to click on a already checked position
or if the game is already over.

Make the onClick method look like this:

```java
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
```

Awesome! If you run the app now you should be able to play the game an restart it to play again!!

But if you noticed we are still not using the scores! 

That's the final part! Finishing up the details!!

###[Go to step 5](https://github.com/fnk0/TicEmojiToe/blob/master/step5.md)




