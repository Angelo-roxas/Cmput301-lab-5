package com.example.aroxas_emotilog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * FrequencyAdapter - Custom adapter for displaying emotion frequency data.
 * This adapter handles the display of emotion frequency counts in a ListView,
 * showing each emotion with its count and a visual progress bar representation.
 * Design Rationale:
 * - Extends BaseAdapter for full control over item display
 * - Uses ViewHolder pattern for efficient ListView performance
 * - Provides visual representation of frequency with progress bars
 * - Handles dynamic updates of frequency data
 *
 */
public class FrequencyAdapter extends BaseAdapter {
    private List<Map.Entry<String, Integer>> frequencyData;
    private final LayoutInflater inflater;
    private int maxCount;
    

    public FrequencyAdapter(Context context, List<Map.Entry<String, Integer>> frequencyData) {
        this.frequencyData = frequencyData;
        this.inflater = LayoutInflater.from(context);
        
        // Find the maximum count for progress bar scaling
        this.maxCount = 0;
        for (Map.Entry<String, Integer> entry : frequencyData) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }
    }
    
    @Override
    public int getCount() {
        return frequencyData.size();
    }
    
    @Override
    public Object getItem(int position) {
        return frequencyData.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.frequency_item, parent, false);
            holder = new ViewHolder();
            holder.emotionText = convertView.findViewById(R.id.emotionText);
            holder.countText = convertView.findViewById(R.id.countText);
            holder.progressBar = convertView.findViewById(R.id.progressBar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Map.Entry<String, Integer> entry = frequencyData.get(position);
        String emotion = entry.getKey();
        int count = entry.getValue();
        
        holder.emotionText.setText(emotion);
        holder.countText.setText(String.valueOf(count));
        
        // Set progress bar (scale to max count)
        if (maxCount > 0) {
            int progress = (int) ((count * 100.0) / maxCount);
            holder.progressBar.setProgress(progress);
        } else {
            holder.progressBar.setProgress(0);
        }
        
        return convertView;
    }
    
    // Updates the frequency data and notifies the adapter of the change.

    public void updateFrequencyData(List<Map.Entry<String, Integer>> newFrequencyData) {
        this.frequencyData = newFrequencyData;
        
        // Recalculate max count
        this.maxCount = 0;
        for (Map.Entry<String, Integer> entry : frequencyData) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
            }
        }
        
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView emotionText;
        TextView countText;
        ProgressBar progressBar;
    }
}
