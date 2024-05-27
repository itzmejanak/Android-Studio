## MenuBars
---
### Option MenuBar
### `XML`
```xml
<!-- Option MenuBar -->
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/action_search" <!-- Unique ID for the search option -->
        android:icon="@drawable/ic_search" <!-- Icon for the search option -->
        android:title="Search" /> <!-- Title for the search option -->
    <!-- Additional menu items -->
</menu>
```

### Usages 
```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.option_menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_search:
            // Handle search option click
            return true;
        // Add additional cases for other menu items if needed
        default:
            return super.onOptionsItemSelected(item);
    }
}

```
---

### PopUp MenuBar
### `XML`
```xml
<!-- PopUp MenuBar -->
<Button
    android:id="@+id/buttonPopupMenu" <!-- Unique ID for the button triggering the PopUp Menu -->
    android:layout_width="wrap_content" <!-- Width of the button wraps its content -->
    android:layout_height="wrap_content" <!-- Height of the button wraps its content -->
    android:text="Show Popup Menu" /> <!-- Text displayed on the button -->
```

### Usages 
```java
Button buttonPopupMenu = findViewById(R.id.buttonPopupMenu);
buttonPopupMenu.setOnClickListener(v -> {
    PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
    popupMenu.setOnMenuItemClickListener(item -> {
        switch (item.getItemId()) {
            // Handle popup menu item clicks
            default:
                return false;
        }
    });
    popupMenu.show();
});

```
---

### Context MenuBar
### `XML`
```xml
<!-- Context MenuBar -->
<TextView
    android:id="@+id/textViewContextMenu" <!-- Unique ID for the view triggering the Context Menu -->
    android:layout_width="wrap_content" <!-- Width of the TextView wraps its content -->
    android:layout_height="wrap_content" <!-- Height of the TextView wraps its content -->
    android:text="Long press to show context menu" <!-- Text displayed in the TextView -->
    android:longClickable="true" /> <!-- Enables long press -->
```
### Usages 
```java
TextView textViewContextMenu = findViewById(R.id.textViewContextMenu);
textViewContextMenu.setOnLongClickListener(v -> {
    registerForContextMenu(v);
    openContextMenu(v);
    return true;
});

@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.context_menu, menu);
}

@Override
public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        // Handle context menu item clicks
        default:
            return super.onContextItemSelected(item);
    }
}

```
---
