package com.example.toma.androidsapt10.UI;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.toma.androidsapt10.Entities.Task;
import com.example.toma.androidsapt10.R;

import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task> {

    public TaskListAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_task, parent, false);
        }
        // Lookup view for data population
        TextView text_description = (TextView) convertView.findViewById(R.id.description);

        // Populate the data into the template view using the data object
        text_description.setText(task.getDescriere());

        // Return the completed view to render on screen
        return convertView;
    }
}