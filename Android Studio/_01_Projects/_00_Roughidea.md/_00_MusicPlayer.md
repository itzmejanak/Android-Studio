
### For playing music

```java
String aPath = "android.resource://" + getPackageName() + "/raw/android_11";

MediaPlayer mp = new MediaPlayer();
mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

Uri audioURI = Uri.parse(aPath);
try {
    mp.setDataSource(this, audioURI);
    mp.prepare();
} catch (IOException e) {
    e.printStackTrace();
}

// Start the playback
mp.start();

// Pause the playback
mp.pause();

// Stop the playback
mp.stop();

// Seek to a specific position (in milliseconds)
int position = 0; // for example, 0ms (0 seconds)
mp.seekTo(position);
```

// use dexter for permission managing

```java
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlay, btnPause, btnStop;
    private ArrayList<String> audioFileList;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Populate the audio file list (replace this with your actual list)
        audioFileList = new ArrayList<>();
        audioFileList.add(Environment.getExternalStorageDirectory().getPath() + "/sample_audio1.mp3");
        audioFileList.add(Environment.getExternalStorageDirectory().getPath() + "/sample_audio2.mp3");
        audioFileList.add(Environment.getExternalStorageDirectory().getPath() + "/sample_audio3.mp3");

        // Set initial data source
        try {
            mediaPlayer.setDataSource(audioFileList.get(currentIndex));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // Start playback
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // Pause playback
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // Stop playback
                    try {
                        mediaPlayer.prepare(); // Prepare for next playback
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Set OnCompletionListener to play next track when current track finishes
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Check if there are more tracks in the list
                if (currentIndex < audioFileList.size() - 1) {
                    currentIndex++; // Move to the next track
                    try {
                        mediaPlayer.reset(); // Reset MediaPlayer
                        mediaPlayer.setDataSource(audioFileList.get(currentIndex)); // Set data source for next track
                        mediaPlayer.prepare(); // Prepare MediaPlayer
                        mediaPlayer.start(); // Start playback of next track
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release resources
        }
    }
}
```

### For next and pervious button:
```java
// Inside onCreate() method after setting click listeners for other buttons
Button btnNext = findViewById(R.id.btnNext);
Button btnPrevious = findViewById(R.id.btnPrevious);

btnNext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        playNextTrack();
    }
});

btnPrevious.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        playPreviousTrack();
    }
});


// Method to play the next track
private void playNextTrack() {
    if (currentIndex < audioFileList.size() - 1) {
        currentIndex++; // Move to the next track
    } else {
        currentIndex = 0; // Start from the first track if reached the end
    }
    playCurrentTrack();
}

// Method to play the previous track
private void playPreviousTrack() {
    if (currentIndex > 0) {
        currentIndex--; // Move to the previous track
    } else {
        currentIndex = audioFileList.size() - 1; // Start from the last track if at the beginning
    }
    playCurrentTrack();
}

// Method to play the current track
private void playCurrentTrack() {
    try {
        mediaPlayer.reset(); // Reset MediaPlayer
        mediaPlayer.setDataSource(audioFileList.get(currentIndex)); // Set data source for current track
        mediaPlayer.prepare(); // Prepare MediaPlayer
        mediaPlayer.start(); // Start playback of current track
    } catch (IOException e) {
        e.printStackTrace();
    }
}

```


### Getting all mp3 and using mediaplayer

To create an ArrayList of all music file paths stored in local storage and integrate it with the MediaPlayer functionality we discussed earlier, you can follow these steps:

### Step 1: Retrieve Music File Paths
First, you need to retrieve all the paths of music files stored in local storage. You can use methods like `listFiles()` to get all files and filter out the ones with ".mp3" extension.

Here's an example method to retrieve music file paths:

```java
private ArrayList<String> getAllMusicPaths() {
    ArrayList<String> musicPaths = new ArrayList<>();
    File musicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    if (musicDirectory.exists() && musicDirectory.isDirectory()) {
        File[] files = musicDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp3")) {
                    musicPaths.add(file.getAbsolutePath());
                }
            }
        }
    }
    return musicPaths;
}
```

### Step 2: Integrate with MediaPlayer
Once you have the ArrayList of music file paths, you can integrate it with the MediaPlayer functionality by setting up the MediaPlayer to play the first track in the list when the activity starts, and providing options to navigate between tracks.

Here's how you can modify the `onCreate()` method in `MainActivity.java` to integrate the music file paths and MediaPlayer:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Retrieve all music file paths
    ArrayList<String> musicPaths = getAllMusicPaths();
    if (musicPaths.isEmpty()) {
        // Handle case when no music files are found
        return;
    }

    // Set up MediaPlayer
    mediaPlayer = new MediaPlayer();
    try {
        mediaPlayer.setDataSource(musicPaths.get(0)); // Set data source to the first track
        mediaPlayer.prepare(); // Prepare MediaPlayer
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Set click listeners for playback controls
    btnPlay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start(); // Start playback
            }
        }
    });

    btnPause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause(); // Pause playback
            }
        }
    });

    btnStop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop(); // Stop playback
                try {
                    mediaPlayer.prepare(); // Prepare for next playback
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    // Set up next and previous buttons
    btnNext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playNextTrack(musicPaths);
        }
    });

    btnPrevious.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playPreviousTrack(musicPaths);
        }
    });
}

// Method to play the next track
private void playNextTrack(ArrayList<String> musicPaths) {
    if (currentIndex < musicPaths.size() - 1) {
        currentIndex++; // Move to the next track
    } else {
        currentIndex = 0; // Start from the first track if reached the end
    }
    playCurrentTrack(musicPaths);
}

// Method to play the previous track
private void playPreviousTrack(ArrayList<String> musicPaths) {
    if (currentIndex > 0) {
        currentIndex--; // Move to the previous track
    } else {
        currentIndex = musicPaths.size() - 1; // Start from the last track if at the beginning
    }
    playCurrentTrack(musicPaths);
}

// Method to play the current track
private void playCurrentTrack(ArrayList<String> musicPaths) {
    try {
        mediaPlayer.reset(); // Reset MediaPlayer
        mediaPlayer.setDataSource(musicPaths.get(currentIndex)); // Set data source for current track
        mediaPlayer.prepare(); // Prepare MediaPlayer
        mediaPlayer.start(); // Start playback of current track
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### Step 3: Run the App
Run the app to test the integration of the ArrayList of music file paths with MediaPlayer functionality, including playback controls and navigation between tracks.

With these modifications, your music player app will now retrieve all music file paths from local storage and use them to set up MediaPlayer for playback. Users can navigate between tracks using the next and previous buttons.