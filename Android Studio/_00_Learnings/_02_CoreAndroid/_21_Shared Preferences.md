## Shared Preferences

1. **Definition**: A way to store or retrieve a small amount of data.
2. **Data Types**: Can store only primitive types (int, float, boolean, and String).
3. **Storage Format**: Data is stored in the form of key-value pairs.

### Uses of Shared Preferences
- User settings
- Keeping user logged into the application
- Session management, etc.

### Characteristics
- Application-specific data
- Can delete data by:
  - Uninstalling the application
  - Manual code (e.g., logout)
  - Mobile settings
- Provides an API (classes) to read, write, or manage its data.

### Initialization
```java
SharedPreferences pref = getApplicationContext().getSharedPreferences("pref-file-name", 0);
SharedPreferences.Editor editor = pref.edit();
```

### Storing Data
```java
editor.putString(key, value);
editor.commit();
```

### Deleting All Data
```java
editor.clear();
editor.commit();
```

### Deleting Selected Data
```java
editor.remove(key);
editor.commit();
```

### Retrieving Data
```java
String value = pref.getString(key, null);
```

### Example: SessionManager Class
```java
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private static final String PREF_FILE_NAME = "shopping";
    private static final int PRIVATE_MODE = 0;

    private static final String KEY_NAME = "key_session_name";
    private static final String KEY_EMAIL = "key_session_email";
    private static final String KEY_PHNO = "key_session_phno";
    private static final String KEY_IF_LOGGED_IN = "key_logged_in";

    public SessionManager(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(PREF_FILE_NAME, PRIVATE_MODE);
        editor = sp.edit();
    }

    public boolean checkSession() {
        return sp.contains(KEY_IF_LOGGED_IN);
    }

    public String getSessionDetails(String key) {
        return sp.getString(key, null);
    }

    public void createSession(String name, String email, String phno) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHNO, phno);
        editor.putBoolean(KEY_IF_LOGGED_IN, true);
        editor.commit();
    }
}
```