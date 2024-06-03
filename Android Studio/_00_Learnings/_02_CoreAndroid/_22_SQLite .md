## SQLite Database

- **Definition**: An open-source relational database.
- **Availability**: Inbuilt in every Android smartphone device.
- **API**: Android provides an API to interact with SQLite Database.

### Components

1. **SQLiteOpenHelper**
   - Used to create the database and manage versions.
   - Methods:
     - `onCreate()`
     - `onUpgrade()`

2. **SQLiteDatabase**
   - Methods:
     - `insert()`
     - `delete()`
     - `update()`
     - `query()`
     - `execSQL(query)`

3. **Cursor**
   - Methods:
     - `moveToFirst()`
     - `moveToNext()`

4. **User Defined Helper Class**
   - Extends `SQLiteOpenHelper` class.
   - Methods:
     - `getWritableDatabase()`
     - `getReadableDatabase()`

### CRUD Operations

- **C** - Create
- **R** - Retrieve
- **U** - Update
- **D** - Delete

### Example: DbHelper Class

```java
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "demo_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "register";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    COLUMN_NAME + " TEXT, " +
                                    COLUMN_EMAIL + " TEXT, " +
                                    COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Create
    public void registerUserHelper(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // Retrieve
    public ArrayList<UserModal> getLoggedinUserDetails(String email) {
        ArrayList<UserModal> al = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM register WHERE email=?";
        
        // Using try-with-resources to ensure resources are closed properly
        try (Cursor cursor = sqLiteDatabase.rawQuery(query, null)) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(1);
                    String emailFromDB = cursor.getString(2);
                    String gender = cursor.getString(3);
                    
                    UserModal user = new UserModal();
                    user.setName(name);
                    user.setEmail(emailFromDB);
                    user.setGender(gender);
                    
                    al.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return al;
    }


    // Update
    public int updateUserEmail(String oldEmail, String newEmail) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, newEmail);
        return sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_EMAIL + "=?", new String[]{oldEmail});
    }

    // Delete
    public int deleteUserByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_EMAIL + "=?", new String[]{email});
    }
}
```

### Usage

- **Initialize Database Helper**:
  ```java
  DbHelper dbHelper = new DbHelper(context);
  ```

- **Create**:
  ```java
  dbHelper.registerUserHelper("John Doe", "john@example.com", "password123");
  ```

- **Update**:
  ```java
  int rowsAffected = dbHelper.updateUserEmail("john@example.com", "john.doe@example.com");
  ```

- **Delete**:
  ```java
  int rowsAffected = dbHelper.deleteUserByEmail("john.doe@example.com");
  ```
```

