package io.github.celestialphineas.sanxing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by celestialphineas on 17-10-17.
 * Public class recycler view adapter for the task fragment
 */

public class TaskRecyclerAdapter
        extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {
    TaskRecyclerAdapter() {
        // Empty constructor
    }
    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        TextView cardSubheading;
        TaskViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.task_card_headline);
            cardSubheading = itemView.findViewById(R.id.task_card_subheading);
            cardTitle.setText("Hello world!");
            cardSubheading.setText("Good night, my friend!");
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int postion) {
        // TODO: Bind the view with data
    }

    @Override
    public int getItemCount() {
        // TODO: Implement this, and return how many cards concerned
        return 10;
    }
}
