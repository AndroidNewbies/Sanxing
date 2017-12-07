package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditTaskActivity;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditTimeLeftActivity;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;


public class TimeLeftRecyclerAdapter
        extends RecyclerView.Adapter<TimeLeftRecyclerAdapter.TimeLeftViewHolder> {

    private List<TimeLeft> timeLeftList;
    private Context context;
    private Calendar timeLeftCalendar = Calendar.getInstance();

    TimeLeftRecyclerAdapter() { }

    TimeLeftRecyclerAdapter(List<TimeLeft> time_left_list) {
        timeLeftList = time_left_list;
    }

    public List<TimeLeft> getTimeLeftList() { return timeLeftList; }
    public void setTimeLeftList(List<TimeLeft> time_left_list) {
        timeLeftList = time_left_list;
        notifyDataSetChanged();
    }

    class TimeLeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_headline)       TextView timeLeftTitle;
        @BindView(R.id.card_subheading)     TextView timeLeftDueTime;
        @BindView(R.id.card_countdown)      TextView timeLeftCountDown;
        @BindView(R.id.card_description)    TextView timeLeftDescription;
        @BindView(R.id.card_button_edit)    AppCompatButton buttonEdit;
        @BindView(R.id.card_button_delete)  AppCompatButton buttonDelete;
        TimeLeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public TimeLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);
        context = parent.getContext();
        return new TimeLeftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimeLeftViewHolder holder, final int position) {
        // TODO: Bind the view with data
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String timeString = dateFormat.format(timeLeftCalendar.getTime());
        holder.timeLeftTitle.setText(timeLeftList.get(position).getContent());
        holder.timeLeftDueTime.setText(timeString);

        // TODO: Calculate the time difference and print it here
        holder.timeLeftCountDown.setText("23 Years");
        // TODO: Get the description and print it out here
        holder.timeLeftDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur non mauris lorem. Mauris ac ex nec purus feugiat venenatis. Suspendisse fringilla.");

        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTimeLeftActivity.class);
                // TODO: Send the object to edit via intent
                context.startActivity(intent);
            }
        });
        // Button delete behavior
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimeLeft timeLeft = timeLeftList.get(position);
                remove(position);
                View.OnClickListener redo = new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        add(timeLeft, position);
                        // TODO: Resume the lazily deleted database entry
                    }
                };
                Snackbar.make(view, R.string.snack_one_item_deleted, R.integer.undo_timeout)
                        .setAction(R.string.undo, redo)
                        .show();
                // TODO: Lazy delete a database entry
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return timeLeftList == null ? 0 : timeLeftList.size();
    }

    // Remove an entry
    public void remove(int position) {
        try { timeLeftList.remove(position); } catch (Exception e) {
            Log.e("TaskRecyclerAdapter", "remove: " + position + ", " + timeLeftList.size());
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, timeLeftList.size());
    }

    // Add an entry
    public void add(TimeLeft timeLeft, int position) {
        try { timeLeftList.add(position, timeLeft); } catch (Exception e) {
            Log.e("TaskRecyclerAdapter", "add: " + position + ", " + timeLeftList.size());
        }
        notifyItemInserted(position);
        notifyItemRangeChanged(position, timeLeftList.size());
    }
}
