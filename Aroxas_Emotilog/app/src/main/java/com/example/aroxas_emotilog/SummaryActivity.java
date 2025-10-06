package com.example.aroxas_emotilog;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


/**
 * SummaryActivity - Displays comprehensive emotion statistics and summaries.
 * 
 * This activity provides detailed analytics about logged emotions, including
 * frequency counts, daily summaries, and overall statistics. It helps users
 * understand their emotional patterns and trends over time.
 * 
 * Design Rationale:
 * - Separates different types of summaries into distinct sections
 * - Uses ListView for efficient display of emotion frequency data
 * - Provides both overall and daily-specific statistics
 * - Handles empty state with informative messages
 * - Updates data dynamically when returning to the activity
 *
 */
public class SummaryActivity extends AppCompatActivity {
    
    private TextView overallStatsText;
    private TextView dailyStatsText;
    private ListView frequencyListView;
    private FrequencyAdapter frequencyAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        
        initializeViews();
        updateOverallStatistics();
        updateDailyStatistics();
        updateFrequencyList();
    }
    

    private void initializeViews() {
        overallStatsText = findViewById(R.id.overallStatsText);
        dailyStatsText = findViewById(R.id.dailyStatsText);
        frequencyListView = findViewById(R.id.frequencyListView);

        // Handle back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: close SummaryActivity
            }
        });
    }
    

    private void updateOverallStatistics() {
        int totalLogs = LogStorage.getTotalLogCount();
        String mostFrequent = LogStorage.getMostFrequentEmotion();
        Map<String, Integer> emotionCounts = LogStorage.getEmotionCounts();
        
        StringBuilder sb = new StringBuilder();
        sb.append("📊 OVERALL STATISTICS\n\n");
        sb.append("Total Emotions Logged: ").append(totalLogs).append("\n");
        
        if (mostFrequent != null) {
            int mostFrequentCount = emotionCounts.get(mostFrequent);
            sb.append("Most Frequent: ").append(mostFrequent)
              .append(" (").append(mostFrequentCount).append(" times)\n");
        }
        
        if (totalLogs > 0) {
            sb.append("\nEmotion Breakdown:\n");
            for (Map.Entry<String, Integer> entry : emotionCounts.entrySet()) {
                String emotion = entry.getKey();
                int count = entry.getValue();
                double percentage = (count * 100.0) / totalLogs;
                sb.append("• ").append(emotion).append(": ").append(count)
                  .append(" (").append(String.format("%.1f", percentage)).append("%)\n");
            }
        } else {
            sb.append("\nNo emotions logged yet.\nStart logging your feelings!");
        }
        
        overallStatsText.setText(sb.toString());
    }
    

    private void updateDailyStatistics() {
        long currentTime = System.currentTimeMillis();
        List<LogEntry> todayLogs = LogStorage.getLogsForDay(currentTime);
        Map<String, Integer> todayCounts = LogStorage.getEmotionCountsForDay(currentTime);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(new Date(currentTime));
        
        StringBuilder sb = new StringBuilder();
        sb.append("📅 TODAY'S SUMMARY (").append(todayDate).append(")\n\n");
        sb.append("Total Logs Today: ").append(todayLogs.size()).append("\n");
        
        if (!todayLogs.isEmpty()) {
            sb.append("\nToday's Emotions:\n");
            for (Map.Entry<String, Integer> entry : todayCounts.entrySet()) {
                String emotion = entry.getKey();
                int count = entry.getValue();
                sb.append("• ").append(emotion).append(": ").append(count).append(" times\n");
            }
            
            // Show recent logs
            sb.append("\nRecent Activity:\n");
            List<LogEntry> recentLogs = new ArrayList<>(todayLogs);
            recentLogs.sort((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
            
            int maxRecent = Math.min(5, recentLogs.size());
            for (int i = 0; i < maxRecent; i++) {
                LogEntry entry = recentLogs.get(i);
                sb.append("• ").append(entry.getEmotion())
                  .append(" at ").append(entry.getFormattedTime()).append("\n");
            }
        } else {
            sb.append("\nNo emotions logged today yet.\nLog your first emotion!");
        }
        
        dailyStatsText.setText(sb.toString());
    }
    

    private void updateFrequencyList() {
        Map<String, Integer> emotionCounts = LogStorage.getEmotionCounts();
        List<Map.Entry<String, Integer>> sortedCounts = new ArrayList<>(emotionCounts.entrySet());
        
        // Sort by count (descending)
        sortedCounts.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        
        frequencyAdapter = new FrequencyAdapter(this, sortedCounts);
        frequencyListView.setAdapter(frequencyAdapter);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh all data when returning to this activity
        updateOverallStatistics();
        updateDailyStatistics();
        updateFrequencyList();
    }
}
