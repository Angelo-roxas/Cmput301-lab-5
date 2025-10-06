package com.example.aroxas_emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * LogAdapter - Custom adapter for displaying LogEntry objects in a ListView.
 * 
 * This adapter handles the display of emotion log entries in a ListView,
 * providing a custom layout for each log entry that shows the emotion
 * and formatted timestamp.
 * 
 * Design Rationale:
 * - Extends BaseAdapter for full control over item display
 * - Uses ViewHolder pattern for efficient ListView performance
 * - Provides methods to update data dynamically
 * - Handles empty state gracefully
 */
public class LogAdapter extends BaseAdapter {
    private Context context;
    private List<LogEntry> logs;
    private LayoutInflater inflater;
    

    public LogAdapter(Context context, List<LogEntry> logs) {
        this.context = context;
        this.logs = logs;
        this.inflater = LayoutInflater.from(context);
    }
    
    @Override
    public int getCount() {
        return logs.size();
    }
    
    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.log_item, parent, false);
            holder = new ViewHolder();
            holder.emotionText = convertView.findViewById(R.id.emotionText);
            holder.timestampText = convertView.findViewById(R.id.timestampText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        LogEntry log = logs.get(position);
        holder.emotionText.setText(log.getEmotion());
        holder.timestampText.setText(log.getFormattedDateTime());
        
        return convertView;
    }
    

    public void updateLogs(List<LogEntry> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }
    

    private static class ViewHolder {
        TextView emotionText;
        TextView timestampText;
    }
}
