### SearchView

```xml
<SearchView
    android:id="@+id/searchView" <!-- Unique ID for the SearchView -->
    android:layout_width="match_parent" <!-- Width of the SearchView matches the parent width -->
    android:layout_height="wrap_content" <!-- Height of the SearchView wraps its content -->
    android:queryHint="Search here" <!-- Hint text displayed in the SearchView when it is empty -->
    android:iconifiedByDefault="true" <!-- Whether the SearchView is iconified by default -->
    android:submitButtonEnabled="false" <!-- Whether the submit button is enabled in the SearchView -->
    android:inputType="text" <!-- Type of input expected in the SearchView --> />
```

### Usages

```java

// Method to initialize widgets and set listeners
private void initializeWidgets() {
    // Initialize the SearchView
    searchView = findViewById(R.id.searchView);

    // Set up query text listener
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // Handle the query submission
            // Perform search operation here
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.getFilter().filter(newText)
            return false;
        }
    });

    // Optional: Handle focus change
    searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
        // Handle focus change
    });
}
```

### WebView

```xml
<WebView
    android:id="@+id/webView" <!-- Unique ID for the WebView -->
    android:layout_width="match_parent" <!-- Width of the WebView matches the parent width -->
    android:layout_height="match_parent" <!-- Height of the WebView matches the parent height -->
    android:layout_gravity="center" <!-- Gravity of the WebView within its container -->
    android:scrollbars="vertical" <!-- Whether to display vertical scrollbars -->
    android:scrollbarStyle="insideOverlay" <!-- Style of the scrollbars -->
    android:scrollbarThumbVertical="@android:color/darker_gray" <!-- Color of the vertical scrollbar thumb -->
    android:scrollbarTrackVertical="@android:color/transparent" <!-- Color of the vertical scrollbar track --> />
```

### Usages

```java
import android.app.ProgressDialog;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call the method to initialize WebView with custom settings and handle errors
        initializeWebView();
    }

    // Method to initialize WebView with custom settings and handle errors
    private void initializeWebView() {
        // Find the WebView in the layout by its ID
        webView = findViewById(R.id.webview_one);

        // Set up a WebViewClient to handle WebView events
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            // Show a progress dialog when the page starts loading
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
            }

            // Dismiss the progress dialog when the page finishes loading
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }

            // Handle errors with Toast messages
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // Show a toast message for the error
                Toast.makeText(MainActivity.this, "Error: " + description, Toast.LENGTH_SHORT).show();
            }
        });

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.OFF);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setSupportMultipleWindows(true);

        // Load a specific URL in the WebView
        webView.loadUrl("https://www.janakdevkota.com.np");
    }
}
```