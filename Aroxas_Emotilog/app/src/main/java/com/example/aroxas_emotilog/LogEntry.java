package com.example.aroxas_emotilog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * LogEntry - Represents a single emotion log entry with timestamp.
 * 
 * This class encapsulates the data for a single emotion logging event,
 * including the emotion type and the timestamp when it was recorded.
 * It provides methods to access the data and format it for display.
 * 
 * Design Rationale:
 * - Immutable design ensures data integrity once created
 * - Simple data structure focused on core requirements
 * - Includes formatting methods for consistent display across the app
 * - Uses long timestamp for precise time tracking
 */
public class LogEntry {
    private final String emotion;
    private final long timestamp;
    

    public LogEntry(String emotion, long timestamp) {
        this.emotion = emotion;
        this.timestamp = timestamp;
    }
    

    public String getEmotion() { 
        return emotion; 
    }
    

    public long getTimestamp() { 
        return timestamp; 
    }
    

    public String getFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    

    public String getFormattedDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
    

    public boolean isSameDay(long otherTimestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String thisDate = sdf.format(new Date(timestamp));
        String otherDate = sdf.format(new Date(otherTimestamp));
        return thisDate.equals(otherDate);
    }
    
    @Override
    public String toString() {
        return emotion + " at " + getFormattedTime();
    }
}
