package com.example.aroxas_emotilog;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


/**
 * LogsActivity - Displays a chronological list of all logged emotions.
 * 
 * This activity provides a detailed view of all emotion logs in chronological order,
 * showing the emotion type and timestamp for each entry. It serves as a detailed
 * log viewer for users to review their emotional history.
 * 
 * Design Rationale:
 * - Uses ListView for efficient display of potentially large lists
 * - Shows data in reverse chronological order (newest first) for better UX
 * - Provides clear visual separation between different log entries
 * - Includes summary information at the top of the screen
 * - Handles empty state gracefully with informative message
 *
 */
public class LogsActivity extends AppCompatActivity {
    
    private ListView logsListView;
    private TextView summaryTextView;
    private LogAdapter logAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        
        initializeViews();
        setupLogsList();
        updateSummary();
    }
    

    private void initializeViews() {
        logsListView = findViewById(R.id.logsListView);
        summaryTextView = findViewById(R.id.summaryTextView);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(LogsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: close this activity to avoid stack buildup
            }
        });
    }




    private void setupLogsList() {
        List<LogEntry> logs = LogStorage.getLogsSortedByTime();
        logAdapter = new LogAdapter(this, logs);
        logsListView.setAdapter(logAdapter);
    }
    

    private void updateSummary() {
        int totalLogs = LogStorage.getTotalLogCount();
        String mostFrequent = LogStorage.getMostFrequentEmotion();
        
        StringBuilder summary = new StringBuilder();
        summary.append("Total Logs: ").append(totalLogs);
        
        if (mostFrequent != null) {
            summary.append("\nMost Frequent: ").append(mostFrequent);
        }
        
        if (totalLogs == 0) {
            summary.append("\n\nNo emotions logged yet.\nStart logging your feelings!");
        }
        
        summaryTextView.setText(summary.toString());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the data when returning to this activity
        if (logAdapter != null) {
            List<LogEntry> logs = LogStorage.getLogsSortedByTime();
            logAdapter.updateLogs(logs);
            updateSummary();
        }
    }
}
