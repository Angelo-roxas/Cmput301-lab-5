package com.example.aroxas_emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - The primary interface for the EmotiLog application.
 * 
 * This class provides the main screen where users can select from 9 different
 * emoticon buttons to log their current emotional state. Each button press
 * records the selected emotion with a timestamp. The activity also provides
 * navigation to view logs and summary screens.
 * 
 * Design Rationale:
 * - Uses a grid layout for emoticon buttons to maximize screen space efficiency
 * - Implements immediate feedback via Toast messages for user interaction
 * - Separates concerns by delegating data storage to LogStorage class
 * - Provides clear navigation paths to different app sections
 *
 */
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeEmoticonButtons();
        initializeActionButtons();
    }
    

    private void initializeEmoticonButtons() {
        // First row buttons
        setupEmoticonButton(R.id.btnHappy, "Happy");
        setupEmoticonButton(R.id.btnSad, "Sad");
        setupEmoticonButton(R.id.btnAngry, "Angry");
        
        // Second row buttons
        setupEmoticonButton(R.id.btnExcited, "Excited");
        setupEmoticonButton(R.id.btnCrying, "Crying");
        setupEmoticonButton(R.id.btnDead, "Dead");
        
        // Third row buttons
        setupEmoticonButton(R.id.btnLoved, "Loved");
        setupEmoticonButton(R.id.btnTired, "Tired");
        setupEmoticonButton(R.id.btnSick, "Sick");
    }
    

    private void setupEmoticonButton(int buttonId, String emotion) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            LogStorage.addLog(emotion);
            showEmotionLoggedToast(emotion);
        });
    }
    

    private void initializeActionButtons() {
        Button viewLogsBtn = findViewById(R.id.btnViewLogs);
        Button summaryBtn = findViewById(R.id.btnSummary);
        
        viewLogsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogsActivity.class);
            startActivity(intent);
        });
        
        summaryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
        });
    }
    

    private void showEmotionLoggedToast(String emotion) {
        String message = emotion + " logged!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
