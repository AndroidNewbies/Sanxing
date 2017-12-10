package io.github.celestialphineas.sanxing.UIHomeTabFragments;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.SanxingBackend.TaskRepo;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditTaskActivity;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.timer.MyDuration;


/**
 * Created by celestialphineas on 17-10-17.
 * Public class recycler view mAdapter for the task fragment
 */

public class TaskRecyclerAdapter
        extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;
    private Calendar taskCalendar = Calendar.getInstance();

    TaskRecyclerAdapter() { }
    TaskRecyclerAdapter(List<Task> task_list) {
        taskList = task_list;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(List<Task> task_list) {
        this.taskList = task_list;
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_root_view)      CardView cardView;
        @BindView(R.id.card_headline)       TextView taskTitle;
        @BindView(R.id.card_subheading)     TextView taskDueTime;
        @BindView(R.id.card_countdown)      TextView taskCountdown;
        @BindView(R.id.card_description)    TextView taskDescription;
        @BindView(R.id.card_button_edit)    AppCompatButton buttonEdit;
        @BindView(R.id.card_button_delete)  AppCompatButton buttonDelete;
        @BindString(R.string.unit_day)      String unitDay;
        @BindString(R.string.unit_hour)     String unitHour;
        @BindString(R.string.unit_minute)   String unitMinute;
        TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater
                            .from(parent.getContext())
                            .inflate(R.layout.home_card, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        if (taskList.isEmpty()) return;
        holder.taskTitle.setText(taskList.get(position).getTitle());
        // TODO: Set taskCalendar to the due calendar got from the database
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);

        holder.taskDueTime.setText(taskList.get(position).getEndDate());

        String begintime = taskList.get(position).getBeginDate();
        String endtime = taskList.get(position).getEndDate();
        Long dif = MyDuration.durationFromAtoB(begintime,endtime);

        long day=dif/(24*60*60*1000);
        long hour=(dif/(60*60*1000)-day*24);
        long min=((dif/(60*1000))-day*24*60-hour*60);

        Log.w("dif:",dif.toString());
        Log.w("begintime:",begintime);
        Log.w("endtime:",endtime);
        holder.taskCountdown.setText("" + day + holder.unitDay
                + hour + holder.unitHour + min + holder.unitMinute);

        holder.taskDescription.setText(taskList.get(position).getContent());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(holder.cardView);
                if(holder.taskDescription.getVisibility() == View.VISIBLE) {
                    holder.taskDescription.setVisibility(View.GONE);
                } else {
                    holder.taskDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                // TODO: Send the object to edit via intent
                context.startActivity(intent);

            }
        });
        // Button delete behavior
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Task task = taskList.get(position);
                //taskList.remove(task);
                remove(position);
                View.OnClickListener redo = new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        add(task, position);

                        // TODO: Restore the lazily deleted database entry
                    }
                };
                Snackbar.make(view, R.string.snack_one_item_deleted, R.integer.undo_timeout)
                        .setAction(R.string.undo, redo)
                        .show();
                TaskRepo repo = new TaskRepo(context);
                task.setState(0);
                repo.update(task);
                // TODO: Lazy delete a database entry
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0: taskList.size();
    }

    // Remove an entry
    public void remove(int position) {
        try { taskList.remove(position); } catch (Exception e) {
            Log.w("TaskRecyclerAdapter", "remove: " + position + ", " + taskList.size());
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, taskList.size());
    }

    // Add an entry
    public void add(Task task, int position) {
        try { taskList.add(position, task);
            Log.w("test","??");
            Log.w("TaskRecyclerAdapter", "add: " + position + ", " + taskList.size());}
        catch (Exception e) {

        }
        //context.getDatabasePath(Task.TABLE);
//        TaskRepo taskRepo = new TaskRepo(context);
//        taskRepo.insert(new Task("test3"));
//        DatabaseHelper helper = new DatabaseHelper(context);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        database.insert(…);
//        database.close();
        notifyItemInserted(position);
        notifyItemRangeChanged(position, taskList.size());
    }

}
