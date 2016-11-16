# Step 2: Defining the App Layout!

Our app will look something like this (You are fre to use different colors or emojis)

![screenshot](https://github.com/fnk0/TicEmojiToe/blob/master/screenshots/screenshot1.png?raw=true =500x)

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

    <View /> <!-- This will just draw a single line -->

    <TextView /> <!-- Displays who won the game -->
    
    <Button /> <!-- Restart game button -->

</LinearLayout>

```