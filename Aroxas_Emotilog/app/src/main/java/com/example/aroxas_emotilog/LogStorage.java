package com.example.aroxas_emotilog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LogStorage - Manages the storage and retrieval of emotion log entries.
 * 
 * This class provides a centralized data storage solution for the EmotiLog application.
 * It maintains a list of all logged emotions with their timestamps and provides
 * various methods to query and analyze the stored data.
 * 
 * Design Rationale:
 * - Uses static methods for global access across the application
 * - Maintains data in memory for simplicity (could be extended to use database)
 * - Provides defensive copying to prevent external modification of internal data
 * - Includes utility methods for data analysis and filtering
 * - Thread-safe operations for concurrent access
 * 
 * Outstanding Issues:
 * - Data is not persisted between app sessions (stored in memory only)
 * - No data validation for emotion strings
 * - No maximum storage limit implemented
 *
 */
public class LogStorage {
    private static final List<LogEntry> logs = new ArrayList<>();
    

    public static synchronized void addLog(String emotion) {
        logs.add(new LogEntry(emotion, System.currentTimeMillis()));
    }
    

    public static synchronized List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }
    

    public static synchronized List<LogEntry> getLogsSortedByTime() {
        List<LogEntry> sortedLogs = new ArrayList<>(logs);
        sortedLogs.sort((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
        return sortedLogs;
    }
    

    public static synchronized List<LogEntry> getLogsForEmotion(String emotion) {
        List<LogEntry> filteredLogs = new ArrayList<>();
        for (LogEntry entry : logs) {
            if (entry.getEmotion().equals(emotion)) {
                filteredLogs.add(entry);
            }
        }
        return filteredLogs;
    }
    

    public static synchronized List<LogEntry> getLogsForDay(long timestamp) {
        List<LogEntry> dayLogs = new ArrayList<>();
        for (LogEntry entry : logs) {
            if (entry.isSameDay(timestamp)) {
                dayLogs.add(entry);
            }
        }
        return dayLogs;
    }
    

    public static synchronized Map<String, Integer> getEmotionCounts() {
        Map<String, Integer> counts = new HashMap<>();
        for (LogEntry entry : logs) {
            String emotion = entry.getEmotion();
            counts.put(emotion, counts.getOrDefault(emotion, 0) + 1);
        }
        return counts;
    }
    

    public static synchronized Map<String, Integer> getEmotionCountsForDay(long timestamp) {
        Map<String, Integer> counts = new HashMap<>();
        for (LogEntry entry : logs) {
            if (entry.isSameDay(timestamp)) {
                String emotion = entry.getEmotion();
                counts.put(emotion, counts.getOrDefault(emotion, 0) + 1);
            }
        }
        return counts;
    }
    

    public static synchronized int getTotalLogCount() {
        return logs.size();
    }
    

    public static synchronized int getLogCountForDay(long timestamp) {
        int count = 0;
        for (LogEntry entry : logs) {
            if (entry.isSameDay(timestamp)) {
                count++;
            }
        }
        return count;
    }


    public static synchronized String getMostFrequentEmotion() {
        if (logs.isEmpty()) {
            return null;
        }
        
        Map<String, Integer> counts = getEmotionCounts();
        String mostFrequent = null;
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        
        return mostFrequent;
    }
}
