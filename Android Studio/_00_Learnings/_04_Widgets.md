## Widgets

### EditText
```xml
<EditText
    android:id="@+id/editText"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:hint="Enter text here"/>
```

### RadioButtons
```xml
<RadioGroup
    android:id="@+id/radioGroup"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <RadioButton
        android:id="@+id/radioButton1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Option 1"/>

    <RadioButton
        android:id="@+id/radioButton2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Option 2"/>
</RadioGroup>
```

### CheckBox
```xml
<CheckBox
    android:id="@+id/checkBox"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Check me"/>
```

### Spinner
```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

### Spinner Drop-down Components

Spinner drop-down components are mentioned in the `values` folder within the `strings.xml` file with attributes of `string-array` like this:

```xml
<string-array name="cities">
    <item>select</item>
    <item>Pokhara</item>
    <item>Ktm</item>
    <item>Npj</item>
</string-array>
```
- Finally we have to use the name of the array in the attributes of the spinner `enteries` and call the name.


---
### Toggle Button

```xml
<ToggleButton
    android:id="@+id/toggleButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Toggle Button"
    android:textOn="ON"
    android:textOff="OFF"
    android:checked="false"/>
```