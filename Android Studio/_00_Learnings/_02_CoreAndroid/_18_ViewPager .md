### ViewPager2 vs. ViewPager

ViewPager2 is the updated version of ViewPager, providing improved performance and additional features. It offers better support for handling changes to the underlying data set, including adding and removing items dynamically.

For the adapter:
- If you're using ViewPager2, extend `FragmentStateAdapter`.
- If you're using the old ViewPager, extend `FragmentPagerAdapter`.

Since we're using ViewPager2, we'll extend `FragmentStateAdapter`.

### Adapter: FragmentStateAdapter

In the adapter's `createFragment` method, we'll switch between all the fragments' layouts. This method is responsible for creating the fragment associated with each position in the ViewPager2.

```java
public class MyFragmentStateAdapter extends FragmentStateAdapter {

    public MyFragmentStateAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            // Add more cases for additional fragments
            default:
                return new DefaultFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of fragments
    }
}
```

### In the main Java file:
```java
ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
MyFragmentStateAdapter adapter = new MyFragmentStateAdapter(this);
viewPager2.setAdapter(adapter);
```

#### Ensure that your layout XML file contains a ViewPager2 element with the appropriate ID:

```xml
<androidx.viewpager2.widget.ViewPager2
    android:id="@+id/viewPager2"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

Here, we create an instance of `MyFragmentStateAdapter` and set it as the adapter for our ViewPager2 (`viewPager2`). Ensure that you've defined the ViewPager2 in your layout XML with the appropriate ID (`viewPager2` in this case).