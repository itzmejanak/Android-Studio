## Tab Layout with Fragments

### In the Main XML Layout File:

```xml
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    app:title="Tab Layout Example" />

<com.google.android.material.tabs.TabLayout
    android:id="@+id/tabLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?attr/colorPrimary"
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
    app:tabGravity="fill"
    app:tabIndicatorColor="@color/white"
    app:tabMode="fixed"
    app:tabSelectedTextColor="@color/white"
    app:tabTextColor="@color/white" />

<FrameLayout
    android:id="@+id/frameLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### In the Main Java File:

```java
TabLayout tabLayout = findViewById(R.id.tabLayout);
FrameLayout frameLayout = findViewById(R.id.frameLayout);

// Add tabs to the TabLayout
TabLayout.Tab tab1 = tabLayout.newTab().setText("Tab 1");
TabLayout.Tab tab2 = tabLayout.newTab().setText("Tab 2");
TabLayout.Tab tab3 = tabLayout.newTab().setText("Tab 3");

tabLayout.addTab(tab1);
tabLayout.addTab(tab2);
tabLayout.addTab(tab3);

// Set up a TabLayout.OnTabSelectedListener to switch fragments
tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment1()).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment2()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Fragment3()).commit();
                break;
            // Add more cases for additional tabs
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
});
```

### Notes:

- Ensure you have Fragment1, Fragment2, Fragment3 (or any other fragments you want to use) defined.
- Replace `R.id.frameLayout` with the ID of the FrameLayout where you want to display the fragments.
- You can customize the appearance of the tabs and the behavior of the TabLayout by adjusting attributes and listeners as needed.