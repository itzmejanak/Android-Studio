### Button

```xml
<Button
    android:id="@+id/button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Button" />
```

#### Usages

```java
Button button = findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
    }
});
```

### TextView

```xml
<TextView
    android:id="@+id/textView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="TextView" />
```

#### Usages
```
TextView textView = findViewById(R.id.textView);
textView.setText("New Text");
```
### EditText

```xml
<EditText
    android:id="@+id/editText"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:hint="Enter text" />
```

#### Usages

```java
EditText editText = findViewById(R.id.editText);
String text = editText.getText().toString();
Toast.makeText(getApplicationContext(), "Entered Text: " + text, Toast.LENGTH_SHORT).show();
```

### Toast

```java
Toast.makeText(getApplicationContext(), "This is a Toast message", Toast.LENGTH_SHORT).show();
```

### Custom Toast

```java
LayoutInflater inflater = getLayoutInflater();
View layout = inflater.inflate(R.layout.custom_toast,
        (ViewGroup) findViewById(R.id.custom_toast_container));

TextView text = layout.findViewById(R.id.text);
text.setText("Custom Toast Message");

Toast toast = new Toast(getApplicationContext());
toast.setDuration(Toast.LENGTH_SHORT);
toast.setView(layout);
toast.show();
```

### CheckBox

```xml
<CheckBox
    android:id="@+id/checkBox"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="CheckBox" />
```

#### Usages

```java
CheckBox checkBox = findViewById(R.id.checkBox);
checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getApplicationContext(), "CheckBox Checked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "CheckBox Unchecked", Toast.LENGTH_SHORT).show();
        }
    }
});
```

### RadioGroup and RadioButton

```xml
<RadioGroup
    android:id="@+id/radioGroup"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <RadioButton
        android:id="@+id/radioButton1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="RadioButton 1" />

    <RadioButton
        android:id="@+id/radioButton2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="RadioButton 2" />
</RadioGroup>
```

#### Usages

```java
RadioGroup radioGroup = findViewById(R.id.radioGroup);
radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = findViewById(checkedId);
        Toast.makeText(getApplicationContext(), "Selected Radio Button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
});
```

### Spinner

```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

#### Usages

```java
Spinner spinner = findViewById(R.id.spinner);
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
});
```

### ToggleButton and Switch

```xml
<ToggleButton
    android:id="@+id/toggleButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textOff="Off"
    android:textOn="On" />

<Switch
    android:id="@+id/switchButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

#### Usages

```java
ToggleButton toggleButton = findViewById(R.id.toggleButton);
toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getApplicationContext(), "Toggle Button: ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Toggle Button: OFF", Toast.LENGTH_SHORT).show();
        }
    }
});

Switch switchButton = findViewById(R.id.switchButton);
switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getApplicationContext(), "Switch: ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Switch: OFF", Toast.LENGTH_SHORT).show();
        }
    }
});
```

### ImageView

```xml
<ImageView
    android:id="@+id/imageView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_launcher_background" />
```

### ImageButton

```xml
<ImageButton
    android:id="@+id/imageButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_launcher_background" />
```

### RatingBar

```xml
<RatingBar
    android:id="@+id/ratingBar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:numStars="5"
    android:stepSize="1" />
```

#### Usages

```java
RatingBar ratingBar = findViewById(R.id.ratingBar);
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Toast.makeText(getApplicationContext(), "Rating: " + rating, Toast.LENGTH_SHORT).show();
    }
});
```

### DatePicker

```xml
<DatePicker
    android:id="@+id/datePicker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerHorizontal="true"
    android:calendarViewShown="true"
    android:datePickerMode="spinner"/>
```

#### Usages

```java
DatePicker datePicker = findViewById(R.id.datePicker);
datePicker.init(2024, 5, 24, new DatePicker.OnDateChangedListener() {
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Handle date change
    }
});

```

### DatePickerDialog

```java
Calendar calendar = Calendar.getInstance();
int year = calendar.get(Calendar.YEAR);
int month = calendar.get(Calendar.MONTH);
int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
    new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Handle date selection
        }
    }, year, month, dayOfMonth);

datePickerDialog.show();
```

### TimePicker

```xml
<TimePicker
    android:id="@+id/timePicker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_below="@id/textView"
    android:layout_centerHorizontal="true"
    android:timePickerMode="spinner"/>
```

#### Usages

```java
TimePicker timePicker = findViewById(R.id.timePicker);

// Set initial time
timePicker.setHour(12);
timePicker.setMinute(0);

// Optionally set a time change listener
timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // Handle time change
    }
});

```

### TimePickerDialog

```java
Calendar calendar = Calendar.getInstance();
int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
int minute = calendar.get(Calendar.MINUTE);

TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
    new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Handle time selection
        }
    }, hourOfDay, minute, true);

timePickerDialog.show();
```