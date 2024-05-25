### ScrollView ***Vertical**

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Your other layout elements here -->

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- Content to be scrolled -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <!-- Your content here -->
            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 1" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 2" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 3" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 4" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 5" />

            <!-- Add more views as needed -->

        </LinearLayout>
    </ScrollView>

</RelativeLayout>
```

### ScrollView  **Horizentical**

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Your other layout elements here -->

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scrollbars="none"
        android:layout_alignParentBottom="true">

        <!-- Content to be scrolled -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <!-- Your content here -->
            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 1" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 2" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 3" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 4" />

            <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Button 5" />

            <!-- Add more buttons as needed -->

        </LinearLayout>
    </ScrollView>

</RelativeLayout>
```

### ViewFlipper

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ViewFlipper
        android:id="@+id/viewFlipper"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:autostart="true"
        android:layout_centerInParent="true">

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/image1" />

        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/image2" />

        <!-- Add more ImageViews for additional images -->

    </ViewFlipper>

</RelativeLayout>
```

### Usages

```java
    private int[] images = {R.drawable.image1, R.drawable.image2}; // Add more image resource IDs as needed
    private ViewFlipper viewFlipper;

    viewFlipper = findViewById(R.id.viewFlipper);

    // Dynamically add ImageViews with images to the ViewFlipper
    for (int imageId : images) {
    ImageView imageView = new ImageView(this);
    imageView.setImageResource(imageId);
    viewFlipper.addView(imageView);
    }

    // Set flip interval (optional)
    viewFlipper.setFlipInterval(3000); // milliseconds (3 seconds)
    viewFlipper.setAutoStart(true); // Auto start flipping
```

### imageSwitcher

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageSwitcher
        android:id="@+id/imageSwitcher"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerInParent="true"
        android:inAnimation="@android:anim/slide_in_left"
        android:outAnimation="@android:anim/slide_out_right" />

</RelativeLayout>
```

### Usages

```java
private int[] imagesId = {R.drawable.image1, R.drawable.image2}; // Add more image resource IDs as needed
private ImageSwitcher imageSwitcher;
private int imgIndex = 0;

imageSwitcher = findViewById(R.id.imageSwitcher);

// Set the ViewFactory for the ImageSwitcher
imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
    @Override
    public View makeView() {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }
});

// Set initial image
imageSwitcher.setImageResource(imagesId[imgIndex]);

public void nextBtn(View view) {
imgIndex = imgIndex + 1;
imageSwitcher.setImageResource(imagesId[imgIndex]);
}

public void previousBtn(View view) {
imgIndex = imgIndex - 1;
imageSwitcher.setImageResource(imagesId[imgIndex]);
}
```