# Step 2: Defining the App Layout!

Our app will look something like this (You are fre to use different colors or emojis)

![screenshot](https://github.com/fnk0/TicEmojiToe/blob/master/screenshots/screenshot1.png?raw=true =300x)

### 1. Understanding Layout components:

For this app we will use some basics Android components. In android there's 2 main components to build
user interfaces.

* View: a Single element on the screen, such as text, images, buttons, etc..
* ViewGroup: A layout component that surrounds Views to define how they should align on the screen.
 
For this app we will just be using one type of ViewGroup. The most common and basic of then **LinearLayout**.

#### LinearLayout
Aligns the Views inside it _Horizontally_ or _Vertically_ in a linear fashion (one after the other).
We will be making usage of one of the _weight_ inside LinearLayout to make the Views take equal space independent of the size of the phone screen.
 
#### Views we will be using

For this app we will be making usage of just a few common Views. 

* Button: Default element to represent a clickable action. (Note: all elements can be made clickable but buttons have some nice properties by default)
* ImageButton: A button which it's primary component is a Image
* TextView: Represents some text on the Screen. Used to draw Strings.
* View: We will use this as a blank element with a background just to draw a single line in the screen

### Understanding our Screen Layout

So.. after looking at the elements and our layout we can start thinking what our layout will be.

Here's an outline of what we want to have:

```xml

<LinearLayout> <!-- Vertical -->
    <TextView />
    
    <!-- Player scores label -->
    <LinearLayout> <!-- Horizontal -->
        <TextView />
        <TextView />
    </LinearLayout>
    
    <!-- Player scores -->
    <LinearLayout> <!-- Horizontal -->
        <TextView />
        <TextView />
    </LinearLayout>
    
    <View /> <!-- This will just draw a single line -->

    <LinearLayout> <!-- Vertical -->
        <!-- Row of buttons -->
        <LinearLayout> <!-- Horizontal -->
            <ImageButton />
            <ImageButton />
            <ImageButton />
        </LinearLayout>
    
        <!-- Row of buttons -->
        <LinearLayout> <!-- Horizontal -->
            <ImageButton />
            <ImageButton />
            <ImageButton />
        </LinearLayout>
        
        <!-- Row of buttons -->
        <LinearLayout> <!-- Horizontal -->
            <ImageButton />
            <ImageButton />
            <ImageButton />
        </LinearLayout>
    </LinearLayout>

    <View /> <!-- This will just draw a single line -->

    <TextView /> <!-- Displays who won the game -->
    
    <Button /> <!-- Restart game button -->

</LinearLayout>

```

Of course this is just an outline, we still have to define the properties of each element. 
In Android XML layout the properties of a element defines the rules of how it should behave.

### Starting the layout: 

Open the _activity_main.xml_ file under the folder _src/main/res/layout_
The file should already have a RelativeLayout inside by default. The RelativeLayout is great but 
a lot more complicated than the LinearLayout. So we will be replacing it to make our lives easier. 

* Replace _RelativeLayout_ for a _LinearLayout_
* Add the properties:
```xml
    android:gravity="center"
    android:orientation="vertical"
```

This properties will align all the elements to the center of the screen both vertically and horizontally.
The orientation property defines how the elements should be layed out by the layout.

Your _activity_main.xml_ It should look now like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.gabilheri.tictactoe.MainActivity"
    android:gravity="center"
    android:orientation="vertical"
    >

</LinearLayout>
```

Great! We have the outer of our layout defined. Let's fill in the rest of the elements.

We are going to add the Scores section first.

#### Adding the scores section:

```xml
<LinearLayout>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="16dp"
        android:text="Score"
        android:textSize="24sp"/>
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="8dp">

        <!-- Note:
            The layout_width property to 0 among weight="1" is a trick
            to make elements use equal space inside a LinearLayout  
        -->
        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center_horizontal"
            android:text="Player 1"
            android:textColor="@color/colorAccent"
            android:textSize="18sp"/>

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center_horizontal"
            android:text="Player 2"
            android:textColor="@color/colorAccent"
            android:textSize="18sp"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="48dp"
        android:orientation="horizontal"
        android:padding="8dp">

        <TextView
            android:id="@+id/p1_score"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center_horizontal"
            android:textSize="18sp"
            tools:text="4"/>

        <TextView
            android:id="@+id/p2_score"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="center_horizontal"
            android:textSize="18sp"
            tools:text="2"/>

    </LinearLayout>
</LinearLayout>
```

You may have noticed that it looks quite different from the Screenshot. That's because we haven't defined
some of the styles yet. We will be doing that at the end of this session. 

On the next section we will be adding the grid of buttons. The most important part of our game.

#### Adding the grid of Buttons:

```xml
<LinearLayout>
    ... stuff here..

    <!-- This will draw a line to separate the PlayingGrid from the score -->
    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/colorAccent"/>

    <!-- Note:
        The showDividers property and the divider properties will
        add a line in between each element of the layout
    -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:divider="@color/colorAccent"
        android:orientation="vertical"
        android:showDividers="middle">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:divider="@color/colorAccent"
            android:orientation="horizontal"
            android:showDividers="middle">

            <!-- Note:
                * The tag property will be used to get the position on the grid
                for the button when playing the game.
                * The width=0 and the weight property will allow the then to fill up
                the row equally independant of the size.
                * The background ?selectableItemBackgroundBorderless will allow the button
                to have a transparent background but still have a nice effec when a user
                clicks a button.
            -->
            <ImageButton
                android:id="@+id/btn_00"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="0 0"/>

            <ImageButton
                android:id="@+id/btn_01"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="0 1"/>

            <ImageButton
                android:id="@+id/btn_02"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="0 2"/>
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:divider="@color/colorAccent"
            android:orientation="horizontal"
            android:showDividers="middle">

            <ImageButton
                android:id="@+id/btn_10"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="1 0"/>

            <ImageButton
                android:id="@+id/btn_11"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="1 1"/>

            <ImageButton
                android:id="@+id/btn_12"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="1 2"/>
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:divider="@color/colorAccent"
            android:showDividers="middle">

            <ImageButton
                android:id="@+id/btn_20"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="2 0"/>

            <ImageButton
                android:id="@+id/btn_21"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="2 1"/>

            <ImageButton
                android:id="@+id/btn_22"
                android:layout_width="0dp"
                android:layout_height="80dp"
                android:layout_weight="1"
                android:background="?selectableItemBackgroundBorderless"
                android:tag="2 2"/>
        </LinearLayout>
    </LinearLayout>
    
    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/colorAccent"/>

</LinearLayout>
```

**Note:** This looks a bit repetitive. You can also build android interfaces by mixing XML and Java.
If you want a little challenge I recommend you building the same grid without adding any buttons through
XML, but using Java instead to add the buttons to the layout.

Next step we will add the end of our layout with the TextView to display the winner and the button to restart the game.
 
#### Finishing up the layout:

```xml
<LinearLayout>
    <TextView
        android:id="@+id/tv_winner"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:padding="16dp"
        android:textSize="18sp"
        tools:text="Player 1 wins"/>
    
    <Button
        android:id="@+id/restart_game"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"
        android:layout_marginTop="8dp"
        android:padding="8dp"
        android:text="Restart Game"
        />
</LinearLayout>
```

Awesome! We now have the layout fully defined! 
The next part is style it and make it look a bit better!
 
###[Go to step 3](https://github.com/fnk0/TicEmojiToe/blob/master/step3.md)