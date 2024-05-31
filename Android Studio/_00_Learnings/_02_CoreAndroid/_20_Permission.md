### Permissions

#### Declared Permissions in AndroidManifest.xml File
Permissions in AndroidManifest.xml file are categorized into two types:

##### 1. Normal Permissions:
- Only declared in AndroidManifest.xml file.
- Examples include INTERNET, BLUETOOTH, FINGER_PRINT, etc.

##### 2. Dangerous Permissions:
- Declared in AndroidManifest.xml file.
- Requires additional code for runtime permissions.
- Examples include CONTACTS, SMS, CALL, LOCATION, etc.

[Here](https://stackoverflow.com/questions/36936914/list-of-android-permissions-normal-permissions-and-dangerous-permissions-in-api) is a list of normal and dangerous permissions.


# Making a Phone Call from an Android App

## Dialing a Number
To open the dialer with a phone number:

```java
Intent intent = new Intent(Intent.ACTION_DIAL);
intent.setData(Uri.parse("tel:" + Number));
startActivity(intent);
```

## Steps to Call Directly from the App

### 1. Add Required Permission in the Manifest File
First, add the required permission in your `AndroidManifest.xml` file:
```xml
<uses-permission android:name="android.permission.CALL_PHONE"/>
```

### 2. Check and Request Permission
Then, check if the permission is granted and request it if necessary:

```java
final int REQUEST_CALL_CODE = 1;

if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
   call();
} else {
    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_CODE);
}

void call(){
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + Number));
    startActivity(intent);
}
```

### Notes:
- Replace `Number` with the actual phone number you want to call.
- Ensure you handle the permission request result in your activity by overriding `onRequestPermissionsResult`.

```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CALL_CODE) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           call();
        } else {
            // Permission denied, show a message to the user
        }
    }
}
```
- Make sure to handle runtime permissions properly to ensure a smooth user experience.


# Sending an SMS from an Android App

## SMS Sending Function
A function to send an SMS:

```java
void smsCode() {
    String msg = getFromId(); // Retrieve the message from the corresponding ID
    String phoneNum = getFromId(); // Retrieve the phone number from the corresponding ID
    SmsManager manager = SmsManager.getDefault();
    manager.sendTextMessage(phoneNum, null, msg, null, null);
    Toast.makeText(this, "SMS was sent", Toast.LENGTH_SHORT).show();
}
```

## Check and Request SMS Permission
A function to send an SMS after checking for the required permission:

```java
void sendSMS(View view) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
        smsCode();
    } else {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_CODE);
    }
}
```

### Notes:
- Replace `getFromId()` with the appropriate method to retrieve the message and phone number from your UI elements.
- Ensure you handle the permission request result in your activity by overriding `onRequestPermissionsResult`.

```java
final int REQUEST_SMS_CODE = 2;

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == REQUEST_SMS_CODE) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, send the SMS
            smsCode();
        } else {
            // Permission denied, show a message to the user
            Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
        }
    }
}
```

- Make sure to handle runtime permissions properly to ensure a smooth user experience.
- Add the following permission in your `AndroidManifest.xml` file:

```xml
<uses-permission android:name="android.permission.SEND_SMS"/>
```

Here is your provided notes formatted into proper Markdown:

```markdown
# Accessing Gallery and Camera in an Android App

## Opening Gallery to Select an Image
To open the gallery and select an image:

```java
Intent intent = new Intent();
intent.setType("image/*");  // For opening the gallery
intent.setAction(Intent.ACTION_GET_CONTENT);

startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_CODE);
```

### Handling the Result
Override the `onActivityResult` method to handle the selected image:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK) {
        Uri uri = data.getData();
        if (uri != null) {
            imageView.setImageURI(uri);
        }
    }
}
```

### Notes:
- Replace `REQUEST_GALLERY_CODE` with your actual request code for the gallery.
- Replace `imageView` with the actual ID of your `ImageView`.

## Example Code with Proper Structure

### Declaring Constants
Declare your request codes at the beginning of your activity:

```java
private static final int REQUEST_GALLERY_CODE = 1;
```



### Opening Gallery
To open the gallery and select an image:

```java
void openGallery() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_CODE);
}
```

### Handling the Result
Override the `onActivityResult` method to handle the selected image:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK) {
        Uri uri = data.getData();
        if (uri != null) {
            ImageView imageView = findViewById(R.id.imageView); // Replace with your ImageView ID
            imageView.setImageURI(uri);
        }
    }
}
```

### Notes:
- Ensure your `ImageView` has the correct ID.
- Replace `"imageView"` with the actual ID of your `ImageView` in your layout file.
