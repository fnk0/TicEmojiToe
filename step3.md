# Step 3: Styling the App

We will do some basic styling to make the app make feel your own.

For colors you can use Android's material design colors reference: https://material.google.com/style/color.html#

Step 1: Define the background of the window.

We could have defined the background color inside the root LinearLayout of our file, but that would
only have changed the background for that specific Layout.

Without more delays, open the file _values.xml_ inside _src/main/res/values_

The file should look like this:

```xml
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
```

To change the background we will add one more property inside ```<style> </style>```

Add the folowing inside style:
```xml
<!-- Note the hex value #424242 can be any color of your choice! -->
<item name="android:windowBackground">#424242</item>
```

Note: if you choose a dark background color you may want consider changing changing the theme, so it display's white text color instead of black.

To change the theme from Light to a Darker one just change the following line:

```xml
<!-- Change this -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">

<!-- To this -->
<style name="AppTheme" parent="Theme.AppCompat">
```

That's all! Now all the text will have a white color by default instead of black.

Next, defining colors!

Next we will define colors for our application. Inside the same folder open _colors.xml_
It should look like this:

```xml

<?xml version="1.0" encoding="utf-8"?>
<!-- Change any of the values in there to a hex color of your choice -->
<resources>
    <color name="colorPrimary">#242424</color>
    <color name="colorPrimaryDark">#212121</color>
    <color name="colorAccent">#4DD0E1</color>
</resources>

```

You can also add colors to this file to be used anywhere in the application. If you noticed, we 
used _colorAccent_ inside the _activity_main.xml_ earlier.

For the next step I recommend you use define a darker version of accentColor to be used by our button background:
Inside resources add:

```xml
<color name="colorAccentDark">#00BCD4</color>
```

Nice! Now if you check the layout or run the app in your phone our emulator you will see that the app has changed!

You may have noticed that the  button looks a bit ugly... Let's style it!

Create a file named _btn_accent.xml_ inside the folder _src/main/res/drawable_ and add the following inside!

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <corners android:radius="8dp" />
            <solid android:color="@color/colorAccentDark" />
        </shape>
    </item>
    <item>
        <shape android:shape="rectangle">
            <corners android:radius="8dp" />
            <solid android:color="@color/colorAccent" />
        </shape>
    </item>
</selector>
```

This background will allow the button to change colors when a user presses it. Just like the default one. 
If you just set a background color to a button, it will look very static and quite ugly, since it will not provide
any feedback of being pressed to the user.

Now go back to activity_main.xml and set the background property of the button to ```@drawable/btn_accent```.
Note: we skipped the .xml when setting the background. That works for all resources. We always skip the extension
when referring images, drawables, etc..

That's it! We added some basic styling to the App! You can check your work by running the app again.

Well, so far the app doesn't do anything yet. Don't worry that's the next step!

###[Go to step 4](https://github.com/fnk0/TicEmojiToe/blob/master/step4.md)