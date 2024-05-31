### Main XML File (activity_main.xml)
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/your_image" <!-- Replace with your image resource -->
        android:contentDescription="Image for animation" />

</LinearLayout>
```

### Animation List (res/anim/animation_list.xml)
```xml
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false"> <!-- Set to true if the animation should only run once -->

    <item
        android:drawable="@drawable/frame1" <!-- First frame of the animation -->
        android:duration="200" /> <!-- Duration for the first frame -->

    <item
        android:drawable="@drawable/frame2" <!-- Second frame of the animation -->
        android:duration="200" /> <!-- Duration for the second frame -->

    <item
        android:drawable="@drawable/frame3" <!-- Third frame of the animation -->
        android:duration="200" /> <!-- Duration for the third frame -->

    <!-- Add more frames as needed -->
</animation-list>
```

### MainActivity.java
```java
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.animation_list); // Set the animation list as background

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start(); // Start the animation
    }
}
```

- lotteFile is a website for using predefine animation
for using third party animation 
implement it into build gradle into dpendency tag
download jason into raw dir
and use it by new widgets name

### PROCESS 
### Implementing Lottie Animations

1. **Add Lottie Dependency to `build.gradle`:**
   ```groovy
   dependencies {
       implementation 'com.airbnb.android:lottie:5.2.0' // Add Lottie dependency
   }
   ```

2. **Download Lottie JSON File:**
   - Go to [LottieFiles](https://lottiefiles.com/), find an animation you like, and download the JSON file.

3. **Place the JSON File in `res/raw` Directory:**
   - Create a `raw` directory under `res` if it doesn't exist.
   - Place your downloaded JSON file (e.g., `animation.json`) in the `res/raw` directory.

4. **Use LottieAnimationView in XML:**

### Main XML File (activity_main.xml)
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/lottieAnimationView" <!-- Unique ID for the LottieAnimationView -->
        android:layout_width="wrap_content" <!-- Width of the LottieAnimationView wraps its content -->
        android:layout_height="wrap_content" <!-- Height of the LottieAnimationView wraps its content -->
        app:lottie_rawRes="@raw/animation" <!-- Reference to the JSON file in the raw directory -->
        app:lottie_autoPlay="true" <!-- Automatically starts the animation when view is attached to a window -->
        app:lottie_loop="true" <!-- Whether the animation should loop indefinitely -->
        app:lottie_speed="1.0" /> <!-- Playback speed of the animation -->

</LinearLayout>
```

### MainActivity.java
```java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        // You can control the animation programmatically if needed
        // lottieAnimationView.setAnimation(R.raw.animation);
        // lottieAnimationView.playAnimation();
    }
}
```


### circular `ImageView` in Android, you can use a third-party library such as `CircleImageView` from GitHub. Here's how to implement it in your project:

1. **Add the dependency to your `build.gradle` file:**
   ```groovy
   dependencies {
       implementation 'de.hdodenhof:circleimageview:3.1.0'
   }
   ```

2. **Add the `CircleImageView` to your XML layout:**

### Main XML File (activity_main.xml)
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <de.hdodenhof.circleimageview.CircleImageView
        android:id="@+id/circleImageView" <!-- Unique ID for the CircleImageView -->
        android:layout_width="100dp" <!-- Width of the CircleImageView -->
        android:layout_height="100dp" <!-- Height of the CircleImageView -->
        android:src="@drawable/your_image" <!-- Source image for the CircleImageView -->
        app:civ_border_width="2dp" <!-- Border width around the CircleImageView -->
        app:civ_border_color="#FF000000" <!-- Border color around the CircleImageView -->
        android:layout_gravity="center" <!-- Gravity of the CircleImageView within its container -->
        android:contentDescription="Profile image" /> <!-- Content description for accessibility -->

</LinearLayout>
```

3. **Load an image into the `CircleImageView` programmatically in your Activity:**

### MainActivity.java
```java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleImageView circleImageView = findViewById(R.id.circleImageView);
        // You can set an image programmatically if needed
        // circleImageView.setImageResource(R.drawable.your_image);
    }
}
```


### Toasty is a library for creating customizable and attractive Toast messages in Android. Here’s how to implement and use Toasty in your project:

1. **Add the dependency to your `build.gradle` file:**
   ```groovy
   dependencies {
       implementation 'com.github.GrenderG:Toasty:1.5.2'
   }
   ```

2. **Use Toasty in your code:**

### MainActivity.java
```java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example usage of Toasty
        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
        Toasty.error(this, "Error!", Toast.LENGTH_SHORT, true).show();
        Toasty.info(this, "Info!", Toast.LENGTH_SHORT, true).show();
        Toasty.warning(this, "Warning!", Toast.LENGTH_SHORT, true).show();
        Toasty.normal(this, "Normal toast").show();
    }
}
```

### Customization Options

Toasty provides several types of toasts with different methods to customize them:

- **Success Toast:**
  ```java
  Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
  ```

- **Error Toast:**
  ```java
  Toasty.error(this, "Error!", Toast.LENGTH_SHORT, true).show();
  ```

- **Info Toast:**
  ```java
  Toasty.info(this, "Info!", Toast.LENGTH_SHORT, true).show();
  ```

- **Warning Toast:**
  ```java
  Toasty.warning(this, "Warning!", Toast.LENGTH_SHORT, true).show();
  ```

- **Normal Toast:**
  ```java
  Toasty.normal(this, "Normal toast").show();
  ```

- **Custom Toast with Icon:**
  ```java
  Toasty.normal(this, "Custom toast", getResources().getDrawable(R.drawable.ic_custom_icon)).show();
  ```

### Customizing Toasty

You can also customize the appearance of Toasty messages globally by configuring it in your `Application` class:

### MyApplication.java
```java
import android.app.Application;
import es.dmoral.toasty.Toasty;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Customize Toasty
        Toasty.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(getAssets(), "your_custom_font.ttf"))
                .setTextSize(16) // Set text size
                .apply(); // Apply the configuration
    }
}
```