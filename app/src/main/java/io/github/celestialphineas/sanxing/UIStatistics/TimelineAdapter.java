package io.github.celestialphineas.sanxing.UIStatistics;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-13.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private LayoutInflater inflater;
    List<TimelineActivity.TimelineItem> items;
    DateFormat dateFormat;
    DateFormat timeFormat;

    public TimelineAdapter(List<TimelineActivity.TimelineItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    static class TimelineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeline_item_time)
        AppCompatTextView timeText;
        @BindView(R.id.timeline_item_content)
        AppCompatTextView itemContent;
        @BindView(R.id.timeline_item_indicator)
        CardView indicator;
        @BindView(R.id.upper_line)
        View upperLine;
        @BindView(R.id.lower_line)
        View lowerLine;

        TimelineViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.timeline_list_template, parent, false);
        Context context = parent.getContext();
        dateFormat = android.text.format.DateFormat.getDateFormat(context);
        timeFormat = android.text.format.DateFormat.getTimeFormat(context);
        return new TimelineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        if (getItemCount() != 0 && items.get(position) != null) {
            TimelineActivity.TimelineItem item = items.get(position);
            String timeString = dateFormat.format(item.calendar.getTime()) + " "
                    + timeFormat.format(item.calendar.getTime());
            holder.timeText.setText(timeString);
            if (item.description != null) holder.itemContent.setText(item.description);
            if (position == 0)
                holder.upperLine.setVisibility(View.INVISIBLE);
            if (position < 4 && position == getItemCount() - 1)//设置左边的线部分不可见
                holder.lowerLine.setVisibility(View.INVISIBLE);
            holder.indicator.setCardBackgroundColor(item.getColor());
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
